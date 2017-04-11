package net.biologeek.bot.plugin.converter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.biologeek.bot.api.plugin.ParameterGroup;
import net.biologeek.bot.api.plugin.ParametersList;
import net.biologeek.bot.api.plugin.Period;
import net.biologeek.bot.api.plugin.PluginBatch;
import net.biologeek.bot.api.plugin.PluginBean;
import net.biologeek.bot.api.plugin.PluginInstaller;
import net.biologeek.bot.api.plugin.ParametersList.Parameter;
import net.biologeek.bot.api.plugin.ParametersList.ParametersGroup;
import net.biologeek.bot.plugin.beans.batch.SpringBatchPluginBatch;
import net.biologeek.bot.plugin.beans.install.AbstractPluginInstaller;

public class PluginToApiConverter {

	public static PluginBean convert(net.biologeek.bot.plugin.beans.PluginBean install) {

		return new PluginBean().pluginId(install.getPluginId())//
				.description(install.getDescription())//
				.installed(install.getPluginId() > 0 ? true : false)//
				.name(install.getName()).installer(convert(install.getInstaller())).batch(convert(install.getBatch()));
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

		ParametersGroup batchGroup = list.new ParametersGroup()//
				.name(ParameterGroup.BATCH)//
				.parameters(convertBatchParams(pluginById.getBatch(), list));

		ParametersGroup beanGroup = list.new ParametersGroup()//
				.name(ParameterGroup.BEAN)//
				.parameters(convertBeanParameters(pluginById, list));

		ParametersGroup installerGroup = list.new ParametersGroup()//
				.name(ParameterGroup.INSTALL)//
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
		params.add(list.new Parameter().name("description").value(String.valueOf(plugin.getDescription())));
		params.add(list.new Parameter().name("jarPath").value(String.valueOf(plugin.getJarFile())));
		params.add(list.new Parameter().name("name").value(String.valueOf(plugin.getName())));
		return params;
	}

	private static List<Parameter> convertBatchParams(net.biologeek.bot.plugin.beans.batch.PluginBatch pluginBatch,
			ParametersList list) {
		List<Parameter> params = new ArrayList<>();
		params.add(list.new Parameter().name("beginning")//
				.value(String.valueOf(pluginBatch.getBatchPeriod().getBeginning().getTime())));
		params.add(list.new Parameter().name("end")//
				.value(String.valueOf(pluginBatch.getBatchPeriod().getEnd().getTime())));
		params.add(list.new Parameter().name("frequency")//
				.value(String.valueOf(pluginBatch.getTimeFrequency())));
		params.add(list.new Parameter().name("lastLaunch")//
				.value(String.valueOf(pluginBatch.getLastLaunchTime().getTime())));
		
		if (pluginBatch instanceof SpringBatchPluginBatch){
			SpringBatchPluginBatch realInstance = (SpringBatchPluginBatch) pluginBatch;
			
			params.add(list.new Parameter().name("job").value(realInstance.getJob()));
			params.add(list.new Parameter().name("writer").value(realInstance.getWriter()));
			params.add(list.new Parameter().name("reader").value(realInstance.getReader()));
			params.add(list.new Parameter().name("procesor").value(realInstance.getProcesor()));
		}
		return params;
	}

}
