package br.org.libros.negocio.biblioteca.dto;

import java.util.List;

import br.org.arquitetura.dto.IDto;
import br.org.libros.negocio.livrobiblioteca.dto.LivroBibliotecaDto;

/**
 * DTO que representa a Entidade
 * {@link br.org.libros.negocio.biblioteca.model.persistence.entity.Biblioteca
 * Biblioteca}.
 *
 */
public class BibliotecaDto implements IDto {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private List<LivroBibliotecaDto> livros;

    public BibliotecaDto() {
    }

    public BibliotecaDto(String nomeBiblioteca) {
        setNome(nomeBiblioteca);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<LivroBibliotecaDto> getLivros() {
        return livros;
    }

    public void setLivros(List<LivroBibliotecaDto> livros) {
        this.livros = livros;
    }

}
