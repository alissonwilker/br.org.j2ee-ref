package br.org.buildtools.checkstyle;

import static br.org.buildtools.arquitetura.ArquiteturaUtils.buscarAnotacaoArquitetural;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.MODIFIERS;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import br.org.buildtools.arquitetura.RestricoesArquiteturais;
import br.org.buildtools.arquitetura.TipoArquitetural;
import br.org.buildtools.arquitetura.enums.AnotacaoArquitetural;

public class AnotacoesArquiteturaisAusentesCheck extends CustomCheck {

    private static final String MSG_ANOTACOES_AUSENTES = MSG_PREFIX + "anotacoesAusentes";

    private RestricoesArquiteturais restricoesArquiteturais = RestricoesArquiteturais.getInstance();

    @Override
    public int[] getAcceptableTokens() {
        return new int[] { CLASS_DEF, INTERFACE_DEF };
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST astClasseOuInterface) {
        TipoArquitetural tipo = new TipoArquiteturalCheck().recuperarTipoValido(astClasseOuInterface);

        if (tipo != null) {
            verificarConformidadeAnotacoes(astClasseOuInterface, tipo);
        }
    }

    private void verificarConformidadeAnotacoes(DetailAST astClasseOuInterface, TipoArquitetural tipo) {
        Collection<AnotacaoArquitetural> anotacoes = new HashSet<AnotacaoArquitetural>();

        DetailAST astModifiers = findFirstAstOfType(astClasseOuInterface, MODIFIERS);
        if (astModifiers != null) {
            List<DetailAST> astModifiersIdents = findAllAstsOfType(astModifiers, IDENT);
            for (DetailAST astIdent : astModifiersIdents) {
                if (astIdent.getParent().getType() == ANNOTATION) {
                    AnotacaoArquitetural anotacao = recuperarAnotacaoArquiteturalValida(astIdent, tipo);
                    if (anotacao != null) {
                        anotacoes.add(anotacao);
                    }
                }
            }
        }

        boolean ehClasseAbstrata = verificarSeEhClasseAbstrata(astClasseOuInterface);
        if (!ehClasseAbstrata) {
            verificarSeFaltamAnotacoesNoTipo(astClasseOuInterface, anotacoes, tipo);
        }
    }

    private void verificarSeFaltamAnotacoesNoTipo(DetailAST astClasseOuInterface,
        Collection<AnotacaoArquitetural> anotacoes, TipoArquitetural tipo) {
        Collection<AnotacaoArquitetural> anotacoesAusentes = restricoesArquiteturais
            .recuperarAnotacoesObrigatoriasAusentes(tipo, anotacoes);
        if (!anotacoesAusentes.isEmpty()) {

            StringBuilder listaNomesAnotacoesAusentes = null;
            for (AnotacaoArquitetural anotacaoArquitetural : anotacoesAusentes) {
                if (listaNomesAnotacoesAusentes == null) {
                    listaNomesAnotacoesAusentes = new StringBuilder();
                    listaNomesAnotacoesAusentes.append("@").append(anotacaoArquitetural.name());
                } else {
                    listaNomesAnotacoesAusentes.append(", @").append(anotacaoArquitetural.name());
                }
            }

            DetailAST identTokenClasseOuInterface = recuperarIdentTokenDaClasseOuInterface(astClasseOuInterface);
            log(identTokenClasseOuInterface.getLineNo(), MSG_ANOTACOES_AUSENTES, listaNomesAnotacoesAusentes);
        }
    }

    private AnotacaoArquitetural recuperarAnotacaoArquiteturalValida(DetailAST astAnnotationIdent,
        TipoArquitetural tipo) {
        String nomeAnotacao = astAnnotationIdent.getText();
        AnotacaoArquitetural anotacaoArquitetural = buscarAnotacaoArquitetural(nomeAnotacao);
        if (anotacaoArquitetural != null
            && restricoesArquiteturais.ehAnotacaoArquiteturalValida(tipo, anotacaoArquitetural)) {
            return anotacaoArquitetural;
        }

        return null;
    }

}
