package br.org.buildtools.arquitetura.fabrica;

import br.org.buildtools.arquitetura.TipoArquitetural;
import br.org.buildtools.arquitetura.enums.AnotacaoArquitetural;
import br.org.buildtools.arquitetura.enums.HerancaArquitetural;
import br.org.buildtools.arquitetura.enums.InterfaceArquitetural;
import br.org.buildtools.arquitetura.enums.PacoteArquitetural;
import br.org.buildtools.arquitetura.enums.SufixoArquitetural;

public class FabricaTipoArquitetural {
    public static TipoArquitetural criarMapper() {
        TipoArquitetural mapper = new TipoArquitetural(SufixoArquitetural.Mapper, PacoteArquitetural.DtoMapper);

        mapper.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Mapper);

        return mapper;
    }

    public static TipoArquitetural criarEntidadePk() {
        TipoArquitetural entidadePk = new TipoArquitetural(SufixoArquitetural.PK, PacoteArquitetural.Entidades);
        
        entidadePk.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Embeddable);
        
        entidadePk.adicionarHeranca(HerancaArquitetural.BaseEntidade);

        return entidadePk;
    }
    
    public static TipoArquitetural criarEntidade() {
        TipoArquitetural entidade = new TipoArquitetural(PacoteArquitetural.Entidades);

        entidade.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Entity);
        entidade.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Table);
        
        entidade.adicionarHeranca(HerancaArquitetural.BaseEntidade);

        return entidade;
    }

    public static TipoArquitetural criarUtil() {
        TipoArquitetural util = new TipoArquitetural(PacoteArquitetural.Util);

        return util;
    }

    public static TipoArquitetural criarInfra() {
        TipoArquitetural infra = new TipoArquitetural(PacoteArquitetural.Infra);

        return infra;
    }

    public static TipoArquitetural criarDto() {
        TipoArquitetural dto = new TipoArquitetural(SufixoArquitetural.Dto, PacoteArquitetural.Dto);
        
        dto.adicionarHeranca(HerancaArquitetural.BaseDto);

        return dto;
    }

    public static TipoArquitetural criarEnum() {
        TipoArquitetural tipoEnum = new TipoArquitetural(SufixoArquitetural.Enum, PacoteArquitetural.Enums);

        return tipoEnum;
    }
    
    public static TipoArquitetural criarAttributeConverter() {
        TipoArquitetural dto = new TipoArquitetural(SufixoArquitetural.Converter, PacoteArquitetural.EntidadesUtilConversores);
        
        dto.adicionarInterface(InterfaceArquitetural.AttributeConverter);

        return dto;
    }
    
    public static TipoArquitetural criarExceptionMapper() {
        TipoArquitetural exceptionMapper = new TipoArquitetural(SufixoArquitetural.ExceptionMapper, PacoteArquitetural.ExcecoesApiMapper);
        
        exceptionMapper.adicionarHeranca(HerancaArquitetural.AbstractExceptionMapper);
        
        exceptionMapper.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Provider);

        return exceptionMapper;
    }

    public static TipoArquitetural criarExcecao() {
        TipoArquitetural excecao = new TipoArquitetural(SufixoArquitetural.Exception, PacoteArquitetural.Excecoes);
        
        excecao.adicionarHeranca(HerancaArquitetural.PjeRuntimeException);
        excecao.adicionarHeranca(HerancaArquitetural.PJeException);

        return excecao;
    }


    public static TipoArquitetural criarDao() {
        TipoArquitetural dao = new TipoArquitetural(SufixoArquitetural.Dao, PacoteArquitetural.Persistencia);
        dao.adicionarHeranca(HerancaArquitetural.PJeSegurancaDao);
        dao.adicionarHeranca(HerancaArquitetural.AbstractPjeBaseDao);

        return dao;
    }

    public static TipoArquitetural criarDaoLegado() {
        TipoArquitetural daoLegado = new TipoArquitetural(SufixoArquitetural.Dao, PacoteArquitetural.PersistenciaLegado);
        daoLegado.adicionarHeranca(HerancaArquitetural.MultiplasInstanciasDao);
        daoLegado.adicionarHeranca(HerancaArquitetural.AbstractPjeBaseDao);

        return daoLegado;
    }

    public static TipoArquitetural criarServico() {
        TipoArquitetural servico = new TipoArquitetural(SufixoArquitetural.Servico, PacoteArquitetural.Servico);
        servico.adicionarHeranca(HerancaArquitetural.PjeBaseServico);
        servico.adicionarHeranca(HerancaArquitetural.AbstractPjeBaseServicoCrud);

        servico.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Stateless);
        servico.adicionarAnotacoesAlternativas(AnotacaoArquitetural.Stateless, AnotacaoArquitetural.Singleton);

        return servico;
    }

    public static TipoArquitetural criarApi() {
        TipoArquitetural api = new TipoArquitetural(SufixoArquitetural.Api, PacoteArquitetural.Api);
        api.adicionarHeranca(HerancaArquitetural.BaseApi);

        api.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Api);
        api.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Path);

        return api;
    }

}
