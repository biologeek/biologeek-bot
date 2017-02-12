package net.biologeek.bot.wiki.client;

import net.biologeek.bot.plugin.Article;
import net.biologeek.bot.plugin.login.Login;
import net.biologeek.bot.plugin.login.Login.LoginBody;
import net.biologeek.bot.plugin.login.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WikipediaEndpoints {
	
	@GET(value="api.php?action=query&prop=")
	
	@POST(value="api.php?action=query&meta=tokens&type=login")
	Call<Token> getToken();
	
	@POST(value="api.php?action=login")
	Call<Login> login(@Query("lgname") String lgname, @Body LoginBody body);
	
	@GET(value="api.php?action=query")
	Call<Article> getArticle(@Query("titles") String titles);

	@GET(value="api.php?action=query")
	Call<Article> getArticleProp(@Query("titles") String string, @Query("prop") String prop);
}
