package net.biologeek.bot.api.plugin.article;

import java.util.List;

public class ArticleContributors extends Article<List<String>>{

	private List<String> value;

	@Override
	public void setValue(List<String> value) {
		this.value = value;
	}

	public List<String> getValue() {
		return value;
	}
}
