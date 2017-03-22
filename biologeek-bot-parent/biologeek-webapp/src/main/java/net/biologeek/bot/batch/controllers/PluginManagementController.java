package net.biologeek.bot.batch.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.biologeek.bot.api.plugin.PluginBean;

@RestController
public class PluginManagementController {
	
	
	
	@RequestMapping(value="/list")
	public ResponseEntity<List<PluginBean>> listPlugins(){
		return new ResponseEntity<List<PluginBean>>(new ArrayList<PluginBean>(), HttpStatus.OK);
	}
	

}
