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
import net.biologeek.bot.plugin.install.PluginSpecificInstallerDelegate;
/**
 * The POJO class that represents a persisted installer configuration. Most attributres might be constants or @Value.
 * 
 * Should not do any business action. It's just a bean.
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractPluginInstaller {
	
	@Id@GeneratedValue
	protected Long id;
	
	@OneToOne(mappedBy="installer")
	protected PluginBean bean;

	protected Date batchPeriodBegin;
	protected Date batchPeriodEnd;
	
	/**
	 * Specifics installer. Must be implementing {@link PluginSpecificInstallerDelegate} interface
	 */
	protected String installerService;
	
	protected String jarPath;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInstallerService() {
		return installerService;
	}

	public void setInstallerService(String installerService) {
		this.installerService = installerService;
	}

	public String getJarPath() {
		return jarPath;
	}

	public void setJarPath(String jarPath) {
		this.jarPath = jarPath;
	}
}
