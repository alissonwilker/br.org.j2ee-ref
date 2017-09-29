package br.org.buildtools.checkstyle;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import br.org.buildtools.checkstyle.TipoArquitetural.AnotacaoArquitetural;
import br.org.buildtools.checkstyle.TipoArquitetural.InterfaceArquitetural;
import br.org.buildtools.checkstyle.TipoArquitetural.PacoteArquitetural;
import br.org.buildtools.checkstyle.TipoArquitetural.SufixoArquitetural;
import br.org.buildtools.checkstyle.TipoArquitetural.TipoArquiteturalAbstrato;

public class RestricoesArquiteturais {
    private Collection<TipoArquitetural> tiposArquiteturais;
    private Map<PacoteArquitetural, Collection<PacoteArquitetural>> restricoesPacotesArquiteturais;

    public RestricoesArquiteturais() {
        instanciarTiposArquiteturais();
        instanciarRestricoesPacotesArquiteturais();
    }

    private void instanciarTiposArquiteturais() {
        tiposArquiteturais = new HashSet<TipoArquitetural>();

        Collection<AnotacaoArquitetural> anotacoesArquiteturais = new HashSet<AnotacaoArquitetural>();
        Collection<InterfaceArquitetural> interfacesArquiteturais = new HashSet<InterfaceArquitetural>();

        TipoArquitetural viewController = new TipoArquitetural(SufixoArquitetural.Controller,
                        PacoteArquitetural.ViewController);
        viewController.setPai(TipoArquiteturalAbstrato.AbstractController);
        anotacoesArquiteturais.add(AnotacaoArquitetural.ManagedBean);
        anotacoesArquiteturais.add(AnotacaoArquitetural.ViewScoped);
        viewController.setAnotacoes(anotacoesArquiteturais);

        TipoArquitetural api = new TipoArquitetural(SufixoArquitetural.Api, PacoteArquitetural.Api);
        api.setPai(TipoArquiteturalAbstrato.AbstractApi);
        anotacoesArquiteturais = new HashSet<AnotacaoArquitetural>();
        anotacoesArquiteturais.add(AnotacaoArquitetural.Path);
        anotacoesArquiteturais.add(AnotacaoArquitetural.Api);
        api.setAnotacoes(anotacoesArquiteturais);

        TipoArquitetural dto = new TipoArquitetural(SufixoArquitetural.Dto, PacoteArquitetural.Dto);
        interfacesArquiteturais.add(InterfaceArquitetural.IDto);
        dto.setInterfaces(interfacesArquiteturais);

        tiposArquiteturais.add(dto);
        tiposArquiteturais.add(api);
        tiposArquiteturais.add(viewController);
    }

    private void instanciarRestricoesPacotesArquiteturais() {
        restricoesPacotesArquiteturais = new HashMap<PacoteArquitetural, Collection<PacoteArquitetural>>();

        Collection<PacoteArquitetural> pacotesRestritos = new HashSet<PacoteArquitetural>();

        pacotesRestritos.add(PacoteArquitetural.Dto);
        restricoesPacotesArquiteturais.put(PacoteArquitetural.ViewController, pacotesRestritos);

        pacotesRestritos = new HashSet<PacoteArquitetural>();
        pacotesRestritos.add(PacoteArquitetural.Api);
        pacotesRestritos.add(PacoteArquitetural.Dto);
        restricoesPacotesArquiteturais.put(PacoteArquitetural.Api, pacotesRestritos);
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
    
    public boolean ehUmTipoArquiteturalValido(TipoArquitetural tipoArquitetural) {
        return tiposArquiteturais.contains(tipoArquitetural);
    }
}

