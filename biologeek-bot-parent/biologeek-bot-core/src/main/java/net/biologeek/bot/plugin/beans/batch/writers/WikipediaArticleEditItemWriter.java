package net.biologeek.bot.plugin.beans.batch.writers;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import net.biologeek.bot.api.plugin.article.Article;
import net.biologeek.bot.plugin.beans.article.ArticleContent;
import net.biologeek.bot.plugin.beans.article.ArticleElement;
import net.biologeek.bot.plugin.converter.ArticleConverter;
import net.biologeek.bot.plugin.converter.ArticleToApiConvertor;

public class WikipediaArticleEditItemWriter<T extends ArticleElement<?>> implements ItemWriter<T> {

	@Override
	public void write(List<? extends T> arg0) throws Exception {
		for (T article : arg0) {
			if (article instanceof ArticleContent) {
				new ArticleToApiConvertor();
				net.biologeek.bot.api.plugin.article.ArticleContent apiArticle = ArticleToApiConvertor
						.convert((ArticleContent) article);
			}
		}
	}

}
