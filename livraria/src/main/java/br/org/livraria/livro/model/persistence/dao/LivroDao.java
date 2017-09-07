package br.org.livraria.livro.model.persistence.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.org.livraria.comum.model.persistence.dao.LivrariaAbstractDao;
import br.org.livraria.livro.model.persistence.entity.Livro;

/**
 * 
 * @see br.org.livraria.comum.model.persistence.dao.LivrariaAbstractDao
 */
@Named
@RequestScoped
public class LivroDao extends LivrariaAbstractDao<Livro, Integer> {
	private static final long serialVersionUID = 1L;

}