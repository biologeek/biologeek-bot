package net.biologeek.bot.batch.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.biologeek.bot.api.plugin.ParametersList;
import net.biologeek.bot.api.plugin.PluginBean;
import net.biologeek.bot.api.plugin.exceptions.Errorable;
import net.biologeek.bot.api.plugin.exceptions.ExceptionWrapper;
import net.biologeek.bot.plugin.beans.batch.SpringBatchPluginBatch;
import net.biologeek.bot.plugin.converter.ExceptionToApiConverter;
import net.biologeek.bot.plugin.converter.PluginToApiConverter;
import net.biologeek.bot.plugin.converter.PluginToModelConverter;
import net.biologeek.bot.plugin.exceptions.BatchException;
import net.biologeek.bot.plugin.exceptions.ConversionException;
import net.biologeek.bot.plugin.exceptions.InstallException;
import net.biologeek.bot.plugin.exceptions.ServiceException;
import net.biologeek.bot.plugin.install.PluginSpecificInstallerDelegate;
import net.biologeek.bot.plugin.services.BatchService;
import net.biologeek.bot.plugin.services.PluginInstallService;
import net.biologeek.bot.plugin.services.PluginService;

@RestController
@RequestMapping("/api/management")
/**
 * This controller manages plugin related actions : <br>
 * <br>
 * - list plugins, - install a plugin, - configure and parameter plugin, - ...
 *
 */
public class PluginManagementController implements DefaultPluginActions {

	@Autowired
	PluginSpecificInstallerDelegate installerService;

	@Autowired
	PluginInstallService installService;

	@Autowired
	PluginService pluginService;

	@Autowired
	BatchService batchService;

	@RequestMapping(value = "/list/all")
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
					PluginToApiConverter.convert(installService.install(PluginToModelConverter.convert(bean))),
					HttpStatus.OK);
		} catch (InstallException | ConversionException e) {
			response = new ResponseEntity<ExceptionWrapper>(ExceptionToApiConverter.convert(e),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	@Override
	@RequestMapping(value = "/configure", method = RequestMethod.POST)
	public ResponseEntity<? extends Errorable> configureBatch(PluginBean bean) {
		ResponseEntity<? extends Errorable> response = null;
		try {
			response = new ResponseEntity<PluginBean>(
					PluginToApiConverter.convert(installService.configure(PluginToModelConverter.convert(bean))),
					HttpStatus.OK);
		} catch (ValidationException | ConversionException e) {
			response = new ResponseEntity<ExceptionWrapper>(ExceptionToApiConverter.convert(e),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return response;
	}

	@Override
	@RequestMapping(value = "/configure/{id}", method = RequestMethod.GET)
	public ResponseEntity<? extends Errorable> configureBatch(@PathVariable("id") Long id) {
		ResponseEntity<? extends Errorable> response = null;
		response = new ResponseEntity<PluginBean>(PluginToApiConverter.convert(pluginService.getPluginById(id)),
				HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/parameters/{id}", method = RequestMethod.GET)
	public ResponseEntity<? extends Errorable> getParametersForBatch(@PathVariable("id") Long id) {
		ResponseEntity<? extends Errorable> response = null;
		response = new ResponseEntity<ParametersList>(
				PluginToApiConverter.convertParamsList(pluginService.getPluginById(id)), HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/parameters/{id}", method = RequestMethod.PUT)
	public ResponseEntity<? extends Errorable> postParametersForBatch(@PathVariable("id") Long id,
			@RequestBody ParametersList params) throws ParseException, ConversionException {
		ResponseEntity<? extends Errorable> response = null;
		net.biologeek.bot.plugin.beans.PluginBean plugin = pluginService.getPluginById(id);
		response = new ResponseEntity<ParametersList>(
				PluginToApiConverter.convertParamsList(
						pluginService.updatePluginParams(id, PluginToModelConverter.convert(params, plugin))),
				HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/start/{id}", method = RequestMethod.PUT)
	public ResponseEntity<? extends Errorable> launchBatch(@PathVariable("id") Long id)
			throws ParseException, ConversionException {
		ResponseEntity<? extends Errorable> response = null;
		net.biologeek.bot.plugin.beans.PluginBean plugin = pluginService.getPluginById(id);
		try {
			response = new ResponseEntity<>(PluginToApiConverter
					.convert(batchService.startSpringBatch((SpringBatchPluginBatch) plugin.getBatch())), HttpStatus.OK);
		} catch (BatchException e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ExceptionWrapper()//
					.errorClassName(e.getClass().getName())//
					.humanReadableError(e.getMessage()), HttpStatus.OK);
		}
		return response;
	}

	@RequestMapping(value = "/pause/{id}", method = RequestMethod.PUT)
	public ResponseEntity<? extends Errorable> pauseBatch(@PathVariable("id") Long id)
			throws ParseException, ConversionException {
		ResponseEntity<? extends Errorable> response = null;
		net.biologeek.bot.plugin.beans.PluginBean plugin = pluginService.getPluginById(id);
		response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return response;
	}

	@RequestMapping(value = "/stop/{id}", method = RequestMethod.PUT)
	public ResponseEntity<? extends Errorable> stopBatch(@PathVariable("id") Long id)
			throws ParseException, ConversionException {
		ResponseEntity<? extends Errorable> response = null;
		net.biologeek.bot.plugin.beans.PluginBean plugin = pluginService.getPluginById(id);
		response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return response;
	}
}
