package br.org.buildtools.checkstyle;

import java.util.Collection;
import java.util.HashSet;

public class TipoArquitetural {
    private SufixoArquitetural sufixo;
    private PacoteArquitetural pacote;
    private TipoArquiteturalAbstrato pai;
    private Collection<AnotacaoArquitetural> anotacoes = new HashSet<AnotacaoArquitetural>();
    private Collection<InterfaceArquitetural> interfaces = new HashSet<InterfaceArquitetural>();

    public static SufixoArquitetural getSufixoArquitetural(String nomeClasseOuInterface) {
        SufixoArquitetural[] sufixos = SufixoArquitetural.values();
        int i;
        for (i = 0; i < sufixos.length; i++) {
            if (nomeClasseOuInterface.endsWith(sufixos[i].name())) {
                break;
            }
        }
        return i < sufixos.length ? sufixos[i] : null;
    }

    public static PacoteArquitetural getPacoteArquitetural(String nomePacote) {
        PacoteArquitetural[] pacotes = PacoteArquitetural.values();
        int i;
        for (i = 0; i < pacotes.length; i++) {
            if (nomePacote.endsWith(pacotes[i].getNomePacote())) {
                break;
            }
        }
        return i < pacotes.length ? pacotes[i] : null;
    }

    public TipoArquitetural(SufixoArquitetural sufixo, PacoteArquitetural pacote) {
        this.sufixo = sufixo;
        this.pacote = pacote;
    }

    public void setAnotacoes(Collection<AnotacaoArquitetural> anotacoes) {
        this.anotacoes = anotacoes;
    }

    public void setInterfaces(Collection<InterfaceArquitetural> interfaces) {
        this.interfaces = interfaces;
    }

    public void setPai(TipoArquiteturalAbstrato pai) {
        this.pai = pai;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((anotacoes == null) ? 0 : anotacoes.hashCode());
        result = prime * result + ((interfaces == null) ? 0 : interfaces.hashCode());
        result = prime * result + ((pacote == null) ? 0 : pacote.hashCode());
        result = prime * result + ((pai == null) ? 0 : pai.hashCode());
        result = prime * result + ((sufixo == null) ? 0 : sufixo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TipoArquitetural other = (TipoArquitetural) obj;
        if (anotacoes == null) {
            if (other.anotacoes != null)
                return false;
        } else if (!anotacoes.equals(other.anotacoes))
            return false;
        if (interfaces == null) {
            if (other.interfaces != null)
                return false;
        } else if (!interfaces.equals(other.interfaces))
            return false;
        if (pacote != other.pacote)
            return false;
        if (pai != other.pai)
            return false;
        if (sufixo != other.sufixo)
            return false;
        return true;
    }

    enum AnotacaoArquitetural {
        ManagedBean, ViewScoped, Path, Api;
    }

    enum InterfaceArquitetural {
        IEntidade, IDto;
    }

    enum SufixoArquitetural {
        Dto, Controller, Api;
    }

    enum TipoArquiteturalAbstrato {
        AbstractController, AbstractApi;
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

