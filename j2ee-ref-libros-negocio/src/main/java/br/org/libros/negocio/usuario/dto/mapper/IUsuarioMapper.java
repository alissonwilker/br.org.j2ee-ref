package br.org.libros.negocio.usuario.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.org.arquitetura.dto.mapper.IGenericMapper;
import br.org.libros.negocio.usuario.dto.UsuarioDto;
import br.org.libros.negocio.usuario.model.persistence.entity.Usuario;

/**
 * 
 * @see br.org.arquitetura.dto.mapper.IGenericMapper
 */
@Mapper
public interface IUsuarioMapper extends IGenericMapper<Usuario, UsuarioDto> {
    IUsuarioMapper INSTANCE = Mappers.getMapper(IUsuarioMapper.class);

}
