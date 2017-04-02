package net.biologeek.bot.wiki.client.endpoints;

import net.biologeek.bot.api.plugin.users.User;
import net.biologeek.bot.api.plugin.users.UsersList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UsersEndpoints {

	@GET("api.php?action=query&list=users")
	Call<User> getUserByName(@Query("ususers") String name);

	@GET("api.php?action=query&list=users")
	Call<UsersList> getUserByNames(@Query("ususers") String join);


}
