package net.biologeek.bot.plugin.services;

import org.springframework.beans.factory.annotation.Autowired;

import net.biologeek.bot.plugin.PluginBean;
import net.biologeek.bot.plugin.repositories.PluginRepository;

public class PluginService {

	@Autowired
	private PluginRepository pluginDao;

	public PluginBean getPluginById(long pluginId) {
		return pluginDao.findOne(pluginId);
	}

	public PluginBean getPluginByPluginName(String name) {
		return pluginDao.findByName(name);
	}

	public PluginBean save(PluginBean bean) {
		return pluginDao.save(bean);
	}

	public PluginBean update(PluginBean bean) {
		return pluginDao.save(bean);
	}
}
