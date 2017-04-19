package net.biologeek.bot.plugin.converter;

import java.util.ArrayList;
import java.util.List;

import net.biologeek.bot.api.plugin.BatchStatus;
import net.biologeek.bot.api.plugin.ParameterDataType;
import net.biologeek.bot.api.plugin.ParameterGroup;
import net.biologeek.bot.api.plugin.ParametersList;
import net.biologeek.bot.api.plugin.ParametersList.Parameter;
import net.biologeek.bot.api.plugin.ParametersList.ParametersGroup;
import net.biologeek.bot.api.plugin.Period;
import net.biologeek.bot.api.plugin.PluginBatch;
import net.biologeek.bot.api.plugin.PluginBean;
import net.biologeek.bot.api.plugin.PluginInstaller;
import net.biologeek.bot.plugin.beans.batch.SpringBatchPluginBatch;
import net.biologeek.bot.plugin.beans.install.AbstractPluginInstaller;

public class PluginToApiConverter {

	public static PluginBean convert(net.biologeek.bot.plugin.beans.PluginBean install) {

		return install == null ? null : new PluginBean()//
				.pluginId(install.getPluginId())//
				.description(install.getDescription())//
				.installed(install.getPluginId() > 0 ? true : false)//
				.name(install.getName())//
				.installer(convert(install.getInstaller()))//
				.batch(convert(install.getBatch()));
	}

	public static PluginBatch convert(net.biologeek.bot.plugin.beans.batch.PluginBatch batch) {
		return new PluginBatch()//
				.batchPeriod(convertPeriod(batch.getBatchPeriod()))//
				.className(batch.getClass().getName())//
				.timeFrequency(batch.getTimeFrequency())//
				.lastLaunchTime(batch.getLastLaunchTime())
				.status(BatchStatus.valueOf(batch.getStatus().name()));
	}

	private static Period convertPeriod(net.biologeek.bot.plugin.beans.Period batchPeriod) {
		return new Period(batchPeriod.getPeriodBeginning(), batchPeriod.getPeriodEnd());
	}

	private static PluginInstaller convert(AbstractPluginInstaller installer) {
		return installer == null ? null : new PluginInstaller()//
				.batchPeriod(convertPeriod(installer.getBatchPeriod()))//
				.id(installer.getId())//
				.installerServiceClass(installer.getInstallerService());
	}

	public static List<PluginBean> convert(List<net.biologeek.bot.plugin.beans.PluginBean> notInstalledPlugins) {
		List<PluginBean> result = new ArrayList<>();

		if (notInstalledPlugins.size() == 0)
			return new ArrayList<>();
		else {
			for (net.biologeek.bot.plugin.beans.PluginBean plugin : notInstalledPlugins) {
				result.add(convert(plugin));
			}
		}

		return result;
	}

	public static ParametersList convertParamsList(net.biologeek.bot.plugin.beans.PluginBean pluginById) {
		ParametersList list = new ParametersList();
		if (pluginById == null)
			return null;
		ParametersGroup batchGroup = new ParametersList.ParametersGroup()//
				.name(ParameterGroup.BATCH)//
				.labelKey("Batch parameters")//
				.parameters(convertBatchParams(pluginById.getBatch(), list));

		ParametersGroup beanGroup = new ParametersList.ParametersGroup()//
				.name(ParameterGroup.BEAN)//
				.labelKey("Plugin parameters")//
				.parameters(convertBeanParameters(pluginById, list));

		ParametersGroup installerGroup = new ParametersList.ParametersGroup()//
				.name(ParameterGroup.INSTALL)//
				.labelKey("Install parameters")//
				.parameters(convertInstallParameters(pluginById.getInstaller(), list));
		list.getGroups().add(batchGroup);
		list.getGroups().add(beanGroup);
		list.getGroups().add(installerGroup);

		return list;
	}

	private static List<Parameter> convertInstallParameters(AbstractPluginInstaller installer, ParametersList list) {
		// TODO Auto-generated method stub
		return null;
	}

	private static List<Parameter> convertBeanParameters(net.biologeek.bot.plugin.beans.PluginBean plugin,
			ParametersList list) {
		List<Parameter> params = new ArrayList<>();
		params.add(new ParametersList.Parameter().name("description").labelKey("parameters.plugin.description").value(String.valueOf(plugin.getDescription())));
		params.add(new ParametersList.Parameter().name("jarPath").labelKey("parameters.plugin.jar.path").value(String.valueOf(plugin.getJarFile())));
		params.add(new ParametersList.Parameter().name("name").labelKey("parameters.plugin.name").value(String.valueOf(plugin.getName())));
		return params;
	}

	private static List<Parameter> convertBatchParams(net.biologeek.bot.plugin.beans.batch.PluginBatch pluginBatch,
			ParametersList list) {
		if (pluginBatch == null)
			return null;
		List<Parameter> params = new ArrayList<>();
		params.add(new ParametersList.Parameter()//
				.name("beginning")//
				.labelKey("parameters.batch.beginning")//
				.type(ParameterDataType.DATE)//
				.value(String.valueOf(pluginBatch.getBatchPeriod().getPeriodBeginning().getTime())));
		params.add(new ParametersList.Parameter()//
				.name("end")//
				.labelKey("parameters.batch.end")//
				.type(ParameterDataType.DATE)//
				.value(String.valueOf(pluginBatch.getBatchPeriod().getPeriodEnd().getTime())));
		params.add(new ParametersList.Parameter()//
				.name("frequency")//
				.labelKey("parameters.batch.frequency")//
				.type(ParameterDataType.NUMERIC)//
				.value(String.valueOf(pluginBatch.getTimeFrequency())));
		params.add(new ParametersList.Parameter()//
				.name("lastLaunch")//
				.labelKey("parameters.batch.last.launch")//
				.type(ParameterDataType.DATE)//
				.value(pluginBatch.getLastLaunchTime() == null ? "0" : String.valueOf(pluginBatch.getLastLaunchTime().getTime())));

		if (pluginBatch instanceof SpringBatchPluginBatch) {
			SpringBatchPluginBatch realInstance = (SpringBatchPluginBatch) pluginBatch;

			params.add(new ParametersList.Parameter().name("job")//
					.labelKey("parameters.batch.job")//
					.type(ParameterDataType.CLASS)//
					.value(realInstance.getJob()));
			params.add(new ParametersList.Parameter().name("writer")//
					.type(ParameterDataType.CLASS)//
					.labelKey("parameters.batch.writer")//
					.value(realInstance.getWriter()));
			params.add(new ParametersList.Parameter().name("reader")//
					.labelKey("parameters.batch.reader")//
					.type(ParameterDataType.CLASS)//
					.value(realInstance.getReader()));
			params.add(new ParametersList.Parameter().name("procesor")//
					.labelKey("parameters.batch.procesor")//
					.type(ParameterDataType.CLASS)//
					.value(realInstance.getProcesor()));
		}
		
		return params;
	}

}
