package br.org.libros.livrobiblioteca.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.org.arquitetura.dto.mapper.IGenericMapper;
import br.org.libros.livrobiblioteca.dto.LivroBibliotecaDto;
import br.org.libros.livrobiblioteca.model.persistence.entity.LivroBiblioteca;

/**
 * 
 * @see br.org.arquitetura.dto.mapper.IGenericMapper
 */
@Mapper
public interface ILivroBibliotecaMapper extends IGenericMapper<LivroBiblioteca, LivroBibliotecaDto> {
	ILivroBibliotecaMapper INSTANCE = Mappers.getMapper(ILivroBibliotecaMapper.class);

}
