package br.org.libros.negocio.usuario.utils;

import java.lang.invoke.MethodHandles;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClientConfig;

import br.org.arquitetura.excecao.EntidadeJaExisteExcecao;
import br.org.arquitetura.excecao.EntidadeNaoEncontradaExcecao;
import br.org.libros.negocio.usuario.dto.UsuarioDto;
import br.org.libros.negocio.usuario.model.business.facade.UsuarioBusinessFacade;

/**
 * Essa classe é responsável por executar ações de configuração durante a
 * inicialização da aplicação.
 *
 */
@Singleton
@Startup
public class AppStartupConfigurator {
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
 
	@Inject
	private UsuarioBusinessFacade usuarioBusinessFacade;

	private static ApplicationInfoManager applicationInfoManager;
	private static EurekaClient eurekaClient; 
	private static String serviceBaseUri;
	
	public String getLivrariaBaseUri() {
		return init();
	}

	private static synchronized ApplicationInfoManager initializeApplicationInfoManager(
			EurekaInstanceConfig instanceConfig) {
//		if (applicationInfoManager == null) {
			InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
			applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
//		}

		return applicationInfoManager;
	}

	private static synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager,
			EurekaClientConfig clientConfig) {
//		if (eurekaClient == null) {
			eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
//		}

		return eurekaClient;
	}

	private String sendRequestToServiceUsingEureka(EurekaClient eurekaClient) {
		String vipAddress = "livraria.j2ee-ref.org.br";

		InstanceInfo nextServerInfo = null;
		try {
			nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
		} catch (Exception e) {
			System.err.println("Cannot get an instance of example service to talk to from eureka");
			System.exit(-1);
		}

		System.out.println("Found an instance of example service to talk to from eureka: "
				+ nextServerInfo.getVIPAddress() + ":" + nextServerInfo.getPort());

		String serviceHttpPort = nextServerInfo.getMetadata().get("service.httpPort");
		String serviceHttpsPort = nextServerInfo.getMetadata().get("service.httpsPort");

		System.out.println(serviceHttpPort);
		System.out.println(serviceHttpsPort);

		if (serviceHttpPort != null) {
			serviceBaseUri = "http://" + nextServerInfo.getHostName() + ":" + serviceHttpPort + "/livraria/api";
		} else if (serviceHttpsPort != null)  {
			serviceBaseUri = "https://" + nextServerInfo.getHostName() + ":" + serviceHttpsPort + "/livraria/api";
		} 

		System.out.println(serviceBaseUri);
		
		return serviceBaseUri;
	}

	private String init() {
		// create the client
		ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(
				new MyDataCenterInstanceConfig());
		EurekaClient client = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());

		// use the client
		String serviceBaseUri = sendRequestToServiceUsingEureka(client);

		// shutdown the client
		eurekaClient.shutdown();
		
		return serviceBaseUri;

	}
	
	/**
	 * Método que configura a aplicação durante sua inicialização.
	 */
	@PostConstruct
	public void configureAppAtStartup() {
		/*
		 * cria os usuários 'admin', com senha 'admin', e 'user', com senha
		 * 'user'. As senhas são codificadas com algoritmo MD5.
		 */
		try {
			usuarioBusinessFacade
					.adicionar(new UsuarioDto("admin", "21232f297a57a5a743894a0e4a801fc3", "ADMINISTRATOR"));
			usuarioBusinessFacade.adicionar(new UsuarioDto("user", "ee11cbb19052e40b07aac0ca060c23ee", "USER"));
		} catch (EntidadeJaExisteExcecao | EntidadeNaoEncontradaExcecao e) {
			logger.error(e.getMessage(), e);
		}
	}

}
