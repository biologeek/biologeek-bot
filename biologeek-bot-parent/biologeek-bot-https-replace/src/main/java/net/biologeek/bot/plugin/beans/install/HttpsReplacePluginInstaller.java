package net.biologeek.bot.plugin.beans.install;

import java.io.File;
import java.io.FileNotFoundException;

import javax.persistence.Entity;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import net.biologeek.bot.plugin.beans.Period;
import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.beans.install.AbstractPluginInstaller;
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
public class HttpsReplacePluginInstaller implements PluginInstaller {

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
			bean.getBatch().setBatchPeriod(
					new Period(bean.getInstaller().getBatchPeriodBegin(), bean.getInstaller().getBatchPeriodEnd()));
		}
		return bean;
	}

	@Override
	public PluginBean beforeRemoveBatch(PluginBean bean) {
		return bean;
	}

	@Override
	public void setAdminPanelHtmlTemplate(String tpl) {
		this.setAdminPanelHtmlTemplate(tpl);
	}

	@Override
	public void setPropertiesFile(String tpl) throws FileNotFoundException {
		if (new File(tpl).exists()) {
			this.setPropertiesFile(tpl);
			return;
		}
		throw new FileNotFoundException();
	}
}
