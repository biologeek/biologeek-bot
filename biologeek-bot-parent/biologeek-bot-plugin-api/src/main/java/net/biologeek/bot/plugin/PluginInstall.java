package net.biologeek.bot.plugin;
/**
 * Defines install (and uninstall) behaviour of a plugin
 */
public interface PluginInstall {
	
	/**
	 * This method will be called when installing new Plugin
	 * 
	 * @param options
	 */
	public void install(String[] options);
	/**
	 * This method will be called when uninstalling Plugin
	 * 
	 * @param options
	 */
	public void uninstall();

}
