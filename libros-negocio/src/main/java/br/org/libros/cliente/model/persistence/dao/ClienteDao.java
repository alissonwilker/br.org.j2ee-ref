package br.org.libros.cliente.model.persistence.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.org.libros.cliente.model.persistence.entity.Cliente;
import br.org.libros.comum.model.persistence.dao.LibrosAbstractDao;

/**
 * 
 * @see br.org.libros.comum.model.persistence.dao.LibrosAbstractDao
 */
@Named
@RequestScoped
public class ClienteDao extends LibrosAbstractDao<Cliente, Integer> {
	private static final long serialVersionUID = 1L;

}
