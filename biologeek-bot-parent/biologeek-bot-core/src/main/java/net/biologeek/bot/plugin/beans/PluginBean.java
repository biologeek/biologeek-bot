package net.biologeek.bot.plugin.beans;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

/**
 * Represents an installed plugin, stored in database 
 * @author xavier
 */
@Entity
public class PluginBean {
	
	private long pluginId;
	private String name;
	private String description;
	@OneToOne(fetch=FetchType.EAGER)
	private PluginBatch batch;

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
	public PluginBatch getBatch() {
		return batch;
	}
	public void setBatch(PluginBatch batch) {
		this.batch = batch;
	}

}
