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
 * Controller de Usuario.
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

    /**
     * Recupera o nome do usuario autenticado no sistema.
     * 
     * @return o nome do usuario autenticado no sistema.
     */
    public String getNomeUsuarioAutenticado() {
        Principal principal = JsfUtils.getRequest().getUserPrincipal();
        if (principal != null) {
            return principal.getName();
        }
        return null;
    }

    /**
     * Verifica se o usuario possui as roles especificadas.
     * 
     * @param roles
     *            as roles especificadas.
     * @return Verdadeiro, caso o usuario possua as roles. Falso, caso contrario.
     */
    public boolean isUserInRole(String... roles) {
        boolean userIsInRole = false;
        int i = 0;
        while (i < roles.length && !userIsInRole) {
            userIsInRole = JsfUtils.getRequest().isUserInRole(roles[i++]);
        }
        return userIsInRole;
    }

    /**
     * Redireciona para a pagina principal se estiver autenticado.
     * 
     * @throws IOException
     *             se ocorrer problema no redirecionamento.
     */
    public void redirecionarSeAutenticado() throws IOException {
        if (isAutenticado()) {
            JsfUtils.redirecionar(Pagina.app);
        }
    }

    /**
     * Realiza login do usuario.
     * 
     * @param usuario
     *            o usuario.
     * @param senha
     *            a senha.
     * @return a pagina de redirecionamento.
     */
    public String login(String usuario, String senha) {
        try {
            JsfUtils.getRequest().login(usuario, senha);
        } catch (ServletException e) {
            FacesMessageUtils.addInfoFacesMessage("login.falhou");
            return null;
        }

        return JsfUtils.getRedirecionamentoComMensagens(Pagina.app);
    }

    /**
     * Realiza logout do usuario.
     * 
     * @return a pagina de redirecionamento.
     */
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