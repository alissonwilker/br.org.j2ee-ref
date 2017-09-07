package br.org.livraria.livro.model.business.facade;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.org.arquitetura.model.business.facade.AbstractBusinessFacade;
import br.org.livraria.livro.dto.LivroDto;
import br.org.livraria.livro.dto.mapper.ILivroMapper;
import br.org.livraria.livro.model.persistence.entity.Livro;

/**
 * 
 * @see br.org.arquitetura.model.business.facade.AbstractBusinessFacade
 */
@Named
@RequestScoped
@Transactional
public class LivroBusinessFacade extends AbstractBusinessFacade<Livro, LivroDto, Integer> {

	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		mapper = ILivroMapper.INSTANCE;
	}
}
