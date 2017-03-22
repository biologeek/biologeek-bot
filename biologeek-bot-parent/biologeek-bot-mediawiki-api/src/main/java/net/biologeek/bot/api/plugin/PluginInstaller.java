package net.biologeek.bot.api.plugin;

import java.io.Serializable;
import java.util.Date;

import net.biologeek.bot.api.plugin.exceptions.Errorable;

public class PluginInstaller implements Errorable, Serializable{

	private Long id;
	protected Date batchPeriodBegin;
	protected Date batchPeriodEnd;
	String installerServiceClass;

	public Date getBatchPeriodBegin() {
		return batchPeriodBegin;
	}

	public void setBatchPeriodBegin(Date batchPeriodBegin) {
		this.batchPeriodBegin = batchPeriodBegin;
	}

	public Date getBatchPeriodEnd() {
		return batchPeriodEnd;
	}

	public void setBatchPeriodEnd(Date batchPeriodEnd) {
		this.batchPeriodEnd = batchPeriodEnd;
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

	public PluginInstaller batchPeriodBegin(Date batchPeriodBegin) {
		this.batchPeriodBegin = batchPeriodBegin;
		return this;
	}

	public PluginInstaller batchPeriodEnd(Date batchPeriodEnd) {
		this.batchPeriodEnd = batchPeriodEnd;
		return this;
	}
}
