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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.beans.batch.PluginBatch;
import net.biologeek.bot.plugin.exceptions.InstallException;
import net.biologeek.bot.plugin.exceptions.UninstallException;
import net.biologeek.bot.plugin.install.AbstractPluginInstaller;
import sun.misc.URLClassPath;

@Service
public abstract class PluginInstallService {

	protected ServiceLoader<PluginBatch> pluginBatchScanner;
	@Autowired
	protected PluginService pluginService;
	@Autowired
	protected PluginJarDelegate jarService;
	protected String propertiesFile;
	protected String adminPanelHtmlTemplate;

	Logger logger;

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
	 * Installs a plugin by saving the {@link PluginBean} and
	 * {@link PluginBatch} objects.
	 * 
	 * Passed PluginBean object must be correctly filled.
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
		AbstractPluginInstaller installer = bean.getInstaller();

		logger.info("Before save batch");
		bean = installer.beforeSaveBatch(bean);

		pluginService.save(bean);

		logger.info("After save batch");
		installer.afterSaveBatch();

		return bean;
	}

	public PluginBean install(String jarFile) throws InstallException {

		PluginBean bean = new PluginBean();
		if (new File(jarFile).exists()) {
			bean.setJarFile(jarFile);
			try {
				bean.setBatch((PluginBatch) jarService.scanJarFileForImplementation(jarFile, PluginBatch.class, true));
				bean.setInstaller((AbstractPluginInstaller) jarService.scanJarFileForImplementation(jarFile,
						AbstractPluginInstaller.class, false));
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
					// TODO Auto-generated catch block
					e.printStackTrace();
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

}
