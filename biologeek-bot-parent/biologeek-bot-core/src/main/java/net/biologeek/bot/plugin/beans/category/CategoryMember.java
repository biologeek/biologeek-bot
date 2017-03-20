package net.biologeek.bot.plugin.beans.category;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import net.biologeek.bot.plugin.beans.batch.SimpleCategoryMember;

@Entity
public class CategoryMember {

	@Id@GeneratedValue
	private Long id;
	private String pageId;
	private String title;
	@Enumerated(EnumType.ORDINAL)
	private MediaType mediaType;
	@ManyToOne
	private CategoryMembers parentCategory;
	
	
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public CategoryMember pageId(String pageid2) {
		this.pageId = pageid2;
		return this;
	}
	public CategoryMember title(String title2) {
		this.title = title2;
		return this;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MediaType getMediaType() {
		return mediaType;
	}
	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}
	public CategoryMembers getParentCategory() {
		return parentCategory;
	}
	public void setParentCategory(CategoryMembers parentCategory) {
		this.parentCategory = parentCategory;
	}
	public CategoryMember mediaType(MediaType convertMediaType) {
		this.mediaType = convertMediaType;
		return this;
	}
	public CategoryMember parentCategory(CategoryMembers object) {
		this.parentCategory = object;
		return this;
	}
	public CategoryMember id(Long id2) {
		this.id = id2;
		return this;
	}
}
