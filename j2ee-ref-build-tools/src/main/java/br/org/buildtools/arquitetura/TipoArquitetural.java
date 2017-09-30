package br.org.buildtools.arquitetura;

import java.util.Collection;
import java.util.HashSet;

import br.org.buildtools.arquitetura.enums.AnotacaoArquitetural;
import br.org.buildtools.arquitetura.enums.HerancaArquitetural;
import br.org.buildtools.arquitetura.enums.InterfaceArquitetural;
import br.org.buildtools.arquitetura.enums.PacoteArquitetural;
import br.org.buildtools.arquitetura.enums.SufixoArquitetural;

public class TipoArquitetural {
    private SufixoArquitetural sufixo;
    private PacoteArquitetural pacote;
    private Collection<AnotacaoArquitetural> anotacoes = new HashSet<AnotacaoArquitetural>();
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

    public void adicionarAnotacao(AnotacaoArquitetural anotacaoArquitetural) {
        if (anotacaoArquitetural != null) {
            anotacoes.add(anotacaoArquitetural);
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

    public boolean possuiAnotacao(AnotacaoArquitetural anotacao) {
        for (AnotacaoArquitetural anotacaoArquitetural : anotacoes) {
            if (anotacaoArquitetural.equals(anotacao)) {
                return true;
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

    public boolean possuiHeranca(HerancaArquitetural heranca) {
        for (HerancaArquitetural herancaArquitetural : herancas) {
            if (herancaArquitetural.equals(heranca)) {
                return true;
            }
        }

        return herancas.size() == 0 && heranca == null ? true : false;
    }

    public SufixoArquitetural getSufixo() {
        return sufixo;
    }

    public PacoteArquitetural getPacote() {
        return pacote;
    }

    public Collection<AnotacaoArquitetural> getAnotacoes() {
        return anotacoes;
    }

    public Collection<InterfaceArquitetural> getInterfaces() {
        return interfaces;
    }

}
