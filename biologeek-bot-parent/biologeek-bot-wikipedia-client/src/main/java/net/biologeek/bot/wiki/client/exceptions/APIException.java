package net.biologeek.bot.wiki.client.exceptions;

import net.biologeek.bot.api.plugin.ErrorResponse;

public class APIException extends Exception {

	public APIException(ErrorResponse body) {
		super(body.getError().getInfo());
	}
	
	public APIException(String message){
		super(message);
	}

}
