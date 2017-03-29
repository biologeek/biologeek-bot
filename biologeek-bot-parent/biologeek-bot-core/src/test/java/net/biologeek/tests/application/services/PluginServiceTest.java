package net.biologeek.tests.application.services;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnit44Runner;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import net.biologeek.bot.plugin.beans.Jar;
import net.biologeek.bot.plugin.beans.PluginBean;
import net.biologeek.bot.plugin.beans.batch.PluginBatch;
import net.biologeek.bot.plugin.beans.batch.SpringBatchPluginBatch;
import net.biologeek.bot.plugin.beans.install.AbstractPluginInstaller;
import net.biologeek.bot.plugin.beans.install.SimplePluginInstaller;
import net.biologeek.bot.plugin.exceptions.InstallException;
import net.biologeek.bot.plugin.exceptions.ServiceException;
import net.biologeek.bot.plugin.repositories.PluginRepository;
import net.biologeek.bot.plugin.services.BatchService;
import net.biologeek.bot.plugin.services.Mergeable;
import net.biologeek.bot.plugin.services.PluginInstallService;
import net.biologeek.bot.plugin.services.PluginJarDelegate;
import net.biologeek.bot.plugin.services.PluginService;
import net.biologeek.tests.application.ApplicationTestConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=ApplicationTestConfiguration.class)
public class PluginServiceTest {

	private PluginService sut;

	@Mock
	PluginRepository repositoryMock;

	@Mock
	PluginRepository pluginDao;

	@Mock
	PluginJarDelegate jarDelegate;

	@Mock
	BatchService batchService;

	@Mock
	Mergeable<AbstractPluginInstaller> installerMergeable;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(PluginServiceTest.class);
		sut = new PluginService();

		jarDelegate = mock(PluginJarDelegate.class);
		pluginDao = mock(PluginRepository.class);
		batchService = mock(BatchService.class);
		installerMergeable = mock(PluginInstallService.class);

		sut.setJarDelegate(jarDelegate);
		sut.setBatchService(batchService);
		sut.setInstallerMergeable(installerMergeable);
		sut.setPluginDao(pluginDao);

	}

	@Test
	@Ignore
	public void shouldGetNotInstalledPlugins()
			throws IOException, ServiceException, ClassNotFoundException, InstallException {

		Mockito.when(jarDelegate.scanDirectoryForJars()).thenReturn(aListOfJars());
		Mockito.when(jarDelegate.scanJarFileForImplementation(any(String.class), any(PluginBean.class.getClass())))
				.thenReturn(PluginBean.class);
		Mockito.when(jarDelegate.scanJarFileForImplementation(any(String.class),
				any(AbstractPluginInstaller.class.getClass()))).thenReturn(SimplePluginInstaller.class);
		Mockito.when(jarDelegate.scanJarFileForImplementation(any(String.class), any(PluginBatch.class.getClass())))
				.thenReturn(SpringBatchPluginBatch.class);
		List<PluginBean> result = sut.getNotInstalledPlugins();

		Assert.assertEquals(2, result.size());

	}

	private List<Jar> aListOfJars() {
		Jar jar1 = new Jar("src/test/resources/file1.jar");
		Jar jar2 = new Jar("/home/biologeek/jar/file2.jar");
		List<Jar> jars = new ArrayList<>();
		jars.add(jar2);
		jars.add(jar1);

		return jars;
	}

}
