package br.org.libros.cliente.api;

import java.net.URISyntaxException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.org.arquitetura.api.AbstractApi;
import br.org.arquitetura.excecao.EntidadeJaExisteExcecao;
import br.org.arquitetura.excecao.EntidadeNaoEncontradaExcecao;
import br.org.libros.cliente.dto.ClienteDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @see br.org.arquitetura.api.AbstractApi
 */
@Path(ClienteApi.PATH)
@Api(ClienteApi.PATH)
public class ClienteApi extends AbstractApi<ClienteDto, Integer> {

	protected static final String PATH = "/clientes";

	public ClienteApi() {
		apiPath = PATH;
	}

	@Override
	@POST
	@ApiOperation(value = "Adicionar um cliente")
	public Response adicionar(@ApiParam(value = "clienteDto") ClienteDto clienteDto) throws EntidadeJaExisteExcecao, EntidadeNaoEncontradaExcecao, URISyntaxException {
		return super.adicionar(clienteDto);
	}

	@Override
	@GET
	@ApiOperation(value = "Recuperar os clientes", response = ClienteDto.class, responseContainer = "List")
	public Response listar() {
		return super.listar();
	}

	@Override
	@DELETE
	@Path("/{idCliente}")
	@ApiOperation(value = "Remover um cliente")
	public Response remover(@PathParam("idCliente") Integer idCliente) throws EntidadeNaoEncontradaExcecao {
		return super.remover(idCliente);
	}

	@Override
	@PUT
	@ApiOperation(value = "Atualizar um cliente", response = ClienteDto.class)
	public Response atualizar(@ApiParam(value = "clienteDto") ClienteDto clienteDto) throws EntidadeNaoEncontradaExcecao, EntidadeJaExisteExcecao {
		return super.atualizar(clienteDto);
	}

	@Override
	@GET
	@Path("/{idCliente}")
	@ApiOperation(value = "Recuperar um cliente", response = ClienteDto.class)
	public Response recuperar(@PathParam("idCliente") Integer idCliente) throws EntidadeNaoEncontradaExcecao {
		return super.recuperar(idCliente);
	}

}