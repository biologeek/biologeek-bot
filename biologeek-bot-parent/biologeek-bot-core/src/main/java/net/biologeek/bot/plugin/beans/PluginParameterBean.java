package net.biologeek.bot.plugin.beans;

/**
 * Represents a parameter of a plugin.
 * 
 * Each time you install a plugin, it should call method createParameters() to
 * create defined parameters and store them in datasource.
 * 
 * 
 * @author xavier
 *
 */
public class PluginParameterBean {

	private long id;
	private long pluginId;
	private String key;
	private String value;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPluginId() {
		return pluginId;
	}

	public void setPluginId(long pluginId) {
		this.pluginId = pluginId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
