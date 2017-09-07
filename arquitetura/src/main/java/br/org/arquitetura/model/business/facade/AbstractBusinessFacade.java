package br.org.arquitetura.model.business.facade;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.org.arquitetura.dto.mapper.IGenericMapper;
import br.org.arquitetura.excecao.EntidadeJaExisteExcecao;
import br.org.arquitetura.excecao.EntidadeNaoEncontradaExcecao;
import br.org.arquitetura.model.business.IBusiness;

/**
 * Classe abstrata que implementa comportamento padrão de uma fachada de módulo.
 *
 * @param <E>
 *            tipo da Entidade.
 * @param <D>
 *            tipo do DTO que representa a Entidade.
 * @param <PK>
 *            tipo da chave primária da Entidade.
 * 
 * @see br.org.arquitetura.model.business.facade.IBusinessFacade
 */
public abstract class AbstractBusinessFacade<E, D, PK extends Serializable> implements IBusinessFacade<D, PK> {
	private static final long serialVersionUID = 1L;

	@Inject
	private IBusiness<E, PK> business;

	protected IGenericMapper<E, D> mapper;

	@Override
	public D adicionar(D dto) throws EntidadeJaExisteExcecao, EntidadeNaoEncontradaExcecao {
		return mapper.converterParaDto(business.adicionar(mapper.converterParaEntidade(dto)));
	}

	@Override
	public List<D> listar() {
		return mapper.converterParaDtos(business.listar());
	}

	@Override
	public void remover(D dto) throws EntidadeNaoEncontradaExcecao {
		business.remover(mapper.converterParaEntidade(dto));
	}

	@Override
	public D atualizar(D dto) throws EntidadeNaoEncontradaExcecao, EntidadeJaExisteExcecao {
		return mapper.converterParaDto(business.atualizar(mapper.converterParaEntidade(dto)));
	}

	@Override
	public D recuperar(PK chavePrimaria) throws EntidadeNaoEncontradaExcecao {
		return mapper.converterParaDto(business.recuperar(chavePrimaria));
	}

	@Override
	public void remover(PK chavePrimaria) throws EntidadeNaoEncontradaExcecao {
		business.remover(chavePrimaria);
	}

}
