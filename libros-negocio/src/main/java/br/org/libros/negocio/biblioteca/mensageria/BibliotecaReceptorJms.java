package br.org.libros.negocio.biblioteca.mensageria;

import java.lang.invoke.MethodHandles;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.org.arquitetura.excecao.EntidadeNaoEncontradaExcecao;
import br.org.arquitetura.mensageria.AbstractReceptorJms;
import br.org.arquitetura.model.business.facade.IBusinessFacade;
import br.org.libros.negocio.livrobiblioteca.dto.LivroBibliotecaDto;

/**
 * 
 * @see br.org.arquitetura.mensageria.AbstractReceptorJms
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "tipoEvento='EntidadeRemovida'"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/ExpiryQueue") })
public class BibliotecaReceptorJms extends AbstractReceptorJms {
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Inject
	private IBusinessFacade<LivroBibliotecaDto, Integer> livroBibliotecaBusinessFacade;

	@Override
	public void onMessage(Message mensagem) {
		try {
			Integer chavePrimaria = mensagem.getBody(Integer.class);
			logger.info("mensagem JMS recebida pelo módulo Biblioteca: " + chavePrimaria);
			livroBibliotecaBusinessFacade.remover(chavePrimaria);
		} catch (JMSException jmsExc) {
			logger.error(jmsExc.getMessage(), jmsExc);
		} catch (EntidadeNaoEncontradaExcecao eneExc) {
			//comportamento normal, não há o que tratar
		}
	}
}
