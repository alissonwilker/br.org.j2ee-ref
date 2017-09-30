package br.org.buildtools.arquitetura.fabrica;

import java.util.Collection;
import java.util.HashSet;

import br.org.buildtools.arquitetura.enums.PacoteArquitetural;

public class FabricaRestricaoPacote {

    public static Collection<PacoteArquitetural> criarRestricoesPacoteApi() {
        Collection<PacoteArquitetural> pacotesRestritos = new HashSet<PacoteArquitetural>();

        pacotesRestritos.add(PacoteArquitetural.ViewController);
        pacotesRestritos.add(PacoteArquitetural.ModelBusiness);
        pacotesRestritos.add(PacoteArquitetural.ModelPersistenceDao);
        pacotesRestritos.add(PacoteArquitetural.ModelPersistenceEntity);
        pacotesRestritos.add(PacoteArquitetural.Mensageria);

        return pacotesRestritos;
    }

    public static Collection<PacoteArquitetural> criarRestricoesPacoteViewController() {
        Collection<PacoteArquitetural> pacotesRestritos = new HashSet<PacoteArquitetural>();

        pacotesRestritos.add(PacoteArquitetural.Api);
        pacotesRestritos.add(PacoteArquitetural.ModelBusiness);
        pacotesRestritos.add(PacoteArquitetural.ModelPersistenceDao);
        pacotesRestritos.add(PacoteArquitetural.ModelPersistenceEntity);
        pacotesRestritos.add(PacoteArquitetural.Mensageria);

        return pacotesRestritos;
    }

    public static Collection<PacoteArquitetural> criarRestricoesPacoteModelBusinessFacade() {
        Collection<PacoteArquitetural> pacotesRestritos = new HashSet<PacoteArquitetural>();

        pacotesRestritos.add(PacoteArquitetural.Api);
        pacotesRestritos.add(PacoteArquitetural.ViewController);
        pacotesRestritos.add(PacoteArquitetural.ModelPersistenceDao);
        pacotesRestritos.add(PacoteArquitetural.Mensageria);

        return pacotesRestritos;
    }

    public static Collection<PacoteArquitetural> criarRestricoesPacoteModelBusiness() {
        Collection<PacoteArquitetural> pacotesRestritos = new HashSet<PacoteArquitetural>();

        pacotesRestritos.add(PacoteArquitetural.Api);
        pacotesRestritos.add(PacoteArquitetural.ViewController);
        pacotesRestritos.add(PacoteArquitetural.ModelBusinessFacade);
        pacotesRestritos.add(PacoteArquitetural.Mensageria);

        return pacotesRestritos;
    }

    public static Collection<PacoteArquitetural> criarRestricoesPacoteModelPersistenceDao() {
        Collection<PacoteArquitetural> pacotesRestritos = new HashSet<PacoteArquitetural>();

        pacotesRestritos.add(PacoteArquitetural.Api);
        pacotesRestritos.add(PacoteArquitetural.ViewController);
        pacotesRestritos.add(PacoteArquitetural.ModelBusiness);
        pacotesRestritos.add(PacoteArquitetural.ModelBusinessFacade);
        pacotesRestritos.add(PacoteArquitetural.Mensageria);

        return pacotesRestritos;
    }

    public static Collection<PacoteArquitetural> criarRestricoesPacoteModelPersistenceEntity() {
        Collection<PacoteArquitetural> pacotesRestritos = new HashSet<PacoteArquitetural>();

        pacotesRestritos.add(PacoteArquitetural.Api);
        pacotesRestritos.add(PacoteArquitetural.ViewController);
        pacotesRestritos.add(PacoteArquitetural.ModelBusiness);
        pacotesRestritos.add(PacoteArquitetural.ModelBusinessFacade);
        pacotesRestritos.add(PacoteArquitetural.ModelPersistenceDao);
        pacotesRestritos.add(PacoteArquitetural.Mensageria);
        pacotesRestritos.add(PacoteArquitetural.Dto);

        return pacotesRestritos;
    }

    public static Collection<PacoteArquitetural> criarRestricoesPacoteMensageria() {
        Collection<PacoteArquitetural> pacotesRestritos = new HashSet<PacoteArquitetural>();

        pacotesRestritos.add(PacoteArquitetural.Api);
        pacotesRestritos.add(PacoteArquitetural.ViewController);
        pacotesRestritos.add(PacoteArquitetural.ModelBusiness);
        pacotesRestritos.add(PacoteArquitetural.ModelPersistenceDao);
        pacotesRestritos.add(PacoteArquitetural.ModelPersistenceEntity);

        return pacotesRestritos;
    }

}
