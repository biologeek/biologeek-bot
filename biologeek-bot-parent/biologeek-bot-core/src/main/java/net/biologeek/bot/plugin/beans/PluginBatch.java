package net.biologeek.bot.plugin.beans;

import java.time.Period;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;


public abstract class PluginBatch {

	/**
	 * Batch Plugin object
	 */
	@OneToOne(fetch=FetchType.EAGER)
	private PluginBean plugin;
	/**
	 * Batch concrete class
	 */
	private String className;
	/**
	 * The period of time over which the batch will be able to run
	 */
	private Period batchPeriod;
	/**
	 * Time frequency is expressed in min-1
	 */
	private double timeFrequency;

	public abstract void execute(String[] params);

	public PluginBean getPlugin() {
		return plugin;
	}

	public void setPlugin(PluginBean plugin) {
		this.plugin = plugin;
	}

	public void getClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Period getBatchPeriod() {
		return batchPeriod;
	}

	public void setBatchPeriod(Period batchPeriod) {
		this.batchPeriod = batchPeriod;
	}

	public double getTimeFrequency() {
		return timeFrequency;
	}

	public void setTimeFrequency(double timeFrequency) {
		this.timeFrequency = timeFrequency;
	}
}
