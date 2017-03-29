package net.biologeek.bot.plugin.beans.install;

import javax.persistence.Entity;

import net.biologeek.bot.plugin.beans.Period;

@Entity
public class SimplePluginInstaller extends AbstractPluginInstaller {

	public SimplePluginInstaller batchPeriod(Period batchPeriodBegin) {
		this.batchPeriod = batchPeriodBegin;
		return this;
	}

	public SimplePluginInstaller installerService(String installerServiceClass) {
		this.installerService = installerServiceClass;
		return this;
	}

	public SimplePluginInstaller jarPath(String jarPath) {
		this.jarPath = jarPath;
		return this;
	}

}
