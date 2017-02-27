package net.biologeek.bot.tests.application;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import net.biologeek.bot.plugin.services.PluginService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SpringTest {

	@Autowired
	PluginService service;
	
	@Test
	public void shouldRun(){
		Assert.assertNotNull(service);
	}
}
