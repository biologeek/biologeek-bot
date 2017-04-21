package net.biologeek.bot.plugin.services;

import net.biologeek.bot.plugin.beans.batch.PluginBatch;
import net.biologeek.bot.plugin.exceptions.BatchException;

public interface BatchDelegate {
	
	/**
	 * Runs the job following parameters set
	 * 
	 * @throws BatchException when error occurs at runtime
	 */
	public void execute () throws BatchException;
	
	/**
	 * Builds job parameters (such as in {@link SpringBatchDelegate} in an instance variable)
	 * using {@link PluginBatch} or its implementors. 
	 *  
	 * @param batch the batch object
	 * @throws BatchException in case of error at setting parameters
	 */
	public void setParameters(PluginBatch batch) throws BatchException;
	
	public void setBatchBean(String beanName, boolean isClass);
}