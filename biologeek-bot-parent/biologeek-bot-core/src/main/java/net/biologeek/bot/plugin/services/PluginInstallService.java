package net.biologeek.bot.plugin.services;

import java.util.ArrayList;
import java.util.List;

import net.biologeek.bot.plugin.PluginBean;

import org.springframework.stereotype.Service;


@Service
public class PluginInstallService {

	
	/**
	 * Lists all plugins installed or not. Default behaviour is to scan classpath for implementations 
	 * @return
	 */
	public List<PluginBean> listAllPlugins(){
		List<PluginBean> result = new ArrayList<PluginBean>();
		
		return result;
	}
	
	public List<PluginBean> listNotYetInstalledPlugins(){
		List<PluginBean> result = new ArrayList<PluginBean>();
		return result;
		
	}
}
