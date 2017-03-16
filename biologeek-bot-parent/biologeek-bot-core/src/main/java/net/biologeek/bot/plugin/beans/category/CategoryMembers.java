package net.biologeek.bot.plugin.beans.category;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
/**
 * Consists of a Category object defined by its id and/or title and composed of
 * sub articles and sub categories (for the moment)
 * 
 * It has a builder pattern !
 */
public class CategoryMembers {

	@Id
	@GeneratedValue
	private Long id;
	private String categoryId;
	private String categoryTitle;

	@OneToMany
	private List<CategoryMember> articles;

	@OneToMany
	private List<CategoryMember> categories;

	public CategoryMembers() {
		super();
		this.articles = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public List<CategoryMember> getArticles() {
		return articles;
	}

	public void setArticles(List<CategoryMember> articles) {
		this.articles = articles;
	}

	public List<CategoryMember> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryMember> categories) {
		this.categories = categories;
	}

	public CategoryMembers articles(List<CategoryMember> articles2) {
		this.setArticles(articles2);
		return this;
	}

	public CategoryMembers categories(List<CategoryMember> articles2) {
		this.setCategories(articles2);
		return this;
	}

	public CategoryMembers categoryId(String id) {
		this.categoryId = id;
		return this;
	}

	public CategoryMembers categoryTitle(String object) {
		this.setCategoryTitle(object);
		return this;
	}

}
