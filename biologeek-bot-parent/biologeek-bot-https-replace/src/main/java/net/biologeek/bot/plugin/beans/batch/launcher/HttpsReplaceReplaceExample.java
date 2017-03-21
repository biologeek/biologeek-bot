package net.biologeek.bot.plugin.beans.batch.launcher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.biologeek.bot.batch.config.HttpsReplaceConfig;

/**
 * 
 *
 */
public class HttpsReplaceReplaceExample {
	public static void main(String[] args) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(HttpsReplaceConfig.class);

		SimpleJobLauncher laucher = (SimpleJobLauncher) ctx.getBean("simpleJobLauncher");

		Job job = (Job) ctx.getBean("httpsReplaceJob");
		JobParameters jobParameters = new JobParameters();
		
		
		laucher.run(job, jobParameters);
	}
}
