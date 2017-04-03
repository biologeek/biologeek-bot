package net.biologeek.bot.api.plugin.login;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {

	public static final String SUCCESS = "Success";
	public static final String FAILED = "Failed";
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

	@JsonIgnoreProperties(ignoreUnknown = true)
	public class LoginStatus {
		@JsonProperty("result")
		private String result;
		@JsonProperty("lguserid")
		private String lguserid;
		@JsonProperty("lgusername")
		private String lgusername;
		@JsonProperty("reason")
		private String reason;

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public String getLguserid() {
			return lguserid;
		}

		public void setLguserid(String lguserid) {
			this.lguserid = lguserid;
		}

		public String getLgusername() {
			return lgusername;
		}

		public void setLgusername(String lgusername) {
			this.lgusername = lgusername;
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
