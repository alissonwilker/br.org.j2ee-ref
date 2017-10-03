package br.org.livraria.livro.model.persistence.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.org.livraria.comum.model.persistence.dao.AbstractLivrariaDao;
import br.org.livraria.livro.model.persistence.entity.Livro;

/**
 * DAO de Livro.
 * 
 * @see br.org.livraria.comum.model.persistence.dao.AbstractLivrariaDao
 */
@Named
@RequestScoped
public class LivroDao extends AbstractLivrariaDao<Livro, Integer> {
    private static final long serialVersionUID = 1L;

}