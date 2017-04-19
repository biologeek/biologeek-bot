package net.biologeek.bot.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.biologeek.bot.plugin.beans.batch.PluginBatch;
import net.biologeek.bot.plugin.beans.batch.SpringBatchPluginBatch;
import net.biologeek.bot.plugin.exceptions.BatchException;
import net.biologeek.bot.plugin.services.BatchDelegate;

@Service
public class HttpReplaceBatchDelegate implements BatchDelegate {

	SpringBatchPluginBatch pluginBatch;
	JobParameters params;
	@Autowired
	JobLauncher launcher;
	@Autowired
	Job job;

	@Override
	public void execute() throws BatchException {
		 try {
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

}
