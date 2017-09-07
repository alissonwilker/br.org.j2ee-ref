package br.org.arquitetura.view.utils;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Classe com métodos utilitários para facilitar acesso a diversos recursos oferecidos pelo JSF, como FacesContext, Resource Bundle...
 *
 */
public class JsfUtils {
	public enum Pagina {
		app, login;
	}

	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public static Application getApplication() {
		return getFacesContext().getApplication();
	}

	public static UIViewRoot getViewRoot() {
		return getFacesContext().getViewRoot();
	}

	public static void setLocale(Locale locale) {
		getViewRoot().setLocale(locale);
	}

	public static ResourceBundle getResourceBundle(String key) {
		return getApplication().getResourceBundle(getFacesContext(), key);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) getExternalContext().getRequest();
	}

	public static ExternalContext getExternalContext() {
		return JsfUtils.getFacesContext().getExternalContext();
	}

	public static String getRedirecionamentoComMensagens(Pagina pagina) {
		getExternalContext().getFlash().setKeepMessages(true);
		return "/pages/" + pagina.name() + "?faces-redirect=true";
	}

	public static void redirecionar(Pagina pagina) throws IOException {
		getExternalContext().redirect(pagina.name() + ".xhtml");
	}

}
