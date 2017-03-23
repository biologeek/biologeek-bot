package net.biologeek.bot.plugin.beans;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import net.biologeek.bot.plugin.beans.batch.PluginBatch;
import net.biologeek.bot.plugin.beans.install.AbstractPluginInstaller;

/**
 * Represents an installed plugin, stored in database
 * 
 * @author xavier
 */
@Entity
public class PluginBean {

	@Id
	@GeneratedValue
	private long pluginId;
	private String name;
	private String description;
	@OneToOne(fetch = FetchType.EAGER)
	private PluginBatch batch;

	@OneToOne
	@JoinColumn(name="installer_id")
	private AbstractPluginInstaller installer;

	private String jarFile;
	
	/**
	 * Is the bean ready for being installed, run, ...
	 */
	private Boolean isComplete;

	public PluginBatch getBatch() {
		return batch;
	}

	public void setBatch(PluginBatch batch) {
		this.batch = batch;
	}

	public String getJarFile() {
		return jarFile;
	}

	public void setJarFile(String jarFile) {
		this.jarFile = jarFile;
	}

	public AbstractPluginInstaller getInstaller() {
		return installer;
	}

	public void setInstaller(AbstractPluginInstaller installer) {
		this.installer = installer;
	}

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

	public Boolean getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}
}
