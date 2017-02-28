package net.biologeek.bot.plugin.beans.article;

import javax.persistence.Entity;

@Entity
public class ArticleContent extends ArticleElement<String> {

	private String value;

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}
}
