package net.biologeek.bot.plugin.converter;

import net.biologeek.bot.plugin.beans.article.ArticleCategories;
import net.biologeek.bot.plugin.beans.article.ArticleContent;

public class ArticleConverter {

	public static ArticleContent convert(net.biologeek.bot.api.plugin.article.ArticleContent articleContent) {
		return new ArticleContent().id(Integer.valueOf(articleContent.getPageid())).title(articleContent.getTitle())//
				.value(articleContent.getValue());
	}

	public static ArticleCategories convert(
			net.biologeek.bot.api.plugin.article.ArticleCategories articleCategories) {
		// TODO Auto-generated method stub
		return null;
	}

}
