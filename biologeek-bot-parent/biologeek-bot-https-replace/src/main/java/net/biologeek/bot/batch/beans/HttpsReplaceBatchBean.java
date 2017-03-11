package net.biologeek.bot.batch.beans;

import net.biologeek.bot.plugin.beans.PluginBean;

public class HttpsReplaceBatchBean extends PluginBean {
	
	public HttpsReplaceBatchBean() {
		this.setDescription("Bot that replaces all links to their https equivalent if it is accessible");
		this.setName("HttpsReplaceBot");
		this.setBatch(new HttpsReplaceBatch());
	}
}