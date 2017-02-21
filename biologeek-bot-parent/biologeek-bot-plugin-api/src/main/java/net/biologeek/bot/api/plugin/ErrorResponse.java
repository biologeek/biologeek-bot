package net.biologeek.bot.api.plugin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.biologeek.bot.api.plugin.serialization.Errorable;

/**
 * * { "error": { "code": "invalidcategory", "info": "The category name you
 * entered is not valid.", "*": "See https://fr.wikipedia.org/w/api.php for API
 * usage. Subscribe to the mediawiki-api-announce mailing list at
 * &lt;https://lists.wikimedia.org/mailman/listinfo/mediawiki-api-announce&gt;
 * for notice of API deprecations and breaking changes." }, "servedby": "mw1278"
 * }
 *
 * @author xcaron
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ErrorResponse implements Errorable {

	private Error error;

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public static class Error {
		private String code;
		private String info;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getInfo() {
			return info;
		}

		public void setInfo(String info) {
			this.info = info;
		}
	}

}
