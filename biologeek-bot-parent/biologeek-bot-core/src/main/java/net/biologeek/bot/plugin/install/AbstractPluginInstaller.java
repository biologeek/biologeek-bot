package net.biologeek.bot.plugin.install;

import javax.persistence.OneToOne;

import net.biologeek.bot.plugin.beans.PluginBean;

public abstract class AbstractPluginInstaller implements PluginInstaller {
	
	
	@OneToOne
	private PluginBean bean;

	public PluginBean getBean() {
		return bean;
	}

	public void setBean(PluginBean bean) {
		this.bean = bean;
	}
	
	
}
