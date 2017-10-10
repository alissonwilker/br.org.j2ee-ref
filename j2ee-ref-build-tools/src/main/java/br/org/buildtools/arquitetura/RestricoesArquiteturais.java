package br.org.buildtools.arquitetura;

import static br.org.buildtools.arquitetura.fabrica.FabricaRestricaoPacote.criarRestricoesPacoteApi;
import static br.org.buildtools.arquitetura.fabrica.FabricaRestricaoPacote.criarRestricoesPacoteMensageria;
import static br.org.buildtools.arquitetura.fabrica.FabricaRestricaoPacote.criarRestricoesPacoteModelBusiness;
import static br.org.buildtools.arquitetura.fabrica.FabricaRestricaoPacote.criarRestricoesPacoteModelBusinessFacade;
import static br.org.buildtools.arquitetura.fabrica.FabricaRestricaoPacote.criarRestricoesPacoteModelPersistenceDao;
import static br.org.buildtools.arquitetura.fabrica.FabricaRestricaoPacote.criarRestricoesPacoteModelPersistenceEntity;
import static br.org.buildtools.arquitetura.fabrica.FabricaRestricaoPacote.criarRestricoesPacoteViewController;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarApi;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarBusiness;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarBusinessFacade;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarDao;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarDto;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarEntidade;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarMapper;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarNotificadorJms;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarReceptorJms;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarViewController;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import br.org.buildtools.arquitetura.enums.AnotacaoArquitetural;
import br.org.buildtools.arquitetura.enums.HerancaArquitetural;
import br.org.buildtools.arquitetura.enums.InterfaceArquitetural;
import br.org.buildtools.arquitetura.enums.PacoteArquitetural;

public class RestricoesArquiteturais {
    private volatile static RestricoesArquiteturais instance;
    
    private Collection<TipoArquitetural> tiposArquiteturais = new HashSet<TipoArquitetural>();
    private Map<PacoteArquitetural, Collection<PacoteArquitetural>> restricoesPacotesArquiteturais = new HashMap<PacoteArquitetural, Collection<PacoteArquitetural>>();

    private RestricoesArquiteturais() {
        instanciarTiposArquiteturais();
        instanciarRestricoesPacotesArquiteturais();
    }
    
    public static RestricoesArquiteturais getInstance() {
        if (instance == null) {
            instance = new RestricoesArquiteturais();
        }
        
        return instance;
    }

    public boolean existeTipoArquitetural(TipoArquitetural tipo) {
        return buscarTipoArquitetural(tipo) != null;
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

    public boolean ehAnotacaoArquiteturalValida(TipoArquitetural tipo, AnotacaoArquitetural anotacao) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);

        if (tipoArquitetural == null) {
            return false;
        }
        
        return tipoArquitetural.ehAnotacaoArquiteturalValida(anotacao);
    }

    public boolean ehInterfaceArquiteturalValida(TipoArquitetural tipo, InterfaceArquitetural interfaceArquitetural) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);

        if (tipoArquitetural == null) {
            return false;
        }

        return tipoArquitetural.possuiInterface(interfaceArquitetural);
    }
    
    public boolean ehHerancaArquiteturalValida(TipoArquitetural tipo, HerancaArquitetural herancaArquitetural) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);

        if (tipoArquitetural == null) {
            return false;
        }
        
        return tipoArquitetural.possuiHeranca(herancaArquitetural);
    }
    
    public Collection<AnotacaoArquitetural> recuperarAnotacoesObrigatoriasAusentes(TipoArquitetural tipo,
            Collection<AnotacaoArquitetural> anotacoes) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);
        
        Collection<AnotacaoArquitetural> anotacoesObrigatorias = tipoArquitetural.getAnotacoesObrigatorias();
        Collection<AnotacaoArquitetural> anotacoesAusentes = new HashSet<AnotacaoArquitetural>(anotacoesObrigatorias);
        
        for (AnotacaoArquitetural anotacaoObrigatoria : anotacoesObrigatorias) {
            if (anotacoes.contains(anotacaoObrigatoria)) {
                anotacoesAusentes.remove(anotacaoObrigatoria);
            } else {
                if (possuiAnotacaoAlternativa(anotacoes, tipoArquitetural, anotacaoObrigatoria)) {
                    anotacoesAusentes.remove(anotacaoObrigatoria);
                }
            }
        }
        
        return anotacoesAusentes;
    }

    public Collection<InterfaceArquitetural> recuperarInterfacesAusentes(TipoArquitetural tipo) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);
        
        Collection<InterfaceArquitetural> interfacesArquiteturaisAusentes = new HashSet<InterfaceArquitetural>();
        interfacesArquiteturaisAusentes.addAll(tipoArquitetural.getInterfaces());
        interfacesArquiteturaisAusentes.removeAll(tipo.getInterfaces());

        return interfacesArquiteturaisAusentes;
    }
    
    public Collection<HerancaArquitetural> recuperarHerancas(TipoArquitetural tipo) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);
        
        return tipoArquitetural.getHerancasObrigatorias();
    }
    
    public boolean deveIgnorarPrefixo(TipoArquitetural tipo) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);
        
        if (tipoArquitetural == null) {
            return false; 
        }
        
        return tipoArquitetural.isIgnorarPrefixo();
    }

    private boolean possuiAnotacaoAlternativa(Collection<AnotacaoArquitetural> anotacoes,
            TipoArquitetural tipoArquitetural, AnotacaoArquitetural anotacaoObrigatoria) {
        
        Collection<AnotacaoArquitetural> anotacoesAlternativas = tipoArquitetural
                .getAnotacoesAlternativas(anotacaoObrigatoria);
        for (AnotacaoArquitetural anotacaoAlternativa : anotacoesAlternativas) {
            if (anotacoes.contains(anotacaoAlternativa)) {
                return true;
            }
        }
        
        return false;
    }
    
    private Collection<PacoteArquitetural> getPacotesRestritos(PacoteArquitetural pacote) {
        return restricoesPacotesArquiteturais.get(pacote);
    }

    private TipoArquitetural buscarTipoArquitetural(TipoArquitetural tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException();
        }

        for (TipoArquitetural tipoArquitetural : tiposArquiteturais) {
            if (tipoArquitetural.getSufixo().equals(tipo.getSufixo()) && tipoArquitetural.getPacote().equals(tipo.getPacote())) {
                return tipoArquitetural;
            }
        }

        for (TipoArquitetural tipoArquitetural : tiposArquiteturais) {
            if (tipoArquitetural.isIgnorarSufixo() && tipoArquitetural.getPacote().equals(tipo.getPacote())) {
                return tipoArquitetural;
            }
        }

        return null;
    }

    private void instanciarTiposArquiteturais() {
        tiposArquiteturais.add(criarMapper());
        tiposArquiteturais.add(criarDto());
        tiposArquiteturais.add(criarApi());
        tiposArquiteturais.add(criarViewController());
        tiposArquiteturais.add(criarBusinessFacade());
        tiposArquiteturais.add(criarBusiness());
        tiposArquiteturais.add(criarDao());
        tiposArquiteturais.add(criarNotificadorJms());
        tiposArquiteturais.add(criarReceptorJms());
        tiposArquiteturais.add(criarEntidade());
        tiposArquiteturais.add(criarUtils());
    }

    private void instanciarRestricoesPacotesArquiteturais() {
        restricoesPacotesArquiteturais.put(PacoteArquitetural.Api, criarRestricoesPacoteApi());
        restricoesPacotesArquiteturais.put(PacoteArquitetural.ViewController, criarRestricoesPacoteViewController());
        restricoesPacotesArquiteturais.put(PacoteArquitetural.ModelBusinessFacade,
            criarRestricoesPacoteModelBusinessFacade());
        restricoesPacotesArquiteturais.put(PacoteArquitetural.ModelBusiness, criarRestricoesPacoteModelBusiness());
        restricoesPacotesArquiteturais.put(PacoteArquitetural.ModelPersistenceDao,
            criarRestricoesPacoteModelPersistenceDao());
        restricoesPacotesArquiteturais.put(PacoteArquitetural.ModelPersistenceEntity,
            criarRestricoesPacoteModelPersistenceEntity());
        restricoesPacotesArquiteturais.put(PacoteArquitetural.Mensageria, criarRestricoesPacoteMensageria());
    }

}
