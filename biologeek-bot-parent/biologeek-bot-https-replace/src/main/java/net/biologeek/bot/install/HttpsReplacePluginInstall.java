package net.biologeek.bot.install;

import net.biologeek.bot.plugin.beans.PluginBatch;
import net.biologeek.bot.plugin.services.PluginInstallService;

public class HttpsReplacePluginInstall extends PluginInstallService {

	@Override
	protected void afterSaveBatch() {
		
	}

	@Override
	public void setAdminPanelHtmlTemplate(String tpl) {
		this.adminPanelHtmlTemplate = tpl;
	}

	@Override
	public void setPropertiesFile(String tpl) {
		this.propertiesFile = tpl;
	}

	@Override
	protected PluginBatch beforeSaveBatch(PluginBatch batch) {
		return batch;
	}

}
