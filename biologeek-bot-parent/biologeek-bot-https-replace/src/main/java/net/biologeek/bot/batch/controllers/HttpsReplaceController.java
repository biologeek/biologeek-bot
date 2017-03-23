package net.biologeek.bot.batch.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.biologeek.bot.api.plugin.PluginBean;
import net.biologeek.bot.api.plugin.exceptions.Errorable;
import net.biologeek.bot.api.plugin.exceptions.ExceptionWrapper;
import net.biologeek.bot.plugin.converter.ExceptionToApiConverter;
import net.biologeek.bot.plugin.converter.PluginToApiConverter;
import net.biologeek.bot.plugin.converter.PluginToModelConverter;
import net.biologeek.bot.plugin.exceptions.InstallException;
import net.biologeek.bot.plugin.services.PluginInstallService;

@RestController
@RequestMapping("/httpsreplace")
public class HttpsReplaceController {

	@Autowired
	PluginInstallService service;

	/**
	 * Installs the plugin. For any batch, installation address must be mapped
	 * as such : <b>/pluginName/install</b>
	 * 
	 * @param bean
	 *            the bean to install
	 * @return
	 */
	@RequestMapping(value = "/install", method = RequestMethod.POST)
	public ResponseEntity<? extends Errorable> installBatch(@RequestBody PluginBean bean) {
		ResponseEntity<? extends Errorable> response = null;

		try {
			response = new ResponseEntity<PluginBean>(
						PluginToApiConverter.convert(service.install(PluginToModelConverter.convert(bean))
					), HttpStatus.OK);
		} catch (InstallException e) {
			response = new ResponseEntity<ExceptionWrapper>(ExceptionToApiConverter.convert(e), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	


}
