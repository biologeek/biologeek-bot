package net.biologeek.bot.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.biologeek.bot.plugin.PluginBatch;
import net.biologeek.bot.plugin.PluginBean;
import net.biologeek.bot.plugin.article.ArticleCategories;
import net.biologeek.bot.plugin.article.ArticleContent;
import net.biologeek.bot.plugin.category.Category;
import net.biologeek.bot.plugin.login.User;
import net.biologeek.bot.plugin.serialization.ContentQueryType;
import net.biologeek.bot.wiki.client.Wikipedia;

@SpringBootApplication
public class HttpsReplaceBatchEntryPoint extends PluginBatch {
	
	PluginBean bean;

	
	public void execute(String[] params) {
		
		SpringApplication.run(this.getClass(), formatParams(params));

	}

	private String formatParams(String[] params) {
		return String.join(" ", params);
	}

	public PluginBean getBean() {
		return bean;
	}

	public void setBean(PluginBean bean) {
		this.bean = bean;
	}
}
