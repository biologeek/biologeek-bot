package net.biologeek.bot.plugin.beans.batch.readers;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import net.biologeek.bot.plugin.beans.article.ArticleContent;
import net.biologeek.bot.plugin.beans.batch.SimpleCategoryMember;
import net.biologeek.bot.plugin.services.SimpleCategoryMembersServices;
import net.biologeek.bot.plugin.services.WikipediaApiService;

public class WikipediaArticleItemReader implements ItemReader<ArticleContent> {

	@Autowired
	private WikipediaApiService wikipedia;
	
	@Autowired
	SimpleCategoryMembersServices scmServices;
	
	private String categoryToScan;
	
	@Override
	public ArticleContent read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		ArticleContent res = new ArticleContent();
		SimpleCategoryMember scm = scmServices.getNextItemToRead();
		
		if (scm != null){
			res = wikipedia.getArticleContent(scm.getTitle());
		}
		return res;
	}

	public String getCategoryToScan() {
		return categoryToScan;
	}

	public void setCategoryToScan(String categoryToScan) {
		this.categoryToScan = categoryToScan;
	}
	

}
