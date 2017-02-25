package net.biologeek.bot.plugin.beans;

import java.util.LinkedList;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;


/**
 * A Spring Batch extension of PluginBatch.
 * 
 *  When using this type of batch, user should register his own batch beans in @Configration annotated class.
 *
 * @param <T>
 */
public abstract class SpringBatchPluginBatch<T> extends PluginBatch<T>  {

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
	
	
	
}
