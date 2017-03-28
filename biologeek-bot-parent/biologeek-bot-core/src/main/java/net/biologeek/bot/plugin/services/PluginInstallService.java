package net.biologeek.bot.plugin.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;

import javax.validation.ValidationException;

import org.hibernate.validator.cfg.defs.NullDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.biologeek.bot.plugin.beans.Jar;
import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.beans.batch.PluginBatch;
import net.biologeek.bot.plugin.beans.install.AbstractPluginInstaller;
import net.biologeek.bot.plugin.exceptions.InstallException;
import net.biologeek.bot.plugin.exceptions.UninstallException;
import net.biologeek.bot.plugin.install.PluginSpecificInstallerDelegate;
import sun.misc.URLClassPath;

/**
 * An abstract service that can be implemented in various ways. A default
 * implementation for Spring Batch based batches is provided in
 * {@link SpringBatchPluginBatch}. <br>
 * <br>
 * This service handles installation of a plugin based on the type of batch. It
 * can be based on a set of interface, to define a set of jobs, steps, ...<br>
 * <br>
 * Should not be mixed up with {@link PluginSpecificInstallerDelegate} which
 * handles specific actions to perform for each batch.
 * 
 *
 */
@Service
public abstract class PluginInstallService implements Mergeable<AbstractPluginInstaller>{

	protected ServiceLoader<PluginBatch> pluginBatchScanner;
	@Autowired
	protected PluginService pluginService;
	@Autowired
	protected PluginJarDelegate jarService;
	protected String propertiesFile;
	protected String adminPanelHtmlTemplate;
	@Autowired
	PluginSpecificInstallerDelegate installer;

	Logger logger;
	private BatchService batchService;

	public PluginInstallService() {
		pluginBatchScanner = ServiceLoader.load(PluginBatch.class);
		logger = Logger.getLogger(PluginInstallService.class.getName());
	}

	/**
	 * Lists all plugins installed or not. Default behaviour is to scan
	 * classpath for implementations
	 * 
	 * @return
	 */
	public List<PluginBatch> listAllPlugins() {
		List<PluginBatch> result = new ArrayList<PluginBatch>();
		for (PluginBatch b : pluginBatchScanner) {
			result.add(b);
		}
		logger.info("listAllPlugins returned " + result.size() + " plugins");
		return result;
	}

	/**
	 * Lists all plugins scanned but not registered in DB
	 * 
	 * @return a list of plugins
	 */
	public List<PluginBatch> listNotYetInstalledPlugins() {
		List<PluginBatch> result = new ArrayList<PluginBatch>();
		for (PluginBatch b : pluginBatchScanner) {
			if (b.getPlugin() == null) {
				PluginBean dataSourcePlugin = pluginService.getPluginByPluginName(b.getPlugin().getName());
				if (dataSourcePlugin != null) {
					b.setPlugin(dataSourcePlugin);
					result.add(b);
				}
			}
		}
		return result;
	}

	public PluginBean saveOrUpdatePluginBean(PluginBean bean) {
		if (bean.getPluginId() > 0)
			return pluginService.update(bean);
		else
			return pluginService.save(bean);
	}

	/**
	 * Configures a plugin and its components
	 * 
	 * @param updated
	 * @return
	 * @throws Exception
	 */
	public PluginBean configure(PluginBean updated) {
		PluginBean base = null;

		if (updated.getPluginId() > 0) {
			base = pluginService.getPluginById(updated.getPluginId());

			base = pluginService.merge(base, updated);
		}
		return base;
	}

	/**
	 * Updates installer part of the plugin. Implement here validation of data
	 * 
	 * @param base
	 * @param updated
	 * @return
	 */
	@Override
	public AbstractPluginInstaller merge(AbstractPluginInstaller base, AbstractPluginInstaller updated) throws ValidationException {
		base.setBatchPeriod(updated.getBatchPeriod());
		base.setInstallerService(updated.getInstallerService());
		base.setJarPath(validateJar(updated.getJarPath()));
		return base;
	}

	private String validateJar(String jarPath) {
		Jar jar = new Jar(jarPath);

		if (jar.exists() && jar.isFile() && jar.isJar())
			return jarPath;
		else
			throw new ValidationException();
	}

	/**
	 * Installs a plugin by saving the {@link PluginBean} and
	 * {@link PluginBatch} objects.
	 * 
	 * Passed PluginBean object must be correctly filled. Install specifics
	 * (bean.installer) can be injected or passed through argument bean
	 * 
	 * Calls {@link PluginInstallService#beforeSaveBatch()} prior to any action
	 * Then plugin is saved and afterSaveBatch method is called
	 * 
	 * @param batch
	 * @return
	 * @throws InstallException
	 */
	public PluginBean install(PluginBean bean) throws InstallException {
		logger.info("Starting batch install");
		jarService.addJarToClasspath(new File(bean.getJarFile()));
		if (installer == null) {
			try {
				Object tempInstance = Class.forName(bean.getInstaller().getInstallerService()).newInstance();
				if (tempInstance instanceof PluginSpecificInstallerDelegate)
					installer = (PluginSpecificInstallerDelegate) tempInstance;
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				logger.severe("Error duriong Installer service instantiation !");
				e.printStackTrace();
			}
		}
		logger.info("Before save batch");
		bean = installer.beforeSaveBatch(bean);
		pluginService.save(bean);
		logger.info("After save batch");
		installer.afterSaveBatch();
		return bean;
	}

	/**
	 * Builds a {@link PluginBean} object with data and classes found in jarFile
	 * and installs the newly built plugin.
	 * 
	 * Description cannot be filled for the moment and title is set to provided
	 * Jar file name
	 * 
	 * @param jarFilePath
	 *            the absolute path of jar plugin to install
	 * @return
	 * @throws InstallException
	 */
	public PluginBean install(String jarFilePath) throws InstallException {

		PluginBean bean = new PluginBean();
		Jar jarFile = new Jar(jarFilePath);
		if (jarFile.exists() && !jarFile.isDirectory() && jarFile.getName().endsWith("jar")) {
			bean.setJarFile(jarFilePath);
			try {
				bean.setBatch((PluginBatch) jarService.scanJarFileForImplementation(jarFile, PluginBatch.class));
				bean.setInstaller((AbstractPluginInstaller) jarService.scanJarFileForImplementation(jarFile,
						AbstractPluginInstaller.class));
				bean.setDescription(null);
				bean.setName(jarFile.getName());
				this.install(bean);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				throw new InstallException(e.getMessage());
			}
		}
		return install(bean);
	}

	/**
	 * This method will be called when uninstalling Plugin. It orchestrates its
	 * uninstall and manages different steps (removing temp folder, database
	 * entries, ...)
	 * 
	 * @param options
	 * @throws UninstallException
	 */
	public void uninstall(String jarFile) throws UninstallException {

		File file = new File(jarFile);
		try {
			this.removeJarFromClasspath(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new UninstallException("File not found");
		}
	}

	/**
	 * Removes jar file from classpath
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 * @throws UninstallException
	 */
	private void removeJarFromClasspath(File file) throws FileNotFoundException, UninstallException {
		if (file.exists()) {
			if (file.isDirectory()) {
				file.delete();

				URL url;
				try {
					url = file.toURI().toURL();

					URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
					Field ucpField = URLClassLoader.class.getDeclaredField("ucp");
					ucpField.setAccessible(true);
					URLClassPath ucp = (URLClassPath) ucpField.get(urlClassLoader);
					Field urlsField = URLClassPath.class.getDeclaredField("urls");
					urlsField.setAccessible(true);
					((Stack) urlsField.get(ucp)).remove(url);
				} catch (MalformedURLException | NoSuchFieldException | SecurityException | IllegalArgumentException
						| IllegalAccessException e) {
					e.printStackTrace();
					throw new UninstallException(e.getMessage());
				}

			}
		} else {
			throw new FileNotFoundException();
		}

	}

	public ServiceLoader<PluginBatch> getPluginBatchScanner() {
		return pluginBatchScanner;
	}

	public void setPluginBatchScanner(ServiceLoader<PluginBatch> pluginBatchScanner) {
		this.pluginBatchScanner = pluginBatchScanner;
	}

	public PluginService getPluginService() {
		return pluginService;
	}

	public void setPluginService(PluginService pluginService) {
		this.pluginService = pluginService;
	}

	public PluginJarDelegate getJarService() {
		return jarService;
	}

	public void setJarService(PluginJarDelegate jarService) {
		this.jarService = jarService;
	}

	public String getPropertiesFile() {
		return propertiesFile;
	}

	public void setPropertiesFile(String propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	public String getAdminPanelHtmlTemplate() {
		return adminPanelHtmlTemplate;
	}

	public void setAdminPanelHtmlTemplate(String adminPanelHtmlTemplate) {
		this.adminPanelHtmlTemplate = adminPanelHtmlTemplate;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public PluginSpecificInstallerDelegate getInstaller() {
		return installer;
	}

	public void setInstaller(PluginSpecificInstallerDelegate installer) {
		this.installer = installer;
	}
}
