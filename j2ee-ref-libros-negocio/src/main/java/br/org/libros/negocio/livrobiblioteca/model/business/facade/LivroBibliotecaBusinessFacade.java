package br.org.libros.negocio.livrobiblioteca.model.business.facade;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.org.arquitetura.model.business.facade.AbstractBusinessFacade;
import br.org.libros.negocio.livrobiblioteca.dto.LivroBibliotecaDto;
import br.org.libros.negocio.livrobiblioteca.dto.mapper.ILivroBibliotecaMapper;
import br.org.libros.negocio.livrobiblioteca.model.persistence.entity.LivroBiblioteca;

/**
 * Fachada de LivroBiblioteca.
 * 
 * @see br.org.arquitetura.model.business.facade.AbstractBusinessFacade
 */
@Named("LivroBibliotecaBusinessFacade")
@RequestScoped
@Transactional
public class LivroBibliotecaBusinessFacade
        extends AbstractBusinessFacade<LivroBiblioteca, LivroBibliotecaDto, Integer> {

    private static final long serialVersionUID = 1L;

    @PostConstruct
    public void init() {
        mapper = ILivroBibliotecaMapper.INSTANCE;
    }

}
