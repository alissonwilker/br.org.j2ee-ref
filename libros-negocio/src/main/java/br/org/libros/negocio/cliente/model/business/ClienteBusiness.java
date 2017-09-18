package br.org.libros.negocio.cliente.model.business;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.org.arquitetura.model.business.AbstractBusiness;
import br.org.libros.negocio.cliente.model.persistence.entity.Cliente;

/**
 * 
 * @see br.org.arquitetura.model.business.AbstractBusiness
 */
@Named
@RequestScoped
public class ClienteBusiness extends AbstractBusiness<Cliente, Integer> {

    private static final long serialVersionUID = 1L;
}
