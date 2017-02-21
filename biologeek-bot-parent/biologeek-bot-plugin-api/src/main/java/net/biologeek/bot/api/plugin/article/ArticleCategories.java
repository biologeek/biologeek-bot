package net.biologeek.bot.api.plugin.article;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArticleCategories extends Article<List<ArticleCategory>> {

	
	@JsonProperty("categories")
	List<ArticleCategory> value;
	
	@Override
	public void setValue(List<ArticleCategory> value) {
		this.value= value;		
	}

	public List<ArticleCategory> getValue() {
		return value;
	}

}
