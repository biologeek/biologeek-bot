package net.biologeek.bot.api.plugin.users;

import java.util.ArrayList;
import java.util.List;

import net.biologeek.bot.api.plugin.Continuable;

public class UsersList implements Continuable {

	private String cmContinue;

	private List<User> users;

	public UsersList() {
		this.users = new ArrayList<>();
	}

	@Override
	public String getCmContinue() {
		return cmContinue;
	}

	@Override
	public void setCmContinue(String cmContinue) {
		this.cmContinue = cmContinue;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
