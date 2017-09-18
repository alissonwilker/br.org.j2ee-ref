package br.org.libros.negocio.biblioteca.api;

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
import br.org.libros.negocio.biblioteca.dto.BibliotecaDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @see br.org.arquitetura.api.AbstractApi
 */
@Path(BibliotecaApi.PATH)
@Api(BibliotecaApi.PATH)
public class BibliotecaApi extends AbstractApi<BibliotecaDto, Integer> {

    public static final String PATH = "/bibliotecas";

    public BibliotecaApi() {
        apiPath = PATH;
    }

    @Override
    @POST
    @ApiOperation(value = "Adicionar uma biblioteca")
    public Response adicionar(@ApiParam(value = "bibliotecaDto") BibliotecaDto bibliotecaDto)
            throws EntidadeJaExisteExcecao, EntidadeNaoEncontradaExcecao, URISyntaxException {
        return super.adicionar(bibliotecaDto);
    }

    @Override
    @GET
    @ApiOperation(value = "Recuperar as bibliotecas", response = BibliotecaDto.class, responseContainer = "List")
    public Response listar() {
        return super.listar();
    }

    @Override
    @DELETE
    @Path("/{idBiblioteca}")
    @ApiOperation(value = "Remover uma biblioteca")
    public Response remover(@PathParam("idBiblioteca") Integer idBiblioteca) throws EntidadeNaoEncontradaExcecao {
        return super.remover(idBiblioteca);
    }

    @Override
    @PUT
    @ApiOperation(value = "Atualizar uma biblioteca", response = BibliotecaDto.class)
    public Response atualizar(@ApiParam(value = "bibliotecaDto") BibliotecaDto bibliotecaDto)
            throws EntidadeNaoEncontradaExcecao, EntidadeJaExisteExcecao {
        return super.atualizar(bibliotecaDto);
    }

    @Override
    @GET
    @Path("/{idBiblioteca}")
    @ApiOperation(value = "Recuperar uma biblioteca", response = BibliotecaDto.class)
    public Response recuperar(@PathParam("idBiblioteca") Integer idBiblioteca) throws EntidadeNaoEncontradaExcecao {
        return super.recuperar(idBiblioteca);
    }

}