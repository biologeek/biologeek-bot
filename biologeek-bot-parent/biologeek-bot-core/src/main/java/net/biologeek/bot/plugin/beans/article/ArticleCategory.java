package net.biologeek.bot.plugin.beans.article;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ArticleCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long articleCateggoryId;
	private long articleId;
	private long categoryId;
	private String fullWikipediaTitle;
	private String readableTitle;

	public long getArticleCateggoryId() {
		return articleCateggoryId;
	}

	public void setArticleCateggoryId(long articleCateggoryId) {
		this.articleCateggoryId = articleCateggoryId;
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
