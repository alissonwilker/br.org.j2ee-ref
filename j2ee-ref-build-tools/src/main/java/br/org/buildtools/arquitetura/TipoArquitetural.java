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
    private boolean ignorarSufixo;
    private boolean ignorarPrefixo;
    private SufixoArquitetural sufixo;
    private PacoteArquitetural pacote;
    private Map<AnotacaoArquitetural, TipoRestricao> anotacoes = new HashMap<AnotacaoArquitetural, TipoRestricao>();
    private Map<AnotacaoArquitetural, Collection<AnotacaoArquitetural>> anotacoesAlternativas = new HashMap<AnotacaoArquitetural, Collection<AnotacaoArquitetural>>();
    private Collection<InterfaceArquitetural> interfaces = new HashSet<InterfaceArquitetural>();
    private Collection<HerancaArquitetural> herancasObrigatorias = new HashSet<HerancaArquitetural>();
    private Collection<HerancaArquitetural> herancasPermitidas = new HashSet<HerancaArquitetural>();

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

    public void adicionarAnotacaoPermitida(AnotacaoArquitetural anotacaoArquitetural) {
        adicionarAnotacao(anotacaoArquitetural, TipoRestricao.PERMITIDO);
    }

    public void adicionarAnotacaoObrigatoria(AnotacaoArquitetural anotacaoArquitetural) {
        adicionarAnotacao(anotacaoArquitetural, TipoRestricao.OBRIGATORIO);
    }

    public void adicionarInterface(InterfaceArquitetural interfaceArquitetural) {
        if (interfaceArquitetural != null) {
            interfaces.add(interfaceArquitetural);
        }
    }

    public void adicionarHerancaObrigatoria(HerancaArquitetural pai) {
        if (pai != null) {
            herancasObrigatorias.add(pai);
        }
    }

    public void adicionarHerancaPermitida(HerancaArquitetural pai) {
        if (pai != null) {
            herancasPermitidas.add(pai);
        }
    }

    public void adicionarAnotacoesAlternativas(AnotacaoArquitetural anotacaoObrigatoria,
        AnotacaoArquitetural... anotacoesAlt) {
        TipoRestricao tipoRestricao = anotacoes.get(anotacaoObrigatoria);
        if (tipoRestricao == null || !tipoRestricao.equals(TipoRestricao.OBRIGATORIO) || anotacoesAlt == null) {
            throw new IllegalArgumentException();
        }

        anotacoesAlternativas.put(anotacaoObrigatoria, Arrays.asList(anotacoesAlt));
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
        for (HerancaArquitetural herancaArquitetural : herancasObrigatorias) {
            if (herancaArquitetural.equals(herancaArq)) {
                return true;
            }
        }

        for (HerancaArquitetural herancaArquitetural : herancasPermitidas) {
            if (herancaArquitetural.equals(herancaArq)) {
                return true;
            }
        }

        return false;
    }

    public boolean isIgnorarSufixo() {
        return ignorarSufixo;
    }
    
    public boolean isIgnorarPrefixo() {
        return ignorarPrefixo;
    }
    
    public void setIgnorarSufixo(boolean ignorarSufixo) {
        this.ignorarSufixo = ignorarSufixo;
    }
    
    public void setIgnorarPrefixo(boolean ignorarPrefixo) {
        this.ignorarPrefixo = ignorarPrefixo;
    }

    public SufixoArquitetural getSufixo() {
        return sufixo;
    }

    public PacoteArquitetural getPacote() {
        return pacote;
    }

    public Collection<AnotacaoArquitetural> getAnotacoesObrigatorias() {
        return getAnotacoes(TipoRestricao.OBRIGATORIO);
    }

    public Collection<AnotacaoArquitetural> getAnotacoesAlternativas(AnotacaoArquitetural anotacaoArquitetural) {
        Collection<AnotacaoArquitetural> anotacoesAlt = anotacoesAlternativas.get(anotacaoArquitetural);
        return anotacoesAlt != null ? anotacoesAlt : new HashSet<AnotacaoArquitetural>();
    }

    public Collection<InterfaceArquitetural> getInterfaces() {
        return interfaces;
    }

    public Collection<HerancaArquitetural> getHerancasObrigatorias() {
        return herancasObrigatorias;
    }

    private void adicionarAnotacao(AnotacaoArquitetural anotacaoArquitetural, TipoRestricao tipoRestricao) {
        if (anotacaoArquitetural != null && tipoRestricao != null) {
            anotacoes.put(anotacaoArquitetural, tipoRestricao);
        }
    }

    private Collection<AnotacaoArquitetural> getAnotacoes(TipoRestricao tipoRestricao) {
        Collection<AnotacaoArquitetural> anotacoesDoTipoRestricao = new HashSet<AnotacaoArquitetural>();

        for (Map.Entry<AnotacaoArquitetural, TipoRestricao> anotacaoTipoRestricao : anotacoes.entrySet()) {
            if (anotacaoTipoRestricao.getValue().equals(tipoRestricao)) {
                anotacoesDoTipoRestricao.add(anotacaoTipoRestricao.getKey());
            }
        }

        return anotacoesDoTipoRestricao;
    }
}

enum TipoRestricao {
    OBRIGATORIO, PERMITIDO;
}
