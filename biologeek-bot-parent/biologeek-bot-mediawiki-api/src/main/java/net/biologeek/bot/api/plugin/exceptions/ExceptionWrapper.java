package net.biologeek.bot.api.plugin.exceptions;

import java.io.Serializable;

public class ExceptionWrapper implements Errorable, Serializable {

	private String humanReadableError;
	private String errorClassName;
	private String stackTrace;
	private int severity;

	public String getHumanReadableError() {
		return humanReadableError;
	}

	public void setHumanReadableError(String humanReadableError) {
		this.humanReadableError = humanReadableError;
	}

	public String getErrorClassName() {
		return errorClassName;
	}

	public void setErrorClassName(String errorClassName) {
		this.errorClassName = errorClassName;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}

	public ExceptionWrapper errorClassName(String name) {
		this.errorClassName = name;
		return this;
	}

	public ExceptionWrapper humanReadableError(String message) {
		this.humanReadableError = message;
		return this;
	}

	public ExceptionWrapper severity(int severity) {
		this.severity = severity;
		return this;
	}

	public ExceptionWrapper stackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
		return this;
	}
}
