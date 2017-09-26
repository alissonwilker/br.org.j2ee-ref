package br.org.arquitetura.api;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.org.arquitetura.dto.IDto;
import br.org.arquitetura.excecao.EntidadeJaExisteExcecao;
import br.org.arquitetura.excecao.EntidadeNaoEncontradaExcecao;
import br.org.arquitetura.model.business.facade.IBusinessFacade;

/**
 * Classe abstrata que contém comportamento padrão de APIs do sistema.
 *
 * @param <D>
 *            tipo do DTO que representa a Entidade.
 * 
 * @see br.org.arquitetura.model.business.facade.AbstractBusinessFacade
 */
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public abstract class AbstractApi<D extends IDto, P extends Serializable> {

    protected String apiPath;

    @Inject
    protected IBusinessFacade<D, P> businessFacade;

    public Response adicionar(D dto) throws EntidadeJaExisteExcecao, EntidadeNaoEncontradaExcecao, URISyntaxException {
        dto = businessFacade.adicionar(dto);
        return Response.created(new URI(apiPath + "/" + dto.getId())).build();
    }

    public Response listar() {
        List<D> dtos = businessFacade.listar();
        return Response.ok(dtos).build();
    }

    public Response remover(P chavePrimaria) throws EntidadeNaoEncontradaExcecao {
        businessFacade.remover(chavePrimaria);
        return Response.ok().build();
    }

    public Response atualizar(D dto) throws EntidadeNaoEncontradaExcecao, EntidadeJaExisteExcecao {
        dto = businessFacade.atualizar(dto);
        return Response.ok(dto).build();
    }

    public Response recuperar(P chavePrimaria) throws EntidadeNaoEncontradaExcecao {
        D dto = businessFacade.recuperar(chavePrimaria);
        return Response.ok(dto).build();
    }
}
