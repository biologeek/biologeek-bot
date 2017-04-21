package net.biologeek.bot.plugin.beans.batch;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import net.biologeek.bot.plugin.beans.Period;
import net.biologeek.bot.plugin.beans.logs.BatchUnitRecord;
import net.biologeek.bot.plugin.utils.SpringToolbox;

/**
 * A Spring Batch extension of PluginBatch.
 * 
 * When using this type of batch, user should register his own batch beans
 * in @Configration annotated class.
 *
 * @param <T>
 */
@Entity
public class SpringBatchPluginBatch extends SpringAwarePluginBatch {

	/**
	 * Spring Batch reader
	 */
	protected String reader;

	/**
	 * Spring Batch writer
	 */
	protected String writer;

	/**
	 * Spring Batch processor
	 */
	protected String procesor;

	/**
	 * Spring Batch job
	 */
	protected String job;

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

	public SpringBatchPluginBatch batchPeriod(Period period) {
		this.batchPeriod = period;
		return this;
	}

	public SpringBatchPluginBatch lastLaunchTime(Date lastLaunchTime) {
		this.lastLaunchTime = lastLaunchTime;
		return this;
	}

	public SpringBatchPluginBatch job(String job2) {
		this.job = job2;
		return this;
	}

	public SpringBatchPluginBatch reader(String reader) {
		this.reader = reader;
		return this;
	}

	public SpringBatchPluginBatch procesor(String procesor) {
		this.procesor = procesor;
		return this;
	}

	public SpringBatchPluginBatch writer(String writer) {
		this.writer = writer;
		return this;
	}

	public SpringBatchPluginBatch logs(List<BatchUnitRecord> logs) {
		this.logs = logs;
		return this;
	}

	public SpringBatchPluginBatch status(org.springframework.batch.core.BatchStatus valueOf) {
		this.status = valueOf;
		return this;
	}
	

	public String getSpringBeanName() {
		return springBeanName == null ? SpringToolbox.resolveClassNameToSpringBeanName(this.job) : springBeanName;
	}
}
