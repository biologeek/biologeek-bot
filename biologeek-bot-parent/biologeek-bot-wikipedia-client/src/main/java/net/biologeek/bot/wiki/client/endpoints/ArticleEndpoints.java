package net.biologeek.bot.wiki.client.endpoints;

import net.biologeek.bot.api.plugin.EditResponse;
import net.biologeek.bot.api.plugin.article.ArticleCategories;
import net.biologeek.bot.api.plugin.article.ArticleContent;
import net.biologeek.bot.api.plugin.category.Category;
import net.biologeek.bot.api.plugin.login.Login;
import net.biologeek.bot.api.plugin.login.Login.LoginBody;
import net.biologeek.bot.api.plugin.login.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ArticleEndpoints {


	@POST(value = "api.php?action=query&meta=tokens&type=login")
	Call<Token> getToken();

	@POST(value = "api.php?action=login")
	Call<Login> login(@Query("lgname") String lgname, @Body LoginBody body);

	@GET(value = "api.php?action=query&prop=extracts&format=json")
	Call<ArticleContent> getArticle(@Query("titles") String titles);

	@GET(value = "api.php?action=query&prop=categories&format=json")
	Call<ArticleCategories> getArticleCategories(@Query("titles") String titles);

	@POST(value="api.php?action=edit")
	Call<EditResponse> editArticleWithTitle(@Query("title") String title, @Body String articleContent);
	
	@POST(value="api.php?action=edit")
	Call<EditResponse> editArticleWithId(@Query("id") String id, @Body String articleContent);
}