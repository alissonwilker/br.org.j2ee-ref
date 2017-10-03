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

        mapper.adicionarAnotacao(AnotacaoArquitetural.Mapper);

        return mapper;
    }

    public static TipoArquitetural criarEntidade() {
        TipoArquitetural entidade = new TipoArquitetural(PacoteArquitetural.Entidades);

        entidade.adicionarAnotacao(AnotacaoArquitetural.Entity);
        entidade.adicionarAnotacao(AnotacaoArquitetural.Table);
        
        entidade.adicionarHeranca(HerancaArquitetural.BaseEntidade);

        return entidade;
    }

    public static TipoArquitetural criarUtils() {
        TipoArquitetural util = new TipoArquitetural(SufixoArquitetural.Util, PacoteArquitetural.Util);

        return util;
    }

    public static TipoArquitetural criarDto() {
        TipoArquitetural dto = new TipoArquitetural(SufixoArquitetural.Dto, PacoteArquitetural.Dto);

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
        
        exceptionMapper.adicionarAnotacao(AnotacaoArquitetural.Provider);

        return exceptionMapper;
    }

    public static TipoArquitetural criarExcecao() {
        TipoArquitetural excecao = new TipoArquitetural(SufixoArquitetural.Exception, PacoteArquitetural.Excecoes);
        
        excecao.adicionarHeranca(HerancaArquitetural.PjeRuntimeException);
        excecao.adicionarHeranca(HerancaArquitetural.PJeException);

        return excecao;
    }

//    public static TipoArquitetural criarNotificadorJms() {
//        TipoArquitetural notificadorJms = new TipoArquitetural(SufixoArquitetural.NotificadorJms,
//            PacoteArquitetural.Mensageria);
//        notificadorJms.adicionarHeranca(HerancaArquitetural.AbstractNotificadorJms);
//
//        notificadorJms.adicionarAnotacao(AnotacaoArquitetural.Stateless);
//
//        return notificadorJms;
//    }
//
//    public static TipoArquitetural criarReceptorJms() {
//        TipoArquitetural receptorJms = new TipoArquitetural(SufixoArquitetural.ReceptorJms,
//            PacoteArquitetural.Mensageria);
//        receptorJms.adicionarHeranca(HerancaArquitetural.AbstractReceptorJms);
//
//        receptorJms.adicionarAnotacao(AnotacaoArquitetural.MessageDriven);
//
//        return receptorJms;
//    }

//    public static TipoArquitetural criarBusinessFacade() {
//        TipoArquitetural businessFacade = new TipoArquitetural(SufixoArquitetural.BusinessFacade,
//            PacoteArquitetural.ModelBusinessFacade);
//        businessFacade.adicionarHeranca(HerancaArquitetural.AbstractBusinessFacade);
//
//        businessFacade.adicionarAnotacao(AnotacaoArquitetural.Named);
//        businessFacade.adicionarAnotacao(AnotacaoArquitetural.RequestScoped);
//        businessFacade.adicionarAnotacao(AnotacaoArquitetural.Transactional);
//
//        return businessFacade;
//    }

    public static TipoArquitetural criarDao() {
<<<<<<< HEAD
        TipoArquitetural dao = new TipoArquitetural(SufixoArquitetural.Dao, PacoteArquitetural.Persistencia);
        dao.adicionarHeranca(HerancaArquitetural.PjeComumBaseDao);

        return dao;
    }

//    public static TipoArquitetural criarAbstractDao() {
//        TipoArquitetural dao = new TipoArquitetural(SufixoArquitetural.AbstractDao,
//            PacoteArquitetural.Persistencia);
//        dao.adicionarHeranca(HerancaArquitetural.AbstractDao);
//
//        return dao;
//    }
=======
        TipoArquitetural dao = new TipoArquitetural(SufixoArquitetural.Dao, PacoteArquitetural.ModelPersistenceDao);
        dao.adicionarHeranca(HerancaArquitetural.AbstractLivrariaDao);
        dao.adicionarHeranca(HerancaArquitetural.AbstractLibrosDao);
        dao.adicionarHeranca(HerancaArquitetural.AbstractDao);

        dao.adicionarAnotacao(AnotacaoArquitetural.Named);
        dao.adicionarAnotacao(AnotacaoArquitetural.RequestScoped);
>>>>>>> refs/remotes/origin/master

    public static TipoArquitetural criarServico() {
        TipoArquitetural servico = new TipoArquitetural(SufixoArquitetural.Servico, PacoteArquitetural.Servico);
        servico.adicionarHeranca(HerancaArquitetural.PjeBaseServico);

        servico.adicionarAnotacao(AnotacaoArquitetural.Stateless);

        return servico;
    }

    public static TipoArquitetural criarApi() {
        TipoArquitetural api = new TipoArquitetural(SufixoArquitetural.Api, PacoteArquitetural.Api);
        api.adicionarHeranca(HerancaArquitetural.BaseApi);

        api.adicionarAnotacao(AnotacaoArquitetural.Path);
        api.adicionarAnotacao(AnotacaoArquitetural.Api);

        return api;
    }

}
