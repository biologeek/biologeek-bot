package net.biologeek.bot.plugin.beans;

import java.util.Date;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import net.biologeek.bot.plugin.beans.logs.BatchUnitRecord;

/**
 * An abstract plugin batch with main batch parameters, attached plugin, and batch unit records.
 *
 * @param <T> The type of object processed (articles, categories, users, ...) 
 */
public abstract class PluginBatch<T> implements Batch {

	/**
	 * Batch Plugin object
	 */
	@OneToOne(fetch = FetchType.EAGER)
	protected PluginBean plugin;
	/**
	 * Batch concrete class
	 */
	private Class<? extends PluginBatch<?>> batchClass;
	/**
	 * The period of time over which the batch will be able to run
	 */
	protected Period batchPeriod;
	/**
	 * Time frequency is expressed in min-1
	 */
	protected double timeFrequency;

	/**
	 * The last time batch was launched
	 */
	protected Date lastLaunchTime;
	
	/**
	 * Log traces of each batch launch
	 */
	protected Set<BatchUnitRecord> logs;

	/**
	 * Method called to launch the batch
	 * 
	 * @param params
	 */
	public abstract void execute(String[] params);

	public Date getLastLaunchTime() {
		return lastLaunchTime;
	}

	public void setLastLaunchTime(Date lastLaunchTime) {
		this.lastLaunchTime = lastLaunchTime;
	}

	public PluginBean getPlugin() {
		return plugin;
	}

	public void setPlugin(PluginBean plugin) {
		this.plugin = plugin;
	}

	public Class<? extends PluginBatch<?>> getBatchClass() {
		return batchClass;
	}

	public void setBatchClass(Class<? extends PluginBatch<?>> batchClass) {
		this.batchClass = batchClass;
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
