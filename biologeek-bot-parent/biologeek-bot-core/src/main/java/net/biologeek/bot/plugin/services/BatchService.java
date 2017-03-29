package net.biologeek.bot.plugin.services;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.biologeek.bot.plugin.beans.batch.PluginBatch;
import net.biologeek.bot.plugin.validation.DateValidator;

@Service
public class BatchService implements Mergeable<PluginBatch>{

	@Autowired
	private DateValidator dateValidator;

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

}
