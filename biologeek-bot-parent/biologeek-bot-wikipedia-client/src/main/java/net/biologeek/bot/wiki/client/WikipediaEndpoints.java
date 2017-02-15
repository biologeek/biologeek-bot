package net.biologeek.bot.wiki.client;

import net.biologeek.bot.plugin.article.Article;
import net.biologeek.bot.plugin.article.ArticleCategories;
import net.biologeek.bot.plugin.article.ArticleContent;
import net.biologeek.bot.plugin.category.Category;
import net.biologeek.bot.plugin.login.Login;
import net.biologeek.bot.plugin.login.Login.LoginBody;
import net.biologeek.bot.plugin.login.Token;
import net.biologeek.bot.plugin.serialization.ContentQueryType;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WikipediaEndpoints {

	@GET(value = "api.php?action=query&prop=")

	@POST(value = "api.php?action=query&meta=tokens&type=login")
	Call<Token> getToken();

	@POST(value = "api.php?action=login")
	Call<Login> login(@Query("lgname") String lgname, @Body LoginBody body);

	@GET(value = "api.php?action=query&prop=extracts&format=json")
	Call<ArticleContent> getArticle(@Query("titles") String titles);

	@GET(value = "api.php?action=query&prop=categories&format=json")
	Call<ArticleCategories> getArticleCategories(@Query("titles") String titles);

	@GET(value = "api.php?action=query&list=categorymembers&format=json")
	Call<? extends Category<?>> getCategoryMembers(@Query("cmtitle") String title);
}
