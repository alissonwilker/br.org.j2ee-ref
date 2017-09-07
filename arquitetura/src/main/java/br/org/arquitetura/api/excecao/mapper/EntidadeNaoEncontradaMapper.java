package br.org.arquitetura.api.excecao.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.org.arquitetura.excecao.EntidadeNaoEncontradaExcecao;

@Provider
public class EntidadeNaoEncontradaMapper implements ExceptionMapper<EntidadeNaoEncontradaExcecao> {

	@Override
	public Response toResponse(EntidadeNaoEncontradaExcecao exception) {
		return Response.status(Response.Status.NOT_FOUND).build();
	}

}
