package net.biologeek.bot.install;

import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.install.PluginInstaller;
import net.biologeek.bot.plugin.services.PluginInstallService;

public class HttpsReplacePluginInstall extends PluginInstallService implements PluginInstaller {

	@Override
	public void afterSaveBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterRemoveBatch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PluginBean beforeSaveBatch(PluginBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PluginBean beforeRemoveBatch(PluginBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAdminPanelHtmlTemplate(String tpl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPropertiesFile(String tpl) {
		// TODO Auto-generated method stub
		
	}

	
}
