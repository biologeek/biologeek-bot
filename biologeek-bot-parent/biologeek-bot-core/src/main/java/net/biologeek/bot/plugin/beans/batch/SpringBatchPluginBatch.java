package net.biologeek.bot.plugin.beans.batch;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

import net.biologeek.bot.plugin.beans.Period;
import net.biologeek.bot.plugin.beans.logs.BatchUnitRecord;

/**
 * A Spring Batch extension of PluginBatch.
 * 
 * When using this type of batch, user should register his own batch beans
 * in @Configration annotated class.
 *
 * @param <T>
 */
@Entity
public class SpringBatchPluginBatch extends PluginBatch {

	/**
	 * Spring Batch Reader
	 */
	protected String reader;
	protected String writer;
	protected String procesor;
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
}
