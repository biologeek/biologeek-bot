package net.biologeek.bot.wiki.client;

public enum Country {

	
	FR("fr"),EN("en"), ALL(null);
	
	private String prefix;

	Country(String prefix) {
		this.prefix = prefix;
	}
	
	public static boolean contains(String test) {

	    for (Country c : Country.values()) {
	        if (c.name().equals(test)) {
	            return true;
	        }
	    }

	    return false;
	}
}
