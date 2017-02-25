package net.biologeek.bot.plugin.main;

import org.apache.log4j.Logger;

import net.biologeek.bot.plugin.exceptions.InstallException;
import net.biologeek.bot.plugin.services.PluginInstallService;
import net.biologeek.bot.plugin.services.PluginJarDelegate;

/**
 * 
 * @author
 *
 */
public class PluginInstallMain {

	private static Logger logger = Logger.getLogger(PluginInstallMain.class.getName());

	public static void main(String[] args) throws InstallException, ClassNotFoundException {
		PluginInstallService service;
		if (args.length != 1) {
			logger.error("Please lauch with following arguments : jar file");
			throw new IllegalArgumentException("Only 1 parameter allowed. Got " + args.length);
		}
	}
}
