package net.biologeek.bot.plugin.beans.batch;

import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.Id;

import net.biologeek.bot.plugin.beans.Period;
import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.beans.logs.BatchUnitRecord;

/**
 * An abstract plugin batch with main batch parameters, attached plugin, and batch unit records.
 *
 * @param <T> The type of object processed (articles, categories, users, ...) 
 */
@Entity
public abstract class PluginBatch<T> implements Batch {

	@Id@GeneratedValue
	private long id;
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
	@Embedded
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
	@OneToMany(fetch=FetchType.LAZY)
	protected List<BatchUnitRecord> logs;

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
