package net.biologeek.bot.plugin.converter;

import java.util.ArrayList;
import java.util.List;

import net.biologeek.bot.api.plugin.PluginBean;
import net.biologeek.bot.api.plugin.PluginInstaller;
import net.biologeek.bot.plugin.beans.Period;
import net.biologeek.bot.plugin.beans.batch.SpringBatchPluginBatch;
import net.biologeek.bot.plugin.beans.install.AbstractPluginInstaller;
import net.biologeek.bot.plugin.beans.install.SimplePluginInstaller;
import net.biologeek.bot.plugin.beans.logs.BatchUnitRecord;
import net.biologeek.bot.plugin.exceptions.ConversionException;

public class PluginToModelConverter {

	public static net.biologeek.bot.plugin.beans.PluginBean convert(PluginBean bean) throws ConversionException {
		return new net.biologeek.bot.plugin.beans.PluginBean()//
				.batch(convertBatch(bean.getBatch()))//
				.description(bean.getDescription())//
				.installer(convertInstaller(bean.getInstaller()));
	}

	private static AbstractPluginInstaller convertInstaller(PluginInstaller installer) throws ConversionException {
		try {
			if (installer.getInstallerServiceClass() == null ||Class.forName(installer.getInstallerServiceClass()).isInstance(SpringBatchPluginBatch.class))
					return new SimplePluginInstaller()//
							.batchPeriod(convert(installer.getBatchPeriod()))//
							.installerService(installer.getInstallerServiceClass())//
							.jarPath(installer.getJarPath());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ConversionException("Could not convert installer");
		}
		return null;
	}


	private static Period convert(net.biologeek.bot.api.plugin.Period batchPeriod) {
		return new Period(batchPeriod.getBeginning(), batchPeriod.getEnd());
	}

	private static SpringBatchPluginBatch convertBatch(net.biologeek.bot.api.plugin.PluginBatch batch) {
			return new SpringBatchPluginBatch()//
					.batchPeriod(convertPeriod(batch.getBatchPeriod()))//
					.lastLaunchTime(batch.getLastLaunchTime()).job(batch.getJob())//
					.reader(batch.getReader())//
					.procesor(batch.getProcesor())//
					.writer(batch.getWriter())//
					.logs(convert(batch.getLogs()));
	}

	private static List<BatchUnitRecord> convert(List<net.biologeek.bot.api.plugin.BatchUnitRecord> logs) {
		List<BatchUnitRecord> records = new ArrayList<>();
		for (net.biologeek.bot.api.plugin.BatchUnitRecord log : logs) {
			records.add(convert(log));
		}
		return records;
	}

	private static BatchUnitRecord convert(net.biologeek.bot.api.plugin.BatchUnitRecord log) {
		return new BatchUnitRecord().beginningDate(log.getBeginningDate())//
				.endDate(log.getEndDate())//
				.exceptionClass(log.getExceptionClass())//
				.exitCode(log.getExitCode())//
				.logOutput(log.getLogOutput());//

	}

	private static Period convertPeriod(net.biologeek.bot.api.plugin.Period batchPeriod) {
		return new Period(batchPeriod.getBeginning(), batchPeriod.getEnd());
	}

}
