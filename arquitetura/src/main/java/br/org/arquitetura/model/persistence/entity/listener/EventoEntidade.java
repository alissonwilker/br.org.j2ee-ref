package br.org.arquitetura.model.persistence.entity.listener;

import java.io.Serializable;

/**
 * Classe que representa um evento de ciclo de vida de entidade.
 *
 */
public class EventoEntidade implements Serializable {
	private static final long serialVersionUID = 1L;
	private Serializable payload;
	private Tipo tipo;

	public enum Tipo {
		EntidadeAtualizada, EntidadePersistida, EntidadeRemovida
	}

	public EventoEntidade() {
	}

	public EventoEntidade(Tipo tipo, Serializable payload) {
		setPayload(payload);
		setTipo(tipo);
	}

	public Serializable getPayload() {
		return payload;
	}

	public void setPayload(Serializable payload) {
		this.payload = payload;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

}
