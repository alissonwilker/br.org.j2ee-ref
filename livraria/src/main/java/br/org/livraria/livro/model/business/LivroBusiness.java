package br.org.livraria.livro.model.business;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.org.arquitetura.model.business.AbstractBusiness;
import br.org.livraria.livro.model.persistence.entity.Livro;

/**
 * 
 * @see br.org.arquitetura.model.business.AbstractBusiness
 */
@Named
@RequestScoped
public class LivroBusiness extends AbstractBusiness<Livro, Integer> {

	private static final long serialVersionUID = 1L;
}
