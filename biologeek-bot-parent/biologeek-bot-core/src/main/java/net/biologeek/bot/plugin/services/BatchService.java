package net.biologeek.bot.plugin.services;

import javax.validation.ValidationException;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.biologeek.bot.plugin.beans.batch.PluginBatch;
import net.biologeek.bot.plugin.beans.batch.SpringBatchPluginBatch;
import net.biologeek.bot.plugin.exceptions.BatchException;
import net.biologeek.bot.plugin.validation.DateValidator;

@Service
public class BatchService implements Mergeable<PluginBatch>{

	@Autowired
	private DateValidator dateValidator;
	
	@Autowired
	JobLauncher jobLauncher;
	Job job;

	@Autowired
	BatchDelegate batchDelegate;

	@Override
	public PluginBatch merge(PluginBatch base, PluginBatch updated) throws ValidationException {
		if (base.getId() != updated.getId()) {
			throw new IllegalArgumentException("Source : " + base.getId() + ", new : " + updated.getId());
		}

		base.setBatchPeriod(dateValidator.validatePeriod(updated.getBatchPeriod()));
		base.setLastLaunchTime(updated.getLastLaunchTime());
		base.setLogs(updated.getLogs());
		base.setTimeFrequency(dateValidator.validateTimeFrequency(updated.getTimeFrequency()));
		// JPA auto save
		return base;
	}

	public DateValidator getDateValidator() {
		return dateValidator;
	}

	public void setDateValidator(DateValidator dateValidator) {
		this.dateValidator = dateValidator;
	}

	public PluginBatch startSpringBatch(SpringBatchPluginBatch batch) throws BatchException {
		if (batch != null && batch.getId() > 0){
			if (batch.getStatus() == BatchStatus.STARTED || batch.getStatus() == BatchStatus.STARTING 
					|| batch.getStatus() == BatchStatus.COMPLETED){
				throw new BatchException("batch.already.running");
			}
			this.startBatch(batch);
			return batch;
		}
		throw new BatchException("batch.null");
	}

	private void startBatch(SpringBatchPluginBatch batch) throws BatchException {
		batchDelegate.setParameters(batch);
		batchDelegate.execute();
	}

}
