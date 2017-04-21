package net.biologeek.bot.plugin.utils;

import org.springframework.util.StringUtils;

public class SpringToolbox {
	
	/**
	 * Resolves default bean name from simle class name AND net.package.ClassName format
	 *   
	 * @param className the class name
	 * @return bean name, null if input null or empty
	 */
	public static String resolveClassNameToSpringBeanName(String className){
		if(className != null && !className.equals("")){
			if(className.split(".").length > 0){
				String[] splitClassName = className.split(".");
				className = splitClassName[splitClassName.length-1];
			}
			return StringUtils.uncapitalize(className);
		}
		return null;
	}

}
