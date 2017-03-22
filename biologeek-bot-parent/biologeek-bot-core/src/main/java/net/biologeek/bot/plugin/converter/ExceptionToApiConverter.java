package net.biologeek.bot.plugin.converter;

import java.util.Arrays;
import java.util.stream.Collectors;

import net.biologeek.bot.api.plugin.ExceptionWrapper;
import net.biologeek.bot.plugin.exceptions.InstallException;

public class ExceptionToApiConverter {

	public static ExceptionWrapper convert(InstallException e) {
		ExceptionWrapper wrapper = new ExceptionWrapper()//
				.errorClassName(e.getClass().getName())//
				.humanReadableError(e.getMessage())//
				.severity(5)
				.stackTrace(Arrays.asList(e.getStackTrace())//
						.stream()//
						.map(t -> t.toString())//
						.collect(Collectors.joining("\n")));
		return wrapper;
	}
}
