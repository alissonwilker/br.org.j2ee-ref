package br.org.libros.negocio.livrobiblioteca.model.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import br.org.arquitetura.model.persistence.entity.IEntidade;

/**
 * Entidade que representa um Livro.
 *
 */
@Entity
@Table(name = "LivroBiblioteca", uniqueConstraints = { @UniqueConstraint(columnNames = { "ID" }) })
public class LivroBiblioteca implements IEntidade {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false, unique = true, length = 10)
	@NotNull
	@Max(Integer.MAX_VALUE)
	private int id;
	
	@Transient
	private String nome;
	
	@Transient 
	private String isbn;

	@Valid
	public LivroBiblioteca() {
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

}