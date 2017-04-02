package net.biologeek.tests.application.converters;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import net.biologeek.bot.api.plugin.Period;
import net.biologeek.bot.api.plugin.PluginInstaller;
import net.biologeek.bot.plugin.beans.install.AbstractPluginInstaller;
import net.biologeek.bot.plugin.converter.PluginToModelConverter;

@SpringBootTest
public class PluginInstallerConverterTest extends AbstractConverterTest<AbstractPluginInstaller> {
	
	@Test
	public void shouldConvertBeanToModel() throws Exception{
		net.biologeek.bot.api.plugin.PluginInstaller bean = aPlugin(); 
		
		AbstractPluginInstaller modelBean = PluginToModelConverter.convertInstaller(bean);
		assertAllGetsAreNotNull(modelBean, Arrays.asList("getBean"));
		
	}

	private PluginInstaller aPlugin() {
		return new net.biologeek.bot.api.plugin.PluginInstaller()//
				.batchPeriod(new Period(new Date(), new Date()))//
				.id(1L)//
				.installerServiceClass("net.biologeek.bot.plugin.beans.install.SimplePluginInstaller")//
				.installerSpecificsClass("net.biologeek.bot.plugin.beans.install.DefaultPluginSpecificInstallerDelegate")
				.jarPath("/blabla/sfsdfs.jar");
	}

}
