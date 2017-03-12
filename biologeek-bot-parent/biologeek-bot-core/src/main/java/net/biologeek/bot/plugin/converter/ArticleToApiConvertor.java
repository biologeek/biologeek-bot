package net.biologeek.bot.plugin.converter;

import java.util.ArrayList;
import java.util.List;

import net.biologeek.bot.api.plugin.article.ArticleCategories;
import net.biologeek.bot.api.plugin.article.ArticleCategory;
import net.biologeek.bot.api.plugin.article.ArticleContent;
import net.biologeek.bot.api.plugin.article.ArticleContributors;

public class ArticleToApiConvertor {

	public static ArticleContent convert(net.biologeek.bot.plugin.beans.article.ArticleContent articleContent) {
		ArticleContent result = new ArticleContent();//
		result.setPageid(String.valueOf(articleContent.getPageId()));
		result.setTitle(articleContent.getTitle());
		result.setValue(articleContent.getValue());
		return result;
	}

	public static ArticleCategories convert(
			net.biologeek.bot.plugin.beans.article.ArticleCategories articleCategories) {
		ArticleCategories ar = new ArticleCategories();
		ar.setPageid(String.valueOf(articleCategories.getPageId()));
		ar.setTitle(articleCategories.getTitle());
		ar.setValue(convert(articleCategories.getValue()));
		return ar;
	}

	private static List<ArticleCategory> convert(List<net.biologeek.bot.plugin.beans.article.ArticleCategory> value) {
		List<ArticleCategory> cats = new ArrayList<>();
		for (net.biologeek.bot.plugin.beans.article.ArticleCategory val : value) {
			cats.add(convert(val));
		}
		return cats;
	}

	private static ArticleCategory convert(net.biologeek.bot.plugin.beans.article.ArticleCategory val) {
		ArticleCategory cat = new ArticleCategory();

		cat.setTitle(val.getFullWikipediaTitle());
		return cat;
	}

	public static ArticleContributors convert(
			net.biologeek.bot.api.plugin.article.ArticleContributors articleContributors) {
		ArticleContributors con = new ArticleContributors();
		con.setPageid(String.valueOf(articleContributors.getPageid()));
		con.setTitle(articleContributors.getTitle());
		con.setValue(articleContributors.getValue());
		return con;
	}

}
