package net.biologeek.bot.plugin.services;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.biologeek.bot.plugin.PluginBatch;
import net.biologeek.bot.plugin.PluginBean;

@Service
public abstract class PluginInstallService {

	ServiceLoader<PluginBatch> pluginBatchScanner;
	@Autowired
	private PluginService pluginService;

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
	
	
	public PluginBean saveOrUpdatePluginBean(PluginBean bean){
		if (bean.getPluginId() > 0)
			return pluginService.update(bean);
		else 
			return pluginService.save(bean);
	}
	
	/**
	 * Installs a plugin by saving the {@link PluginBean} and {@link PluginBatch} objects.
	 * 
	 * Use {@link PluginInstallService#installBatch()} and call this method inside
	 * @param batch
	 * @return
	 */
	public PluginBatch installBatch(PluginBatch batch){
		
		if (batch != null || batch.getPlugin() != null){
			
			pluginService.save(batch.getPlugin());
		}
		return batch;
	}
	
	public abstract PluginBatch installBatch();
}
