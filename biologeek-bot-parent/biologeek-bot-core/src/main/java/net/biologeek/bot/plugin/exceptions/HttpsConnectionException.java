package net.biologeek.bot.plugin.exceptions;

import java.net.ConnectException;

public class HttpsConnectionException extends ConnectException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -548359592412170708L;

	public HttpsConnectionException(String s) {
		super(s);
	}

}
