package net.biologeek.bot.batch.beans;

import javax.persistence.Entity;

import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.beans.article.ArticleContent;
import net.biologeek.bot.plugin.beans.batch.SpringBatchPluginBatch;


/**
 * The concrete Httpsreplace plugin batch
 */
@Entity
public class HttpsReplaceBatch extends SpringBatchPluginBatch<ArticleContent> {

	
	public HttpsReplaceBatch() {
		super();
	}
	
	public HttpsReplaceBatch(PluginBean bean) {
		super();
		this.setPlugin(bean);
	}
}
