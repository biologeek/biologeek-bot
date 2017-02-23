package net.biologeek.bot.plugin.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Service;

import net.biologeek.bot.plugin.beans.Plugin;
import net.biologeek.bot.plugin.beans.PluginBatch;
import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.exceptions.InstallException;
import net.biologeek.bot.plugin.exceptions.UninstallException;
import sun.misc.URLClassPath;

@Service
public abstract class PluginInstallService {

	ServiceLoader<PluginBatch> pluginBatchScanner;
	@Autowired
	private PluginService pluginService;
	@Autowired
	private PluginJarDelegate jarService;
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
	 * 
	 * @return
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
	 * Calls {@link PluginInstallService#beforeSaveBatch()} prior to any action
	 * Then plugin is saved and afterSaveBatch method is called
	 * 
	 * @param batch
	 * @return
	 */
	public PluginBatch install(String jarFile) {

		logger.info("Starting batch install");
		File jar = new File(jarFile);
		
		jarService.scanJarFileForAnnotatedClass(jarFile, Plugin.class);
		return null;
	}

	protected abstract void afterSaveBatch();

	protected abstract PluginBatch beforeSaveBatch(PluginBatch batch);

	/**
	 * This method will be called when uninstalling Plugin
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
					Field ucpField =  URLClassLoader.class.getDeclaredField("ucp");
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


	public abstract void setAdminPanelHtmlTemplate(String tpl);

	public abstract void setPropertiesFile(String tpl);

	

}
