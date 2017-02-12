package net.biologeek.bot.plugin;


/**
 * Represents an installed plugin, stored in database 
 * @author xavier
 */
public class PluginBean {
	
	
	private long id;
	private String name;
	private String description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String descriptions) {
		this.description = descriptions;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	

}
