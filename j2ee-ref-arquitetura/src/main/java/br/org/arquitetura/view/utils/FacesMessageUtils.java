package br.org.arquitetura.view.utils;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;

/**
 * Classe com métodos utilitários para tratamento de mensagens JSF.
 *
 */
public class FacesMessageUtils {

    /**
     * Adiciona um mensagem de informação a partir da chave e parâmetros que devem
     * ser recuperados do bundle de mensagens.
     * 
     * @param key
     *            chave da mensagem no bundle de mensagens.
     * @param parameters
     *            parâmetros para formatar a mensagem parametrizada.
     */
    public static void addInfoFacesMessage(String key, Object... parameters) {
        FacesMessage facesMessage = getInfoFacesMessage(key, parameters);
        JsfUtils.getFacesContext().addMessage(null, facesMessage);
    }

    /**
     * Recupera uma mensagem formatada do bundle de mensagens e retorna como uma
     * FacesMessage informativa.
     * 
     * @param key
     *            chave da mensagem no bundle de mensagens.
     * @param parameters
     *            parâmetros para formatar a mensagem parametrizada.
     * @return FacesMessage do tipo INFO com a mensagem formatada.
     */
    public static FacesMessage getInfoFacesMessage(String key, Object... parameters) {
        String message = getMsgResourceBundle().getString(key);
        String formattedMessage = MessageFormat.format(message, parameters);
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, formattedMessage, formattedMessage);
        return facesMessage;
    }

    private static ResourceBundle getMsgResourceBundle() {
        return JsfUtils.getResourceBundle("msg");
    }
}
