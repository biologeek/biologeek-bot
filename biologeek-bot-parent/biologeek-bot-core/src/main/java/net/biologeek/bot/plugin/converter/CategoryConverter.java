package net.biologeek.bot.plugin.converter;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.biologeek.bot.api.plugin.NS;
import net.biologeek.bot.plugin.beans.category.CategoryMember;
import net.biologeek.bot.plugin.beans.category.CategoryMembers;
import net.biologeek.bot.plugin.beans.category.MediaType;

public class CategoryConverter {

	public static CategoryMembers convert(net.biologeek.bot.api.plugin.category.CategoryMembers categoryMembers,
			String categoryListOfPatterns) {
		/*
		 * Returns a list of patterns parametered that identfy a wiki category.
		 * Usually in english Wikipedia it's Category:Physics for example
		 */
		Predicate<net.biologeek.bot.api.plugin.category.CategoryMembers.CategoryMember> predicate = getCategoryListOfPatterns(
				categoryListOfPatterns);

		List<CategoryMember> articles = categoryMembers.getValue()//
				.stream()//
				.filter(predicate)//
				.map(t -> CategoryConverter.convert(t))//
				.collect(Collectors.toList());

		List<CategoryMember> categories = categoryMembers.getValue()//
				.stream()//
				.filter(predicate.negate())//
				.map(CategoryConverter::convert).collect(Collectors.toList());

		return new CategoryMembers()//
				.articles(articles)//
				.categories(categories)//
				.categoryId(null)//
				.categoryTitle(null);
	}

	private static Predicate<net.biologeek.bot.api.plugin.category.CategoryMembers.CategoryMember> getCategoryListOfPatterns(
			String categoryListOfPatterns) {
		return new Predicate<net.biologeek.bot.api.plugin.category.CategoryMembers.CategoryMember>() {

			@Override
			public boolean test(net.biologeek.bot.api.plugin.category.CategoryMembers.CategoryMember t) {
				String title = null;
				try {
					title = convertToUTF8(t.getTitle());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String[] patterns = categoryListOfPatterns.split(";");
				for (String pattern : patterns) {
					if (title.contains(pattern)) {
						return true;
					}
				}
				return false;
			}

			private String convertToUTF8(String title) throws UnsupportedEncodingException {
				byte[] bytes = title.getBytes("UTF8");

				return new String(bytes, "UTF8");
			}
		};
	}

	public static CategoryMember convert(net.biologeek.bot.api.plugin.category.CategoryMembers.CategoryMember members) {
		return new CategoryMember().pageId(members.getPageid())//
				.title(members.getTitle())//
				.mediaType(convertMediaType(members.getNs()))//
				.parentCategory(null)// FIXME
		;
	}

	private static MediaType convertMediaType(int ns) {
		return MediaType.valueOf(NS.valueOf(ns).name());
	}

}
