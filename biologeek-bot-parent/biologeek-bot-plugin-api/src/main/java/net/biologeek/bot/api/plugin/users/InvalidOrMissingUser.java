package net.biologeek.bot.api.plugin.users;

public class InvalidOrMissingUser extends AbstractUser {

	private String reason;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
