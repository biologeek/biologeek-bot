package net.biologeek.bot.plugin.converter;

import net.biologeek.bot.plugin.beans.article.ArticleCategories;
import net.biologeek.bot.plugin.beans.article.ArticleContent;
import net.biologeek.bot.plugin.beans.article.ArticleContributors;

public class ArticleConverter {

	public static ArticleContent convert(net.biologeek.bot.api.plugin.article.ArticleContent articleContent) {
		return new ArticleContent()//
				.id(Integer.valueOf(articleContent.getPageid()))//
				.title(articleContent.getTitle())//
				.value(articleContent.getValue());
	}

	public static ArticleCategories convert(
			net.biologeek.bot.api.plugin.article.ArticleCategories articleCategories) {
		return new ArticleCategories().id(Integer.valueOf(articleCategories.getPageid()))//
				.title(articleCategories.getTitle())//
				.value(articleCategories.getValue());
	}

	public static ArticleContributors convert(
			net.biologeek.bot.api.plugin.article.ArticleContributors articleContributors) {
		return new ArticleContributors()//
				.id(Integer.valueOf(articleContributors.getPageid()))//
				.title(articleContributors.getTitle())//
				.value(articleContributors.getValue());
	}

}
