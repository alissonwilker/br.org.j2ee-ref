package br.org.buildtools.arquitetura;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import br.org.buildtools.arquitetura.enums.AnotacaoArquitetural;
import br.org.buildtools.arquitetura.enums.HerancaArquitetural;
import br.org.buildtools.arquitetura.enums.InterfaceArquitetural;
import br.org.buildtools.arquitetura.enums.PacoteArquitetural;
import br.org.buildtools.arquitetura.enums.SufixoArquitetural;

public class TipoArquitetural {
    private SufixoArquitetural sufixo;
    private PacoteArquitetural pacote;
    private Map<AnotacaoArquitetural, TipoRestricao> anotacoes = new HashMap<AnotacaoArquitetural, TipoRestricao>();
    private Map<AnotacaoArquitetural, AnotacaoArquitetural[]> anotacoesAlternativas = new HashMap<AnotacaoArquitetural, AnotacaoArquitetural[]>();
    private Collection<InterfaceArquitetural> interfaces = new HashSet<InterfaceArquitetural>();
    private Collection<HerancaArquitetural> herancas = new HashSet<HerancaArquitetural>();
    
    public TipoArquitetural(PacoteArquitetural pacote) {
        this(SufixoArquitetural.NULL, pacote);
    }
    
    public TipoArquitetural(SufixoArquitetural sufixo, PacoteArquitetural pacote) {
        if (sufixo == null || pacote == null) {
            throw new IllegalArgumentException();
        }
        
        this.sufixo = sufixo;
        this.pacote = pacote;
    }
    
    public void adicionarAnotacao(AnotacaoArquitetural anotacaoArquitetural, TipoRestricao tipoRestricao) {
        if (anotacaoArquitetural != null && tipoRestricao != null) {
            anotacoes.put(anotacaoArquitetural, tipoRestricao);
        }
    }
    
    public void adicionarInterface(InterfaceArquitetural interfaceArquitetural) {
        if (interfaceArquitetural != null) {
            interfaces.add(interfaceArquitetural);
        }
    }
    
    public void adicionarHeranca(HerancaArquitetural pai) {
        if (pai != null) {
            herancas.add(pai);
        }
    }
    
    public void adicionarAnotacoesAlternativas(AnotacaoArquitetural anotacaoObrigatoria,
            AnotacaoArquitetural... anotacoesAlt) {
        TipoRestricao tipoRestricao = anotacoes.get(anotacaoObrigatoria);
        if (tipoRestricao == null || !tipoRestricao.equals(TipoRestricao.OBRIGATORIO)) {
            throw new IllegalArgumentException();
        }
        
        anotacoesAlternativas.put(anotacaoObrigatoria, anotacoesAlt);
    }
    
    public boolean ehAnotacaoArquiteturalValida(AnotacaoArquitetural anotacao) {
        TipoRestricao tipoRestricao = anotacoes.get(anotacao);
        
        if (tipoRestricao != null) {
            return true;
        }
        
        Collection<AnotacaoArquitetural> anotacoesArquiteturais = anotacoes.keySet();
        for (AnotacaoArquitetural anotacaoArquitetural : anotacoesArquiteturais) {
            Collection<AnotacaoArquitetural> anotacoesAlternativas = getAnotacoesAlternativas(anotacaoArquitetural);
            for (AnotacaoArquitetural anotacaoAlternativa : anotacoesAlternativas) {
                if (anotacaoAlternativa.equals(anotacao)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public boolean possuiInterface(InterfaceArquitetural interfaceArq) {
        for (InterfaceArquitetural interfaceArquitetural : interfaces) {
            if (interfaceArquitetural.equals(interfaceArq)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean possuiHeranca(HerancaArquitetural herancaArq) {
        for (HerancaArquitetural herancaArquitetural : herancas) {
            if (herancaArquitetural.equals(herancaArq)) {
                return true;
            }
        }
        
        return false;
    }
    
    public SufixoArquitetural getSufixo() {
        return sufixo;
    }
    
    public PacoteArquitetural getPacote() {
        return pacote;
    }
    
    public Collection<AnotacaoArquitetural> getAnotacoes(TipoRestricao tipoRestricao) {
        Collection<AnotacaoArquitetural> anotacoesDoTipoRestricao = new HashSet<AnotacaoArquitetural>();
        
        for (Map.Entry<AnotacaoArquitetural, TipoRestricao> anotacaoTipoRestricao : anotacoes.entrySet()) {
            if (anotacaoTipoRestricao.getValue().equals(tipoRestricao)) {
                anotacoesDoTipoRestricao.add(anotacaoTipoRestricao.getKey());
            }
        }
        
        return anotacoesDoTipoRestricao;
    }
    
    public Collection<AnotacaoArquitetural> getAnotacoesAlternativas(AnotacaoArquitetural anotacaoArquitetural) {
        return Arrays.asList(anotacoesAlternativas.get(anotacaoArquitetural));
    }
    
    public Collection<InterfaceArquitetural> getInterfaces() {
        return interfaces;
    }
    
    public Collection<HerancaArquitetural> getHerancas() {
        return herancas;
    }
    
}
