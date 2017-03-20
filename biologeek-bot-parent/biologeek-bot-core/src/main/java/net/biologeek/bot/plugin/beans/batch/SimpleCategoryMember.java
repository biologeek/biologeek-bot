package net.biologeek.bot.plugin.beans.batch;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.biologeek.bot.plugin.beans.category.CategoryMember;

@Entity
@Table(name="flux.https_replace_articles")
/**
 * Same object but different use and thus different table
 */
public class SimpleCategoryMember extends CategoryMember {

	public SimpleCategoryMember(CategoryMember categories) {
		super();
		this.setId(categories.getId());
		this.setMediaType(categories.getMediaType());
		this.setPageId(categories.getPageId());
		this.setParentCategory(categories.getParentCategory());
		this.setTitle(categories.getTitle());
	}

}
