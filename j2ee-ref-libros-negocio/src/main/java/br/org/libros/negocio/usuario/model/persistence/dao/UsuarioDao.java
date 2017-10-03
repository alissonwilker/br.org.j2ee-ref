package br.org.libros.negocio.usuario.model.persistence.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.org.libros.negocio.comum.model.persistence.dao.AbstractLibrosDao;
import br.org.libros.negocio.usuario.model.persistence.entity.Usuario;

/**
 * DAO de Usuario.
 * 
 * @see br.org.libros.negocio.comum.model.persistence.dao.AbstractLibrosDao
 */
@Named
@RequestScoped
public class UsuarioDao extends AbstractLibrosDao<Usuario, Integer> {
    private static final long serialVersionUID = 1L;

}
