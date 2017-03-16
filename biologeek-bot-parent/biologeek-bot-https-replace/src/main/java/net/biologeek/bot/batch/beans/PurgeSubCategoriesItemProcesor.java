package net.biologeek.bot.batch.beans;

import org.springframework.batch.item.ItemProcessor;

import net.biologeek.bot.plugin.beans.batch.SimpleCategoryMember;
import net.biologeek.bot.plugin.beans.category.CategoryMembers;
import net.biologeek.bot.plugin.converter.CategoryConverter;

/**
 * Convenient ItemProcessor for purging sub categories and only treating articles
 * Converts to {@link SimpleCategoryMember}
 *
 */
public class PurgeSubCategoriesItemProcesor implements ItemProcessor<CategoryMembers, SimpleCategoryMember>{

	@Override
	public SimpleCategoryMember process(CategoryMembers arg0) throws Exception {
		return CategoryConverter.convert(arg0.categories(null));
	}

}
