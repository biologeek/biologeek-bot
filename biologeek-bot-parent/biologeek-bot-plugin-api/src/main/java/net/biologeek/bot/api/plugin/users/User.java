package net.biologeek.bot.api.plugin.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Generic class referening common user infos
 * <br><br>
 * See for example :<br>
 * https://en.wikipedia.org/w/api.php?action=query&list=users&ususers=1.2.3.4%7CCatrope%7CVandal01%7CBob
 * &usprop=blockinfo%7Cgroups%7Ceditcount%7Cregistration%7Cemailable%7Cgender
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	private String userId;
	private String name;
	private String registration;
	private List<String> groups;
	private String emailable;
	private String gender;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

}
