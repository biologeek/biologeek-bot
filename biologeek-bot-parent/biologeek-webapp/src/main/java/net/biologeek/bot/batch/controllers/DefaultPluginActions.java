package net.biologeek.bot.batch.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import net.biologeek.bot.api.plugin.PluginBean;
import net.biologeek.bot.api.plugin.exceptions.Errorable;
import net.biologeek.bot.plugin.exceptions.ConversionException;

public interface DefaultPluginActions {

	public ResponseEntity<List<PluginBean>> listPlugins();
	public ResponseEntity<List<PluginBean>> listNotInstalledPlugins();
	public ResponseEntity<? extends Errorable> installBatch(@RequestBody PluginBean bean);
	public ResponseEntity<? extends Errorable> configureBatch(@RequestBody PluginBean bean) throws ConversionException;
	public ResponseEntity<? extends Errorable> configureBatch(Long id);

}
