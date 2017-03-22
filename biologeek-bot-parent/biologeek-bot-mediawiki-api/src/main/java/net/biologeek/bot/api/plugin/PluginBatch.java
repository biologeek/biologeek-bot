package net.biologeek.bot.api.plugin;

import java.io.Serializable;

import net.biologeek.bot.api.plugin.Period;
import net.biologeek.bot.api.plugin.exceptions.Errorable;

public class PluginBatch implements Errorable, Serializable{

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

	public PluginBatch timeFrequency(double timeFrequency) {
		this.timeFrequency = timeFrequency;
		return this;
	}

	public PluginBatch batchPeriod(Period batchPeriod) {
		this.batchPeriod = batchPeriod;
		return this;
	}

	public PluginBatch className(String className) {
		this.className = className;
		return this;
	}

}
