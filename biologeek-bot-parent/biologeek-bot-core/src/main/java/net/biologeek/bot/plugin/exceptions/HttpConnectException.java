package net.biologeek.bot.plugin.exceptions;

import java.net.ConnectException;

public class HttpConnectException extends ConnectException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5940021930840206221L;

	public HttpConnectException(String message) {
		super(message);
	}

}
