package net.biologeek.bot.api.plugin.login;

public class LoginRequest {
	/**
	 * Login object passed to body when logging
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
}
