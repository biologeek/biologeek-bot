package net.biologeek.bot.batch.beans;

import org.springframework.batch.item.ItemProcessor;

import net.biologeek.bot.plugin.beans.batch.SimpleCategoryMember;
import net.biologeek.bot.plugin.beans.batch.SimpleCategoryMembers;
import net.biologeek.bot.plugin.beans.category.CategoryMember;
import net.biologeek.bot.plugin.beans.category.CategoryMembers;

/**
 * Convenient ItemProcessor for purging sub categories and only treating
 * articles Converts to {@link SimpleCategoryMember}
 *
 */
public class PurgeSubCategoriesItemProcesor implements ItemProcessor<CategoryMembers, SimpleCategoryMembers> {

	@Override
	public SimpleCategoryMembers process(CategoryMembers arg0) throws Exception {
		SimpleCategoryMembers result = new SimpleCategoryMembers();
		for (CategoryMember member : arg0.getArticles()) {
			result.getMembers().add(new SimpleCategoryMember(member));
		}
		return result;
	}

}
