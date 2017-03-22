package net.biologeek.bot.api.plugin;

import java.io.Serializable;
import java.util.Date;

import net.biologeek.bot.api.plugin.exceptions.Errorable;

/**
 * Represents a period of time with a beginning and an end
 * 
 */
public class Period implements Errorable, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8782512170960479892L;
	private Date beginning;
	private Date end;

	public Period(Date batchPeriodBegin, Date batchPeriodEnd) {
		this.beginning = batchPeriodBegin;
		this.end = batchPeriodEnd;
	}

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
