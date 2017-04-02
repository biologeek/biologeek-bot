package net.biologeek.bot.api.plugin.users;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Generic class referening common user infos <br>
 * <br>
 * See for example :<br>
 * https://en.wikipedia.org/w/api.php?action=query&list=users&ususers=1.2.3.4%7CCatrope%7CVandal01%7CBob
 * &usprop=blockinfo%7Cgroups%7Ceditcount%7Cregistration%7Cemailable%7Cgender
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends AbstractUser {

	private String userId;
	private String registration;
	private List<String> groups;
	private String emailable;
	private String gender;
	private String blockinfo;
	private String implicitgroups;
	private String rights;
	private String editcount;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getEmailable() {
		return emailable;
	}

	public void setEmailable(String emailable) {
		this.emailable = emailable;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBlockinfo() {
		return blockinfo;
	}

	public void setBlockinfo(String blockinfo) {
		this.blockinfo = blockinfo;
	}

	public String getImplicitgroups() {
		return implicitgroups;
	}

	public void setImplicitgroups(String implicitgroups) {
		this.implicitgroups = implicitgroups;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public String getEditcount() {
		return editcount;
	}

	public void setEditcount(String editcount) {
		this.editcount = editcount;
	}

}
