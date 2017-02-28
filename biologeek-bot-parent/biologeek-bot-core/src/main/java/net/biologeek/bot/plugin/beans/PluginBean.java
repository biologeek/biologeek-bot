package net.biologeek.bot.plugin.beans;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.Id;

import net.biologeek.bot.plugin.beans.batch.PluginBatch;
import net.biologeek.bot.plugin.install.AbstractPluginInstaller;

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
	@SuppressWarnings("rawtypes")
	@OneToOne(fetch = FetchType.EAGER)
	private PluginBatch batch;

	@OneToOne
	private AbstractPluginInstaller installer;

	private String jarFile;
	private Class<? extends AbstractPluginInstaller> installerClass;
	private Class<?> pluginClass;

	@SuppressWarnings("rawtypes")
	public PluginBatch getBatch() {
		return batch;
	}

	public void setBatch(@SuppressWarnings("rawtypes") PluginBatch batch) {
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

	public Class<? extends AbstractPluginInstaller> getInstallerClass() {
		return installerClass;
	}

	public void setInstallerClass(Class<? extends AbstractPluginInstaller> installerClass) {
		this.installerClass = installerClass;
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

	public Class<?> getPluginClass() {
		return pluginClass;
	}

	public void setPluginClass(Class<?> pluginClass) {
		this.pluginClass = pluginClass;
	}

}
