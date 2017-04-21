package net.biologeek.bot.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import net.biologeek.bot.plugin.beans.batch.PluginBatch;
import net.biologeek.bot.plugin.beans.batch.SpringBatchPluginBatch;
import net.biologeek.bot.plugin.exceptions.BatchException;
import net.biologeek.bot.plugin.services.BatchDelegate;

@Service
public class HttpReplaceBatchDelegate implements BatchDelegate ,ApplicationContextAware {

	SpringBatchPluginBatch pluginBatch;
	JobParameters params;
	@Autowired
	JobLauncher launcher;
	Job job;
	ApplicationContext applicationContext;

	@Override
	public void execute() throws BatchException {
		 try {
			 /*
			  * FIXME EVENTUALLY 
			  * Need to respect following specs :
			  * - A job can be called when plugin is loaded 
			  * => Technically plugin jar is added to classpath, thus providing necesary beans and config for launch
			  * - Two or more jobs should be able to coexist and run simultaneously. 
			  * => Within a Spring environment it means that you will not be able to autowire a Job bean as you'll get more than one
			  * => Using @Qualifier is not possible either as long as you can't variabilize bean name
			  * => My solution is to use the good old ApplicationContext bean to get the bean. 
			  * => However I'm open to any suggestion if you have one !
			  */
			 job = (Job) applicationContext.getBean(pluginBatch.getSpringBeanName());
			launcher.run(job, params);
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			e.printStackTrace();
			throw new BatchException("batch.run.exception");
		}
	}

	@Override
	public void setParameters(PluginBatch batch) throws BatchException {
		this.pluginBatch = (SpringBatchPluginBatch) batch;
		params = new JobParametersBuilder()//
				.addDate("param.validity.beginning", pluginBatch.getBatchPeriod().getPeriodBeginning())
				.addDate("param.validity.end", pluginBatch.getBatchPeriod().getPeriodEnd())
				.addDouble("param.frequency", pluginBatch.getTimeFrequency())
				.toJobParameters();
	}

	@Override
	public void setBatchBean(String beanName, boolean isClass) {
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
