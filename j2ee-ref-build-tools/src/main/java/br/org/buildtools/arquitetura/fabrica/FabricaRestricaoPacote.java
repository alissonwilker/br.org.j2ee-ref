package br.org.buildtools.arquitetura.fabrica;

import java.util.Collection;
import java.util.HashSet;

import br.org.buildtools.arquitetura.enums.PacoteArquitetural;

public class FabricaRestricaoPacote {

    public static Collection<PacoteArquitetural> criarRestricoesPacoteApi() {
        Collection<PacoteArquitetural> pacotesRestritos = new HashSet<PacoteArquitetural>();

        pacotesRestritos.add(PacoteArquitetural.EntidadesUtilConversores);
        pacotesRestritos.add(PacoteArquitetural.Excecoes);
        pacotesRestritos.add(PacoteArquitetural.ExcecoesApiMapper);
        pacotesRestritos.add(PacoteArquitetural.Persistencia);

        return pacotesRestritos;
    }

    public static Collection<PacoteArquitetural> criarRestricoesPacoteExcecoesApiMapper() {
        Collection<PacoteArquitetural> pacotesRestritos = new HashSet<PacoteArquitetural>();

        pacotesRestritos.add(PacoteArquitetural.Excecoes);
        pacotesRestritos.add(PacoteArquitetural.Dto);

        return pacotesRestritos;
    }

    public static Collection<PacoteArquitetural> criarRestricoesPacoteServico() {
        Collection<PacoteArquitetural> pacotesRestritos = new HashSet<PacoteArquitetural>();

        pacotesRestritos.add(PacoteArquitetural.EntidadesUtilConversores);
        pacotesRestritos.add(PacoteArquitetural.ExcecoesApiMapper);

        return pacotesRestritos;
    }

}
