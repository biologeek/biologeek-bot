package net.biologeek.bot.plugin.beans.batch;

import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import net.biologeek.bot.plugin.beans.Period;
import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.beans.logs.BatchUnitRecord;

/**
 * An abstract plugin batch with main batch parameters, attached plugin, and batch unit records.
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class PluginBatch implements Batch {

	@Id@GeneratedValue
	private long id;
	/**
	 * Batch Plugin object
	 */
	@OneToOne(fetch = FetchType.EAGER)
	protected PluginBean plugin;
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


	public PluginBatch() {
		super();
		// TODO Auto-generated constructor stub
	}


	public PluginBatch(PluginBean bean) {
		super();
		this.plugin = bean;
	}

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<BatchUnitRecord> getLogs() {
		return logs;
	}

	public void setLogs(List<BatchUnitRecord> logs) {
		this.logs = logs;
	}
}
