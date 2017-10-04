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

        mapper.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Mapper);

        return mapper;
    }

    public static TipoArquitetural criarEntidade() {
        TipoArquitetural entidade = new TipoArquitetural(PacoteArquitetural.ModelPersistenceEntity);

        entidade.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Entity);
        entidade.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Table);

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

        notificadorJms.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Stateless);

        return notificadorJms;
    }

    public static TipoArquitetural criarReceptorJms() {
        TipoArquitetural receptorJms = new TipoArquitetural(SufixoArquitetural.ReceptorJms,
            PacoteArquitetural.Mensageria);
        receptorJms.adicionarHeranca(HerancaArquitetural.AbstractReceptorJms);

        receptorJms.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.MessageDriven);

        return receptorJms;
    }

    public static TipoArquitetural criarBusinessFacade() {
        TipoArquitetural businessFacade = new TipoArquitetural(SufixoArquitetural.BusinessFacade,
            PacoteArquitetural.ModelBusinessFacade);
        businessFacade.adicionarHeranca(HerancaArquitetural.AbstractBusinessFacade);

        businessFacade.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Named);
        businessFacade.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.RequestScoped);
        businessFacade.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Transactional);

        return businessFacade;
    }

    public static TipoArquitetural criarDao() {
        TipoArquitetural dao = new TipoArquitetural(SufixoArquitetural.Dao, PacoteArquitetural.ModelPersistenceDao);
        dao.adicionarHeranca(HerancaArquitetural.AbstractLivrariaDao);
        dao.adicionarHeranca(HerancaArquitetural.AbstractLibrosDao);
        dao.adicionarHeranca(HerancaArquitetural.AbstractDao);

        dao.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Named);
        dao.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.RequestScoped);

        return dao;
    }

    public static TipoArquitetural criarBusiness() {
        TipoArquitetural business = new TipoArquitetural(SufixoArquitetural.Business, PacoteArquitetural.ModelBusiness);
        business.adicionarHeranca(HerancaArquitetural.AbstractBusiness);

        business.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Named);
        business.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.RequestScoped);

        return business;
    }

    public static TipoArquitetural criarApi() {
        TipoArquitetural api = new TipoArquitetural(SufixoArquitetural.Api, PacoteArquitetural.Api);
        api.adicionarHeranca(HerancaArquitetural.AbstractApi);

        api.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Path);
        api.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.Api);

        return api;
    }

    public static TipoArquitetural criarViewController() {
        TipoArquitetural viewController = new TipoArquitetural(SufixoArquitetural.Controller,
            PacoteArquitetural.ViewController);
        viewController.adicionarHeranca(HerancaArquitetural.AbstractController);

        viewController.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.ManagedBean);
        viewController.adicionarAnotacaoObrigatoria(AnotacaoArquitetural.ViewScoped);

        return viewController;
    }
}
