package net.biologeek.bot.plugin.beans.article;

import javax.persistence.Entity;

@Entity
public class ArticleContent extends ArticleElement implements Valuable<String> {

	private String value;

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}
	
	
	public ArticleContent id(long id) {
		this.id = id;
		return this;
	}

	public ArticleContent title(String title) {
		this.title = title;
		return this;
	}

	public ArticleContent value(String value2) {
		this.value = value2;
		return this;
	}
	
}
