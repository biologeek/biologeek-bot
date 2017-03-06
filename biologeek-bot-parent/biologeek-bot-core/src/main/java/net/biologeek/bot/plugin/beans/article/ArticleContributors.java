package net.biologeek.bot.plugin.beans.article;

import java.util.List;

public class ArticleContributors extends ArticleElement<List<String>> {

	
	private List<String> value;

	@Override
	public List<String> getValue() {
		return value;
	}

	@Override
	public void setValue(List<String> value) {
		this.value = value;
	}

	public ArticleContributors id(Integer id) {
		this.id = id;
		return this;
	}

	public ArticleContributors title(String title) {
		this.title = title;
		return this;
	}

	public ArticleContributors value(List<String> value2) {
		this.value = value2;
		return this;
	}

}
