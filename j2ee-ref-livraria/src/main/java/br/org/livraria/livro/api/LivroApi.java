package br.org.livraria.livro.api;

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
import br.org.livraria.livro.dto.LivroDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * API de Livro.
 * 
 * @see br.org.arquitetura.api.AbstractApi
 */
@Path(LivroApi.PATH)
@Api(LivroApi.PATH)
public class LivroApi extends AbstractApi<LivroDto, Integer> {

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