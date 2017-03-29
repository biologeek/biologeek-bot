package net.biologeek.bot.plugin.beans.install;

import java.io.FileNotFoundException;

import org.springframework.stereotype.Service;

import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.install.PluginSpecificInstallerDelegate;

/**
 * A default {@link PluginSpecificInstallerDelegate} implementation that
 * basically does... nothing ! <br>
 * <br>
 * No specific actions to performs. Everything is managed by default
 * configuration
 */
@Service
public class DefaultPluginSpecificInstallerDelegate implements PluginSpecificInstallerDelegate {

	public static final String DEFAULT_ADMIN_HTML_TEMPLATE = "<div id=\"inner-specific-config-container\">"
			+ "Nothing to parameter for this batch !" //
			+ "</div>";

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
		return bean;
	}

	@Override
	public PluginBean beforeRemoveBatch(PluginBean bean) {
		// TODO Auto-generated method stub
		return bean;
	}

	@Override
	public void setAdminPanelHtmlTemplate(String tpl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPropertiesFile(String tpl) throws FileNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getAdminPanelHtmlTemplate() {
		// TODO Auto-generated method stub
		return DEFAULT_ADMIN_HTML_TEMPLATE;
	}

	@Override
	public String getPropertiesFile() {
		// TODO Auto-generated method stub
		return null;
	}

}
