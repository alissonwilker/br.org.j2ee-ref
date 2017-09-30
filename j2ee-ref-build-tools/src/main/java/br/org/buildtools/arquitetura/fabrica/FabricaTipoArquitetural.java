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
        mapper.adicionarHeranca(HerancaArquitetural.IGenericMapper);

        mapper.adicionarAnotacao(AnotacaoArquitetural.Mapper);

        return mapper;
    }

    public static TipoArquitetural criarEntidade() {
        TipoArquitetural entidade = new TipoArquitetural(PacoteArquitetural.ModelPersistenceEntity);

        entidade.adicionarAnotacao(AnotacaoArquitetural.Entity);
        entidade.adicionarAnotacao(AnotacaoArquitetural.Table);

        entidade.adicionarInterface(InterfaceArquitetural.IEntidade);

        return entidade;
    }

    public static TipoArquitetural criarUtils() {
        TipoArquitetural util = new TipoArquitetural(PacoteArquitetural.Utils);

        return util;
    }

    public static TipoArquitetural criarDto() {
        TipoArquitetural dto = new TipoArquitetural(SufixoArquitetural.Dto, PacoteArquitetural.Dto);

        dto.adicionarInterface(InterfaceArquitetural.IDto);

        return dto;
    }

    public static TipoArquitetural criarNotificadorJms() {
        TipoArquitetural notificadorJms = new TipoArquitetural(SufixoArquitetural.NotificadorJms,
            PacoteArquitetural.Mensageria);
        notificadorJms.adicionarHeranca(HerancaArquitetural.AbstractNotificadorJms);

        notificadorJms.adicionarAnotacao(AnotacaoArquitetural.Stateless);

        return notificadorJms;
    }

    public static TipoArquitetural criarReceptorJms() {
        TipoArquitetural receptorJms = new TipoArquitetural(SufixoArquitetural.ReceptorJms,
            PacoteArquitetural.Mensageria);
        receptorJms.adicionarHeranca(HerancaArquitetural.AbstractReceptorJms);

        receptorJms.adicionarAnotacao(AnotacaoArquitetural.MessageDriven);

        return receptorJms;
    }

    public static TipoArquitetural criarBusinessFacade() {
        TipoArquitetural businessFacade = new TipoArquitetural(SufixoArquitetural.BusinessFacade,
            PacoteArquitetural.ModelBusinessFacade);
        businessFacade.adicionarHeranca(HerancaArquitetural.AbstractBusinessFacade);

        businessFacade.adicionarAnotacao(AnotacaoArquitetural.Named);
        businessFacade.adicionarAnotacao(AnotacaoArquitetural.RequestScoped);
        businessFacade.adicionarAnotacao(AnotacaoArquitetural.Transactional);

        return businessFacade;
    }

    public static TipoArquitetural criarDao() {
        TipoArquitetural dao = new TipoArquitetural(SufixoArquitetural.Dao, PacoteArquitetural.ModelPersistenceDao);
        dao.adicionarHeranca(HerancaArquitetural.LivrariaAbstractDao);
        dao.adicionarHeranca(HerancaArquitetural.LibrosAbstractDao);

        dao.adicionarAnotacao(AnotacaoArquitetural.Named);
        dao.adicionarAnotacao(AnotacaoArquitetural.RequestScoped);

        return dao;
    }

    public static TipoArquitetural criarAbstractDao() {
        TipoArquitetural dao = new TipoArquitetural(SufixoArquitetural.AbstractDao,
            PacoteArquitetural.ModelPersistenceDao);
        dao.adicionarHeranca(HerancaArquitetural.AbstractDao);

        return dao;
    }

    public static TipoArquitetural criarBusiness() {
        TipoArquitetural business = new TipoArquitetural(SufixoArquitetural.Business, PacoteArquitetural.ModelBusiness);
        business.adicionarHeranca(HerancaArquitetural.AbstractBusiness);

        business.adicionarAnotacao(AnotacaoArquitetural.Named);
        business.adicionarAnotacao(AnotacaoArquitetural.RequestScoped);

        return business;
    }

    public static TipoArquitetural criarApi() {
        TipoArquitetural api = new TipoArquitetural(SufixoArquitetural.Api, PacoteArquitetural.Api);
        api.adicionarHeranca(HerancaArquitetural.AbstractApi);

        api.adicionarAnotacao(AnotacaoArquitetural.Path);
        api.adicionarAnotacao(AnotacaoArquitetural.Api);

        return api;
    }

    public static TipoArquitetural criarViewController() {
        TipoArquitetural viewController = new TipoArquitetural(SufixoArquitetural.Controller,
            PacoteArquitetural.ViewController);
        viewController.adicionarHeranca(HerancaArquitetural.AbstractController);

        viewController.adicionarAnotacao(AnotacaoArquitetural.ManagedBean);
        viewController.adicionarAnotacao(AnotacaoArquitetural.ViewScoped);

        return viewController;
    }
}
