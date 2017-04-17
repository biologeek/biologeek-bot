package net.biologeek.bot.plugin.validation;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.biologeek.bot.plugin.beans.Period;

@Component
public class DateValidator {

	@Value("${platform.job.frequency.max}")
	private String maxJobLaunchFrequency;
	
	
	public double validateTimeFrequency(double timeFrequency) {
		if (maxJobLaunchFrequency != null && !maxJobLaunchFrequency.isEmpty()
				&& timeFrequency > Long.valueOf(maxJobLaunchFrequency)) {
			return timeFrequency;
		}
		throw new ValidationException();

	}

	public Period validatePeriod(Period batchPeriod) {
		if (batchPeriod.getPeriodBeginning().after(batchPeriod.getPeriodEnd()))
			throw new ValidationException();
		return batchPeriod;
	}

	public String getMaxJobLaunchFrequency() {
		return maxJobLaunchFrequency;
	}

	public void setMaxJobLaunchFrequency(String maxJobLaunchFrequency) {
		this.maxJobLaunchFrequency = maxJobLaunchFrequency;
	}

}
