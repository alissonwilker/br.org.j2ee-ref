package br.org.libros.web.usuario.view.controller;

import java.io.IOException;
import java.security.Principal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.ServletException;

import br.org.arquitetura.view.controller.AbstractController;
import br.org.arquitetura.view.utils.FacesMessageUtils;
import br.org.arquitetura.view.utils.JsfUtils;
import br.org.arquitetura.view.utils.JsfUtils.Pagina;
import br.org.libros.negocio.usuario.dto.UsuarioDto;

/**
 * 
 * @see br.org.arquitetura.view.controller.AbstractController
 */
@Named
@RequestScoped
public class UsuarioController extends AbstractController<UsuarioDto, Integer> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private boolean autenticado;

	public boolean isAutenticado() {
		return JsfUtils.getRequest().getUserPrincipal() != null;
	}

	public String getNomeUsuarioAutenticado() {
		Principal principal = JsfUtils.getRequest().getUserPrincipal();
		if (principal != null) {
			return principal.getName();
		}
		return null;
	}

	public boolean isUserInRole(String... roles) {
		boolean userIsInRole = false;
		int i = 0;
		while (i < roles.length && !userIsInRole) {
			userIsInRole = JsfUtils.getRequest().isUserInRole(roles[i++]);
		}
		return userIsInRole;
	}

	public void redirecionarSeAutenticado() throws IOException {
		if (isAutenticado()) {
			JsfUtils.redirecionar(Pagina.app);
		}
	}

	public String login(String usuario, String senha) {
		try {
			JsfUtils.getRequest().login(usuario, senha);
		} catch (ServletException e) {
			FacesMessageUtils.addInfoFacesMessage("login.falhou");
			return null;
		}

		return JsfUtils.getRedirecionamentoComMensagens(Pagina.app);
	}

	public String logout() {
		try {
			JsfUtils.getRequest().logout();
			return JsfUtils.getRedirecionamentoComMensagens(Pagina.login);
		} catch (ServletException e) {
			FacesMessageUtils.addInfoFacesMessage("logout.falhou");
		}

		return null;
	}

}