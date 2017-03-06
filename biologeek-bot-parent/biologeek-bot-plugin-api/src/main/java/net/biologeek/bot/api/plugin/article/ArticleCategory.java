package net.biologeek.bot.api.plugin.article;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArticleCategory {

	@JsonProperty("title")
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArticleCategory title(String string) {
		this.title = string;
		return this;
	}
}
