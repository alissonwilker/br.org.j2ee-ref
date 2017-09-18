package br.org.livraria.livro.model.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import br.org.arquitetura.model.persistence.entity.listener.DefaultEntityListener;
import br.org.arquitetura.model.persistence.entity.validator.annotation.Isbn;

/**
 * Entidade que representa um Livro.
 *
 */
@Entity
@Table(name = "Livro", uniqueConstraints = { @UniqueConstraint(columnNames = { "ID" }) })
@EntityListeners(DefaultEntityListener.class)
public class Livro implements IEntidade {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true, length = 10)
    @NotNull
    @Max(Integer.MAX_VALUE)
    private int id;

    @Column(name = "ISBN", nullable = false, unique = true, length = 13)
    @NotNull
    @Isbn
    private String isbn;

    @Column(name = "NOME", length = 20, unique = false, nullable = false)
    @NotNull
    @Size(max = 20)
    private String nome;

    @Valid
    public Livro() {
    }

    @Valid
    public Livro(String isbn, String nome) {
        setIsbn(isbn);
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(@NotNull @Isbn String isbn) {
        this.isbn = isbn;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(@NotNull @Size(max = 20) String nome) {
        this.nome = nome;
    }

}