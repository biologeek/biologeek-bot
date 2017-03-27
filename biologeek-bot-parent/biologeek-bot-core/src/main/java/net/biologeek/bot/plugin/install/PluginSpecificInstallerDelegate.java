package net.biologeek.bot.plugin.install;

import java.io.FileNotFoundException;

import net.biologeek.bot.api.plugin.PluginInstaller;
import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.services.PluginInstallService;

/**
 * Defines install (and uninstall) behaviour of a plugin. <br>
 * <br>
 * Serves for defining specific actions to perform before and after
 * installation, set various properties and be able to retrieve them. <br>
 * <br>
 * Used by {@link PluginInstallService} for plugin install.
 */
public interface PluginSpecificInstallerDelegate {

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

	public abstract String getAdminPanelHtmlTemplate();

	public abstract String getPropertiesFile();

}
