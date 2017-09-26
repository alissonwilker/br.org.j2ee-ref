package br.org.libros.negocio.livrobiblioteca.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.org.arquitetura.dto.mapper.IGenericMapper;
import br.org.libros.negocio.livrobiblioteca.dto.LivroBibliotecaDto;
import br.org.libros.negocio.livrobiblioteca.model.persistence.entity.LivroBiblioteca;

/**
 * Mapper para converter entidade de LivroBiblioteca em DTO e vice-versa.
 * 
 * @see br.org.arquitetura.dto.mapper.IGenericMapper
 */
@Mapper
public interface ILivroBibliotecaMapper extends IGenericMapper<LivroBiblioteca, LivroBibliotecaDto> {
    ILivroBibliotecaMapper INSTANCE = Mappers.getMapper(ILivroBibliotecaMapper.class);

}
