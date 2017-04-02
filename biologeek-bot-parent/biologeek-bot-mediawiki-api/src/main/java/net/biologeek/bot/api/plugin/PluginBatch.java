package net.biologeek.bot.api.plugin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.biologeek.bot.api.plugin.exceptions.Errorable;

public class PluginBatch implements Errorable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2645767722636292786L;
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

	private Date lastLaunchTime;

	/**
	 * Class name of job
	 */
	private String job;

	/**
	 * Reader for the batch
	 */
	private String reader;

	private String writer;

	private String procesor;
	
	private List<BatchUnitRecord> logs;

	public String getReader() {
		return reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getProcesor() {
		return procesor;
	}

	public void setProcesor(String procesor) {
		this.procesor = procesor;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Date getLastLaunchTime() {
		return lastLaunchTime;
	}

	public void setLastLaunchTime(Date lastLaunchTime) {
		this.lastLaunchTime = lastLaunchTime;
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

	public List<BatchUnitRecord> getLogs() {
		return logs;
	}

	public void setLogs(List<BatchUnitRecord> logs) {
		this.logs = logs;
	}

	public PluginBatch logs(List<BatchUnitRecord> arrayList) {
		this.logs =arrayList;
		return this;
	}

	public PluginBatch reader(String reader) {
		this.reader = reader;
		return this;
	}

	public PluginBatch processor(String processor) {
		this.procesor = processor;
		return this;
	}

	public PluginBatch writer(String writer) {
		this.writer = writer;
		return this;
	}

	public PluginBatch job(String job) {
		this.job = job;
		return this;
	}

	public PluginBatch lastLaunchTime(Date lastLaunchTime) {
		this.lastLaunchTime = lastLaunchTime;
		return this;
	}

	
	

}
