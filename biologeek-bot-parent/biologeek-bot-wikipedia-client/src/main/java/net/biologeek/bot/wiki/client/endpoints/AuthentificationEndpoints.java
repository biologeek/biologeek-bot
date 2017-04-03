package net.biologeek.bot.wiki.client.endpoints;

import net.biologeek.bot.api.plugin.login.LoginResponse;
import net.biologeek.bot.api.plugin.login.LoginRequest.LoginBody;
import net.biologeek.bot.api.plugin.login.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 
 */
public interface AuthentificationEndpoints {
	@POST(value = "api.php?action=query&meta=tokens&type=login")
	Call<Token> getToken();

	@POST(value = "api.php?action=login&format=json")
	Call<LoginResponse> login(@Query("lgname") String lgname, @Body LoginBody body);

}
