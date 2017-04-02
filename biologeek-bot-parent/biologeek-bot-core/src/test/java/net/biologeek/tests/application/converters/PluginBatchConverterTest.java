package net.biologeek.tests.application.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.springframework.batch.core.Job;

import net.biologeek.bot.api.plugin.Period;
import net.biologeek.bot.api.plugin.PluginBatch;
import net.biologeek.bot.plugin.beans.batch.SpringBatchPluginBatch;
import net.biologeek.bot.plugin.converter.PluginToModelConverter;

public class PluginBatchConverterTest extends AbstractConverterTest<SpringBatchPluginBatch> {

	@Test
	public void shouldConvertBeanToModel() throws Exception {
		PluginBatch bean = aBatch();

		SpringBatchPluginBatch modelBean = PluginToModelConverter.convertBatch(bean);
		assertAllGetsAreNotNull(modelBean, Arrays.asList("getPlugin"));

	}

	private PluginBatch aBatch() {
		
		return new net.biologeek.bot.api.plugin.PluginBatch()//
				.batchPeriod(new Period(new Date(), new Date()))//
				.className(SpringBatchPluginBatch.class.getName())//
				.logs(new ArrayList<>())//
				.timeFrequency(10)//
				.lastLaunchTime(new Date())//
				.processor("net.biologeek.bot.batch.beans.PurgeSubCategoriesItemProcesor")
				.job(Job.class.getName())//
				.reader("net.biologeek.bot.plugin.beans.batch.reader.WikipediaArticleItemReader")//
				.writer("net.biologeek.bot.plugin.beans.batch.writers.WikipediaArticleEditItemWriter");//
	}
}
