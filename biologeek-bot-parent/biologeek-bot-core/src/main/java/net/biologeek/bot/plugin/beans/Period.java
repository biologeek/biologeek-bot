package net.biologeek.bot.plugin.beans;

import java.util.Date;


/**
 * Represents a period of time with a beginning and an end
 * 
 */
public class Period {
	
	private Date beginning;
	private Date end;
	
	
	public Date getBeginning() {
		return beginning;
	}
	public void setBeginning(Date beginning) {
		this.beginning = beginning;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
	
}
