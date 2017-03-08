package net.biologeek.bot.install;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import net.biologeek.bot.plugin.beans.Period;
import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.install.AbstractPluginInstaller;
import net.biologeek.bot.plugin.install.PluginInstaller;
import net.biologeek.bot.plugin.services.PluginInstallService;

/**
 * {@link HttpsReplacePluginInstaller} implements {@link PluginInstaller}
 * defined behaviors for install These implementations are used when installer
 * is get and called during plugin install.
 * 
 * It defines the actions to realize before and after installing batch and
 * before and after uninstalling. Also it sets properties file and admin page
 * template specific to batch
 */
public class HttpsReplacePluginInstaller extends AbstractPluginInstaller {

	@Autowired
	PluginInstallService service;

	@Value("plugin.batch.period.begin")
	private Date batchPeriodBegin;
	@Value("plugin.batch.period.end")
	private Date batchPeriodEnd;

	private Period batchPeriod;

	public HttpsReplacePluginInstaller() {
		super();
		this.batchPeriod = new Period(batchPeriodBegin, batchPeriodEnd);

	}

	public HttpsReplacePluginInstaller(PluginBean bean) {
		super(bean);
		this.batchPeriod = new Period(batchPeriodBegin, batchPeriodEnd);
	}

	@Override
	public void afterSaveBatch() {
		Logger.getLogger(this.getClass()).info("afterSaveBatch() for plugin HttpsReplace plugin.");
	}

	@Override
	public void afterRemoveBatch() {
		Logger.getLogger(this.getClass()).info("afterRemoveBatch() for plugin HttpsReplace plugin.");
	}

	@Override
	public PluginBean beforeSaveBatch(PluginBean bean) {

		bean.setDescription("A plugin that replaces http links to https links");
		bean.setName("HttpsReplaceBatch");
		if (bean.getBatch() != null) {
			bean.getBatch().setBatchPeriod(batchPeriod);
		}
		return bean;
	}

	@Override
	public PluginBean beforeRemoveBatch(PluginBean bean) {
		return bean;
	}

	@Override
	public void setAdminPanelHtmlTemplate(String tpl) {
		this.service.setAdminPanelHtmlTemplate(tpl);
	}

	@Override
	public void setPropertiesFile(String tpl) throws FileNotFoundException {
		if (new File(tpl).exists()) {
			this.service.setPropertiesFile(tpl);
			return;
		}
		throw new FileNotFoundException();
	}

	public PluginInstallService getService() {
		return service;
	}

	public void setService(PluginInstallService service) {
		this.service = service;
	}
}
