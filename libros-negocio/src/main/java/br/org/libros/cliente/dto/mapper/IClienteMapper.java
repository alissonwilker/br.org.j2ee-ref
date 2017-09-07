package br.org.libros.cliente.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.org.arquitetura.dto.mapper.IGenericMapper;
import br.org.libros.cliente.dto.ClienteDto;
import br.org.libros.cliente.model.persistence.entity.Cliente;

/**
 * 
 * @see br.org.arquitetura.dto.mapper.IGenericMapper
 */
@Mapper
public interface IClienteMapper extends IGenericMapper<Cliente, ClienteDto> {
	IClienteMapper INSTANCE = Mappers.getMapper(IClienteMapper.class);

}
