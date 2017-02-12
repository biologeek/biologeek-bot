package net.biologeek.bot.wiki.client;

import net.biologeek.bot.plugin.Article;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WikipediaEndpoints {
	
	@GET(value="?action=query&prop=")
	Call<Article> getArticle(String title);
}
