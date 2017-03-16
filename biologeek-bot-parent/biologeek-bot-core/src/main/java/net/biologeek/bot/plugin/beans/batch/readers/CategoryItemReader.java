package net.biologeek.bot.plugin.beans.batch.readers;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import net.biologeek.bot.plugin.beans.category.CategoryMembers;
import net.biologeek.bot.plugin.converter.CategoryConverter;
import net.biologeek.bot.wiki.client.Wikipedia;

public class CategoryItemReader implements ItemReader<CategoryMembers> {

	private String categoryTitle;

	@Autowired
	Wikipedia wikipedia;

	@Value("${category.pattern.list}")
	String categoryListOfPatterns;

	@Override
	public CategoryMembers read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (categoryTitle == null)
			throw new IllegalArgumentException("Forgot to init title");
		CategoryMembers result = new CategoryMembers();
		net.biologeek.bot.api.plugin.category.CategoryMembers partialResult = new net.biologeek.bot.api.plugin.category.CategoryMembers();
		
		do {
			CategoryMembers members = CategoryConverter.convert(
					wikipedia.getCategoryMembers(categoryTitle, partialResult.getCmContinue()), categoryListOfPatterns
			);
			result.getArticles().addAll(members.getArticles());
			result.getCategories().addAll(members.getCategories());
			
		} while(partialResult.getCmContinue() != null);
		
		return result;
	}

	public ItemReader<CategoryMembers> title(String categoryToScan) {
		if (categoryToScan.equals(""))
			throw new IllegalArgumentException();
		else
			this.setCategoryTitle(categoryToScan);
		return this;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public Wikipedia getWikipedia() {
		return wikipedia;
	}

	public void setWikipedia(Wikipedia wikipedia) {
		this.wikipedia = wikipedia;
	}

}
