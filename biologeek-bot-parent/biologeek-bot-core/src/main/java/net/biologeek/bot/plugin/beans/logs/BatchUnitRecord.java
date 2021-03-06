package net.biologeek.bot.plugin.beans.logs;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import net.biologeek.bot.plugin.beans.batch.PluginBatch;

@Entity
/**
 *  A log of a batch launch. It records exit code, can serve for performance record, detecting an issue, ...
 *
 */
public class BatchUnitRecord {

	@Id@GeneratedValue
	private long recordId;
	private Date creationDate;
	private Date beginningDate;
	private Date endDate;
	@ManyToOne(fetch=FetchType.LAZY)
	private PluginBatch recordBatch;
	private int exitCode;

	private String logOutput;

	private String stackTrace;

	private String exceptionClass;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getBeginningDate() {
		return beginningDate;
	}

	public void setBeginningDate(Date beginningDate) {
		this.beginningDate = beginningDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getExitCode() {
		return exitCode;
	}

	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}

	public String getLogOutput() {
		return logOutput;
	}

	public void setLogOutput(String logOutput) {
		this.logOutput = logOutput;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}


	public BatchUnitRecord recordId(long recordId) {
		this.recordId = recordId;
		return this;
	}

	public BatchUnitRecord beginningDate(Date beginningDate) {
		this.beginningDate = beginningDate;
		return this;
	}

	public BatchUnitRecord endDate(Date endDate) {
		this.endDate = endDate;
		return this;
	}

	public BatchUnitRecord recordBatch(PluginBatch recordBatch) {
		this.recordBatch = recordBatch;
		return this;
	}

	public BatchUnitRecord exitCode(int exitCode) {
		this.exitCode = exitCode;
		return this;
	}

	public BatchUnitRecord logOutput(String logOutput) {
		this.logOutput = logOutput;
		return this;
	}

	public BatchUnitRecord stackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
		return this;
	}

	public BatchUnitRecord exceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
		return this;
	}
}
