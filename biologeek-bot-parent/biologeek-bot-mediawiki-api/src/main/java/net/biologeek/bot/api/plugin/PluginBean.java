package net.biologeek.bot.api.plugin;

import java.io.Serializable;

import net.biologeek.bot.api.plugin.exceptions.Errorable;

/**
 * Represents a plugin. When update, id must be not null, else it must be null
 * 
 */
public class PluginBean implements Errorable, Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7336931735921439960L;
	private long pluginId;
	private String name;
	private String description;
	private boolean isInstalled;
	
	private PluginBatch batch;
	private PluginInstaller installer;
	
	
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
	public boolean isInstalled() {
		return isInstalled;
	}
	public void setInstalled(boolean isInstalled) {
		this.isInstalled = isInstalled;
	}

	public PluginBatch getBatch() {
		return batch;
	}
	public void setBatch(PluginBatch batch) {
		this.batch = batch;
	}
	public PluginInstaller getInstaller() {
		return installer;
	}
	public void setInstaller(PluginInstaller installer) {
		this.installer = installer;
	}

	// BUILDER
	
	public PluginBean description(String descriptions) {
		this.description = descriptions;
		return this;
	}
	public PluginBean name(String name) {
		this.name = name;
		return this;
	}
	public PluginBean pluginId(long pluginId) {
		this.pluginId = pluginId;
		return this;
	}
	public PluginBean installed(boolean isInstalled) {
		this.isInstalled = isInstalled;
		return this;
	}
	public PluginBean installer(PluginInstaller installer) {
		this.installer = installer;
		return this;
	}

	public PluginBean batch(PluginBatch batch) {
		this.batch = batch;
		return this;
	}
}
