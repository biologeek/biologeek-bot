package net.biologeek.bot.api.plugin;


/**
 * Represents an installed plugin, stored in database 
 */
public class PluginBean {
	
	
	private long pluginId;
	private String name;
	private String description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String descriptions) {
		this.description = descriptions;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPluginId() {
		return pluginId;
	}
	public void setPluginId(long pluginId) {
		this.pluginId = pluginId;
	}

}
