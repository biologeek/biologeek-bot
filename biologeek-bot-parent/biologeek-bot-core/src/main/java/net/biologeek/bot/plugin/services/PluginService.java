package net.biologeek.bot.plugin.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.validation.ValidationException;

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

import net.biologeek.bot.plugin.beans.Jar;
import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.beans.batch.PluginBatch;
import net.biologeek.bot.plugin.beans.install.AbstractPluginInstaller;
import net.biologeek.bot.plugin.exceptions.BatchException;
import net.biologeek.bot.plugin.exceptions.InstallException;
import net.biologeek.bot.plugin.exceptions.ServiceException;
import net.biologeek.bot.plugin.repositories.PluginRepository;

@Service
public class PluginService implements Mergeable<PluginBean>{

	@Autowired
	private PluginRepository pluginDao;

	@Autowired
	private PluginJarDelegate jarDelegate;

	private Logger logger;

	@Autowired
	private BatchService batchService;

	@Autowired
	private Mergeable<AbstractPluginInstaller> installerMergeable;

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

	/**
	 * Returns all registered plugins
	 * @return all registered plugins
	 */
	public List<PluginBean> getInstalledPlugins(){
		return pluginDao.findAll();
	}
	public PluginBean save(PluginBean bean) {
		logger.info("Starting batch install");
		return pluginDao.save(bean);
	}

	public PluginBean update(PluginBean bean) {
		return pluginDao.save(bean);
	}

	/**
	 * Lauches an instance of a Spring Batch Job
	 * 
	 * @param launcher
	 * @param job
	 * @param arguments
	 * @throws BatchException 
	 */
	public void launchSpringBatchJob(Class<? extends JobLauncher> launcher, Class<? extends Job> job,
			JobParameters arguments) throws BatchException {
		Job jobInstance = null;
		JobLauncher launcherInstance = null;
		JobExecution execution;

		if (arguments == null) {
			arguments = new JobParameters();
		}
		try {
			launcherInstance = launcher.newInstance();
			jobInstance = job.newInstance();
			execution = launcherInstance.run(jobInstance, arguments);
		} catch (InstantiationException | IllegalAccessException | JobParametersInvalidException
				| JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException e) {
			e.printStackTrace();
			throw new BatchException(e.getMessage());
		}
	}

	/**
	 * Builds and returns all plugins present in a directory
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<PluginBean> getNotInstalledPlugins() throws ServiceException {
		List<PluginBean> result = new ArrayList<>();

		try {
			List<Jar> jars = jarDelegate.scanDirectoryForJars();

			for (Jar jar : jars) {
				PluginBean pluginToAdd = new PluginBean();
				
				try {
					buildBean(jar, pluginToAdd);
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
					throw new ServiceException(e.getMessage());
				}
			}
		} catch (IOException | ClassNotFoundException | InstallException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}

		return result;
	}

	/**
	 * Builds a PluginBean by scanning jar for implementations of {@link PluginBatch} and {@link AbstractPluginInstaller}.
	 * Also, sets jar file path and temporary plugin name.
	 * 
	 * @param jar
	 * @param pluginToAdd plugin to build
	 * @throws ClassNotFoundException in case no implementation of given abstract class is found
	 * @throws InstallException if can't add Jar to classpath
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void buildBean(Jar jar, PluginBean pluginToAdd) throws ClassNotFoundException, InstallException, InstantiationException, IllegalAccessException {
		pluginToAdd.setBatch((PluginBatch) jarDelegate.scanJarFileForImplementation(jar, PluginBatch.class).newInstance());
		pluginToAdd.setInstaller((AbstractPluginInstaller) jarDelegate.scanJarFileForImplementation(jar, AbstractPluginInstaller.class).newInstance());
		pluginToAdd.setJarFile(jar.getAbsolutePath());
		pluginToAdd.setName(jarDelegate.scanJarFileForImplementation(jar, PluginBean.class).getSimpleName());
	}

	/**
	 * Returns all plugins, merging installed and not installed ones
	 * @return
	 * @throws ServiceException
	 */
	public List<PluginBean> getAllPlugins() throws ServiceException {
		List<PluginBean> beans = new ArrayList<>();
		beans.addAll(this.getNotInstalledPlugins());
		beans.addAll(this.getInstalledPlugins());
		return beans;
	}
	
	
	/**
	 * Merges old and new Plugin
	 * 
	 * @param base
	 *            the plugin as stored in DB
	 * @param updated
	 *            freh new update not saved yet
	 * @return
	 */
	public PluginBean merge(PluginBean base, PluginBean updated) throws ValidationException {

		if (base.getPluginId() != updated.getPluginId()) {
			throw new IllegalArgumentException("Source : " + base.getPluginId() + ", new : " + updated.getPluginId());
		}

		if (base.getBatch() == null) {
			throw new NullPointerException("Batch null");
		}

		updated.name(base.getName())//
				.description(updated.getDescription())//
				.batch(batchService.merge(base.getBatch(), updated.getBatch()))//
				.installer(installerMergeable.merge(base.getInstaller(), updated.getInstaller()));

		return base;
	}

	public PluginRepository getPluginDao() {
		return pluginDao;
	}

	public void setPluginDao(PluginRepository pluginDao) {
		this.pluginDao = pluginDao;
	}

	public PluginJarDelegate getJarDelegate() {
		return jarDelegate;
	}

	public void setJarDelegate(PluginJarDelegate jarDelegate) {
		this.jarDelegate = jarDelegate;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public BatchService getBatchService() {
		return batchService;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	public Mergeable<AbstractPluginInstaller> getInstallerMergeable() {
		return installerMergeable;
	}

	public void setInstallerMergeable(Mergeable<AbstractPluginInstaller> installerMergeable) {
		this.installerMergeable = installerMergeable;
	}
}
