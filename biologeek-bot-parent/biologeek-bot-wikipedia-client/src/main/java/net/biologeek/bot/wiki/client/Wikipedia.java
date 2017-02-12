package net.biologeek.bot.wiki.client;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
	Retrofit retrofit;
	private String baseURL;
	private String userAgent;
	protected String token;

	public Retrofit getRetrofit() {
		return retrofit;
	}

	public void setRetrofit(Retrofit retrofit) {
		this.retrofit = retrofit;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static class WikipediaBuilder {

		private Wikipedia instance;

		public WikipediaBuilder() {
			instance = new Wikipedia();
		}

		public WikipediaBuilder language(Country country) {
			instance.setCountry(country);
			return this;
		}

		public WikipediaBuilder baseURL(String baseURL) {
			instance.setBaseURL(baseURL);
			return this;
		}

		public Wikipedia build() {
			instance.setRetrofit(//
			new Retrofit.Builder()//
					.baseUrl(instance.getBaseURL())//
					.addConverterFactory(JacksonConverterFactory.create())//
					.client(client(instance.getToken())).build()//
			);
			return instance;
		}

		/**
		 * Builds OkHttp client with Authorization header and eventually other
		 * defaults
		 * 
		 * @param token
		 *            authentication token
		 * @return the client
		 */
		private OkHttpClient client(final String token) {
			return new OkHttpClient.Builder()//
					.addInterceptor(new Interceptor() {

						@Override
						public Response intercept(Chain arg0)
								throws IOException {
							Request req = arg0.request();
							req.newBuilder().addHeader("Authorization", token);
							return arg0.proceed(req);
						}
					}).build();
		}
	}
}
