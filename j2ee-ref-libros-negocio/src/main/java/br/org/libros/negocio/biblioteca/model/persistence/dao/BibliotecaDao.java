package br.org.libros.negocio.biblioteca.model.persistence.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.org.libros.negocio.biblioteca.model.persistence.entity.Biblioteca;
import br.org.libros.negocio.comum.model.persistence.dao.AbstractLibrosDao;

/**
 * DAO da Biblioteca.
 * 
 * @see br.org.libros.negocio.comum.model.persistence.dao.AbstractLibrosDao
 */
@Named
@RequestScoped
public class BibliotecaDao extends AbstractLibrosDao<Biblioteca, Integer> {
    private static final long serialVersionUID = 1L;

}
