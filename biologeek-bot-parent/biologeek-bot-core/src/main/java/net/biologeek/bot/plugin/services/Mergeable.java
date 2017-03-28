package net.biologeek.bot.plugin.services;

import javax.validation.ValidationException;

/**
 * Interface used to define merging behaviour for each component.<br>
 * Allows decoupling functions in services
 * 
 * @param <T>
 */
public interface Mergeable<T> {
	public T merge(T base, T updated) throws ValidationException;
}
