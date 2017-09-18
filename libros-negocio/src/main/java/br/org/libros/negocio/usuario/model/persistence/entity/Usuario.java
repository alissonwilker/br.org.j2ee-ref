package br.org.libros.negocio.usuario.model.persistence.entity;

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

/**
 * Entidade que representa um Cliente.
 *
 */
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "ID" }) })
public class Usuario implements IEntidade {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true, length = 10)
    @NotNull
    @Max(Integer.MAX_VALUE)
    private int id;

    @Column(name = "password", nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String senha;

    @Column(name = "role", nullable = false, length = 15)
    @NotNull
    @Size(max = 15)
    private String perfil;

    @Column(name = "username", nullable = false, unique = true, length = 20)
    @NotNull
    @Size(max = 20)
    private String nome;

    @Valid
    public Usuario() {
    }

    @Valid
    public Usuario(String nome, String senha, String perfil) {
        setNome(nome);
        setSenha(senha);
        setPerfil(perfil);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(@NotNull @Size(max = 32) String senha) {
        this.senha = senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(@NotNull @Size(max = 15) String perfil) {
        this.perfil = perfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(@NotNull @Size(max = 20) String nome) {
        this.nome = nome;
    }

}