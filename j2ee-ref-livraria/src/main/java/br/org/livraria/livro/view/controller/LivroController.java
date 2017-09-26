package br.org.livraria.livro.view.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.org.arquitetura.model.business.facade.IBusinessFacade;
import br.org.arquitetura.view.controller.AbstractController;
import br.org.arquitetura.view.utils.JsfUtils;
import br.org.arquitetura.view.utils.JsfUtils.Pagina;
import br.org.livraria.livro.dto.LivroDto;

/**
 * Controller de Livro.
 * 
 * @see br.org.arquitetura.view.controller.AbstractController
 */
@ManagedBean
@ViewScoped
public class LivroController extends AbstractController<LivroDto, Integer> {

    private static final long serialVersionUID = 1L;

    @PostConstruct
    @Inject
    public void init(IBusinessFacade<LivroDto, Integer> businessFacade) {
        this.businessFacade = businessFacade;
    }

    /**
     * Cadastra um novo livro.
     * 
     * @param isbnLivro
     *            ISBN do livro a ser adicionado.
     * @param nomeLivro
     *            nome do livro a ser adicionado.
     * @return página inicial da aplicação.
     */
    public String adicionarLivro(String isbnLivro, String nomeLivro) {
        LivroDto livroDto = new LivroDto(isbnLivro, nomeLivro);
        if (super.adicionar(livroDto)) {
            return JsfUtils.getRedirecionamentoComMensagens(Pagina.app);
        }
        return null;
    }

}