package net.biologeek.bot.batch.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.biologeek.bot.api.plugin.PluginBean;
import net.biologeek.bot.plugin.converter.PluginToApiConverter;
import net.biologeek.bot.plugin.exceptions.ServiceException;
import net.biologeek.bot.plugin.install.PluginInstallerService;
import net.biologeek.bot.plugin.services.PluginService;

@RestController

/**
 * This controller manages plugin related actions : - list plugins, - ...
 *
 */
public class PluginManagementController {

	@Autowired
	PluginInstallerService installerService;

	@Autowired
	PluginService pluginService;

	@RequestMapping(value = "/list")
	public ResponseEntity<List<PluginBean>> listPlugins() {
		List<PluginBean> result = null;
		try {
			result = PluginToApiConverter.convert(pluginService.getAllPlugins());
		} catch (ServiceException e) {
			e.printStackTrace();
			return new ResponseEntity<List<PluginBean>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<PluginBean>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/list/notinstalled")
	public ResponseEntity<List<PluginBean>> listNotInstalledPlugins() {
		List<PluginBean> result = null;
		try {
			result = PluginToApiConverter.convert(pluginService.getNotInstalledPlugins());
		} catch (ServiceException e) {
			e.printStackTrace();
			return new ResponseEntity<List<PluginBean>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<PluginBean>>(result, HttpStatus.OK);
	}

}
