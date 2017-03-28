package net.biologeek.bot.batch.beans;

import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.beans.batch.SpringBatchPluginBatch;

public class HttpsReplaceBatchBean extends PluginBean {
	
	public HttpsReplaceBatchBean() {
		this.description("Bot that replaces all links to their https equivalent if it is accessible")//
		.name("HttpsReplaceBot")//
		.batch(new SpringBatchPluginBatch());
	}
}
