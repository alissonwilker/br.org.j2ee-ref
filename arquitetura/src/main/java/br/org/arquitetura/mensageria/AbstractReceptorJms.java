package br.org.arquitetura.mensageria;

import javax.jms.MessageListener;

/**
 * Classe abstrata que representa o Message Driven Bean responsável por receber
 * mensagens de outros módulos/sistemas via JMS.
 * 
 */
public abstract class AbstractReceptorJms implements MessageListener {
}
