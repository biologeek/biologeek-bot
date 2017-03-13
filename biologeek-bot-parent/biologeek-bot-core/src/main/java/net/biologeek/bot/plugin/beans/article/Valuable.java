package net.biologeek.bot.plugin.beans.article;

public interface Valuable<T> {

	public abstract T getValue();

	public abstract void setValue(T value);

}
