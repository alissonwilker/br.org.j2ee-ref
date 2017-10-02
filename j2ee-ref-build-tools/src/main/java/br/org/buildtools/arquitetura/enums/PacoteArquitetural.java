package br.org.buildtools.arquitetura.enums;

public enum PacoteArquitetural {
    Base("br.jus.csjt.pje"), Api("api"), ApiExcecaoMapper("api.excecao.mapper"), Dto("dto"), DtoMapper("dto.mappers"), Excecao(
        "excecao"), Mensageria("mensageria"), Servico("servico"), ModelBusinessFacade(
            "model.business.facade"), Persistencia("persistencia"), Entidades(
                "entidades"), ModelPersistenceEntityListener(
                    "model.persistence.entity.listener"), ModelPersistenceEntityValidator(
                        "model.persistence.entity.validator"), ModelPersistenceEntityValidatorAnnotation(
                            "model.persistence.entity.validator.annotation"), Util("util"), ViewController(
                                "view.controller"), ViewConverter("view.converter"), ViewValidator("view.validator"), Enums("enums"), Excecoes("excecoes"), ExcecoesApiMapper("excecoes.api.mapper"), EntidadesUtilConversores("entidades.util.conversores");

    private String nomePacote;

    private PacoteArquitetural(String nomePacote) {
        this.nomePacote = nomePacote;
    }

    public String getNomePacote() {
        return nomePacote;
    }
}
