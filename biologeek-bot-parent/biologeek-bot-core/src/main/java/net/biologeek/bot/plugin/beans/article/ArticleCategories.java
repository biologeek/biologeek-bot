package net.biologeek.bot.plugin.beans.article;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class ArticleCategories extends ArticleElement implements Valuable<List<ArticleCategory>> {
	
	@OneToMany
	private List<ArticleCategory> value;

	@Override
	public List<ArticleCategory> getValue() {
		return value;
	}

	public void setValue(List<ArticleCategory> value) {
		this.value = value;
	}

	public ArticleCategories id(Integer id) {
		this.id = id;
		return this;
	}

	public ArticleCategories title(String title) {
		this.title = title;
		return this;
	}

	public ArticleCategories value(List<ArticleCategory> value2) {
		this.value = value2;
		return this;
	}
 
}
