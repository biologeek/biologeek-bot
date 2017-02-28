package net.biologeek.bot.plugin.beans;

import net.biologeek.bot.plugin.beans.batch.PluginBatch;
import net.biologeek.bot.plugin.services.PluginInstallService;

/**
 * Every plugin must implement this interface
 *
 */
public @interface Plugin {

	String jarFile() default "";

	Class<? extends PluginInstallService> installerClass() default PluginInstallService.class;

	@SuppressWarnings("rawtypes")
	/**
	 * the batch entry point class
	 * @return
	 */
	Class<? extends PluginBatch> entryPoint() default PluginBatch.class;

}
