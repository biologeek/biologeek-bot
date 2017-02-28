package net.biologeek.tests.application;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.biologeek.bot.plugin.services.PluginService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ApplicationTestConfiguration.class)
public class SpringTest {

	@Autowired
	PluginService service;
	
	@Test
	public void shouldRun(){
		Assert.assertNotNull(service);
	}
}
