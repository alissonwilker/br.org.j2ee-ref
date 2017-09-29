package br.org.buildtools.checkstyle;

import java.util.Collection;
import java.util.HashSet;

import br.org.buildtools.checkstyle.TipoArquitetural.AnotacaoArquitetural;
import br.org.buildtools.checkstyle.TipoArquitetural.TipoArquiteturalAbstrato;

public class TipoArquitetural {
    private SufixoArquitetural sufixo;
    private PacoteArquitetural pacote;
    private TipoArquiteturalAbstrato pai;
    private Collection<AnotacaoArquitetural> anotacoes = new HashSet<AnotacaoArquitetural>();
    private Collection<InterfaceArquitetural> interfaces = new HashSet<InterfaceArquitetural>();
    
    // TODO refatorar para incluir a possibilidade de um sufixo estar contido dentro de outro. Por exemplo: Dto e
    // MapperDto.
    public static SufixoArquitetural buscarSufixoArquitetural(String nomeClasseOuInterface) {
        SufixoArquitetural[] sufixos = SufixoArquitetural.values();
        int i;
        for (i = 0; i < sufixos.length; i++) {
            if (nomeClasseOuInterface.endsWith(sufixos[i].name())) {
                break;
            }
        }
        return i < sufixos.length ? sufixos[i] : null;
    }
    
    // TODO refatorar para incluir a possibilidade de um pacote estar contido dentro de outro. Por exemplo: api e
    // excecao.api.
    public static PacoteArquitetural buscarPacoteArquitetural(String nomePacote) {
        PacoteArquitetural[] pacotes = PacoteArquitetural.values();
        int i;
        for (i = 0; i < pacotes.length; i++) {
            if (nomePacote.endsWith(pacotes[i].getNomePacote())) {
                break;
            }
        }
        return i < pacotes.length ? pacotes[i] : null;
    }
    
    public static TipoArquiteturalAbstrato buscarTipoArquiteturalAbstrato(String nomePai) {
        for (TipoArquiteturalAbstrato tipoArquiteturalAbstrato : TipoArquiteturalAbstrato.values()) {
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
    
    public TipoArquitetural(SufixoArquitetural sufixo, PacoteArquitetural pacote) {
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
    
    public void setPai(TipoArquiteturalAbstrato pai) {
        this.pai = pai;
    }
    
    public SufixoArquitetural getSufixo() {
        return sufixo;
    }
    
    public PacoteArquitetural getPacote() {
        return pacote;
    }
    
    public TipoArquiteturalAbstrato getPai() {
        return pai;
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
        ManagedBean, ViewScoped, Path, Api, Named, RequestScoped, Transactional;
    }
    
    enum InterfaceArquitetural {
        IEntidade, IDto;
    }
    
    enum SufixoArquitetural {
        Dto, Controller, Api, BusinessFacade, Business, Dao;
    }
    
    enum TipoArquiteturalAbstrato {
        AbstractController, AbstractApi, AbstractBusinessFacade, AbstractBusiness, AbstractDao;
    }
    
    enum PacoteArquitetural {
        Base("br.org"), Api("api"), ApiExcecaoMapper("api.excecao.mapper"), Dto("dto"), DtoMapper(
                "dto.mapper"), Excecao("excecao"), Mensageria("mensageria"), ModelBusiness(
                        "model.business"), ModelBusinessFacade("model.business.facade"), ModelPersistenceDao(
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
