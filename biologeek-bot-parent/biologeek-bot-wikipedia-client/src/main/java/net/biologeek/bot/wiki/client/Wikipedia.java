package net.biologeek.bot.wiki.client;

import java.io.IOException;
import java.util.logging.Logger;

import net.biologeek.bot.plugin.Article;
import net.biologeek.bot.plugin.Category;
import net.biologeek.bot.plugin.login.Login;
import net.biologeek.bot.plugin.login.Login.LoginStatus;
import net.biologeek.bot.plugin.login.LoginResponseType;
import net.biologeek.bot.plugin.login.Token;
import net.biologeek.bot.plugin.login.User;
import net.biologeek.bot.plugin.serialization.ContentQueryType;
import net.biologeek.bot.plugin.serialization.Errorable;
import net.biologeek.bot.wiki.client.exceptions.NotRetriableException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okhttp3.Interceptor.Chain;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Wikipedia API client with high-level methods to access article read and
 * write/edit functions
 * 
 * @author xavier
 *
 */
public class Wikipedia {

	private Country country;
	WikipediaEndpoints service;
	private String baseURL;
	private String userAgent;
	protected String token;
	private int loginRetries;
	private int maxLogins;
	private int tokenMinLength;
	private User user;
	private boolean isLoggedIn;
	private long timeToWait;
	
	Logger logger;

	public Wikipedia() {
		super();
		this.logger = Logger.getLogger(Wikipedia.class.getName());
	}

	public WikipediaEndpoints getService() {
		return service;
	}

	public void setService(WikipediaEndpoints service) {
		this.service = service;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
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

	public int getLoginRetries() {
		return loginRetries;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets token and logins bot to wikipedia if user is not connected. Else
	 * does nothing
	 * 
	 * @return
	 * @throws NotRetriableException
	 */
	public Wikipedia login() throws NotRetriableException {
		if (token == null || token.isEmpty()) {
			Call<Token> token = service.getToken();
			try {

				Token executedBody = token.execute().body();
				System.out.println(token.execute().raw());
				if (executedBody.getQuery() != null //
						&& executedBody.getQuery().getTokens() != null //
						&& executedBody.getQuery().getTokens().getLoginToken() != null
						&& executedBody.getQuery().getTokens().getLoginToken().length() > tokenMinLength) {
					// TODO parametrize token minLength

					retrofit2.Response<Login> loginResult = service
							.login(user.getUsername(), new Login().new LoginBody(user.getPassword(), user.getToken()))
							.execute();
					if (loginResult.isSuccessful() && (loginResult.body().getWarnings() != null //
							|| loginResult.body().getWarnings().isEmpty())) {
						setToken(loginResult.body().getLogin().getResult());
						setLoggedIn(true);
					} else {
						handleErrorResponses(loginResult.body().getLogin());
					}

				} else if (executedBody.getWarnings() != null && !executedBody.getWarnings().isEmpty()) {
					throw new NotRetriableException("Could not get token");
				}
			} catch (IOException | RetryCallException e) {
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
	 * 
	 * @param title
	 * @return
	 */
	public Category getCategoryMembers(String title){		
		try{
			if (!title.startsWith("Category:"))
				title = "Category:" + title;
			Response<Category> response = this.getService().getCategoryMembers(title).execute();
				return response.body();
		} catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
	public Article getArticle(String title) throws Exception {
		try {
			Response<Article> response = this.getService().getArticle(title).execute();			
			return response.body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	public Article getArticleProp(String title, ContentQueryType property) throws Exception {
		try {
			Response<Article> response = this.getService().getArticleProp(title, property).execute();			
			return response.body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void handleErrorResponses(LoginStatus login) throws NotRetriableException, RetryCallException {

		switch (LoginResponseType.valueOf(login.getResult())) {

		case WrongPass:
			throw new NotRetriableException("Wrong password");

		default:
			throw new RetryCallException("Unknown error, so retry...");
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

		public WikipediaBuilder language(Country country) {
			instance.setCountry(country);
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
			instance.setService(//
					new Retrofit.Builder()//
							.baseUrl(instance.getBaseURL())//
							.addConverterFactory(JacksonConverterFactory.create())//
							.client(client(instance.getToken(), instance.getTokenMinLength())).build()
							.create(WikipediaEndpoints.class)//
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
					/*.addInterceptor(new Interceptor() {

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

						@Override
						/**
						 * Adds JSON format to query by default
						 *
						public okhttp3.Response intercept(Chain arg0) throws IOException {
							Request req = arg0.request();
							req.url()//
									.newBuilder()//
									.addQueryParameter("format", "json")//
									.build();
							return arg0.proceed(req);
						}
					})*/.addInterceptor(logging).build();
		}
	}

	public Article getArticleExternalLinks(String string) {
		return getService().getArticleExternalLinks(string);
	}
}
