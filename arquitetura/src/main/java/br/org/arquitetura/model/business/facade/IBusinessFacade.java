package br.org.arquitetura.model.business.facade;

import java.io.Serializable;
import java.util.List;

import br.org.arquitetura.excecao.EntidadeJaExisteExcecao;
import br.org.arquitetura.excecao.EntidadeNaoEncontradaExcecao;

/**
 * Interface de uma fachada para a camada negocial de um módulo. A fachada é o
 * ponto de entrada para a camada negocial do módulo. Normalmente utilizada por
 * Controladores ou pela API.
 *
 * @param <D>
 *            tipo do DTO.
 * @param <PK>
 *            tipo da chave primária da Entidade representada pelo DTO.
 *            
 * @see br.org.arquitetura.model.business.IBusiness
 */
public interface IBusinessFacade<D, PK extends Serializable> extends Serializable {
	public D adicionar(D dto) throws EntidadeJaExisteExcecao, EntidadeNaoEncontradaExcecao;

	public List<D> listar();

	public void remover(D dto) throws EntidadeNaoEncontradaExcecao;

	public void remover(PK chavePrimaria) throws EntidadeNaoEncontradaExcecao;

	public D atualizar(D dto) throws EntidadeNaoEncontradaExcecao, EntidadeJaExisteExcecao;

	public D recuperar(PK chavePrimaria) throws EntidadeNaoEncontradaExcecao;

}
