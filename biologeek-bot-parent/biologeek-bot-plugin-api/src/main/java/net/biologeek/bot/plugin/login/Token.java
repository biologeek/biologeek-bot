package net.biologeek.bot.plugin.login;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.biologeek.bot.plugin.login.Login.Warning;

public class Token {

	@JsonProperty("query")
	private Query query;
	
	@JsonProperty("warnings")
	private List<Warning> warnings;

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public List<Warning> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<Warning> warnings) {
		this.warnings = warnings;
	}

	public class Query {
		@JsonProperty("tokens")
		private Tokens tokens;

		public class Tokens {
			@JsonProperty("loginToken")
			private String loginToken;

			public String getLoginToken() {
				return loginToken;
			}

			public void setLoginToken(String loginToken) {
				this.loginToken = loginToken;
			}
		}

		public Tokens getTokens() {
			return tokens;
		}

		public void setTokens(Tokens tokens) {
			this.tokens = tokens;
		}
	}
}
