package net.biologeek.bot.plugin.services;

import org.springframework.beans.factory.annotation.Autowired;

import net.biologeek.bot.plugin.beans.Prop;
import net.biologeek.bot.plugin.beans.article.ArticleCategories;
import net.biologeek.bot.plugin.beans.article.ArticleContent;
import net.biologeek.bot.plugin.beans.article.ArticleElement;
import net.biologeek.bot.wiki.client.Wikipedia;

public class WikipediaApiService {

	@Autowired
	Wikipedia wikipedia;
	@Autowired
	private ArticleService articleService;

	public WikipediaApiService() {

	}

	public ArticleElement getArticleElement(long articleId, Prop prop, String[] extraArgs) {
		ArticleElement article = null;

		article = getArticleInstance(prop, article);

		articleService = 
		
		return article;
	}

	private ArticleElement getArticleInstance(Prop prop, ArticleElement article) {
		switch (prop) {
		case CONTENT:
			article = new ArticleContent();
			break;
		case BELONG_TO_CATEGORIES:
			article = new ArticleCategories();
			break;
		}
		return article;
	}
}
