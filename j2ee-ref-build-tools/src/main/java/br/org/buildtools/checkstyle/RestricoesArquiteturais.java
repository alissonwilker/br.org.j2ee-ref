package br.org.buildtools.checkstyle;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import br.org.buildtools.checkstyle.TipoArquitetural.AnotacaoArquitetural;
import br.org.buildtools.checkstyle.TipoArquitetural.InterfaceArquitetural;
import br.org.buildtools.checkstyle.TipoArquitetural.PacoteArquitetural;
import br.org.buildtools.checkstyle.TipoArquitetural.SufixoArquitetural;
import br.org.buildtools.checkstyle.TipoArquitetural.HerancaArquitetural;

public class RestricoesArquiteturais {
    private Collection<TipoArquitetural> tiposArquiteturais = new HashSet<TipoArquitetural>();
    private Map<PacoteArquitetural, Collection<PacoteArquitetural>> restricoesPacotesArquiteturais = new HashMap<PacoteArquitetural, Collection<PacoteArquitetural>>();

    public RestricoesArquiteturais() {
        instanciarTiposArquiteturais();
        instanciarRestricoesPacotesArquiteturais();
    }

    private void instanciarTiposArquiteturais() {
        tiposArquiteturais.add(criarMapper());
        tiposArquiteturais.add(criarDto());
        tiposArquiteturais.add(criarApi());
        tiposArquiteturais.add(criarViewController());
        tiposArquiteturais.add(criarBusinessFacade());
        tiposArquiteturais.add(criarBusiness());
        tiposArquiteturais.add(criarAbstractDao());
        tiposArquiteturais.add(criarDao());
        tiposArquiteturais.add(criarNotificadorJms());
        tiposArquiteturais.add(criarReceptorJms());
        tiposArquiteturais.add(criarEntidade());
        tiposArquiteturais.add(criarUtils());
   }

    private void instanciarRestricoesPacotesArquiteturais() {
        restricoesPacotesArquiteturais.put(PacoteArquitetural.ViewController, criarRestricoesPacoteViewController());
        restricoesPacotesArquiteturais.put(PacoteArquitetural.Api, criarRestricoesPacoteApi());
    }

    private TipoArquitetural criarMapper() {
        TipoArquitetural mapper = new TipoArquitetural(SufixoArquitetural.Mapper, PacoteArquitetural.DtoMapper);
        mapper.adicionarHeranca(HerancaArquitetural.IGenericMapper);

        mapper.adicionarAnotacao(AnotacaoArquitetural.Mapper);

        return mapper;
    }

    private TipoArquitetural criarEntidade() {
        TipoArquitetural entidade = new TipoArquitetural(PacoteArquitetural.ModelPersistenceEntity);

        entidade.adicionarAnotacao(AnotacaoArquitetural.Entity);
        entidade.adicionarAnotacao(AnotacaoArquitetural.Table);
        
        entidade.adicionarInterface(InterfaceArquitetural.IEntidade);

        return entidade;
    }

    private TipoArquitetural criarUtils() {
        TipoArquitetural util = new TipoArquitetural(PacoteArquitetural.Utils);

        return util;
    }

    private TipoArquitetural criarDto() {
        TipoArquitetural dto = new TipoArquitetural(SufixoArquitetural.Dto, PacoteArquitetural.Dto);

        dto.adicionarInterface(InterfaceArquitetural.IDto);

        return dto;
    }

    private TipoArquitetural criarNotificadorJms() {
        TipoArquitetural notificadorJms = new TipoArquitetural(SufixoArquitetural.NotificadorJms,
                        PacoteArquitetural.Mensageria);
        notificadorJms.adicionarHeranca(HerancaArquitetural.AbstractNotificadorJms);

        notificadorJms.adicionarAnotacao(AnotacaoArquitetural.Stateless);

        return notificadorJms;
    }

    private TipoArquitetural criarReceptorJms() {
        TipoArquitetural receptorJms = new TipoArquitetural(SufixoArquitetural.ReceptorJms,
                        PacoteArquitetural.Mensageria);
        receptorJms.adicionarHeranca(HerancaArquitetural.AbstractReceptorJms);

        receptorJms.adicionarAnotacao(AnotacaoArquitetural.MessageDriven);

        return receptorJms;
    }

    private TipoArquitetural criarBusinessFacade() {
        TipoArquitetural businessFacade = new TipoArquitetural(SufixoArquitetural.BusinessFacade,
                        PacoteArquitetural.ModelBusinessFacade);
        businessFacade.adicionarHeranca(HerancaArquitetural.AbstractBusinessFacade);

        businessFacade.adicionarAnotacao(AnotacaoArquitetural.Named);
        businessFacade.adicionarAnotacao(AnotacaoArquitetural.RequestScoped);
        businessFacade.adicionarAnotacao(AnotacaoArquitetural.Transactional);

        return businessFacade;
    }

    private TipoArquitetural criarDao() {
        TipoArquitetural dao = new TipoArquitetural(SufixoArquitetural.Dao, PacoteArquitetural.ModelPersistenceDao);
        dao.adicionarHeranca(HerancaArquitetural.LivrariaAbstractDao);
        dao.adicionarHeranca(HerancaArquitetural.LibrosAbstractDao);

        dao.adicionarAnotacao(AnotacaoArquitetural.Named);
        dao.adicionarAnotacao(AnotacaoArquitetural.RequestScoped);

        return dao;
    }

    private TipoArquitetural criarAbstractDao() {
        TipoArquitetural dao = new TipoArquitetural(SufixoArquitetural.AbstractDao, PacoteArquitetural.ModelPersistenceDao);
        dao.adicionarHeranca(HerancaArquitetural.AbstractDao);

        return dao;
    }
    private TipoArquitetural criarBusiness() {
        TipoArquitetural business = new TipoArquitetural(SufixoArquitetural.Business, PacoteArquitetural.ModelBusiness);
        business.adicionarHeranca(HerancaArquitetural.AbstractBusiness);

        business.adicionarAnotacao(AnotacaoArquitetural.Named);
        business.adicionarAnotacao(AnotacaoArquitetural.RequestScoped);

        return business;
    }

    private TipoArquitetural criarApi() {
        TipoArquitetural api = new TipoArquitetural(SufixoArquitetural.Api, PacoteArquitetural.Api);
        api.adicionarHeranca(HerancaArquitetural.AbstractApi);

        api.adicionarAnotacao(AnotacaoArquitetural.Path);
        api.adicionarAnotacao(AnotacaoArquitetural.Api);

        return api;
    }

    private TipoArquitetural criarViewController() {
        TipoArquitetural viewController = new TipoArquitetural(SufixoArquitetural.Controller,
                        PacoteArquitetural.ViewController);
        viewController.adicionarHeranca(HerancaArquitetural.AbstractController);
        
        viewController.adicionarAnotacao(AnotacaoArquitetural.ManagedBean);
        viewController.adicionarAnotacao(AnotacaoArquitetural.ViewScoped);

        return viewController;
    }

    private Collection<PacoteArquitetural> criarRestricoesPacoteApi() {
        Collection<PacoteArquitetural> pacotesRestritos = new HashSet<PacoteArquitetural>();

        pacotesRestritos.add(PacoteArquitetural.ModelBusiness);
        pacotesRestritos.add(PacoteArquitetural.ModelPersistenceDao);

        return pacotesRestritos;
    }

    private Collection<PacoteArquitetural> criarRestricoesPacoteViewController() {
        Collection<PacoteArquitetural> pacotesRestritos = new HashSet<PacoteArquitetural>();

        pacotesRestritos.add(PacoteArquitetural.ModelBusiness);
        pacotesRestritos.add(PacoteArquitetural.ModelPersistenceDao);

        return pacotesRestritos;
    }

    private Collection<PacoteArquitetural> getPacotesRestritos(PacoteArquitetural pacote) {
        return restricoesPacotesArquiteturais.get(pacote);
    }

    public boolean ehUmPacoteRestrito(String nomePacote, PacoteArquitetural pacoteArquitetural) {
        Collection<PacoteArquitetural> restricoesPacote = getPacotesRestritos(pacoteArquitetural);
        if (restricoesPacote != null) {
            for (PacoteArquitetural pacoteArquiteturalRestrito : restricoesPacote) {
                if (nomePacote.endsWith(pacoteArquiteturalRestrito.getNomePacote())) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean existeTipoArquitetural(TipoArquitetural tipo) {
        return buscarTipoArquitetural(tipo) != null;
    }

    private TipoArquitetural buscarTipoArquitetural(TipoArquitetural tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException();
        }

        for (TipoArquitetural tipoArquitetural : tiposArquiteturais) {
            if (tipoArquitetural.getSufixo().equals(tipo.getSufixo())
                            && tipoArquitetural.getPacote().equals(tipo.getPacote())) {
                return tipoArquitetural;
            }
        }

        return null;
    }

    public boolean ehAnotacaoArquiteturalValida(TipoArquitetural tipo, AnotacaoArquitetural anotacao) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);

        if (tipoArquitetural == null) {
            return false;
        }

        return tipoArquitetural.possuiAnotacao(anotacao);
    }

    public boolean ehInterfaceArquiteturalValida(TipoArquitetural tipo, InterfaceArquitetural interfaceArquitetural) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);

        if (tipoArquitetural == null) {
            return false;
        }

        return tipoArquitetural.possuiInterface(interfaceArquitetural);
    }

    public Collection<AnotacaoArquitetural> recuperarAnotacoesAusentes(TipoArquitetural tipo) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);

        if (tipoArquitetural == null) {
            return null;
        }

        Collection<AnotacaoArquitetural> anotacoesArquiteturaisAusentes = new HashSet<AnotacaoArquitetural>();
        anotacoesArquiteturaisAusentes.addAll(tipoArquitetural.getAnotacoes());
        anotacoesArquiteturaisAusentes.removeAll(tipo.getAnotacoes());

        return anotacoesArquiteturaisAusentes;
    }

    public Collection<InterfaceArquitetural> recuperarInterfacesAusentes(TipoArquitetural tipo) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);

        if (tipoArquitetural == null) {
            return null;
        }

        Collection<InterfaceArquitetural> interfacesArquiteturaisAusentes = new HashSet<InterfaceArquitetural>();
        interfacesArquiteturaisAusentes.addAll(tipoArquitetural.getInterfaces());
        interfacesArquiteturaisAusentes.removeAll(tipo.getInterfaces());

        return interfacesArquiteturaisAusentes;
    }

    public boolean ehHerancaValida(TipoArquitetural tipo, HerancaArquitetural heranca) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);

        if (tipoArquitetural == null) {
            return false;
        }
        
        return tipoArquitetural.possuiHeranca(heranca);
    }

}
