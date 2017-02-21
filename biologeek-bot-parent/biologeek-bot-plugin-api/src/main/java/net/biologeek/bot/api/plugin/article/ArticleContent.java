package net.biologeek.bot.api.plugin.article;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArticleContent extends Article<String> {

	@JsonProperty("extract")
	private String value;

	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

}
