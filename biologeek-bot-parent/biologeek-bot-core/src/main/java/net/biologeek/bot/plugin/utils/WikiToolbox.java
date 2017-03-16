package net.biologeek.bot.plugin.utils;

import net.biologeek.bot.wiki.client.utils.Constants;

public class WikiToolbox {
	
	
	public static String appendCategoryIfNecesary(String title){
		if (!title.startsWith(Constants.CATEGORY_TITLE_PREFIX_EN))
			title = Constants.CATEGORY_TITLE_PREFIX_EN + title;
		return title;
	}

}
