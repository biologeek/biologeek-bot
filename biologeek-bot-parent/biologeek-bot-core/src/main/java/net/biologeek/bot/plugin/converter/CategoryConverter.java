package net.biologeek.bot.plugin.converter;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.biologeek.bot.api.plugin.NS;
import net.biologeek.bot.plugin.beans.batch.SimpleCategoryMember;
import net.biologeek.bot.plugin.beans.category.CategoryMember;
import net.biologeek.bot.plugin.beans.category.CategoryMembers;
import net.biologeek.bot.plugin.beans.category.MediaType;

public class CategoryConverter {

	public static CategoryMembers convert(net.biologeek.bot.api.plugin.category.CategoryMembers categoryMembers,
			String categoryListOfPatterns) {
		CategoryMembers result = new CategoryMembers();
		/*
		 * Returns a list of patterns parametered that identfy a wiki category.
		 * Usually in english Wikipedia it's Category:Physics for example
		 */
		Predicate<net.biologeek.bot.api.plugin.category.CategoryMembers.CategoryMember> categoryPredicate = getCategoryListOfPatterns(
				categoryListOfPatterns);

		Stream<CategoryMember> stream = categoryMembers.getValue()//
				.stream()//
				.filter(categoryPredicate.negate())//
				.map(t -> CategoryConverter.convert(t));//
		stream.forEach(t -> t.setParentCategory(result));
		List<CategoryMember> articles = stream.collect(Collectors.toList());

		Stream<CategoryMember> stream1 = categoryMembers.getValue()//
				.stream()//
				.filter(categoryPredicate)//
				.map(CategoryConverter::convert);
		stream1.forEach(t -> t.setParentCategory(result));
		List<CategoryMember> categories = stream1.collect(Collectors.toList());

		return new CategoryMembers()//
				.articles(articles)//
				.categories(categories)//
				.categoryId(null)//
				.categoryTitle(null);
	}

	/**
	 * Returns predicate used to discriminate categories, medias, ... from
	 * standard articles. <br>
	 * <br>
	 * Note that as you inject your own list of patterns, you can discriminate
	 * whatever you want and even mix namespaces<br>
	 * <br>
	 * Unicode codes are converted to standard text <br>
	 * <br>
	 * Note : Should not be here but I don't know where to put it.
	 * 
	 * @param categoryListOfPatterns
	 * @return
	 */
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
		return new CategoryMember().pageId(String.valueOf(members.getPageid()))//
				.title(members.getTitle())//
				.mediaType(convertMediaType(members.getNs()))//
				.parentCategory(null)// FIXME
		;
	}

	private static MediaType convertMediaType(int ns) {
		return MediaType.valueOf(NS.valueOf(ns).name());
	}

	public static SimpleCategoryMember convert(CategoryMember categories) {
		return new SimpleCategoryMember(categories);
	}

}
