package net.biologeek.bot.plugin.converter;

import net.biologeek.bot.api.plugin.Period;
import net.biologeek.bot.api.plugin.PluginBatch;
import net.biologeek.bot.api.plugin.PluginBean;
import net.biologeek.bot.api.plugin.PluginInstaller;
import net.biologeek.bot.plugin.beans.install.AbstractPluginInstaller;

public class PluginToApiConverter {

	public static PluginBean convert(net.biologeek.bot.plugin.beans.PluginBean install) {
		
		return new PluginBean().pluginId(install.getPluginId())//
				.description(install.getDescription())//
				.installed(install.getPluginId() > 0 ? true : false)//
				.name(install.getName())
				.installer(convert(install.getInstaller()))
				.batch(convert(install.getBatch()));
	}

	private static PluginBatch convert(net.biologeek.bot.plugin.beans.batch.PluginBatch batch) {
		return new PluginBatch()//
				.batchPeriod(convertPeriod(batch.getBatchPeriod()))//
				.className(batch.getClass().getName())//
				.timeFrequency(batch.getTimeFrequency());
	}

	private static Period convertPeriod(net.biologeek.bot.plugin.beans.Period batchPeriod) {
		return new Period(batchPeriod.getBeginning(), batchPeriod.getEnd());
	}

	private static PluginInstaller convert(AbstractPluginInstaller installer) {
		return new PluginInstaller()//
				.batchPeriodBegin(installer.getBatchPeriodBegin())//
				.id(installer.getId())//
				.installerServiceClass(installer.getInstallerService())//
				.batchPeriodEnd(installer.getBatchPeriodEnd());
	}

}
