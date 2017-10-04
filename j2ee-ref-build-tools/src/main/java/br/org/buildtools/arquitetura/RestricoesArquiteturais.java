package br.org.buildtools.arquitetura;

import static br.org.buildtools.arquitetura.fabrica.FabricaRestricaoPacote.criarRestricoesPacoteApi;
import static br.org.buildtools.arquitetura.fabrica.FabricaRestricaoPacote.criarRestricoesPacoteServico;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarApi;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarDao;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarDto;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarEntidade;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarEntidadePk;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarMapper;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarServico;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarUtil;
import static br.org.buildtools.arquitetura.fabrica.FabricaTipoArquitetural.criarInfra;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import br.org.buildtools.arquitetura.enums.AnotacaoArquitetural;
import br.org.buildtools.arquitetura.enums.HerancaArquitetural;
import br.org.buildtools.arquitetura.enums.InterfaceArquitetural;
import br.org.buildtools.arquitetura.enums.PacoteArquitetural;

public class RestricoesArquiteturais {
    private Collection<TipoArquitetural> tiposArquiteturais = new HashSet<TipoArquitetural>();
    private Map<PacoteArquitetural, Collection<PacoteArquitetural>> restricoesPacotesArquiteturais = new HashMap<PacoteArquitetural, Collection<PacoteArquitetural>>();
    
    public RestricoesArquiteturais() {
        instanciarTiposArquiteturais();
        instanciarRestricoesPacotesArquiteturais();
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

        return tipoArquitetural.getHerancas();
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
            if (tipoArquitetural.getSufixo().equals(tipo.getSufixo())
                    && tipoArquitetural.getPacote().equals(tipo.getPacote())) {
                return tipoArquitetural;
            }
        }
        
        return null;
    }
    
    private void instanciarTiposArquiteturais() {
        tiposArquiteturais.add(criarMapper());
        tiposArquiteturais.add(criarDto());
        tiposArquiteturais.add(criarApi());
        tiposArquiteturais.add(criarServico());
        tiposArquiteturais.add(criarDao());
        tiposArquiteturais.add(criarEntidade());
        tiposArquiteturais.add(criarEntidadePk());
        tiposArquiteturais.add(criarUtil());
        tiposArquiteturais.add(criarInfra());
    }
    
    private void instanciarRestricoesPacotesArquiteturais() {
        restricoesPacotesArquiteturais.put(PacoteArquitetural.Api, criarRestricoesPacoteApi());
        restricoesPacotesArquiteturais.put(PacoteArquitetural.Servico, criarRestricoesPacoteServico());
    }
    
}
