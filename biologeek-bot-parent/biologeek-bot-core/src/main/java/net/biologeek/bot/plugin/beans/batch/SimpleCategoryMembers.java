package net.biologeek.bot.plugin.beans.batch;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class SimpleCategoryMembers  {

	@Id@GeneratedValue
	private long id;
	private List<SimpleCategoryMember> members;
	
	
	public SimpleCategoryMembers() {
		super();
		this.members = new ArrayList<>();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<SimpleCategoryMember> getMembers() {
		return members;
	}
	public void setMembers(List<SimpleCategoryMember> members) {
		this.members = members;
	}
}
