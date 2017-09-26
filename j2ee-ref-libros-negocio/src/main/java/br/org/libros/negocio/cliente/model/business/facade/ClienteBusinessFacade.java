package br.org.libros.negocio.cliente.model.business.facade;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.org.arquitetura.model.business.facade.AbstractBusinessFacade;
import br.org.libros.negocio.cliente.dto.ClienteDto;
import br.org.libros.negocio.cliente.dto.mapper.IClienteMapper;
import br.org.libros.negocio.cliente.model.persistence.entity.Cliente;

/**
 * Fachada de Cliente.
 * 
 * @see br.org.arquitetura.model.business.facade.AbstractBusinessFacade
 */
@Named
@RequestScoped
@Transactional
public class ClienteBusinessFacade extends AbstractBusinessFacade<Cliente, ClienteDto, Integer> {

    private static final long serialVersionUID = 1L;

    @PostConstruct
    public void init() {
        mapper = IClienteMapper.INSTANCE;
    }

}
