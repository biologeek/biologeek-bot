package net.biologeek.bot.plugin.services;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Service;

import net.biologeek.bot.plugin.beans.Jar;
import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.exceptions.InstallException;
import net.biologeek.bot.plugin.utils.Constants;

@Service
public class PluginJarDelegate {

	@Value("plugin.jar.directory")
	String jarBaseDirectory;

	private Logger logger;

	public Object scanJarFileForImplementation(String jar, Class clazz)
			throws ClassNotFoundException, InstallException {
		return this.scanJarFileForImplementation(jar, clazz, false);
	}

	public Object scanJarFileForImplementation(File jar, Class clazz) throws ClassNotFoundException, InstallException {
		return this.scanJarFileForImplementation(jar.getAbsolutePath(), clazz, false);
	}

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

		Set<BeanDefinition> objects = provider.findCandidateComponents("");

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

	public List<Jar> scanDirectoryForJars() throws IOException {
		List<Jar> jars = new ArrayList<>();
		File baseDirectory = new File(jarBaseDirectory);

		if (baseDirectory.exists() && baseDirectory.isDirectory()) {
			jars = listJarFiles(jarBaseDirectory);
		}
		return jars;
	}

	List<Jar> listJarFiles(String jarString) throws IOException {
		return listFilesWithExtension(jarString, "jar");
	}

	/**
	 * Lists files within given directory using a predicate on file extension
	 * 
	 * @param baseDirectory
	 * @param extension
	 * @return
	 * @throws IOException
	 */
	List<Jar> listFilesWithExtension(String baseDirectory, String extension) throws IOException {
		Stream<Path> paths = Files.walk(Paths.get(URI.create(baseDirectory)));
		List<Jar> result = new ArrayList<>();

		paths.filter(new Predicate<Path>() {

			@Override
			public boolean test(Path t) {
				return FilenameUtils.getExtension(t.toAbsolutePath().toString()).equalsIgnoreCase(extension);
			}
		})//
				.forEach(t -> result.add(new Jar(t.toAbsolutePath().toString())));

		return result;
	}

	public String getJarBaseDirectory() {
		return jarBaseDirectory;
	}

	public void setJarBaseDirectory(String jarBaseDirectory) {
		this.jarBaseDirectory = jarBaseDirectory;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

}
