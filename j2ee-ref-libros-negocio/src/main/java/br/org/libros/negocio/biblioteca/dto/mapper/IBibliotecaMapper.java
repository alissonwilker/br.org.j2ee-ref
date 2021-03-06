package br.org.libros.negocio.biblioteca.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.org.arquitetura.dto.mapper.IGenericMapper;
import br.org.libros.negocio.biblioteca.dto.BibliotecaDto;
import br.org.libros.negocio.biblioteca.model.persistence.entity.Biblioteca;
import br.org.libros.negocio.livrobiblioteca.dto.mapper.ILivroBibliotecaMapper;

/**
 * Mapper para converter entidade de biblioteca em DTO e vice-versa.
 * 
 * @see br.org.arquitetura.dto.mapper.IGenericMapper
 */
@Mapper(uses = ILivroBibliotecaMapper.class)
public interface IBibliotecaMapper extends IGenericMapper<Biblioteca, BibliotecaDto> {
    IBibliotecaMapper INSTANCE = Mappers.getMapper(IBibliotecaMapper.class);

}
