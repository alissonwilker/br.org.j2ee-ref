package br.org.arquitetura.model.persistence.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;

import br.org.arquitetura.excecao.EntidadeJaExisteExcecao;
import br.org.arquitetura.excecao.EntidadeNaoEncontradaExcecao;

/**
 * Classe abstrata que implementa comportamento padrão de um DAO.
 *
 * @param <E>
 *            tipo da Entidade.
 * @param <PK>
 *            tipo da chave primária da Entidade.
 * 
 * @see br.org.arquitetura.model.persistence.dao.IDao
 */
public abstract class AbstractDao<E, PK extends Serializable> implements IDao<E, PK>, Serializable {

    private static final long serialVersionUID = 1L;

    private EntityManager entityManager;
    private Class<?> domain;

    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Class<?> getDomainClass() {
        if (this.domain == null) {
            this.domain = getGenericTypeArgument(this.getClass(), 0);
        }
        return this.domain;
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> getGenericTypeArgument(final Class<?> clazz, final int idx) {
        final Type type = clazz.getGenericSuperclass();
        ParameterizedType paramType;
        try {
            paramType = (ParameterizedType) type;
        } catch (ClassCastException cause) {
            paramType = (ParameterizedType) ((Class<T>) type).getGenericSuperclass();
        }
        return (Class<T>) paramType.getActualTypeArguments()[idx];
    }

    @Override
    public E adicionar(E entidade) throws EntidadeJaExisteExcecao {
        try {
            entityManager.persist(entidade);
            return entidade;
        } catch (PersistenceException eeex) {
            if (eeex.getCause() instanceof ConstraintViolationException) {
                throw new EntidadeJaExisteExcecao(eeex);
            } else {
                throw eeex;
            }
        }
    }

    @Override
    public void remover(PK chavePrimaria) throws EntidadeNaoEncontradaExcecao {
        E entidade = recuperar(chavePrimaria);
        remover(entidade);
    }

    @Override
    public void remover(E entidade) throws EntidadeNaoEncontradaExcecao {
        try {
            entidade = entityManager.merge(entidade);
            entityManager.remove(entidade);
            entityManager.flush(); // requerido para mandar mensagem JMS pelo EntityListener.
        } catch (IllegalArgumentException iaex) {
            throw new EntidadeNaoEncontradaExcecao(iaex);
        }
    }

    @Override
    public E atualizar(E entidade) throws EntidadeNaoEncontradaExcecao, EntidadeJaExisteExcecao {
        try {
            E entidadeAtualizada = entityManager.merge(entidade);
            entityManager.flush();
            return entidadeAtualizada;
        } catch (IllegalArgumentException iaex) {
            throw new EntidadeNaoEncontradaExcecao(iaex);
        } catch (PersistenceException eeex) {
            if (eeex.getCause() instanceof ConstraintViolationException) {
                throw new EntidadeJaExisteExcecao(eeex);
            } else {
                throw eeex;
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public E recuperar(PK chavePrimaria) throws EntidadeNaoEncontradaExcecao {
        E entity = (E) entityManager.find(getDomainClass(), chavePrimaria);
        if (entity == null) {
            throw new EntidadeNaoEncontradaExcecao();
        }
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<E> listar() {
        return entityManager.createQuery("from " + getDomainClass().getSimpleName())
                        .getResultList();
    }

}
