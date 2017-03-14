package net.biologeek.bot.plugin.services;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import net.biologeek.bot.plugin.beans.Prop;
import net.biologeek.bot.plugin.beans.article.ArticleCategories;
import net.biologeek.bot.plugin.beans.article.ArticleContent;
import net.biologeek.bot.plugin.beans.article.ArticleContributors;
import net.biologeek.bot.plugin.beans.article.ArticleElement;
import net.biologeek.bot.plugin.beans.category.CategoryMember;
import net.biologeek.bot.plugin.beans.category.CategoryMembers;
import net.biologeek.bot.plugin.converter.ArticleToModelConverter;
import net.biologeek.bot.plugin.converter.CategoryConverter;
import net.biologeek.bot.plugin.exceptions.ServiceException;
import net.biologeek.bot.wiki.client.Wikipedia;
import net.biologeek.bot.wiki.client.exceptions.APIException;
import net.biologeek.bot.wiki.client.exceptions.WikiException;

public class WikipediaApiService {

	@Autowired
	Wikipedia wikipedia;
	@Autowired
	private ArticleService<ArticleContent> articleContentService;

	@Value("${category.pattern.list}")
	String categoryListOfPatterns;

	public WikipediaApiService() {

	}

	public ArticleElement getArticleElement(String articleTitle, Prop prop, String[] extraArgs) {
		ArticleElement article = null;

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

	private ArticleContributors getArticleContributors(String articleTitle) {
		return ArticleToModelConverter.convert(wikipedia.getArticleContributors(articleTitle));
	}

	private ArticleElement getArticleModificationHistory(String articleTitle) {
		// TODO Auto-generated method stub
		return null;
	}

	private ArticleCategories getArticleCategories(String articleTitle) throws WikiException {
		return ArticleToModelConverter.convert(wikipedia.getArticleCategories(articleTitle));
	}

	private ArticleContent getArticleContent(String articleTitle) throws WikiException {
		return ArticleToModelConverter.convert(wikipedia.getArticleContent(articleTitle));
	}

	public Wikipedia getWikipedia() {
		return wikipedia;
	}

	public void setWikipedia(Wikipedia wikipedia) {
		this.wikipedia = wikipedia;
	}

	public CategoryMembers getCategoryMembers(String categoryToScan) throws ServiceException {
		CategoryMembers result = new CategoryMembers();
		net.biologeek.bot.api.plugin.category.CategoryMembers partialResult = new net.biologeek.bot.api.plugin.category.CategoryMembers();
		do {
			// First time cmContinue is null so it's ok
			try {
				partialResult = wikipedia.getCategoryMembers(categoryToScan, partialResult.getCmContinue());
			} catch (APIException e) {
				e.printStackTrace();
				throw new ServiceException(e.getMessage());
			}
			result.getArticles().addAll(extractArticlesFromList(CategoryConverter.convert(partialResult, categoryListOfPatterns)));
			result.getCategories().addAll(extractCategoriesFromList(CategoryConverter.convert(partialResult)));
		} while (partialResult.getCmContinue() != null);

		return result;
	}

	List<CategoryMember> extractCategoriesFromList(CategoryMembers partialResult) {
			partialResult.getArticles();
			return null;}

	private List<CategoryMember> extractArticlesFromList(CategoryMembers partialResult) {
		// TODO Auto-generated method stub
		return null;
	}
}
