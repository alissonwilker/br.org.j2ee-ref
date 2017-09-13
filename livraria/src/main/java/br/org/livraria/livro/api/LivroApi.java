package br.org.livraria.livro.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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

import br.org.arquitetura.api.AbstractApi;
import br.org.arquitetura.excecao.EntidadeJaExisteExcecao;
import br.org.arquitetura.excecao.EntidadeNaoEncontradaExcecao;
import br.org.livraria.livro.dto.LivroDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @see br.org.arquitetura.api.AbstractApi
 */
@Path(LivroApi.PATH)
@Api(LivroApi.PATH)
@Singleton
public class LivroApi extends AbstractApi<LivroDto, Integer> {
	private static ApplicationInfoManager applicationInfoManager;
	private static EurekaClient eurekaClient;
	private DynamicPropertyFactory configInstance;
	
	@Context
    private UriInfo uri;

	private synchronized ApplicationInfoManager initializeApplicationInfoManager(
			EurekaInstanceConfig instanceConfig) {
		if (applicationInfoManager == null) {
			InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
			applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);

			Map<String, String> metadataMap = new HashMap<String, String>();
			metadataMap.put("service.port", String.valueOf(uri.getBaseUri().getPort()));
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

	@PostConstruct
	public void start() {
		configInstance = com.netflix.config.DynamicPropertyFactory.getInstance();
		applicationInfoManager = initializeApplicationInfoManager(new MyDataCenterInstanceConfig());
		eurekaClient = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());
		
		applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.STARTING);
		waitForRegistrationWithEureka(eurekaClient);
		applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
		System.out.println("Service started and ready to process requests..");
	}

	@PreDestroy
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

	protected static final String PATH = "/livros";

	public LivroApi() {
		apiPath = PATH;
	}

	@Override
	@POST
	@ApiOperation(value = "Adicionar um livro")
	public Response adicionar(@ApiParam(value = "livroDto") LivroDto livroDto)
			throws EntidadeJaExisteExcecao, EntidadeNaoEncontradaExcecao, URISyntaxException {
		return super.adicionar(livroDto);
	}

	@Override
	@GET
	@ApiOperation(value = "Recuperar os livros", response = LivroDto.class, responseContainer = "List")
	public Response listar() {
		return super.listar();
	}

	@Override
	@DELETE
	@Path("/{idLivro}")
	@ApiOperation(value = "Remover um livro")
	public Response remover(@PathParam("idLivro") Integer idLivro) throws EntidadeNaoEncontradaExcecao {
		return super.remover(idLivro);
	}

	@Override
	@PUT
	@ApiOperation(value = "Atualizar um livro", response = LivroDto.class)
	public Response atualizar(@ApiParam(value = "livroDto") LivroDto livroDto)
			throws EntidadeNaoEncontradaExcecao, EntidadeJaExisteExcecao {
		return super.atualizar(livroDto);
	}

	@Override
	@GET
	@Path("/{idLivro}")
	@ApiOperation(value = "Recuperar um livro", response = LivroDto.class)
	public Response recuperar(@PathParam("idLivro") Integer idLivro) throws EntidadeNaoEncontradaExcecao {
		return super.recuperar(idLivro);
	}

}