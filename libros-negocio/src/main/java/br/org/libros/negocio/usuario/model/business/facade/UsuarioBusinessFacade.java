package br.org.libros.negocio.usuario.model.business.facade;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import br.org.arquitetura.model.business.facade.AbstractBusinessFacade;
import br.org.libros.negocio.usuario.dto.UsuarioDto;
import br.org.libros.negocio.usuario.dto.mapper.IUsuarioMapper;
import br.org.libros.negocio.usuario.model.persistence.entity.Usuario;

/**
 * 
 * @see br.org.arquitetura.model.business.facade.AbstractBusinessFacade
 */
@Named
@RequestScoped
@Transactional
public class UsuarioBusinessFacade extends AbstractBusinessFacade<Usuario, UsuarioDto, Integer> {

	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {
		mapper = IUsuarioMapper.INSTANCE;
	}
}
