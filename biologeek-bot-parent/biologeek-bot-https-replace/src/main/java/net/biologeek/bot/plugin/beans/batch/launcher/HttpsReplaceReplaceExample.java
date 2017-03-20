package net.biologeek.bot.plugin.beans.batch.launcher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.biologeek.bot.batch.config.HttpsReplaceConfig;

/**
 * 
 *
 */
public class HttpsReplaceReplaceExample {

	@Autowired
	@Qualifier("httpsReplaceJob")
	Job job;
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(HttpsReplaceConfig.class);

		SimpleJobLauncher laucher = (SimpleJobLauncher) ctx.getBean("simpleJobLauncher");

		
		JobParameters jobParameters = new JobParameters();
		
		
		laucher.run(job, jobParameters)
	}
}
