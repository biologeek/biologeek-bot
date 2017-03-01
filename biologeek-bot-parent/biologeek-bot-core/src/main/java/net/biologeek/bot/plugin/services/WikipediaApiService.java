package net.biologeek.bot.plugin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import net.biologeek.bot.plugin.beans.Prop;
import net.biologeek.bot.plugin.beans.article.ArticleCategories;
import net.biologeek.bot.plugin.beans.article.ArticleCategory;
import net.biologeek.bot.plugin.beans.article.ArticleContent;
import net.biologeek.bot.plugin.beans.article.ArticleElement;
import net.biologeek.bot.plugin.converter.ArticleConverter;
import net.biologeek.bot.wiki.client.Wikipedia;
import net.biologeek.bot.wiki.client.exceptions.WikiException;

public class WikipediaApiService {

	@Autowired
	Wikipedia wikipedia;
	@Autowired
	private ArticleService articleService;

	public WikipediaApiService() {

	}

	public ArticleElement getArticleElement(String articleTitle, Prop prop, String[] extraArgs) {
		ArticleElement article = null;

		article = getArticleInstance(prop, article);
		try {
			switch (prop) {
			case CONTENT:
				return getArticleContent(articleTitle);
			case BELONG_TO_CATEGORIES:
				return getArticleCategories(articleTitle);
			case CONTRIBUTORS:
				return getArticleContributors(articleTitle);
			case MODIFICATION_HISTORY:
				return getArticleModificationHistory(articleTitle);
			case SUB_CATEGORIES:
				throw new IllegalArgumentException("Articles don't have subcategories");
			case TITLE:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return article;
	}

	private ArticleElement getArticleContributors(String articleTitle) {
		// TODO Auto-generated method stub
		return null;
	}

	private ArticleElement getArticleModificationHistory(String articleTitle) {
		// TODO Auto-generated method stub
		return null;
	}

	private ArticleElement getArticleCategories(String articleTitle) throws WikiException {
		return ArticleConverter.convert(wikipedia.getArticleCategories(articleTitle));
	}

	private ArticleContent getArticleContent(String articleTitle) throws WikiException {
		return ArticleConverter.convert(wikipedia.getArticleContent(articleTitle));
	}

	private ArticleElement getArticleInstance(Prop prop, ArticleElement article) {
		switch (prop) {
		case CONTENT:
			article = new ArticleContent();
			break;
		case BELONG_TO_CATEGORIES:
			article = new ArticleCategories();
			break;
		case CONTRIBUTORS:
			break;
		case MODIFICATION_HISTORY:
			break;
		case SUB_CATEGORIES:
			break;
		case TITLE:
			break;
		}
		return article;
	}

	public Wikipedia getWikipedia() {
		return wikipedia;
	}

	public void setWikipedia(Wikipedia wikipedia) {
		this.wikipedia = wikipedia;
	}

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
}
