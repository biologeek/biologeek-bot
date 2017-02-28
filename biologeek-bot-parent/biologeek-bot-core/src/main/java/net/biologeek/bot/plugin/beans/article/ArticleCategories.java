package net.biologeek.bot.plugin.beans.article;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity
public class ArticleCategories extends ArticleElement<List<ArticleCategory>> {
	
	@OneToMany
	private List<ArticleCategory> value;

	@Override
	public List<ArticleCategory> getValue() {
		return value;
	}

	@Override
	public void setValue(List<ArticleCategory> value) {
		this.value = value;
	}
 
}
