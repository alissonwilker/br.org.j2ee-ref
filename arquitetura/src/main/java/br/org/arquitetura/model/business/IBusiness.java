package br.org.arquitetura.model.business;

import java.io.Serializable;
import java.util.List;

import br.org.arquitetura.excecao.EntidadeJaExisteExcecao;
import br.org.arquitetura.excecao.EntidadeNaoEncontradaExcecao;

/**
 * Interface padrão da camada negocial de um módulo.
 *
 * @param <E>
 *            tipo da Entidade.
 * @param <PK>
 *            tipo da chave primária da Entidade.
 * 
 * @see br.org.arquitetura.model.persistence.dao.IDao
 */
public interface IBusiness<E, PK extends Serializable> {

    E adicionar(E entidade) throws EntidadeJaExisteExcecao, EntidadeNaoEncontradaExcecao;

    List<E> listar();

    void remover(E entidade) throws EntidadeNaoEncontradaExcecao;

    void remover(PK chavePrimaria) throws EntidadeNaoEncontradaExcecao;

    E atualizar(E entidade) throws EntidadeNaoEncontradaExcecao, EntidadeJaExisteExcecao;

    E recuperar(PK chavePrimaria) throws EntidadeNaoEncontradaExcecao;

}
