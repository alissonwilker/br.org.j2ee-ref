package br.org.arquitetura.model.persistence.entity.listener;

import java.lang.invoke.MethodHandles;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.org.arquitetura.model.persistence.entity.IEntidade;

/**
 * Classe que lança eventos CDI quando as Entidades do sistema são atualizadas.
 *
 */
public class DefaultEntityListener {
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Inject
	@IEntidadeAtualizada
	private Event<EventoEntidade> eventoEntidadeAtualizada;

	@Inject
	@IEntidadePersistida
	private Event<EventoEntidade> eventoEntidadePersistida;

	@Inject
	@IEntidadeRemovida
	private Event<EventoEntidade> eventoEntidadeRemovida;

	/**
	 * Método chamado quando uma entidade é persistida.
	 * 
	 * @param entidade
	 *            a entidade persistida.
	 */
	@PostPersist
	public void postPersist(IEntidade entidade) {
		String mensagem = "postPersist de " + entidade.getClass().getSimpleName();
		logger.info(mensagem);
		eventoEntidadePersistida.fire(new EventoEntidade(EventoEntidade.Tipo.EntidadePersistida, entidade.getId()));
	}

	/**
	 * Método chamado quando uma entidade é atualizada.
	 * 
	 * @param entidade
	 *            a entidade atualizada.
	 */
	@PostUpdate
	public void postUpdate(IEntidade entidade) {
		String mensagem = "postUpdate de " + entidade.getClass().getSimpleName();
		logger.info(mensagem);
		eventoEntidadeAtualizada.fire(new EventoEntidade(EventoEntidade.Tipo.EntidadeAtualizada, entidade.getId()));

	}

	/**
	 * Método chamado quando uma entidade é removida.
	 * 
	 * @param entidade
	 *            a entidade removida.
	 */
	@PostRemove
	public void postRemove(IEntidade entidade) {
		String mensagem = "postRemove de " + entidade.getClass().getSimpleName();
		logger.info(mensagem);
		eventoEntidadeRemovida.fire(new EventoEntidade(EventoEntidade.Tipo.EntidadeRemovida, entidade.getId()));
	}

}
