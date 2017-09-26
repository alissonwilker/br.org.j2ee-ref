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
 * @param <P>
 *            tipo da chave primária da Entidade.
 * 
 * @see br.org.arquitetura.model.persistence.dao.IDao
 */
public interface IBusiness<E, P extends Serializable> {

    E adicionar(E entidade) throws EntidadeJaExisteExcecao, EntidadeNaoEncontradaExcecao;

    List<E> listar();

    void remover(E entidade) throws EntidadeNaoEncontradaExcecao;

    void remover(P chavePrimaria) throws EntidadeNaoEncontradaExcecao;

    E atualizar(E entidade) throws EntidadeNaoEncontradaExcecao, EntidadeJaExisteExcecao;

    E recuperar(P chavePrimaria) throws EntidadeNaoEncontradaExcecao;

}
