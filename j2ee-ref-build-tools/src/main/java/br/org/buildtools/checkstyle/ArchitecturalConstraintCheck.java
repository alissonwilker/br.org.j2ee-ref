package br.org.buildtools.checkstyle;

import static com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IMPORT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.PACKAGE_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.STATIC_IMPORT;

import java.util.Collection;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import br.org.buildtools.checkstyle.TipoArquitetural.AnotacaoArquitetural;
import br.org.buildtools.checkstyle.TipoArquitetural.InterfaceArquitetural;
import br.org.buildtools.checkstyle.TipoArquitetural.PacoteArquitetural;
import br.org.buildtools.checkstyle.TipoArquitetural.SufixoArquitetural;

public class ArchitecturalConstraintCheck extends CustomCheck {

    private static final String MSG_PREFIX = "restricaoArquitetural.";
    private static final String MSG_PACOTE_BASE = MSG_PREFIX + "pacoteBase";
    private static final String MSG_PACOTE_ARQUITETURAL = MSG_PREFIX + "pacoteArquitetural";
    private static final String MSG_PACOTE_RESTRITO = MSG_PREFIX + "pacoteRestrito";
    private static final String MSG_SUFIXO_ARQUITETURAL = MSG_PREFIX + "sufixoArquitetural";
    private static final String MSG_TIPO_INVALIDO = MSG_PREFIX + "tipoInvalido";
    private static final String MSG_ANOTACAO_INVALIDA = MSG_PREFIX + "anotacaoInvalida";
    private static final String MSG_ANOTACOES_AUSENTES = MSG_PREFIX + "anotacoesAusentes";
    private static final String MSG_HERANCA_INVALIDA = MSG_PREFIX + "herancaInvalida";
    private static final String MSG_INTERFACE_INVALIDA = MSG_PREFIX + "interfaceInvalida";
    private static final String MSG_INTERFACES_AUSENTES = MSG_PREFIX + "interfacesAusentes";

    private RestricoesArquiteturais restricoesArquiteturais;

    private TipoArquitetural tipo;
    private PacoteArquitetural pacoteArquitetural;
    private SufixoArquitetural sufixoArquitetural;

    public ArchitecturalConstraintCheck() {
        restricoesArquiteturais = new RestricoesArquiteturais();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] { CLASS_DEF, INTERFACE_DEF, STATIC_IMPORT, IMPORT, PACKAGE_DEF };
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    /**
     * Limpa os atributos da instância. Esse método deve ser executado sempre que for iniciada a análise
     * de um novo arquivo, pois a instância é a mesma para todas as análises (mesmo de arquivos
     * diferentes). A análise de um novo arquivo pode ser identificada através do TokenType.PACKAGE_DEF,
     * que ocorre sempre na primeira linha do arquivo.
     */
    private void limpaAtributosInstancia() {
        tipo = null;
        pacoteArquitetural = null;
        sufixoArquitetural = null;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == PACKAGE_DEF) {
            verificarConformidadePacote(ast);
        }

        if (ast.getType() == TokenTypes.IMPORT || ast.getType() == TokenTypes.STATIC_IMPORT) {
            verificarConformidadeImports(ast);
        }

        if (ast.getType() == CLASS_DEF || ast.getType() == INTERFACE_DEF) {
            verificarConformidadeClasseOuInterface(ast);
        }

    }

    private void verificarConformidadeClasseOuInterface(DetailAST astClasseOuInterface) {
        verificarConformidadeSufixoArquitetural(astClasseOuInterface);

        verificarConfomidadeTipoArquitetural(astClasseOuInterface);

        verificarConformidadeAnotacoes(astClasseOuInterface);

        verificarConformidadeHeranca(astClasseOuInterface);

        verificarConformidadeInterfaces(astClasseOuInterface);
    }

    private void verificarConformidadeInterfaces(DetailAST astClasseOuInterface) {
        DetailAST astImplements = findFirstAstOfType(astClasseOuInterface, TokenTypes.IMPLEMENTS_CLAUSE);
        if (astImplements != null) {
            List<DetailAST> astImplementsIdents = findAllAstsOfType(astImplements, IDENT);
            if (astImplementsIdents != null) {
                for (DetailAST astIdent : astImplementsIdents) {
                    if (astIdent.getParent().getType() == TokenTypes.IMPLEMENTS_CLAUSE) {
                        verificarConformidadeInterface(astIdent);
                    }
                }
            }
        }

        verificarSeFaltamInterfacesNoTipo(astClasseOuInterface);
    }

    private void verificarSeFaltamInterfacesNoTipo(DetailAST astClasseOuInterface) {
        Collection<InterfaceArquitetural> interfacesAusentes = restricoesArquiteturais
                        .recuperarInterfacesAusentes(tipo);
        if (interfacesAusentes != null && !interfacesAusentes.isEmpty()) {

            StringBuilder listaNomesInterfacesAusentes = null;
            for (InterfaceArquitetural interfaceArquitetural : interfacesAusentes) {
                if (listaNomesInterfacesAusentes == null) {
                    listaNomesInterfacesAusentes = new StringBuilder();
                    listaNomesInterfacesAusentes.append(interfaceArquitetural.name());
                } else {
                    listaNomesInterfacesAusentes.append(",").append(interfaceArquitetural.name());
                }
            }

            log(astClasseOuInterface.getLineNo(), MSG_INTERFACES_AUSENTES, listaNomesInterfacesAusentes);
        }
    }

    private void verificarConformidadeInterface(DetailAST astInterfaceIdent) {
        String nomeInterface = astInterfaceIdent.getText();
        InterfaceArquitetural interfaceArquitetural = TipoArquitetural.buscarInterfaceArquitetural(nomeInterface);
        if (interfaceArquitetural != null) {
            if (restricoesArquiteturais.ehInterfaceArquiteturalValida(tipo, interfaceArquitetural)) {
                tipo.adicionarInterface(interfaceArquitetural);
            } else {
                log(astInterfaceIdent.getLineNo(), MSG_INTERFACE_INVALIDA, interfaceArquitetural.name());
            }
        }
    }

    private void verificarConformidadeHeranca(DetailAST astClasseOuInterface) {
        DetailAST astExtends = findFirstAstOfType(astClasseOuInterface, TokenTypes.EXTENDS_CLAUSE);
        if (astExtends != null) {
            String nomePai = findFirstAstOfType(astExtends, IDENT).getText();
            System.out.println(nomePai);

            tipo.setPai(TipoArquitetural.buscarTipoArquiteturalAbstrato(nomePai));

            if (!restricoesArquiteturais.temHerancaValida(tipo)) {
                log(astClasseOuInterface.getLineNo(), MSG_HERANCA_INVALIDA);
            }
        }
    }

    private void verificarConformidadeAnotacoes(DetailAST astClasseOuInterface) {
        DetailAST astModifiers = findFirstAstOfType(astClasseOuInterface, TokenTypes.MODIFIERS);
        if (astModifiers != null) {
            List<DetailAST> astModifiersIdents = findAllAstsOfType(astModifiers, TokenTypes.IDENT);
            if (astModifiersIdents != null) {
                for (DetailAST astIdent : astModifiersIdents) {
                    if (astIdent.getParent().getType() == TokenTypes.ANNOTATION) {
                        verificarConformidadeAnotacao(astIdent);
                    }
                }
            }
        }

        verificarSeFaltamAnotacoesNoTipo(astClasseOuInterface);
    }

    private void verificarSeFaltamAnotacoesNoTipo(DetailAST astClasseOuInterface) {
        Collection<AnotacaoArquitetural> anotacoesAusentes = restricoesArquiteturais.recuperarAnotacoesAusentes(tipo);
        if (anotacoesAusentes != null && !anotacoesAusentes.isEmpty()) {

            StringBuilder listaNomesAnotacoesAusentes = null;
            for (AnotacaoArquitetural anotacaoArquitetural : anotacoesAusentes) {
                if (listaNomesAnotacoesAusentes == null) {
                    listaNomesAnotacoesAusentes = new StringBuilder();
                    listaNomesAnotacoesAusentes.append("@").append(anotacaoArquitetural.name());
                } else {
                    listaNomesAnotacoesAusentes.append(", @").append(anotacaoArquitetural.name());
                }
            }

            log(astClasseOuInterface.getLineNo(), MSG_ANOTACOES_AUSENTES, listaNomesAnotacoesAusentes);
        }
    }

    private void verificarConformidadeAnotacao(DetailAST astAnnotationIdent) {
        String nomeAnotacao = astAnnotationIdent.getText();
        AnotacaoArquitetural anotacaoArquitetural = TipoArquitetural.buscarAnotacaoArquitetural(nomeAnotacao);
        if (anotacaoArquitetural != null) {
            if (restricoesArquiteturais.ehAnotacaoArquiteturalValida(tipo, anotacaoArquitetural)) {
                tipo.adicionarAnotacao(anotacaoArquitetural);
            } else {
                log(astAnnotationIdent.getLineNo(), MSG_ANOTACAO_INVALIDA, anotacaoArquitetural.name());
            }
        }
    }

    private void verificarConfomidadeTipoArquitetural(DetailAST astClasseOuInterface) {
        tipo = new TipoArquitetural(sufixoArquitetural, pacoteArquitetural);
        if (!restricoesArquiteturais.existeTipoArquitetural(tipo)) {
            log(astClasseOuInterface.getLineNo(), MSG_TIPO_INVALIDO);
        }
    }

    private void verificarConformidadeSufixoArquitetural(DetailAST astClasseOuInterface) {
        String nomeClasseOuInterface = recuperarNomeDaClasseOuInterface(astClasseOuInterface);

        sufixoArquitetural = TipoArquitetural.buscarSufixoArquitetural(nomeClasseOuInterface);
        if (sufixoArquitetural == null) {
            log(astClasseOuInterface.getLineNo(), MSG_SUFIXO_ARQUITETURAL, nomeClasseOuInterface);
        }

        System.out.println(sufixoArquitetural);
    }

    private String recuperarNomeDaClasseOuInterface(DetailAST astClasseOuInterface) {
        DetailAST directChild = astClasseOuInterface.getFirstChild();
        while (directChild != null && directChild.getType() != IDENT) {
            directChild = directChild.getNextSibling();
        }

        String nomeClasseOuInterface = null;
        if (directChild != null && directChild.getType() == IDENT) {
            nomeClasseOuInterface = directChild.getText();
        }

        System.out.println(nomeClasseOuInterface);
        return nomeClasseOuInterface;
    }

    private void verificarConformidadeImports(DetailAST astImport) {
        String importComNomeClasse = fullyQualifiedPackage(astImport);
        String importApenasPacote = importComNomeClasse.substring(0, importComNomeClasse.lastIndexOf("."));

        if (restricoesArquiteturais.ehUmPacoteRestrito(importApenasPacote, pacoteArquitetural)) {
            System.out.println("acessando pacote arquitetural restrito ######################################");
            log(astImport.getLineNo(), MSG_PACOTE_RESTRITO, pacoteArquitetural.getNomePacote());
        }
    }

    private void verificarConformidadePacote(DetailAST astPacote) {
        limpaAtributosInstancia();

        String nomePacote = fullyQualifiedPackage(astPacote);
        System.out.println(nomePacote);

        verificarConformidadePacoteBase(astPacote, nomePacote);
        verificarConformidadePacoteArquitetural(astPacote, nomePacote);
    }

    private void verificarConformidadePacoteArquitetural(DetailAST astPacote, String nomePacote) {
        pacoteArquitetural = TipoArquitetural.buscarPacoteArquitetural(nomePacote);

        if (pacoteArquitetural == null) {
            System.out.println("não pertence a um pacote arquitetural!! @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            log(astPacote.getLineNo(), MSG_PACOTE_ARQUITETURAL, nomePacote);
        }

        System.out.println(pacoteArquitetural.getNomePacote());
    }

    private void verificarConformidadePacoteBase(DetailAST astPacote, String nomePacote) {
        if (!nomePacote.startsWith(PacoteArquitetural.Base.getNomePacote())) {
            System.out.println("pacote base errado!! @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            log(astPacote.getLineNo(), MSG_PACOTE_BASE, nomePacote, PacoteArquitetural.Base);
        }
    }

}
