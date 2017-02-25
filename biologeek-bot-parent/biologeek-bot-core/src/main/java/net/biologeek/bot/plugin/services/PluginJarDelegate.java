package net.biologeek.bot.plugin.services;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Service;

import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.exceptions.InstallException;
import net.biologeek.bot.plugin.utils.Constants;

@Service
public class PluginJarDelegate {

	private Logger logger;

	/**
	 * Adds the jar to the classpath and scans for the implementation of class1
	 * 
	 * @param jar
	 *            the jar file to add and scan
	 * @param clazz
	 *            the super class to extend
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstallException
	 */
	public Object scanJarFileForImplementation(String jar, Class clazz, boolean isNecesaryToAddToClasspath)
			throws ClassNotFoundException, InstallException {
		Object result = new Object();
		if (isNecesaryToAddToClasspath)
			addJarToClasspath(new File(jar));

		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);

		provider.addIncludeFilter(new AssignableTypeFilter(clazz.getClass()));

		Set<BeanDefinition> objects = provider.findCandidateComponents("net.biologeek.plugin.plugins");

		List<String> list = new ArrayList<>();

		for (BeanDefinition obj : objects) {
			list.add(obj.getBeanClassName());
		}

		if (list.size() == 1) {
			result = Class.forName(list.get(0));
		}
		return result;
	}

	/**
	 * Adds parametered file to classpath of the application
	 * 
	 * @param file
	 * @throws InstallException
	 */
	public void addJarToClasspath(File file) throws InstallException {
		URI uri = file.toURI();
		try {
			URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();

			URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class }).invoke(loader,
					new Object[] { uri.toURL() });
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | MalformedURLException e) {
			e.printStackTrace();
			throw new InstallException("There was an error while removing plugin from classpth");
		}

	}

	public List<String> scanClasspathForAnnotatedClass(PluginBean bean, Class<? extends Annotation> class1) {

		List<String> res = new ArrayList<>();

		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(true);

		provider.addIncludeFilter(new AnnotationTypeFilter(class1));

		for (BeanDefinition beanDef : provider.findCandidateComponents(Constants.BASE_PACKAGE)) {
			res.add(beanDef.getBeanClassName());
		}

		return res;
	}

	/**
	 * Tries to instantiate class toInstantiate. Throws exception if class is
	 * not annotated with anno or if it could not instantiate object
	 * 
	 * @param toInstantiate
	 * @param anno
	 * @return
	 * @throws InstallException
	 */
	public Object instantiateAnnotatedClass(Class<?> toInstantiate, Class<? extends Annotation> anno)
			throws InstallException {
		if (toInstantiate.isAnnotationPresent(anno)) {
			try {
				return toInstantiate.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				logger.severe("Could not instantiate Class " + toInstantiate.getName());
				e.printStackTrace();
				throw new InstallException(e.getMessage());
			}
		} else {
			throw new IllegalArgumentException("Class is not annotated with " + anno.getName());
		}
	}
}
