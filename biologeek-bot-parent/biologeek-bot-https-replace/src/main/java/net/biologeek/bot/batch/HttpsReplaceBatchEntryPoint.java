package net.biologeek.bot.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.biologeek.bot.plugin.PluginBatch;
import net.biologeek.bot.plugin.article.ArticleCategories;
import net.biologeek.bot.plugin.article.ArticleContent;
import net.biologeek.bot.plugin.category.Category;
import net.biologeek.bot.plugin.login.User;
import net.biologeek.bot.plugin.serialization.ContentQueryType;
import net.biologeek.bot.wiki.client.Wikipedia;

@SpringBootApplication
public class HttpsReplaceBatchEntryPoint implements PluginBatch {

	public static void main(String[] args) throws Exception{
		
		User user = new User();
		user.setPassword("iliM,57.050");
		user.setUsername("xcaron");
		Wikipedia client = new Wikipedia.WikipediaBuilder(new Wikipedia())//
				.baseURL("https://fr.wikipedia.org/w/")//
				.maxLogins(5)//
				.tokenMinLength(4)//
				.build();
		
		
		ArticleCategories article =  client.getArticleCategories("Tintin");
		
		System.out.println(article);
		
	}
	public void execute(String[] params) {
		
		SpringApplication.run(this.getClass(), formatParams(params));

	}

	private String formatParams(String[] params) {
		return String.join(" ", params);
	}

}
