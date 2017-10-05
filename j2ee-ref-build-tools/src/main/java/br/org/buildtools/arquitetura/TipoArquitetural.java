package br.org.buildtools.arquitetura;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class TipoArquitetural {
    private boolean ignorarSufixo;
    private String sufixo;
    private String pacote;
    private Map<String, TipoRestricao> anotacoes = new HashMap<String, TipoRestricao>();
    private Map<String, Collection<String>> anotacoesAlternativas = new HashMap<String, Collection<String>>();
    private Collection<String> interfaces = new HashSet<String>();
    private Collection<String> herancas = new HashSet<String>();

    public TipoArquitetural(String pacote) {
        this(pacote, "null");
    }

    public TipoArquitetural(String pacote, String sufixo) {
        this.pacote = pacote;
        this.sufixo = sufixo;
    }

    public void adicionarAnotacaoPermitida(String anotacaoArquitetural) {
        adicionarAnotacao(anotacaoArquitetural, TipoRestricao.PERMITIDO);
    }

    public void adicionarAnotacaoObrigatoria(String... anotacoesObrigatorias) {
        for (String anotacao : anotacoesObrigatorias) {
            adicionarAnotacao(anotacao, TipoRestricao.OBRIGATORIO);
        }
    }

    public void adicionarAnotacoesObrigatorias(String anotacaoArquitetural) {
        adicionarAnotacao(anotacaoArquitetural, TipoRestricao.OBRIGATORIO);
    }

    public void adicionarInterface(String... interfacesArquiteturais) {
        if (interfacesArquiteturais != null) {
            interfaces.addAll(Arrays.asList(interfacesArquiteturais));
        }
    }

    public void adicionarHeranca(String... herancasArquiteturais) {
        if (herancasArquiteturais != null) {
            herancas.addAll(Arrays.asList(herancasArquiteturais));
        }
    }

    public void adicionarAnotacoesAlternativas(String anotacaoObrigatoria,
        String... anotacoesAlt) {
        TipoRestricao tipoRestricao = anotacoes.get(anotacaoObrigatoria);
        if (tipoRestricao == null || !tipoRestricao.equals(TipoRestricao.OBRIGATORIO) || anotacoesAlt == null) {
            throw new IllegalArgumentException();
        }

        anotacoesAlternativas.put(anotacaoObrigatoria, Arrays.asList(anotacoesAlt));
    }

    public boolean ehAnotacaoArquiteturalValida(String anotacao) {
        TipoRestricao tipoRestricao = anotacoes.get(anotacao);

        if (tipoRestricao != null) {
            return true;
        }

        Collection<String> anotacoesArquiteturais = anotacoes.keySet();
        for (String anotacaoArquitetural : anotacoesArquiteturais) {
            Collection<String> anotacoesAlternativas = getAnotacoesAlternativas(anotacaoArquitetural);
            for (String anotacaoAlternativa : anotacoesAlternativas) {
                if (anotacaoAlternativa.equals(anotacao)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean possuiInterface(String interfaceArq) {
        for (String interfaceArquitetural : interfaces) {
            if (interfaceArquitetural.equals(interfaceArq)) {
                return true;
            }
        }
        return false;
    }

    public boolean possuiHeranca(String herancaArq) {
        for (String herancaArquitetural : herancas) {
            if (herancaArquitetural.equals(herancaArq)) {
                return true;
            }
        }

        return false;
    }

    public boolean isIgnorarSufixo() {
        return ignorarSufixo;
    }
    
    public void setIgnorarSufixo(boolean ignorarSufixo) {
        this.ignorarSufixo = ignorarSufixo;
    }

    public String getSufixo() {
        return sufixo;
    }

    public String getPacote() {
        return pacote;
    }

    public Collection<String> getAnotacoesObrigatorias() {
        return getAnotacoes(TipoRestricao.OBRIGATORIO);
    }

    public Collection<String> getAnotacoesAlternativas(String anotacaoArquitetural) {
        Collection<String> anotacoesAlt = anotacoesAlternativas.get(anotacaoArquitetural);
        return anotacoesAlt != null ? anotacoesAlt : new HashSet<String>();
    }

    public Collection<String> getInterfaces() {
        return interfaces;
    }

    public Collection<String> getHerancas() {
        return herancas;
    }

    private void adicionarAnotacao(String anotacaoArquitetural, TipoRestricao tipoRestricao) {
        if (anotacaoArquitetural != null && tipoRestricao != null) {
            anotacoes.put(anotacaoArquitetural, tipoRestricao);
        }
    }

    private Collection<String> getAnotacoes(TipoRestricao tipoRestricao) {
        Collection<String> anotacoesDoTipoRestricao = new HashSet<String>();

        for (Map.Entry<String, TipoRestricao> anotacaoTipoRestricao : anotacoes.entrySet()) {
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
