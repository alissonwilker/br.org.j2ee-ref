package br.org.livraria.livro.utils;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;

/**
 * Essa classe é responsável por executar ações de configuração durante a
 * inicialização da aplicação.
 *
 */
@WebListener
public class AppStartupConfigurator implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("estou aqui");
		try {
			MBeanServer server = java.lang.management.ManagementFactory.getPlatformMBeanServer();

			ObjectName httpBinding = new ObjectName(
					"jboss.as.expr:socket-binding-group=standard-sockets,socket-binding=http");
			String httpBound = (String) server.getAttribute(httpBinding, "bound");
			System.out.println("http bound:" + httpBound);
			if (httpBound.equalsIgnoreCase("true")) {
				portaHttp = (String) server.getAttribute(httpBinding, "boundPort");
				System.out.println("http port:" + portaHttp);
			}

			ObjectName httpsBinding = new ObjectName(
					"jboss.as.expr:socket-binding-group=standard-sockets,socket-binding=https");
			String httpsBound = (String) server.getAttribute(httpsBinding, "bound");
			System.out.println("https bound:" + httpsBound);
			if (httpsBound.equalsIgnoreCase("true")) {
				portaHttps = (String) server.getAttribute(httpsBinding, "boundPort");
				System.out.println("https port:" + portaHttps);
			}
		} catch (MalformedObjectNameException | InstanceNotFoundException | AttributeNotFoundException
				| ReflectionException | MBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	}

	private static ApplicationInfoManager applicationInfoManager;
	private static EurekaClient eurekaClient;
	private DynamicPropertyFactory configInstance;
	private String portaHttp;
	private String portaHttps;
	private Map<String, String> metadataMap = new HashMap<String, String>();

	private synchronized ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
		if (applicationInfoManager == null) {
			InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
			applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
			
			metadataMap.put("service.httpPort", portaHttp);
			metadataMap.put("service.httpsPort", portaHttps);
			applicationInfoManager.registerAppMetadata(metadataMap);
		}

		return applicationInfoManager;
	}

	private static synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager,
			EurekaClientConfig clientConfig) {
		if (eurekaClient == null) {
			eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
		}

		return eurekaClient;
	}

	public void start() {
		configInstance = com.netflix.config.DynamicPropertyFactory.getInstance();
		applicationInfoManager = initializeApplicationInfoManager(new MyDataCenterInstanceConfig());
		eurekaClient = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());

		applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
		waitForRegistrationWithEureka(eurekaClient);
		System.out.println("Service started and ready to process requests..");
	}

	public void stop() {
		if (eurekaClient != null) {
			System.out.println("Shutting down server. Demo over.");
			eurekaClient.shutdown();
		}
	}

	private void waitForRegistrationWithEureka(EurekaClient eurekaClient) {
		// my vip address to listen on
		String vipAddress = configInstance.getStringProperty("eureka.vipAddress", "sampleservice.mydomain.net").get();
		InstanceInfo nextServerInfo = null;
		while (nextServerInfo == null) {
			try {
				nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
			} catch (Throwable e) {
				System.out.println("Waiting ... verifying service registration with eureka ...");

				try {
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
