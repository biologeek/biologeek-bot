package net.biologeek.bot.plugin.beans.batch;

public abstract class SpringAwarePluginBatch extends PluginBatch{

	
	/**
	 * Batch Spring bean name
	 */
	protected String springBeanName;

	public String getSpringBeanName() {
		return springBeanName;
	}

	public void setSpringBeanName(String springBeanName) {
		this.springBeanName = springBeanName;
	}
	
}
