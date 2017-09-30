package br.org.buildtools.arquitetura.enums;

public enum PacoteArquitetural {
    Base("br.org"), Api("api"), ApiExcecaoMapper("api.excecao.mapper"), Dto("dto"), DtoMapper("dto.mapper"), Excecao(
        "excecao"), Mensageria("mensageria"), ModelBusiness("model.business"), ModelBusinessFacade(
            "model.business.facade"), ModelPersistenceDao("model.persistence.dao"), ModelPersistenceEntity(
                "model.persistence.entity"), ModelPersistenceEntityListener(
                    "model.persistence.entity.listener"), ModelPersistenceEntityValidator(
                        "model.persistence.entity.validator"), ModelPersistenceEntityValidatorAnnotation(
                            "model.persistence.entity.validator.annotation"), Utils("utils"), ViewController(
                                "view.controller"), ViewConverter("view.converter"), ViewValidator("view.validator");

    private String nomePacote;

    private PacoteArquitetural(String nomePacote) {
        this.nomePacote = nomePacote;
    }

    public String getNomePacote() {
        return nomePacote;
    }
}
