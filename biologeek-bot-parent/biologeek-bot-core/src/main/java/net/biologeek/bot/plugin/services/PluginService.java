package net.biologeek.bot.plugin.services;

import java.util.logging.Logger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.repositories.PluginRepository;

@Service
public class PluginService {

	@Autowired
	private PluginRepository pluginDao;
	private Logger logger;

	public PluginService() {
		super();
		logger = Logger.getLogger(this.getClass().getName());
	}

	public PluginBean getPluginById(long pluginId) {
		return pluginDao.findOne(pluginId);
	}

	public PluginBean getPluginByPluginName(String name) {
		return pluginDao.findByName(name);
	}

	public PluginBean save(PluginBean bean) {
		logger.info("Starting batch install");
		return pluginDao.save(bean);
	}

	public PluginBean update(PluginBean bean) {
		return pluginDao.save(bean);
	}
	
	
	/**
	 * Lauches an instance of 
	 * @param launcher
	 * @param job
	 * @param arguments
	 */
	public void launchSpringBatchJob(Class<? extends JobLauncher> launcher, Class<? extends Job> job, JobParameters arguments){
		Job jobInstance = null;
		JobLauncher launcherInstance = null;
		JobExecution execution;
		
		
		if (arguments == null){
			arguments = new JobParameters();
		}
		try {
			launcherInstance = launcher.newInstance();
			jobInstance = job.newInstance();
			
			execution = launcherInstance.run(jobInstance, arguments);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
