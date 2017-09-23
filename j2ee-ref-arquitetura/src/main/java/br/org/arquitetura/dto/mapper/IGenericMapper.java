package br.org.arquitetura.dto.mapper;

import java.util.List;

/**
 * Mapeador genérico de Entidade para DTO e vice-versa.
 *
 * @param <E>
 *            tipo da Entidade.
 * @param <D>
 *            tipo do DTO.
 */
public interface IGenericMapper<E, D> {
    /**
     * Converte uma Entidade em seu correspondente DTO.
     * 
     * @param entidade
     *            a Entidade a ser convertida em DTO.
     * @return um DTO que representa a Entidade.
     */
    D converterParaDto(E entidade);

    /**
     * Converte um DTO em sua Entidade correspondente.
     * 
     * @param dto
     *            o DTO a ser convertida na Entidade.
     * @return uma Entidade que corresponde ao DTO.
     */
    E converterParaEntidade(D dto);

    /**
     * Converte uma lista de Entidades na lista correspondente de DTOs.
     * 
     * @param entidades
     *            a lista de Entidades que deve ser convertida em lista de DTOs.
     * @return a lista de DTOs que corresponde à lista de Entidades original.
     */
    List<D> converterParaDtos(List<E> entidades);

    /**
     * Converte uma lista de DTOs na lista correspondente de Entidades.
     * 
     * @param dtos
     *            a lista de DTOs que deve ser convertida em lista de Entidades.
     * @return a lista de Entidades que corresponde à lista de DTOs original.
     */
    List<E> converterParaEntidades(List<D> dtos);

}
