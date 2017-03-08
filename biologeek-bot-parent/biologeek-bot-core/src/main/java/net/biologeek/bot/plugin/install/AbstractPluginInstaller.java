package net.biologeek.bot.plugin.install;

import javax.persistence.Entity;

import net.biologeek.bot.plugin.beans.PluginBean;

@Entity
public abstract class AbstractPluginInstaller implements PluginInstaller {
	
	
	protected PluginBean bean;

	public AbstractPluginInstaller(PluginBean bean2) {
		this.bean = bean2;
	}

	public AbstractPluginInstaller() {
		this.bean = null;
	}

	public PluginBean getBean() {
		return bean;
	}

	public void setBean(PluginBean bean) {
		this.bean = bean;
	}
	
	
}
