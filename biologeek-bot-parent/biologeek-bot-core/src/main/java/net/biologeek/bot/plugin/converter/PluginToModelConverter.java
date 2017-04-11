package net.biologeek.bot.plugin.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.biologeek.bot.api.plugin.ParameterGroup;
import net.biologeek.bot.api.plugin.ParametersList;
import net.biologeek.bot.api.plugin.PluginBean;
import net.biologeek.bot.api.plugin.PluginInstaller;
import net.biologeek.bot.plugin.beans.Period;
import net.biologeek.bot.plugin.beans.batch.SpringBatchPluginBatch;
import net.biologeek.bot.plugin.beans.install.AbstractPluginInstaller;
import net.biologeek.bot.plugin.beans.install.SimplePluginInstaller;
import net.biologeek.bot.plugin.beans.logs.BatchUnitRecord;
import net.biologeek.bot.plugin.exceptions.ConversionException;
import net.minidev.asm.ConvertDate;

public class PluginToModelConverter {

	private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");

	public static net.biologeek.bot.plugin.beans.PluginBean convert(PluginBean bean) throws ConversionException {
		return new net.biologeek.bot.plugin.beans.PluginBean()//
				.batch(convertBatch(bean.getBatch()))//
				.description(bean.getDescription())//
				.installer(convertInstaller(bean.getInstaller()))//
				.name(bean.getName())//
				.jarFile(bean.getJarFile());
	}

	public static AbstractPluginInstaller convertInstaller(PluginInstaller installer) throws ConversionException {
		return new SimplePluginInstaller()//
				.id(installer.getId()).batchPeriod(convert(installer.getBatchPeriod()))//
				.installerService(installer.getInstallerServiceClass())//
				.jarPath(installer.getJarPath());
	}

	public static Period convert(net.biologeek.bot.api.plugin.Period batchPeriod) {
		return new Period(batchPeriod.getBeginning(), batchPeriod.getEnd());
	}

	public static SpringBatchPluginBatch convertBatch(net.biologeek.bot.api.plugin.PluginBatch batch) {
		return new SpringBatchPluginBatch()//
				.batchPeriod(convert(batch.getBatchPeriod()))//
				.lastLaunchTime(batch.getLastLaunchTime()).job(batch.getJob())//
				.reader(batch.getReader())//
				.procesor(batch.getProcesor())//
				.writer(batch.getWriter())//
				.logs(convert(batch.getLogs()));
	}

	public static List<BatchUnitRecord> convert(List<net.biologeek.bot.api.plugin.BatchUnitRecord> logs) {
		List<BatchUnitRecord> records = new ArrayList<>();
		for (net.biologeek.bot.api.plugin.BatchUnitRecord log : logs) {
			records.add(convert(log));
		}
		return records;
	}

	public static BatchUnitRecord convert(net.biologeek.bot.api.plugin.BatchUnitRecord log) {
		return new BatchUnitRecord().beginningDate(log.getBeginningDate())//
				.endDate(log.getEndDate())//
				.exceptionClass(log.getExceptionClass())//
				.exitCode(log.getExitCode())//
				.logOutput(log.getLogOutput());//

	}

	/**
	 * Must be overriden for batch concrete classes
	 * 
	 * @param params
	 * @param bean
	 * @return
	 * @throws ParseException
	 */
	public static net.biologeek.bot.plugin.beans.PluginBean convert(ParametersList params,
			net.biologeek.bot.plugin.beans.PluginBean bean) throws ParseException {

		if (bean == null) {
			bean = new net.biologeek.bot.plugin.beans.PluginBean();
		}

		bean.description(params.getGroup(ParameterGroup.BEAN).getParameter("description").getValue())//
				.jarFile(params.getGroup(ParameterGroup.INSTALL).getParameter("jarPath").getValue())//
				.name(params.getGroup(ParameterGroup.BEAN).getParameter("name").getValue());

		if (bean.getBatch() != null) {
			bean.getBatch().setLastLaunchTime(
					convertDate(params.getGroup(ParameterGroup.BATCH).getParameter("lastLaunch").getValue()));
			bean.getBatch().setBatchPeriod(getPeriod(params));
			bean.getBatch().setTimeFrequency(
					Double.parseDouble(params.getGroup(ParameterGroup.BATCH).getParameter("frequency").getValue()));

			if (bean.getBatch() instanceof SpringBatchPluginBatch) {
				((SpringBatchPluginBatch) bean.getBatch())
						.job(params.getGroup(ParameterGroup.BATCH).getParameter("job").getValue());
				((SpringBatchPluginBatch) bean.getBatch())
						.writer(params.getGroup(ParameterGroup.BATCH).getParameter("writer").getValue());
				((SpringBatchPluginBatch) bean.getBatch())
						.reader(params.getGroup(ParameterGroup.BATCH).getParameter("reader").getValue());
				((SpringBatchPluginBatch) bean.getBatch())
						.procesor(params.getGroup(ParameterGroup.BATCH).getParameter("procesor").getValue());
			}
		}
		if (bean.getInstaller() != null) {
			bean.getInstaller().setBatchPeriod(getPeriod(params));
			bean.getInstaller().setInstallerService(
					params.getGroup(ParameterGroup.INSTALL).getParameter("installerServvice").getValue());
			bean.getInstaller().setJarPath(params.getGroup(ParameterGroup.INSTALL).getParameter("jarPath").getValue());
		}

		return bean;
	}

	private static Period getPeriod(ParametersList params) throws ParseException {
		return new Period(convertDate(params.getGroup(ParameterGroup.BATCH).getParameter("beginning").getValue()),
				convertDate(params.getGroup(ParameterGroup.BATCH).getParameter("end").getValue()));
	}

	private static Date convertDate(String value) throws ParseException {
		// TODO Auto-generated method stub
		return SDF.parse(value);
	}

}
