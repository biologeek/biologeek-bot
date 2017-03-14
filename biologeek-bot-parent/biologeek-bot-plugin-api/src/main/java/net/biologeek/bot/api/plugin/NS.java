package net.biologeek.bot.api.plugin;

/**
 * @see https://www.mediawiki.org/wiki/Help:Magic_words#Namespaces_2
 *
 */
public enum NS {

	USER(4), MEDIA(-2), ARTICLE(0), CATEGORY(14), FILE(6), TEMPLATE(10);

	private int val;

	NS(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}

	public static NS valueOf(int toTest) {
		for(NS ns : NS.values()){
			if (ns.getVal() == toTest)
				return ns;
		}
		return null;
	}

}
