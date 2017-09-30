package br.org.buildtools.checkstyle;

import java.util.Collection;
import java.util.HashSet;

public class TipoArquitetural {
    private SufixoArquitetural sufixo;
    private PacoteArquitetural pacote;
    private Collection<AnotacaoArquitetural> anotacoes = new HashSet<AnotacaoArquitetural>();
    private Collection<InterfaceArquitetural> interfaces = new HashSet<InterfaceArquitetural>();
    private Collection<HerancaArquitetural> herancas = new HashSet<HerancaArquitetural>();

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
    
    enum AnotacaoArquitetural {
        ManagedBean, ViewScoped, Path, Api, Named, RequestScoped, Transactional, Stateless, Mapper, Entity, Table, MessageDriven;
    }

    enum InterfaceArquitetural {
        IEntidade, IDto;
    }

    enum SufixoArquitetural {
        Dto, Controller, Api, BusinessFacade, Business, Dao, NotificadorJms, ReceptorJms, Mapper, AbstractDao, NULL;
    }

    enum HerancaArquitetural {
        IGenericMapper, AbstractController, AbstractApi, AbstractBusinessFacade, AbstractBusiness, AbstractDao, AbstractNotificadorJms, LibrosAbstractDao, LivrariaAbstractDao, AbstractReceptorJms;
    }

    enum PacoteArquitetural {
        Base("br.org"), Api("api"), ApiExcecaoMapper("api.excecao.mapper"), Dto("dto"), DtoMapper(
                        "dto.mapper"), Excecao("excecao"), Mensageria("mensageria"), ModelBusiness(
                                        "model.business"), ModelBusinessFacade(
                                                        "model.business.facade"), ModelPersistenceDao(
                                                                        "model.persistence.dao"), ModelPersistenceEntity(
                                                                                        "model.persistence.entity"), ModelPersistenceEntityListener(
                                                                                                        "model.persistence.entity.listener"), ModelPersistenceEntityValidator(
                                                                                                                        "model.persistence.entity.validator"), ModelPersistenceEntityValidatorAnnotation(
                                                                                                                                        "model.persistence.entity.validator.annotation"), Utils(
                                                                                                                                                        "utils"), ViewController(
                                                                                                                                                                        "view.controller"), ViewConverter(
                                                                                                                                                                                        "view.converter"), ViewValidator(
                                                                                                                                                                                                        "view.validator");

        private String nomePacote;

        private PacoteArquitetural(String nomePacote) {
            this.nomePacote = nomePacote;
        }

        public String getNomePacote() {
            return nomePacote;
        }
    }
}
