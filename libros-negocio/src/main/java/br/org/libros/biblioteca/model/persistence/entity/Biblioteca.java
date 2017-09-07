package br.org.libros.biblioteca.model.persistence.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.org.arquitetura.model.persistence.entity.IEntidade;
import br.org.libros.livrobiblioteca.model.persistence.entity.LivroBiblioteca;

/**
 * Entidade que representa uma Biblioteca.
 *
 */
@Entity
@Table(name = "Biblioteca", uniqueConstraints = { @UniqueConstraint(columnNames = { "ID" }) })
public class Biblioteca implements IEntidade {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true, length = 10)
	@NotNull
	@Max(Integer.MAX_VALUE)
	private int id;

	@Column(name = "NOME", length = 20, unique = true, nullable = false)
	@NotNull
	@Size(max = 20)
	private String nome;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<LivroBiblioteca> livros;

	@Valid
	public Biblioteca() {
	}

	@Valid
	public Biblioteca(String nome) {
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

	public String getNome() {
		return nome;
	}

	public void setNome(@NotNull @Size(max = 20) String nome) {
		this.nome = nome;
	}

	public List<LivroBiblioteca> getLivros() {
		return livros;
	}

	public void setLivros(List<LivroBiblioteca> livros) {
		this.livros = livros;
	}

}