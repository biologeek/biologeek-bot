package net.biologeek.bot.plugin.beans.article;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ArticleCategory {

	@Id@GeneratedValue(strategy = GenerationType.AUTO)
	private long articleCategoryId;
	private long articleId;
	private long categoryId;
	private String fullWikipediaTitle;
	private String readableTitle;
	
	@ManyToOne
	private ArticleCategories articleCategories;

	public long getArticleCategoryId() {
		return articleCategoryId;
	}

	public void setArticleCategoryId(long articleCateggoryId) {
		this.articleCategoryId = articleCateggoryId;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getFullWikipediaTitle() {
		return fullWikipediaTitle;
	}

	public void setFullWikipediaTitle(String fullWikipediaTitle) {
		this.fullWikipediaTitle = fullWikipediaTitle;
	}

	public String getReadableTitle() {
		return readableTitle;
	}

	public void setReadableTitle(String readableTitle) {
		this.readableTitle = readableTitle;
	}

}
