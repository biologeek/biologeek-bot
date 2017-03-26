package net.biologeek.bot.plugin.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.transform.ConversionException;

import net.biologeek.bot.api.plugin.PluginBean;
import net.biologeek.bot.api.plugin.PluginInstaller;
import net.biologeek.bot.plugin.beans.Period;
import net.biologeek.bot.plugin.beans.batch.SpringBatchPluginBatch;
import net.biologeek.bot.plugin.beans.install.AbstractPluginInstaller;
import net.biologeek.bot.plugin.beans.logs.BatchUnitRecord;

public class PluginToModelConverter {

	public static net.biologeek.bot.plugin.beans.PluginBean convert(PluginBean bean) {
		return new net.biologeek.bot.plugin.beans.PluginBean()//
				.batch(convertBatch(bean.getBatch()))//
				.description(bean.getDescription())//
				.installer(convertInstaller(bean.getInstaller()));
	}

	private static AbstractPluginInstaller convertInstaller(PluginInstaller installer) {
		// TODO Auto-generated method stub
		return null;
	}

	private static SpringBatchPluginBatch<?> convertBatch(net.biologeek.bot.api.plugin.PluginBatch batch) {
		try {
			return new SpringBatchPluginBatch<>()//
					.batchPeriod(convertPeriod(batch.getBatchPeriod()))//
					.lastLaunchTime(batch.getLastLaunchTime()).job((Job) Class.forName(batch.getJob()).newInstance())//
					.reader((ItemReader) Class.forName(batch.getReader()).newInstance())//
					.procesor((ItemProcessor) Class.forName(batch.getProcesor()).newInstance())//
					.writer((ItemWriter) Class.forName(batch.getWriter()).newInstance()).logs(convert(batch.getLogs()));
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new ConversionException(e.getMessage());
		}
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
		// TODO Auto-generated method stub
		return null;
	}

}
