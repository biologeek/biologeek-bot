package net.biologeek.bot.api.plugin.login;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Login {

	@JsonProperty("warnings")
	private List<Warning> warnings;
	@JsonProperty("login")
	private LoginStatus login;

	public class Warning {
		
		@JsonProperty("*")
		String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	public class LoginStatus {
		private String result;

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}
	}

	/**
	 * Login object passed to body when logging
	 * @author xcaron
	 *
	 */
	public class LoginBody {
		private String lgPassword;
		private String lgToken;

		public LoginBody(String password, String token) {
			this.lgPassword = password;
			this.lgToken = token;
		}

		public String getLgPassword() {
			return lgPassword;
		}

		public void setLgPassword(String lgPassword) {
			this.lgPassword = lgPassword;
		}

		public String getLgToken() {
			return lgToken;
		}

		public void setLgToken(String lgToken) {
			this.lgToken = lgToken;
		}
	}

	public List<Warning> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<Warning> warnings) {
		this.warnings = warnings;
	}

	public LoginStatus getLogin() {
		return login;
	}

	public void setLogin(LoginStatus login) {
		this.login = login;
	}
}
