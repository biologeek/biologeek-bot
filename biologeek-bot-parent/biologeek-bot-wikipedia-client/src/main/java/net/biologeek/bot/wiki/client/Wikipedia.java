package net.biologeek.bot.wiki.client;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import net.biologeek.bot.api.plugin.article.ArticleCategories;
import net.biologeek.bot.api.plugin.article.ArticleContent;
import net.biologeek.bot.api.plugin.article.ArticleContributors;
import net.biologeek.bot.api.plugin.category.CategoryMembers;
import net.biologeek.bot.api.plugin.login.LoginResponse;
import net.biologeek.bot.api.plugin.login.LoginResponse.LoginStatus;
import net.biologeek.bot.api.plugin.login.LoginRequest;
import net.biologeek.bot.api.plugin.login.LoginResponseType;
import net.biologeek.bot.api.plugin.login.Token;
import net.biologeek.bot.api.plugin.login.User;
import net.biologeek.bot.api.plugin.users.UsersList;
import net.biologeek.bot.wiki.client.endpoints.ArticleEndpoints;
import net.biologeek.bot.wiki.client.endpoints.AuthentificationEndpoints;
import net.biologeek.bot.wiki.client.endpoints.CategoriesEndpoints;
import net.biologeek.bot.wiki.client.endpoints.UsersEndpoints;
import net.biologeek.bot.wiki.client.exceptions.APIException;
import net.biologeek.bot.wiki.client.exceptions.NotRetriableException;
import net.biologeek.bot.wiki.client.exceptions.WikiException;
import net.biologeek.bot.wiki.client.utils.Constants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Wikipedia API client with high-level methods to access article read and
 * write/edit functions, category properties, user profiles, ... <br>
 * <br>
 * This class is the unique entry point for logging in and out, retrieving
 * articles, categories, users, reading and editing
 *
 */
public class Wikipedia {

	private List<Country> countries;
	ArticleEndpoints articleEndpoints;
	CategoriesEndpoints categoryEndpoints;
	UsersEndpoints usersEndpoints;
	private String baseURL;
	private String userAgent;
	protected String loginToken;
	private int loginRetries;
	private int maxLogins;
	private int tokenMinLength;
	private User user;
	private boolean isLoggedIn;
	private long timeToWait;

	Logger logger;
	private AuthentificationEndpoints authentEndpoints;

	public Wikipedia() {
		super();
		this.logger = Logger.getLogger(Wikipedia.class.getName());
	}

	/**
	 * Gets token and logins bot to wikipedia if user is not connected. Else
	 * does nothing<br>
	 * <br>
	 * 
	 * Tested login procedure that works : <br>
	 * 1. Send GET to
	 * https://fr.wikipedia.org/w/api.php?action=query&meta=tokens&type=login&format=json<br>
	 * Returns :<br>
	 * <code>{<br>
	 * "batchcomplete": "",<br>
	 * "query": {<br>
	 * "tokens": {<br>
	 * "logintoken": "70c35a9343b62220a8a4d63ba16bb49358e2c1ef+\\"<br>
	 * }<br>
	 * }<br>
	 * }<br>
	 * </code>
	 * 
	 * 2. POST request to
	 * https://fr.wikipedia.org/w/api.php?action=login&lgname=Zybalou1234&format=json
	 * <br>
	 * <br>
	 * with form-data : lgpassword=myPassword,
	 * lgtoken=70c35a9343b62220a8a4d63ba16bb49358e2c1ef+\ <br>
	 * <br>
	 * Returns :<br>
	 * <code>
	 * {<br>
	 * "warnings": {<br>
	 * "main": {<br>
	 * "*": "Subscribe to the mediawiki-api-announce mailing list at
	 * <https://lists.wikimedia.org/mailman/listinfo/mediawiki-api-announce> for
	 * notice of API deprecations and breaking changes. Use
	 * [[Special:ApiFeatureUsage]] to see usage of deprecated features by your
	 * application."<br>
	 * },<br>
	 * "login": {<br>
	 * "*": "Main-account login via \"action=login\" is deprecated and may stop
	 * working without warning. To continue login with \"action=login\", see
	 * [[Special:BotPasswords]]. To safely continue using main-account login,
	 * see \"action=clientlogin\"."<br>
	 * }<br>
	 * },<br>
	 * "login": {<br>
	 * "result": "Success",<br>
	 * "lguserid": 2772988,<br>
	 * "lgusername": "Zybalou1234"<br>
	 * }<br>
	 * }<br>
	 * </code>
	 * 
	 * @return
	 * @throws NotRetriableException
	 */
	public Wikipedia login() throws NotRetriableException {
		if (loginToken == null || loginToken.isEmpty()) {
			Call<Token> token = authentEndpoints.getToken();
			try {
				// FIXME find login procedure
				Token executedBody = token.execute().body();
				System.out.println(token.execute().raw());

				this.setLoginToken(executedBody.getToken());
				if (executedBody.getToken() != null && executedBody.getToken().length() > tokenMinLength) {
					// TODO parametrize token minLength

					retrofit2.Response<LoginResponse> loginResult = authentEndpoints
							.login(user.getUsername(), new LoginRequest()//
							.new LoginBody(user.getPassword(), user.getToken())).execute();

					// Not sure that ap
					if (loginResult.isSuccessful() && loginResult.body().getLogin() != null
							&& loginResult.body().getLogin().getResult().equalsIgnoreCase(LoginResponse.SUCCESS)) {
						setLoggedIn(true);
					} else {
						handleErrorResponses(loginResult.body().getLogin());
					}

				} else if (executedBody.getWarnings() != null && !executedBody.getWarnings().isEmpty()) {
					throw new NotRetriableException("Could not get token : "
							+ String.join(" | ", (String[]) executedBody.getWarnings().toArray()));
				}
			} catch (IOException e) {
				e.printStackTrace();
				if (loginRetries < maxLogins) {
					loginRetries++;
					this.login();
				}
			}

		}
		return this;
	}

	/**
	 * Returns raw response from mediawiki API mapped into an object.
	 * 
	 * @param title
	 * @return
	 * @throws APIException
	 */
	@SuppressWarnings({})
	public CategoryMembers getCategoryMembers(String title, String continueParam) throws APIException {
		try {
			if (!title.startsWith("Category:"))
				title = Constants.CATEGORY_TITLE_PREFIX_EN + title;
			Response<CategoryMembers> response = (Response<CategoryMembers>) this.getCategoryEndpoints()
					.getCategoryMembers(title).execute();
			return response.body();
		} catch (IOException e) {
			throw new APIException(e.getMessage());
		}
	}

	/**
	 * 
	 * Returns the categories a category with title {@link title} belongs to.
	 * <br>
	 * <br>
	 * The 2 last parameters are related to mediawiki default result size.<br>
	 * Continue param is the last category id retrieved and servers to get next
	 * subcategories <br>
	 * <br>
	 * If you want to get all result, just set {@link getall} parameter to true
	 * 
	 * @param title
	 *            the title of the category
	 * @param continueParam
	 *            a string to add to request to get next categories. Example :
	 *            "subcat|0403423d374b4d2f042d2f044527574b011301848f10|117136"
	 * @param getAll
	 *            boolean to get all result for this category
	 * @return a {@link CategoryMembers} object containing desired categories
	 * @throws APIException
	 * @throws IOException
	 */
	@SuppressWarnings("null")
	public CategoryMembers getCategoriesIBelongTo(String title, String continueParam, boolean getAll)
			throws APIException {
		if (title.startsWith("Category:"))
			title = Constants.CATEGORY_TITLE_PREFIX_EN + title;

		String cmcontinue = null;
		CategoryMembers result = new CategoryMembers();
		try {
			if (getAll) {
				// Loop over to get all possible responses
				do {
					Response<CategoryMembers> response = this.getCategoryEndpoints()//
							.getCategoriesIBelongTo(title, cmcontinue)//
							.execute();
					result.getValue().addAll(response.body().getValue());
					cmcontinue = response.body().getCmContinue();
				} while (cmcontinue != null || !cmcontinue.equals(""));
			} else {
				result = this.getCategoryEndpoints()//
						.getCategoriesIBelongTo(title, cmcontinue)//
						.execute().body();
			}
		} catch (IOException e) {
			throw new APIException(e.getMessage());
		}
		return result;
	}

	/**
	 * Returns an {@link ArticleContent} object containing title and article
	 * body
	 * 
	 * @param title
	 *            the title
	 * @return
	 * @throws WikiException
	 *             in case it could not retrieve the article
	 */
	public ArticleContent getArticleContent(String title) throws WikiException {
		try {
			Response<ArticleContent> response = this.getArticleEndpoints().getArticle(title).execute();
			return response.body();
		} catch (IOException e) {
			e.printStackTrace();
			throw new WikiException(e.getMessage());
		}
	}

	/**
	 * Returns the categories the article belongs to
	 * 
	 * @param title
	 *            the title of the article
	 * @return an {@link ArticleCategories} object encapsulating a list of
	 *         categories
	 * @throws WikiException
	 */
	public ArticleCategories getArticleCategories(String title) throws WikiException {
		try {
			Response<ArticleCategories> response = this.getArticleEndpoints().getArticleCategories(title).execute();
			return response.body();
		} catch (IOException e) {
			e.printStackTrace();
			throw new WikiException(e.getMessage());
		}
	}

	/**
	 * Allows to retrieve an user details by its pseudo<br>
	 * <br>
	 * 
	 * @param name
	 * @return
	 * @throws WikiException
	 */
	public net.biologeek.bot.api.plugin.users.User getUserByName(String name) throws WikiException {
		try {
			Response<net.biologeek.bot.api.plugin.users.User> response = this.getUsersEndpoints().getUserByName(name)
					.execute();
			return response.body();
		} catch (IOException e) {
			e.printStackTrace();
			throw new WikiException(e.getMessage());
		}
	}

	/**
	 * Allows to retrieve several users details by their pseudos<br>
	 * <br>
	 * 
	 * @param name
	 * @return
	 * @throws WikiException
	 */
	public UsersList getUsersByNames(String[] names) throws WikiException {
		try {
			Response<UsersList> response = this.getUsersEndpoints().getUserByNames(String.join("|", names)).execute();
			return response.body();
		} catch (IOException e) {
			e.printStackTrace();
			throw new WikiException(e.getMessage());
		}
	}

	/**
	 * Handles response in case of error during login
	 * 
	 * @param login
	 * @throws NotRetriableException
	 */
	private void handleErrorResponses(LoginStatus login) throws NotRetriableException {

		if (login.getResult().equalsIgnoreCase(LoginResponse.FAILED)) {
			throw new NotRetriableException(login.getReason());
		}
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public long getTimeToWait() {
		return timeToWait;
	}

	public void setTimeToWait(long timeToWait) {
		this.timeToWait = timeToWait;
	}

	public static class WikipediaBuilder {

		private Wikipedia instance;

		public WikipediaBuilder(Wikipedia instance2) {
			if (instance2 == null)
				instance = new Wikipedia();
			else
				instance = instance2;
		}

		public WikipediaBuilder maxLogins(int maxLogins) {
			instance.setMaxLogins(maxLogins);
			return this;
		}

		public WikipediaBuilder tokenMinLength(int tokenMinLength) {
			instance.setTokenMinLength(tokenMinLength);
			return this;
		}

		public WikipediaBuilder languages(List<Country> country) {
			instance.setCountries(country);
			return this;
		}

		public WikipediaBuilder baseURL(String baseURL) {
			instance.setBaseURL(baseURL);
			return this;
		}

		public WikipediaBuilder user(User user) {
			instance.setUser(user);
			return this;
		}

		public WikipediaBuilder timeToWait(long timeToWait) {
			instance.setTimeToWait(timeToWait);
			return this;
		}

		public Wikipedia build() {
			instance.setArticleEndpoints(//
					new Retrofit.Builder()//
							.baseUrl(instance.getBaseURL())//
							.addConverterFactory(JacksonConverterFactory.create())//
							.client(client(instance.getLoginToken(), instance.getTokenMinLength())).build()
							.create(ArticleEndpoints.class)//
			);
			instance.setCategoryEndpoints(//
					new Retrofit.Builder()//
							.baseUrl(instance.getBaseURL())//
							.addConverterFactory(JacksonConverterFactory.create())//
							.client(client(instance.getLoginToken(), instance.getTokenMinLength())).build()
							.create(CategoriesEndpoints.class)//
			);

			instance.setUsersEndpoints(//
					new Retrofit.Builder()//
							.baseUrl(instance.getBaseURL())//
							.addConverterFactory(JacksonConverterFactory.create())//
							.client(client(instance.getLoginToken(), instance.getTokenMinLength())).build()
							.create(UsersEndpoints.class)//
			);
			return instance;
		}

		/**
		 * Builds OkHttp client with token query param and eventually other
		 * (json format) defaults
		 * 
		 * @param token
		 *            authentication token
		 * @return the client
		 */
		private OkHttpClient client(final String token, final int tokenMinLength) {

			HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
			logging.setLevel(Level.BODY);
			return new OkHttpClient.Builder()//
					.addInterceptor(new Interceptor() {
						@Override
						public okhttp3.Response intercept(Chain arg0) throws IOException {
							Request req = arg0.request();
							if (token != null && !token.isEmpty() && token.length() > tokenMinLength) {
								// TODO parametrize min length in properties and
								// inject via @Value
								req.url().newBuilder().addQueryParameter("token", token);
							}
							return arg0.proceed(req);
						}
					}).addInterceptor(new Interceptor() {
						@Override // Adds JSON format to query by default
						public okhttp3.Response intercept(Chain arg0) throws IOException {
							Request req = arg0.request();
							req.url()//
									.newBuilder()// .addQueryParameter("format",
													// "json")
									.build();
							return arg0.proceed(req);
						}
					}).addInterceptor(logging).build();
		}
	}

	public List<Country> getCountries() {
		return countries;
	}

	public void setUsersEndpoints(UsersEndpoints create) {
		this.usersEndpoints = create;
	}

	public void setCountries(List<Country> country2) {
		this.countries = country2;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String token) {
		this.loginToken = token;
	}

	public int getLoginRetries() {
		return loginRetries;
	}

	public void setLoginRetries(int loginRetries) {
		this.loginRetries = loginRetries;
	}

	public int getMaxLogins() {
		return maxLogins;
	}

	public void setMaxLogins(int maxLogins) {
		this.maxLogins = maxLogins;
	}

	public int getTokenMinLength() {
		return tokenMinLength;
	}

	public void setTokenMinLength(int tokenMinLength) {
		this.tokenMinLength = tokenMinLength;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public ArticleEndpoints getArticleEndpoints() {
		return articleEndpoints;
	}

	public void setArticleEndpoints(ArticleEndpoints articleEndpoints) {
		this.articleEndpoints = articleEndpoints;
	}

	public CategoriesEndpoints getCategoryEndpoints() {
		return categoryEndpoints;
	}

	public void setCategoryEndpoints(CategoriesEndpoints categoryEndpoints) {
		this.categoryEndpoints = categoryEndpoints;
	}

	public ArticleContributors getArticleContributors(String articleTitle) {
		return null;
	}

	public UsersEndpoints getUsersEndpoints() {
		return usersEndpoints;
	}

}
