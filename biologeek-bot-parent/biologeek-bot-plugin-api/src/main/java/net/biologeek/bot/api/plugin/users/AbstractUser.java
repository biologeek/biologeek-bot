package net.biologeek.bot.api.plugin.users;


/**
 * A user can be invalid or missing. In these cases, only their name appears.
 *
 */
public class AbstractUser {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
