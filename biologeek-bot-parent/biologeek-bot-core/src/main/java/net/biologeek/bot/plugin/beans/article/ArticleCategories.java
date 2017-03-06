package net.biologeek.bot.plugin.beans.article;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import net.biologeek.bot.api.plugin.article.ArticleCategory;


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
