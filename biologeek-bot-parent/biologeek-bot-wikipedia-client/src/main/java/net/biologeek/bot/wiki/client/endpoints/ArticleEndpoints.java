package net.biologeek.bot.wiki.client.endpoints;

import net.biologeek.bot.api.plugin.EditResponse;
import net.biologeek.bot.api.plugin.article.ArticleCategories;
import net.biologeek.bot.api.plugin.article.ArticleContent;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ArticleEndpoints {

	@GET(value = "api.php?action=query&prop=extracts&format=json")
	Call<ArticleContent> getArticle(@Query("titles") String titles);

	@GET(value = "api.php?action=query&prop=categories&format=json")
	Call<ArticleCategories> getArticleCategories(@Query("titles") String titles);

	@POST(value="api.php?action=edit")
	Call<EditResponse> editArticleWithTitle(@Query("title") String title, @Body String articleContent);
	
	@POST(value="api.php?action=edit")
	Call<EditResponse> editArticleWithId(@Query("id") String id, @Body String articleContent);
}
