package br.org.libros.negocio.biblioteca.model.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.json.JsonArray;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import br.org.arquitetura.excecao.RuntimeExcecao;
import br.org.arquitetura.model.business.AbstractBusiness;
import br.org.libros.negocio.biblioteca.model.persistence.entity.Biblioteca;
import br.org.libros.negocio.livrobiblioteca.model.persistence.entity.LivroBiblioteca;

/**
 * 
 * @see br.org.arquitetura.model.business.AbstractBusiness
 */
@Named
@RequestScoped
public class BibliotecaBusiness extends AbstractBusiness<Biblioteca, Integer> {

    private static ApplicationInfoManager applicationInfoManager;
    private static EurekaClient eurekaClient;

    private static synchronized ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
        if (applicationInfoManager == null) {
            InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
            applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
        }

        return applicationInfoManager;
    }

    private static synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig clientConfig) {
        if (eurekaClient == null) {
            eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
        }

        return eurekaClient;
    }


    public void sendRequestToServiceUsingEureka(EurekaClient eurekaClient) {
        // initialize the client
        // this is the vip address for the example service to talk to as defined in conf/sample-eureka-service.properties
        String vipAddress = "sampleservice.mydomain.net";

        InstanceInfo nextServerInfo = null;
        try {
            nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
            System.out.println("teste: " + nextServerInfo.getMetadata().get("teste"));
        } catch (Exception e) {
            System.err.println("Cannot get an instance of example service to talk to from eureka");
            System.exit(-1);
        }

        System.out.println("Found an instance of example service to talk to from eureka: "
                + nextServerInfo.getVIPAddress() + ":" + nextServerInfo.getPort());

        System.out.println("healthCheckUrl: " + nextServerInfo.getHealthCheckUrl());
        System.out.println("override: " + nextServerInfo.getOverriddenStatus());
        System.out.println("nextServerInfo.getHostName(): " + nextServerInfo.getHostName());
        System.out.println("nextServerInfo.getIPAddr(): " + nextServerInfo.getIPAddr());

//        Socket s = new Socket();
        int serverPort = nextServerInfo.getPort();
        System.out.println("nextServerInfo.getPort(): " + nextServerInfo.getPort());
        
        testarRecuperarLivros(nextServerInfo.getHostName());
//        try {
//            s.connect(new InetSocketAddress(nextServerInfo.getHostName(), serverPort));
//        } catch (IOException e) {
//            System.err.println("Could not connect to the server :"
//                    + nextServerInfo.getHostName() + " at port " + serverPort);
//        } catch (Exception e) {
//            System.err.println("Could not connect to the server :"
//                    + nextServerInfo.getHostName() + " at port " + serverPort + "due to Exception " + e);
//        }
//        try {
//            String request = "FOO " + new Date();
//            System.out.println("Connected to server. Sending a sample request: " + request);
//
//            PrintStream out = new PrintStream(s.getOutputStream());
//            out.println(request);
//
//            System.out.println("Waiting for server response..");
//            BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream()));
//            String str = rd.readLine();
//            if (str != null) {
//                System.out.println("Received response from server: " + str);
//                System.out.println("Exiting the client. Demo over..");
//            }
//            rd.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    
    private void testarRecuperarLivros(String hostName) {
		// TODO substituir endereço hardcoded por um service discovery
		Client client = ClientBuilder.newClient();
        System.out.println("Testando resolucao do servidor..");
		JsonArray jsonArrayResponse = client.target("http://" + hostName + ":8180/livraria/api/livros/")
				.request(MediaType.APPLICATION_JSON).get(JsonArray.class);
		ObjectMapper jsonToObjectMapper = new ObjectMapper();
		List<LivroBiblioteca> livrosBibliotecas = null;
		try {
			livrosBibliotecas = jsonToObjectMapper.readValue(jsonArrayResponse.toString(),
					new TypeReference<List<LivroBiblioteca>>() {
					});
		} catch (IOException e) {
			throw new RuntimeExcecao(e);
		}
		
		if (livrosBibliotecas != null) {
			for (LivroBiblioteca livroBiblioteca : livrosBibliotecas) {
				System.out.println(livroBiblioteca.getId());
			}
		}
	}

	@PostConstruct
    public void init() {
        // create the client
        ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(new MyDataCenterInstanceConfig());
        EurekaClient client = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());

        // use the client
        sendRequestToServiceUsingEureka(client);

        // shutdown the client
        eurekaClient.shutdown();

    }
	
	private static final long serialVersionUID = 1L;

	@Override
	public Biblioteca atualizar(Biblioteca entidade) throws EntidadeJaExisteExcecao, EntidadeNaoEncontradaExcecao {
			verificarExistenciaLivros(entidade);
			return super.atualizar(entidade);
	}

	@Override
	public Biblioteca adicionar(Biblioteca entidade) throws EntidadeJaExisteExcecao, EntidadeNaoEncontradaExcecao {
			verificarExistenciaLivros(entidade);
			return super.atualizar(entidade);
	}
	
	private void verificarExistenciaLivros(Biblioteca entidade) throws EntidadeNaoEncontradaExcecao {
		if (entidade.getLivros() != null) {
			for (LivroBiblioteca livro : entidade.getLivros()) {
				//TODO substituir endereço hardcoded por um service discovery
				Client client = ClientBuilder.newClient();
				Response response = client.target("http://localhost:8080/livraria/api/livros/" + livro.getId()).request(MediaType.APPLICATION_JSON).get();
				if (Status.fromStatusCode(response.getStatus()) != Status.OK) {
					throw new EntidadeNaoEncontradaExcecao();
				}
			}
		}
	}
	
}
