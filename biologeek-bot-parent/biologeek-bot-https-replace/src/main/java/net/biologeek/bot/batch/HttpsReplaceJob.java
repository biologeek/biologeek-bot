package net.biologeek.bot.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.stereotype.Component;

import net.biologeek.bot.plugin.beans.batch.WikiJob;
@Component("httpsReplaceJob")
public class HttpsReplaceJob implements Job, WikiJob {

	@Override
	public void execute(JobExecution arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JobParametersIncrementer getJobParametersIncrementer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobParametersValidator getJobParametersValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRestartable() {
		// TODO Auto-generated method stub
		return false;
	}

}
