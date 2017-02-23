package net.biologeek.bot.plugin.beans;

import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

public abstract class PluginBatch<T> {

	/**
	 * Batch Plugin object
	 */
	@OneToOne(fetch=FetchType.EAGER)
	protected PluginBean plugin;
	/**
	 * Batch concrete class
	 */
	protected String className;
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
	
	
	public abstract void setReader(ItemReader<T> reader);
	public abstract void setWriter(ItemWriter<T> writer);
	public abstract void setProcesor(ItemProcessor<T, T> procesor);
	

	/**
	 * Method called to launch the batch
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
