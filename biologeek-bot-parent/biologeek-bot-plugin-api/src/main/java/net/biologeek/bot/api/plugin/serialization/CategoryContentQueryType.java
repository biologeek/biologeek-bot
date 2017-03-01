package net.biologeek.bot.api.plugin.serialization;

public enum CategoryContentQueryType {

	
	categorymembers;

	public static CategoryContentQueryType value(String string) {
		for (CategoryContentQueryType value : CategoryContentQueryType.values()){
			if (value.name().equals(string))
				return value;
		}
		return null;
	}
}
