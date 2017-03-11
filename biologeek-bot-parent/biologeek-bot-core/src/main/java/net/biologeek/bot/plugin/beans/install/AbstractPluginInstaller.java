package net.biologeek.bot.plugin.beans.install;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;

import org.springframework.beans.factory.annotation.Value;

import net.biologeek.bot.plugin.beans.Period;
import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.install.PluginInstaller;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractPluginInstaller implements PluginInstaller {
	
	@Id@GeneratedValue
	private Long id;
	
	@OneToOne(mappedBy="installer")
	protected PluginBean bean;

	@Value("plugin.batch.period.begin")
	protected Date batchPeriodBegin;
	@Value("plugin.batch.period.end")
	protected Date batchPeriodEnd;

	protected Period batchPeriod;

	public AbstractPluginInstaller(PluginBean bean2) {
		this.bean = bean2;
	}

	public AbstractPluginInstaller() {
		this.bean = null;
	}

	public PluginBean getBean() {
		return bean;
	}

	public void setBean(PluginBean bean) {
		this.bean = bean;
	}

	public Date getBatchPeriodBegin() {
		return batchPeriodBegin;
	}

	public void setBatchPeriodBegin(Date batchPeriodBegin) {
		this.batchPeriodBegin = batchPeriodBegin;
	}

	public Date getBatchPeriodEnd() {
		return batchPeriodEnd;
	}

	public void setBatchPeriodEnd(Date batchPeriodEnd) {
		this.batchPeriodEnd = batchPeriodEnd;
	}

	public Period getBatchPeriod() {
		return batchPeriod;
	}

	public void setBatchPeriod(Period batchPeriod) {
		this.batchPeriod = batchPeriod;
	}
	
	
}