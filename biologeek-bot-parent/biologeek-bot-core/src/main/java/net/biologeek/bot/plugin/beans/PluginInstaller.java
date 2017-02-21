package net.biologeek.bot.plugin.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines install (and uninstall) behaviour of a plugin
 */
public abstract class PluginInstaller {
	
	
	
	/**
	 * This method will be called when installing new Plugin
	 * 
	 * @param options
	 */
	public void install(String[] options){
		
	}
	/**
	 * This method will be called when uninstalling Plugin
	 * 
	 * @param options
	 */
	public void uninstall(){
		
	}
	
	
	public abstract void setAdminPanelHtmlTemplate(String tpl);
	public abstract void setPropertiesFile(String tpl);

}
