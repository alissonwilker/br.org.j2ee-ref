package br.org.libros.negocio.cliente.dto;

import br.org.arquitetura.dto.IDto;

/**
 * DTO que representa a Entidade {@link br.org.libros.negocio.cliente.model.persistence.entity.Cliente Cliente}.
 *
 */
public class ClienteDto implements IDto {

	private static final long serialVersionUID = 1L;

	private int id;

	private String cpf;

	private String nome;

	public ClienteDto() {
	}

	public ClienteDto(String cpfCliente, String nomeCliente) {
		setCpf(cpfCliente);
		setNome(nomeCliente);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
