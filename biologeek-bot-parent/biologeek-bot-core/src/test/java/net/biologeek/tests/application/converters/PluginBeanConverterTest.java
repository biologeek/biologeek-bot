package net.biologeek.tests.application.converters;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import net.biologeek.bot.api.plugin.Period;
import net.biologeek.bot.api.plugin.PluginBatch;
import net.biologeek.bot.api.plugin.PluginInstaller;
import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.converter.PluginToModelConverter;

@SpringBootTest
public class PluginBeanConverterTest extends AbstractConverterTest<PluginBean> {
	
	@Test
	public void shouldConvertBeanToModel() throws Exception{
		net.biologeek.bot.api.plugin.PluginBean bean = aPlugin(); 
		
		PluginBean modelBean = PluginToModelConverter.convert(bean);
		assertAllGetsAreNotNull(modelBean, null);
		
	}

	private net.biologeek.bot.api.plugin.PluginBean aPlugin() {
		// TODO Auto-generated method stub
		return new net.biologeek.bot.api.plugin.PluginBean()//
				.batch(new PluginBatch()//
						.batchPeriod(new Period(new Date(), new Date())).logs(new ArrayList<>()))//
				.description("description")//
				.installed(true)//
				.installer(new PluginInstaller().batchPeriod(new Period(new Date(), new Date())))//
				.name("name")//
				.jarFile("/home/bob/jar.jar")//
				.pluginId(1);
	}

}
