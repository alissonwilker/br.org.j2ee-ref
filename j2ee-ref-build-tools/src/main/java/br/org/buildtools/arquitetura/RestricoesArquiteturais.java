package br.org.buildtools.arquitetura;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class RestricoesArquiteturais {
    private String pacoteBase;
    private Map<String, TipoArquitetural> tiposArquiteturais = new HashMap<String, TipoArquitetural>();
    private Map<String, Collection<String>> restricoesPacotesArquiteturais = new HashMap<String, Collection<String>>();
    private Collection<String> sufixos = new HashSet<String>();
    private Collection<String> herancas = new HashSet<String>();
    private Collection<String> interfaces = new HashSet<String>();
    private Collection<String> anotacoes = new HashSet<String>();
    private Collection<String> pacotes = new HashSet<String>();
    private Map<String, Collection<String>> restricoesPacotes = new HashMap<String, Collection<String>>();

    public void adicionarTipo(String nome, String pacote, String sufixo) {
        tiposArquiteturais.put(nome, new TipoArquitetural(pacote, sufixo));
        System.out.println("adicionado tipo #" + nome + "#");
    }

    public void adicionarInterfacesTipo(String tipo, Collection<String> interfacesTipo) {
        String[] interfaces = new String[interfacesTipo.size()];
        System.out.println("interfaces tipo #" + tipo + "#");
        System.out.println("######" + tiposArquiteturais.get(tipo) + "####");
        System.out.println("######" + interfacesTipo + "####");
       tiposArquiteturais.get(tipo).adicionarInterface(interfacesTipo.toArray(interfaces));
    }

    public void adicionarHerancasTipo(String tipo, Collection<String> herancasTipo) {
        String[] herancas = new String[herancasTipo.size()];
        tiposArquiteturais.get(tipo).adicionarHeranca(herancasTipo.toArray(herancas));
    }

    public void adicionarAnotacoesObrigatoriasTipo(String tipo, Collection<String> anotacoesObrigatoriasTipo) {
        String[] anotacoes = new String[anotacoesObrigatoriasTipo.size()];
        tiposArquiteturais.get(tipo).adicionarAnotacaoObrigatoria(anotacoesObrigatoriasTipo.toArray(anotacoes));
    }

    public void adicionarRestricaoPacote(String pacote, Collection<String> restricaoPacote) {
        this.restricoesPacotes.put(pacote, restricaoPacote);
    }

    public void adicionarPacotes(Collection<String> pacotes) {
        this.pacotes.addAll(pacotes);
    }

    public void adicionarAnotacoes(Collection<String> anotacoes) {
        this.anotacoes.addAll(anotacoes);
    }

    public void adicionarInterfaces(Collection<String> interfaces) {
        this.interfaces.addAll(interfaces);
    }

    public void adicionarHerancas(Collection<String> herancas) {
        this.herancas.addAll(herancas);
    }

    public void adicionarSufixos(Collection<String> sufixos) {
        this.sufixos.addAll(sufixos);
    }

    public boolean existeTipoArquitetural(TipoArquitetural tipo) {
        return buscarTipoArquitetural(tipo) != null;
    }

    public boolean ehUmPacoteRestrito(String nomePacote, String pacoteArquitetural) {
        Collection<String> restricoesPacote = getPacotesRestritos(pacoteArquitetural);
        if (restricoesPacote != null) {
            for (String pacoteArquiteturalRestrito : restricoesPacote) {
                if (nomePacote.endsWith(pacoteArquiteturalRestrito)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void setPacoteBase(String pacoteBase) {
        this.pacoteBase = pacoteBase;
    }

    public String getPacoteBase() {
        return pacoteBase;
    }

    public boolean ehAnotacaoArquiteturalValida(TipoArquitetural tipo, String anotacao) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);

        if (tipoArquitetural == null) {
            return false;
        }

        return tipoArquitetural.ehAnotacaoArquiteturalValida(anotacao);
    }

    public boolean ehInterfaceArquiteturalValida(TipoArquitetural tipo, String interfaceArquitetural) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);

        if (tipoArquitetural == null) {
            return false;
        }

        return tipoArquitetural.possuiInterface(interfaceArquitetural);
    }

    public boolean ehHerancaArquiteturalValida(TipoArquitetural tipo, String herancaArquitetural) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);

        if (tipoArquitetural == null) {
            return false;
        }

        return tipoArquitetural.possuiHeranca(herancaArquitetural);
    }

    public Collection<String> recuperarAnotacoesObrigatoriasAusentes(TipoArquitetural tipo,
        Collection<String> anotacoes) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);

        Collection<String> anotacoesObrigatorias = tipoArquitetural.getAnotacoesObrigatorias();
        Collection<String> anotacoesAusentes = new HashSet<String>(anotacoesObrigatorias);

        for (String anotacaoObrigatoria : anotacoesObrigatorias) {
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

    public Collection<String> recuperarInterfacesAusentes(TipoArquitetural tipo) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);

        Collection<String> interfacesArquiteturaisAusentes = new HashSet<String>();
        interfacesArquiteturaisAusentes.addAll(tipoArquitetural.getInterfaces());
        interfacesArquiteturaisAusentes.removeAll(tipo.getInterfaces());

        return interfacesArquiteturaisAusentes;
    }

    public Collection<String> recuperarHerancas(TipoArquitetural tipo) {
        TipoArquitetural tipoArquitetural = buscarTipoArquitetural(tipo);

        return tipoArquitetural.getHerancas();
    }

    public String buscarSufixoArquitetural(String nomeClasse) {
        // TODO eliminar conversão para array
        String[] sufixosArray = new String[sufixos.size()];
        sufixosArray = sufixos.toArray(sufixosArray);
        String sufixo = null;
        int i;
        for (i = 0; i < sufixosArray.length; i++) {
            if (nomeClasse.endsWith(sufixosArray[i])) {
                if (sufixo == null) {
                    sufixo = sufixosArray[i];
                } else {
                    if (sufixosArray[i].length() > sufixo.length()) {
                        sufixo = sufixosArray[i];
                    }
                }
            }
        }

        return sufixo;
    }

    public String buscarPacoteArquitetural(String nomePacote) {
        // TODO eliminar conversão para array
        String[] pacotesArray = new String[pacotes.size()];
        pacotesArray = pacotes.toArray(pacotesArray);
        String pacote = null;
        int i;
        for (i = 0; i < pacotesArray.length; i++) {
            if (nomePacote.endsWith(pacotesArray[i])) {
                if (pacote == null) {
                    pacote = pacotesArray[i];
                } else {
                    if (pacotesArray[i].length() > pacote.length()) {
                        pacote = pacotesArray[i];
                    }
                }
            }
        }

        return pacote;
    }

    public String buscarHerancaArquitetural(String nomePai) {
        for (String heranca : herancas) {
            if (heranca.equals(nomePai)) {
                return heranca;
            }
        }

        return null;
    }

    public String buscarAnotacaoArquitetural(String nomeAnotacao) {
        for (String anotacaoArquitetural : anotacoes) {
            if (anotacaoArquitetural.equals(nomeAnotacao)) {
                return anotacaoArquitetural;
            }
        }

        return null;
    }

    public String buscarInterfaceArquitetural(String nomeInterface) {
        for (String interfaceArquitetural : interfaces) {
            if (interfaceArquitetural.equals(nomeInterface)) {
                return interfaceArquitetural;
            }
        }

        return null;
    }

    private boolean possuiAnotacaoAlternativa(Collection<String> anotacoes, TipoArquitetural tipoArquitetural,
        String anotacaoObrigatoria) {

        Collection<String> anotacoesAlternativas = tipoArquitetural.getAnotacoesAlternativas(anotacaoObrigatoria);
        for (String anotacaoAlternativa : anotacoesAlternativas) {
            if (anotacoes.contains(anotacaoAlternativa)) {
                return true;
            }
        }

        return false;
    }

    private Collection<String> getPacotesRestritos(String pacote) {
        return restricoesPacotesArquiteturais.get(pacote);
    }

    private TipoArquitetural buscarTipoArquitetural(TipoArquitetural tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException();
        }

        for (TipoArquitetural tipoArquitetural : tiposArquiteturais.values()) {
            if (tipoArquitetural.getSufixo().equals(tipo.getSufixo())
                && tipoArquitetural.getPacote().equals(tipo.getPacote())) {
                return tipoArquitetural;
            }

            if (tipoArquitetural.isIgnorarSufixo() && tipoArquitetural.getPacote().equals(tipo.getPacote())) {
                return tipoArquitetural;
            }
        }

        return null;
    }

}
