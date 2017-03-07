package net.biologeek.bot.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import net.biologeek.bot.api.plugin.PluginBatch;
import net.biologeek.bot.api.plugin.PluginBean;
import net.biologeek.bot.install.HttpsReplacePluginInstall;
import net.biologeek.bot.plugin.beans.Plugin;

@SpringBootApplication
public class HttpsReplaceBatchEntryPoint {
	
	PluginBean bean;

	
	public void execute(String[] params) {
		
		SpringApplication.run(this.getClass(), formatParams(params));

	}

	private String formatParams(String[] params) {
		return String.join(" ", params);
	}

	public PluginBean getBean() {
		return bean;
	}

	public void setBean(PluginBean bean) {
		this.bean = bean;
	}
}
