package br.org.arquitetura.model.business;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.org.arquitetura.excecao.EntidadeJaExisteExcecao;
import br.org.arquitetura.excecao.EntidadeNaoEncontradaExcecao;
import br.org.arquitetura.model.persistence.dao.IDao;

/**
 * Classe abstrata que contém implementação de comportamento padrão da camada negocial de um módulo.
 *
 * @param <E> tipo da Entidade.
 * @param <PK> tipo da chave primária da Entidade.
 * 
 * @see br.org.arquitetura.model.business.IBusiness
 */
public abstract class AbstractBusiness<E, PK extends Serializable> implements IBusiness<E, PK>, Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IDao<E, PK> dao;

	@Override
	public E adicionar(E entidade) throws EntidadeJaExisteExcecao, EntidadeNaoEncontradaExcecao {
		return dao.adicionar(entidade);
	}

	@Override
	public void remover(E entidade) throws EntidadeNaoEncontradaExcecao {
		dao.remover(entidade);
	}

	@Override
	public void remover(PK chavePrimaria) throws EntidadeNaoEncontradaExcecao {
		dao.remover(chavePrimaria);
	}

	@Override
	public E atualizar(E entidade) throws EntidadeNaoEncontradaExcecao, EntidadeJaExisteExcecao {
		return dao.atualizar(entidade);
	}

	@Override
	public List<E> listar() {
		return dao.listar();
	}

	@Override
	public E recuperar(PK chavePrimaria) throws EntidadeNaoEncontradaExcecao {
		return dao.recuperar(chavePrimaria);
	}
	
}
