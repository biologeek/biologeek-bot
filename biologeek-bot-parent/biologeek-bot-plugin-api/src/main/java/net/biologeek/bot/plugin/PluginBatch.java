package net.biologeek.bot.plugin;

public abstract class PluginBatch {

	private PluginBean plugin;
	private String className;

	public abstract void execute(String[] params);

	public PluginBean getPlugin() {
		return plugin;
	}

	public void setPlugin(PluginBean plugin) {
		this.plugin = plugin;
	}

	public void getClassName(String className){
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
