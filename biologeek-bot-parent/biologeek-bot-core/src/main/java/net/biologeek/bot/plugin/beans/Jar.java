package net.biologeek.bot.plugin.beans;

import java.io.File;

import org.hibernate.loader.custom.Return;

public class Jar extends File {

	public Jar(String pathname) {
		super(pathname);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2385061968353985657L;

	
	public boolean isJar(){
		if (this.getName().endsWith(".jar")){
			return true;
		}
		return false;
	}
	
}
