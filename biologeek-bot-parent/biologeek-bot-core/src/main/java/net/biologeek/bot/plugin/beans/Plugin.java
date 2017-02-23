package net.biologeek.bot.plugin.beans;

import org.springframework.core.annotation.AliasFor;

import net.biologeek.bot.plugin.services.PluginInstallService;

/**
 * Every plugin must implement this interface
 *
 */
public @interface Plugin {

	String jarFile() default "";

	Class<? extends PluginInstallService> installerClass() default PluginInstallService.class;

	@SuppressWarnings("rawtypes")
	Class<? extends PluginBatch> entryPoint() default PluginBatch.class;

}
