package net.biologeek.bot.plugin.beans.install;

import java.util.Date;

public class SimplePluginInstaller extends AbstractPluginInstaller {

	public SimplePluginInstaller batchPeriodBegin(Date batchPeriodBegin) {
		this.batchPeriodBegin = batchPeriodBegin;
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
