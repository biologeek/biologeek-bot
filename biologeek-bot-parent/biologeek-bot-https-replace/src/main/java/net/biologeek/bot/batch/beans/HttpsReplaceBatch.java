package net.biologeek.bot.batch.beans;

import java.util.ArrayList;

import net.biologeek.bot.batch.HttpsReplaceJob;
import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.beans.batch.PluginBatch;
import net.biologeek.bot.plugin.beans.logs.BatchUnitRecord;

public class HttpsReplaceBatch extends PluginBatch {


	public HttpsReplaceBatch() {
		super();
		this.setJobClass(HttpsReplaceJob.class);
		this.setLogs(new ArrayList<BatchUnitRecord>());
	}
	
	public HttpsReplaceBatch(PluginBean bean) {
		super();
		this.setJobClass(HttpsReplaceJob.class);
		this.setPlugin(bean);
		this.setLogs(new ArrayList<BatchUnitRecord>());
	}
}
