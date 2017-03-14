package net.biologeek.bot.plugin.beans.batch.readers;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import net.biologeek.bot.api.plugin.article.ArticleContent;
import net.biologeek.bot.plugin.beans.category.CategoryMembers;
import net.biologeek.bot.plugin.services.WikipediaApiService;

public class WikipediaArticleItemReader implements ItemReader<ArticleContent> {

	@Autowired
	private WikipediaApiService wikipedia;
	
	private String categoryToScan;
	
	@Override
	public ArticleContent read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		CategoryMembers catMembers = wikipedia.getCategoryMembers(categoryToScan);
		
		return null;
	}

	public String getCategoryToScan() {
		return categoryToScan;
	}

	public void setCategoryToScan(String categoryToScan) {
		this.categoryToScan = categoryToScan;
	}
	

}
