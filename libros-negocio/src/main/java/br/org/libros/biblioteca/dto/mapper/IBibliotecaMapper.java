package br.org.libros.biblioteca.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.org.arquitetura.dto.mapper.IGenericMapper;
import br.org.libros.biblioteca.dto.BibliotecaDto;
import br.org.libros.biblioteca.model.persistence.entity.Biblioteca;
import br.org.libros.livrobiblioteca.dto.mapper.ILivroBibliotecaMapper;

/**
 * 
 * @see br.org.arquitetura.dto.mapper.IGenericMapper
 */
@Mapper(uses = ILivroBibliotecaMapper.class)
public interface IBibliotecaMapper extends IGenericMapper<Biblioteca, BibliotecaDto> {
	IBibliotecaMapper INSTANCE = Mappers.getMapper(IBibliotecaMapper.class);

}
