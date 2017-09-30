package br.org.buildtools.arquitetura;

import br.org.buildtools.arquitetura.enums.AnotacaoArquitetural;
import br.org.buildtools.arquitetura.enums.HerancaArquitetural;
import br.org.buildtools.arquitetura.enums.InterfaceArquitetural;
import br.org.buildtools.arquitetura.enums.PacoteArquitetural;
import br.org.buildtools.arquitetura.enums.SufixoArquitetural;

public class ArquiteturaUtils {
    
    public static SufixoArquitetural buscarSufixoArquitetural(String nomeClasse) {
        SufixoArquitetural[] sufixos = SufixoArquitetural.values();
        SufixoArquitetural sufixo = null;
        int i;
        for (i = 0; i < sufixos.length; i++) {
            if (nomeClasse.endsWith(sufixos[i].name())) {
                if (sufixo == null) {
                    sufixo = sufixos[i];
                } else {
                    if (sufixos[i].name().length() > sufixo.name().length()) {
                        sufixo = sufixos[i];
                    }
                }
            }
        }

        return sufixo;
    }

    public static PacoteArquitetural buscarPacoteArquitetural(String nomePacote) {
        PacoteArquitetural[] pacotes = PacoteArquitetural.values();
        PacoteArquitetural pacote = null;
        int i;
        for (i = 0; i < pacotes.length; i++) {
            if (nomePacote.endsWith(pacotes[i].getNomePacote())) {
                if (pacote == null) {
                    pacote = pacotes[i];
                } else {
                    if (pacotes[i].name().length() > pacote.name().length()) {
                        pacote = pacotes[i];
                    }
                }
            }
        }
        
        return pacote;
    }

    public static HerancaArquitetural buscarHerancaArquitetural(String nomePai) {
        for (HerancaArquitetural tipoArquiteturalAbstrato : HerancaArquitetural.values()) {
            if (tipoArquiteturalAbstrato.name().equals(nomePai)) {
                return tipoArquiteturalAbstrato;
            }
        }

        return null;
    }

    public static AnotacaoArquitetural buscarAnotacaoArquitetural(String nomeAnotacao) {
        for (AnotacaoArquitetural anotacaoArquitetural : AnotacaoArquitetural.values()) {
            if (anotacaoArquitetural.name().equals(nomeAnotacao)) {
                return anotacaoArquitetural;
            }
        }

        return null;
    }

    public static InterfaceArquitetural buscarInterfaceArquitetural(String nomeInterface) {
        for (InterfaceArquitetural interfaceArquitetural : InterfaceArquitetural.values()) {
            if (interfaceArquitetural.name().equals(nomeInterface)) {
                return interfaceArquitetural;
            }
        }

        return null;
    }

}
