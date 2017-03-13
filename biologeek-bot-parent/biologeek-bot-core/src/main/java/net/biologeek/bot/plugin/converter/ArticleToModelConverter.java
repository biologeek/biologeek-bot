package net.biologeek.bot.plugin.converter;

import java.util.ArrayList;
import java.util.List;

import net.biologeek.bot.plugin.beans.article.ArticleCategories;
import net.biologeek.bot.plugin.beans.article.ArticleCategory;
import net.biologeek.bot.plugin.beans.article.ArticleContent;
import net.biologeek.bot.plugin.beans.article.ArticleContributors;

public class ArticleToModelConverter {

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
				.value(convert(articleCategories.getValue()));
	}

	private static List<ArticleCategory> convert(List<net.biologeek.bot.api.plugin.article.ArticleCategory> value) {
		List<ArticleCategory> cats = new ArrayList<>();
		for(net.biologeek.bot.api.plugin.article.ArticleCategory val : value){
			cats.add(convert(val));
		}
		return cats;
	}

	private static ArticleCategory convert(net.biologeek.bot.api.plugin.article.ArticleCategory val) {
		ArticleCategory cat = new ArticleCategory();
		
		cat.setFullWikipediaTitle(val.getTitle());
		cat.setReadableTitle(val.getTitle());
		//TODO : Rajouter l'ID
		return cat;
	}

	public static ArticleContributors convert(
			net.biologeek.bot.api.plugin.article.ArticleContributors articleContributors) {
		return new ArticleContributors()//
				.id(Integer.valueOf(articleContributors.getPageid()))//
				.title(articleContributors.getTitle())//
				.value(articleContributors.getValue());
	}

}
