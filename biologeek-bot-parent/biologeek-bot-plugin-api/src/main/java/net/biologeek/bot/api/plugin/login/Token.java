package net.biologeek.bot.api.plugin.login;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.biologeek.bot.api.plugin.login.LoginResponse.Warning;


/**
 * 
 * @author xcaron
 *
 */
public class Token {
	
	private String token;
	
	private List<String> warnings;

	public List<String> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		if (token.endsWith("+\\\\"))
			this.token = token.substring(0, token.length()-1);
		else 
			this.token = token;
	}
}
