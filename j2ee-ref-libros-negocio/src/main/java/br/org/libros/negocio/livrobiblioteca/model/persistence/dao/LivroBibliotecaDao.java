package br.org.libros.negocio.livrobiblioteca.model.persistence.dao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.org.arquitetura.excecao.EntidadeNaoEncontradaExcecao;
import br.org.libros.negocio.comum.model.persistence.dao.AbstractLibrosDao;
import br.org.libros.negocio.livrobiblioteca.model.persistence.entity.LivroBiblioteca;

/**
 * DAO de LivroBiblioteca.
 * 
 * @see br.org.libros.negocio.comum.model.persistence.dao.AbstractLibrosDao
 */
@Named
@RequestScoped
public class LivroBibliotecaDao extends AbstractLibrosDao<LivroBiblioteca, Integer> {
    private static final long serialVersionUID = 1L;

    @Override
    public void remover(LivroBiblioteca entidade) throws EntidadeNaoEncontradaExcecao {
        entityManager.createNativeQuery("delete from BIBLIOTECA_LIVROBIBLIOTECA where LIVROS_ID = :id")
                .setParameter("id", entidade.getId()).executeUpdate();
        entityManager.flush();
        super.remover(entidade);
    }

}
