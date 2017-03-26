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
public class SpringBatchPluginBatch<T> extends PluginBatch {

	/**
	 * Spring Batch Reader
	 */
	protected ItemReader<T> reader;
	protected ItemWriter<T> writer;
	protected ItemProcessor<T, T> procesor;
	protected Job job;
	protected LinkedList<Step> steps;

	public ItemReader<T> getReader() {
		return reader;
	}

	public void setReader(ItemReader<T> reader) {
		this.reader = reader;
	}

	public ItemWriter<T> getWriter() {
		return writer;
	}

	public void setWriter(ItemWriter<T> writer) {
		this.writer = writer;
	}

	public ItemProcessor<T, T> getProcesor() {
		return procesor;
	}

	public void setProcesor(ItemProcessor<T, T> procesor) {
		this.procesor = procesor;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public LinkedList<Step> getSteps() {
		return steps;
	}

	public void setSteps(LinkedList<Step> steps) {
		this.steps = steps;
	}

	public SpringBatchPluginBatch<T> batchPeriod(Period batchPeriod) {
		this.batchPeriod = batchPeriod;
		return this;
	}

	public SpringBatchPluginBatch<T> lastLaunchTime(Date batchPeriod) {
		this.lastLaunchTime = batchPeriod;
		return this;
	}

	public SpringBatchPluginBatch<T> job(Job job) {
		this.job = job;
		return this;
	}

	public SpringBatchPluginBatch<T> reader(ItemReader reader2) {
		this.reader = reader2;
		return this;
	}

	public SpringBatchPluginBatch<T> writer(ItemWriter reader2) {
		this.writer = reader2;
		return this;
	}

	public SpringBatchPluginBatch<T> procesor(ItemProcessor reader2) {
		this.procesor = reader2;
		return this;
	}

	public SpringBatchPluginBatch<T> steps(LinkedList<Step> list) {
		this.steps = list;
		return this;
	}

	public SpringBatchPluginBatch<T> logs(List<BatchUnitRecord> logs) {
		this.logs = logs;
		return this;
	}
}
