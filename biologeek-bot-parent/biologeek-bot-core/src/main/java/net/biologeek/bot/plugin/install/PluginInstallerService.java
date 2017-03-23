package net.biologeek.bot.plugin.install;

import java.io.FileNotFoundException;

import net.biologeek.bot.plugin.beans.PluginBean;

/**
 * Defines install (and uninstall) behaviour of a plugin
 */
public interface PluginInstallerService {
	

	
	public abstract void afterSaveBatch();
	public abstract void afterRemoveBatch();

	/**
	 * This method will be called when installing new Plugin
	 * 
	 * @param options
	 */
	public abstract PluginBean beforeSaveBatch(PluginBean bean);
	public abstract PluginBean beforeRemoveBatch(PluginBean bean);
	

	
	public abstract void setAdminPanelHtmlTemplate(String tpl);
	public abstract void setPropertiesFile(String tpl) throws FileNotFoundException;

}
