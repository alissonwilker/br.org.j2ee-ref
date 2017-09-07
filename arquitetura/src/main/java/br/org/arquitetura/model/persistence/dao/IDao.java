package br.org.arquitetura.model.persistence.dao;

import java.io.Serializable;
import java.util.List;

import br.org.arquitetura.excecao.EntidadeJaExisteExcecao;
import br.org.arquitetura.excecao.EntidadeNaoEncontradaExcecao;

/**
 * Interface padrão de um DAO.
 *
 * @param <E>
 *            tipo da Entidade.
 * @param <PK>
 *            tipo da chave primária da Entidade.
 */
public interface IDao<E, PK extends Serializable> {

	/**
	 * Adiciona um registro da Entidade na base de dados.
	 * 
	 * @param entidade
	 *            a Entidade a ser adicionada na base.
	 * @return um objeto gerenciado da Entidade adicionada.
	 * @throws EntidadeJaExisteExcecao
	 *             se a Entidade já existir na base.
	 */
	public E adicionar(E entidade) throws EntidadeJaExisteExcecao;

	/**
	 * Lista todos os registros da Entidade que existem na base da dados.
	 * 
	 * @return uma lista com os registros da Entidade.
	 */
	public List<E> listar();

	/**
	 * Remove uma Entidade da base de dados.
	 * 
	 * @param entidade
	 *            a Entidade a ser removida.
	 * @throws EntidadeNaoEncontradaExcecao
	 *             se a Entidade não for encontrada para remoção.
	 */
	public void remover(E entidade) throws EntidadeNaoEncontradaExcecao;

	/**
	 * Remove uma Entidade a partir da chave primária.
	 * 
	 * @param chavePrimaria
	 *            a chave primária da Entidade a ser removida.
	 * @throws EntidadeNaoEncontradaExcecao
	 *             se a Entidade correspondente à chave primária não for
	 *             encontrada.
	 */
	public void remover(PK chavePrimaria) throws EntidadeNaoEncontradaExcecao;

	/**
	 * Atualiza o registro de uma Entidade na base de dados.
	 * 
	 * @param entidade
	 *            a Entidade gerenciada a ser atualizada na base.
	 * @return um objeto gerenciado da Entidade atualizada.
	 * @throws EntidadeNaoEncontradaExcecao
	 *             se a Entidade não for encontrada na base para atualização.
	 * @throws EntidadeJaExisteExcecao
	 *             se ocorrer violação de constraint.
	 */
	public E atualizar(E entidade) throws EntidadeNaoEncontradaExcecao, EntidadeJaExisteExcecao;

	/**
	 * Recupera uma Entidade da base de dados a partir da chave primária.
	 * 
	 * @param chavePrimaria
	 *            a chave primária da Entidade a ser recuperada.
	 * @return um objeto gerenciado da Entidade recuperada.
	 * @throws EntidadeNaoEncontradaExcecao
	 *             se a Entidade não for encontrada na base.
	 */
	public E recuperar(PK chavePrimaria) throws EntidadeNaoEncontradaExcecao;

}
