package br.org.livraria.livro.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.org.arquitetura.dto.mapper.IGenericMapper;
import br.org.livraria.livro.dto.LivroDto;
import br.org.livraria.livro.model.persistence.entity.Livro;

/**
 * 
 * @see br.org.arquitetura.dto.mapper.IGenericMapper
 */
@Mapper
public interface ILivroMapper extends IGenericMapper<Livro, LivroDto> {
    ILivroMapper INSTANCE = Mappers.getMapper(ILivroMapper.class);

}
