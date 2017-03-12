package net.biologeek.bot.batch.example;

import java.net.MalformedURLException;
import java.net.URL;

public class Examle {
	
	
	public static void main(String[] args) throws MalformedURLException{
		
		
		URL url = new URL("https://fr.wikipedia.org/wiki/Hypno5e");
		
		
		System.out.println("Protocol : " + url.getProtocol());
		System.out.println("File : " + url.getFile());
		System.out.println("Path : " + url.getPath());
		System.out.println("getHost : " + url.getHost());
		System.out.println("getQuery : " + url.getQuery());
		System.out.println("getDefaultPort : " + url.getDefaultPort());
	}

}
