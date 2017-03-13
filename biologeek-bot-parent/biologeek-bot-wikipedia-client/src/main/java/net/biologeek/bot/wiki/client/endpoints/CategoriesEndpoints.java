package net.biologeek.bot.wiki.client.endpoints;

import net.biologeek.bot.api.plugin.category.CategoryMembers;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit 2 REST endpoints interface. 
 * 
 * Add here all endpoints related to categories
 *
 */
public interface CategoriesEndpoints {
	@GET(value = "api.php?action=query&list=categorymembers&format=json")
	Call<CategoryMembers> getCategoryMembers(@Query("cmtitle") String title);

	@GET(value = "api.php?action=query&prop=categoriesformat=json")
	Call<CategoryMembers> getCategoriesIBelongTo(@Query("titles") String title, @Query("cmcontinue") String cmContinue);
}
