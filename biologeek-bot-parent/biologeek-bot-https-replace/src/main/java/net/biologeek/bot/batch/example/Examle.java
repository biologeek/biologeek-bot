package net.biologeek.bot.batch.example;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

import net.biologeek.bot.wiki.client.exceptions.APIException;

public class Examle {

	public static void main(String[] args) throws MalformedURLException {

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
