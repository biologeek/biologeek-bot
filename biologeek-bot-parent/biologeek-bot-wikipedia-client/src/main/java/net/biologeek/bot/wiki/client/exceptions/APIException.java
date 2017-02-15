package net.biologeek.bot.wiki.client.exceptions;

import net.biologeek.bot.plugin.ErrorResponse;

public class APIException extends Exception {

	public APIException(ErrorResponse body) {
		super(body.getError().getInfo());
	}

}
