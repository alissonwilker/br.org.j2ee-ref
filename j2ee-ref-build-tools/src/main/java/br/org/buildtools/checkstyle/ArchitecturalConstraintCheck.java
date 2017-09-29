package br.org.buildtools.checkstyle;

import static com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IMPORT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.PACKAGE_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.STATIC_IMPORT;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import br.org.buildtools.checkstyle.TipoArquitetural.AnotacaoArquitetural;
import br.org.buildtools.checkstyle.TipoArquitetural.InterfaceArquitetural;
import br.org.buildtools.checkstyle.TipoArquitetural.PacoteArquitetural;
import br.org.buildtools.checkstyle.TipoArquitetural.SufixoArquitetural;
import br.org.buildtools.checkstyle.TipoArquitetural.TipoArquiteturalAbstrato;

public class ArchitecturalConstraintCheck extends CustomCheck {

    private static final String MSG_PREFIX = "restricaoArquitetural.";
    private static final String MSG_PACOTE_BASE = MSG_PREFIX + "pacoteBase";
    private static final String MSG_PACOTE_ARQUITETURAL = MSG_PREFIX + "pacoteArquitetural";
    private static final String MSG_PACOTE_RESTRITO = MSG_PREFIX + "pacoteRestrito";
    private static final String MSG_SUFIXO_ARQUITETURAL = MSG_PREFIX + "sufixoArquitetural";
    private static final String MSG_TIPO_INVALIDO = MSG_PREFIX + "tipoInvalido";

    private RestricoesArquiteturais restricoesArquiteturais;

    private String nomePacote;
    private String nomeClasseOuInterface;
    private PacoteArquitetural pacoteArquitetural;
    private SufixoArquitetural sufixoArquitetural;
    private TipoArquiteturalAbstrato pai;
    private Collection<AnotacaoArquitetural> anotacoes;
    private Collection<InterfaceArquitetural> interfaces;

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

    private void limpaAtributosInstancia() {
        nomePacote = null;
        nomeClasseOuInterface = null;
        pacoteArquitetural = null;
        sufixoArquitetural = null;
        pai = null;
        anotacoes = new HashSet<AnotacaoArquitetural>();
        interfaces = new HashSet<InterfaceArquitetural>();
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == PACKAGE_DEF) {
            limpaAtributosInstancia();
            nomePacote = fullyQualifiedPackage(ast);
            System.out.println(nomePacote);

            if (!nomePacote.startsWith(PacoteArquitetural.Base.getNomePacote())) {
                System.out.println("pacote base errado!! @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                log(ast.getLineNo(), MSG_PACOTE_BASE, nomePacote, PacoteArquitetural.Base);
            }

            pacoteArquitetural = TipoArquitetural.getPacoteArquitetural(nomePacote);
            if (pacoteArquitetural == null) {
                System.out.println("não pertence a um pacote arquitetural!! @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                log(ast.getLineNo(), MSG_PACOTE_ARQUITETURAL, nomePacote);
            }
            System.out.println(pacoteArquitetural.getNomePacote());
        }

        if (ast.getType() == TokenTypes.IMPORT || ast.getType() == TokenTypes.STATIC_IMPORT) {
            String importWithClassName = fullyQualifiedPackage(ast);
            String importName = importWithClassName.substring(0, importWithClassName.lastIndexOf("."));
            Collection<PacoteArquitetural> restricoesPacote = restricoesArquiteturais
                            .getPacotesRestritos(pacoteArquitetural);
            if (restricoesPacote != null) {
                for (PacoteArquitetural pacoteArquiteturalRestrito : restricoesPacote) {
                    if (importName.endsWith(pacoteArquiteturalRestrito.getNomePacote())) {
                        System.out.println(
                                        "acessando pacote arquitetural restrito ######################################");
                        log(ast.getLineNo(), MSG_PACOTE_RESTRITO, pacoteArquiteturalRestrito.getNomePacote(),
                                        pacoteArquitetural.getNomePacote());
                    }
                }
            }

        }

        if (ast.getType() == CLASS_DEF || ast.getType() == INTERFACE_DEF) {
            DetailAST directChild = ast.getFirstChild();
            while (directChild != null && directChild.getType() != IDENT) {
                directChild = directChild.getNextSibling();
            }

            if (directChild != null && directChild.getType() == IDENT) {
                nomeClasseOuInterface = directChild.getText();
            }
            System.out.println(nomeClasseOuInterface);
            sufixoArquitetural = TipoArquitetural.getSufixoArquitetural(nomeClasseOuInterface);
            if (sufixoArquitetural == null) {
                log(ast.getLineNo(), MSG_SUFIXO_ARQUITETURAL, nomeClasseOuInterface);
            }
            System.out.println(sufixoArquitetural);

            DetailAST classModifiers = findFirstAstOfType(ast, TokenTypes.MODIFIERS);
            if (classModifiers != null) {
                List<DetailAST> astAnnotations = findAllAstsOfType(classModifiers, TokenTypes.IDENT);
                if (astAnnotations != null) {
                    for (DetailAST detailAST : astAnnotations) {
                        if (detailAST.getParent().getType() == TokenTypes.ANNOTATION) {
                            String anotacao = detailAST.getText();
                            for (AnotacaoArquitetural anotacaoArquitetural : AnotacaoArquitetural.values()) {
                                if (anotacaoArquitetural.name().equals(anotacao)) {
                                    anotacoes.add(anotacaoArquitetural);
                                    System.out.println("@" + anotacaoArquitetural.name());
                                }
                            }
                        }
                    }
                }
            }

            DetailAST astExtends = findFirstAstOfType(ast, TokenTypes.EXTENDS_CLAUSE);
            if (astExtends != null) {
                String nomePai = findFirstAstOfType(astExtends, IDENT).getText();
                System.out.println(nomePai);

                for (TipoArquiteturalAbstrato tipoArquiteturalAbstrato : TipoArquiteturalAbstrato.values()) {
                    if (tipoArquiteturalAbstrato.name().equals(nomePai)) {
                        pai = tipoArquiteturalAbstrato;
                    }
                }
            }

            DetailAST astImplements = findFirstAstOfType(ast, TokenTypes.IMPLEMENTS_CLAUSE);
            if (astImplements != null) {
                List<DetailAST> astIdents = findAllAstsOfType(astImplements, IDENT);
                if (astIdents != null) {
                    for (DetailAST detailAST : astIdents) {
                        if (detailAST.getParent().getType() == TokenTypes.IMPLEMENTS_CLAUSE) {
                            String nomeInterface = detailAST.getText();
                            for (InterfaceArquitetural interfaceArquitetural : InterfaceArquitetural.values()) {
                                if (interfaceArquitetural.name().equals(nomeInterface)) {
                                    interfaces.add(interfaceArquitetural);
                                    System.out.println(nomeInterface);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (sufixoArquitetural != null && pacoteArquitetural != null) {
            TipoArquitetural ta = new TipoArquitetural(sufixoArquitetural, pacoteArquitetural);
            ta.setPai(pai);
            ta.setAnotacoes(anotacoes);
            ta.setInterfaces(interfaces);

            if (!restricoesArquiteturais.getTiposArquiteturais().contains(ta)) {
                System.out.println("não está conforme a arquitetura");
                log(ast.getLineNo(), MSG_TIPO_INVALIDO, nomeClasseOuInterface);
            } else {
                System.out.println("está conforme a arquitetura");
            }
        }

    }

}


