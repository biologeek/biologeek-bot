package net.biologeek.bot.api.plugin;

import java.io.Serializable;
import java.util.Date;

import javax.swing.Spring;

import net.biologeek.bot.api.plugin.exceptions.Errorable;

/**
 * @author xcaron
 *
 */
public class PluginInstaller implements Errorable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5432158865578250749L;
	private Long id;
	protected Period batchPeriod;

	protected Date lastLaunchTime;
	/**
	 * The kind of installer used for the batch. For example if batch is based
	 * on Spring Batch, set this to {@link SpringBatchPluginBatch}
	 */
	String installerServiceClass;

	/**
	 * The concrete class implementing {@link PluginSpecificInstallerDelegate}
	 */
	String installerSpecificsClass;

	
	String jarPath;
	 
	
	public String getJarPath() {
		return jarPath;
	}

	public void setJarPath(String jarPath) {
		this.jarPath = jarPath;
	}

	public Date getLastLaunchTime() {
		return lastLaunchTime;
	}

	public void setLastLaunchTime(Date lastLaunchTime) {
		this.lastLaunchTime = lastLaunchTime;
	}

	public String getInstallerSpecificsClass() {
		return installerSpecificsClass;
	}

	public void setInstallerSpecificsClass(String installerSpecificsClass) {
		this.installerSpecificsClass = installerSpecificsClass;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Period getBatchPeriod() {
		return batchPeriod;
	}

	public void setBatchPeriod(Period batchPeriod) {
		this.batchPeriod = batchPeriod;
	}

	public String getInstallerServiceClass() {
		return installerServiceClass;
	}

	public void setInstallerServiceClass(String installerServiceClass) {
		this.installerServiceClass = installerServiceClass;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PluginInstaller id(Long id) {
		this.id = id;
		return this;
	}

	public PluginInstaller installerServiceClass(String installerServiceClass) {
		this.installerServiceClass = installerServiceClass;
		return this;
	}

	public PluginInstaller batchPeriod(Period period){
		this.batchPeriod = period;
		return this;
	}
}
