package net.biologeek.bot.plugin.beans;

import java.util.Date;

import javax.persistence.Embeddable;


/**
 * Represents a period of time with a beginning and an end
 * 
 */
@Embeddable
public class Period {
	
	private Date periodBeginning;
	private Date periodEnd;
	
	
	public Period() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Period(Date batchPeriodBegin, Date batchPeriodEnd) {
		this.periodBeginning = batchPeriodBegin;
		this.periodEnd = batchPeriodEnd;
	}
	public Date getPeriodBeginning() {
		return periodBeginning;
	}
	public void setPeriodBeginning(Date beginning) {
		this.periodBeginning = beginning;
	}
	public Date getPeriodEnd() {
		return periodEnd;
	}
	public void setPeriodEnd(Date end) {
		this.periodEnd = end;
	}
	
	
}
