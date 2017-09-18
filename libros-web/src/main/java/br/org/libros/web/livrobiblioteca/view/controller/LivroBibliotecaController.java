package br.org.libros.web.livrobiblioteca.view.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.org.arquitetura.model.business.facade.IBusinessFacade;
import br.org.arquitetura.view.controller.AbstractController;
import br.org.libros.negocio.livrobiblioteca.dto.LivroBibliotecaDto;

/**
 * 
 * @see br.org.arquitetura.view.controller.AbstractController
 */
@ManagedBean
@ViewScoped
public class LivroBibliotecaController extends AbstractController<LivroBibliotecaDto, Integer> {

    private static final long serialVersionUID = 1L;

    @PostConstruct
    @Inject
    public void init(IBusinessFacade<LivroBibliotecaDto, Integer> businessFacade) {
        this.businessFacade = businessFacade;
    }

}