package br.org.libros.cliente.model.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.org.arquitetura.model.persistence.entity.IEntidade;
import br.org.arquitetura.model.persistence.entity.validator.annotation.Cpf;

/**
 * Entidade que representa um Cliente.
 *
 */
@Entity
@Table(name = "Cliente", uniqueConstraints = { @UniqueConstraint(columnNames = { "ID" }) })
public class Cliente implements IEntidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true, length = 10)
	@NotNull
	@Max(Integer.MAX_VALUE)
	private int id;

	@Column(name = "CPF", nullable = false, unique = true, length = 11)
	@NotNull
	@Cpf
	private String cpf;

	@Column(name = "NOME", nullable = false, unique = false, length = 20)
	@NotNull
	@Size(max = 20)
	private String nome;

	@Valid
	public Cliente() {
	}

	@Valid
	public Cliente(String cpf, String nome) {
		setCpf(cpf);
		setNome(nome);
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(@NotNull @Cpf String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(@NotNull @Size(max = 20) String nome) {
		this.nome = nome;
	}

}