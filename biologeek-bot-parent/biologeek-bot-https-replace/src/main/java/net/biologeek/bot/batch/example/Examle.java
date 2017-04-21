package net.biologeek.bot.batch.example;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.stream.Collectors;

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

import javassist.bytecode.ClassFile;
import javassist.tools.reflect.Reflection;
import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.exceptions.ServiceException;
import net.biologeek.bot.plugin.install.PluginSpecificInstallerDelegate;
import net.biologeek.bot.wiki.client.exceptions.APIException;

public class Examle {

	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException, IOException {

		URL url = new URL("https://fr.wikipedia.org/wiki/Hypno5e");

		System.out.println("Protocol : " + url.getProtocol());
		System.out.println("File : " + url.getFile());
		System.out.println("Path : " + url.getPath());
		System.out.println("getHost : " + url.getHost());
		System.out.println("getQuery : " + url.getQuery());
		System.out.println("getDefaultPort : " + url.getDefaultPort());

		try {
			Blabla b = new Examle.Blabla();
		} catch (APIException e) {
			System.out.println(Arrays.asList(e.getStackTrace())//
					.stream()//
					.map(t -> t.toString())//
					.collect(Collectors.joining("\n")));
		}

		Path path = Paths.get(URI.create("file://C:/Utilisateurs/xcaron"));

		System.out.println(path.getFileName());
		System.out.println(path.toAbsolutePath().toString());

		File file = new File(
				"D:/Profiles/xcaron/git/biologeek-bot/biologeek-bot-parent/biologeek-bot-https-replace/target/biologeek-bot-https-replace-0.0.1-SNAPSHOT.jar");

		if (file.exists()) {
			URL uri = file.toURI().toURL();
			JarInputStream jis = new JarInputStream(new FileInputStream(file));

			JarEntry entry = null;

			URLClassLoader loader = null;

			while ((entry = jis.getNextJarEntry()) != null) {
				if (entry.isDirectory()){
				}
				if (entry.getName().endsWith(".class")) {
					ClassFile classFile = new ClassFile(new DataInputStream(jis));

					System.out.println(classFile.getName());
				}

			}

		}
		
		
		String[] names = new String[]{"aa", "zzz", "eeeeee", "rrrrrr"};
		
		System.out.println(String.join("|", names));
		System.out.println("*****************************************");
		String str = "ClassNotFound";
		
		System.out.println("Length : "+str.split(".").length+" - "+String.join(".", str.split(".")));
	}

	public static class Blabla {
		Blabla() throws APIException {
			try {
				throw new Exception();
			} catch (Exception e) {
				throw new APIException("blallaea");
			}
		}
	}
}
