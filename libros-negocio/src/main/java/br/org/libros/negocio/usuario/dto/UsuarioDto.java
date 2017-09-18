package br.org.libros.negocio.usuario.dto;

import br.org.arquitetura.dto.IDto;

/**
 * DTO que representa a Entidade
 * {@link br.org.libros.negocio.usuario.model.persistence.entity.Usuario
 * Usuario}.
 *
 */
public class UsuarioDto implements IDto {

    private static final long serialVersionUID = 1L;

    private int id;

    private String senha;

    private String perfil;

    private String nome;

    public UsuarioDto() {
    }

    public UsuarioDto(String nome, String senha, String perfil) {
        setNome(nome);
        setSenha(senha);
        setPerfil(perfil);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
