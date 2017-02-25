package net.biologeek.bot.plugin.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.repositories.PluginRepository;

public class PluginService {

	@Autowired
	private PluginRepository pluginDao;
	private Logger logger;

	public PluginService() {
		super();
		logger = Logger.getLogger(this.getClass().getName());
	}

	public PluginBean getPluginById(long pluginId) {
		return pluginDao.findOne(pluginId);
	}

	public PluginBean getPluginByPluginName(String name) {
		return pluginDao.findByName(name);
	}

	public PluginBean save(PluginBean bean) {
		logger.info("Starting batch install");
		return pluginDao.save(bean);
	}

	public PluginBean update(PluginBean bean) {
		return pluginDao.save(bean);
	}
}
