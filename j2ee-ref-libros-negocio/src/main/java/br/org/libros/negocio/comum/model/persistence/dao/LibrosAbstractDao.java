package br.org.libros.negocio.comum.model.persistence.dao;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import br.org.arquitetura.model.persistence.dao.AbstractDao;

/**
 * Classe abstrata que implementa comportamento padrão dos DAO's do módulo
 * Biblioteca.
 * 
 * @see br.org.arquitetura.model.persistence.dao.AbstractDao
 */
public abstract class LibrosAbstractDao<E, P extends Serializable> extends AbstractDao<E, P> {
    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "LibrosPersistenceUnit", type = PersistenceContextType.TRANSACTION)
    protected EntityManager entityManager;

    @PostConstruct
    public void init() {
        setEntityManager(entityManager);
    }
}
