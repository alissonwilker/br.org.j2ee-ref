package br.org.buildtools.checkstyle;

import static com.puppycrawl.tools.checkstyle.api.TokenTypes.ABSTRACT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.MODIFIERS;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

public class PrefixoClasseAbstrataCheck extends CustomCheck {

    private static final String MSG_PREFIX = "restricaoArquitetural.";
    private static final String MSG_PREFIXO_CLASSE_ABSTRATA_AUSENTE = MSG_PREFIX + "prefixoClasseAbstrataAusente";
    private static final String MSG_PREFIXO_ABSTRACT_ERRADO = MSG_PREFIX + "prefixoAbstractErrado";

    private String prefixo = null;

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] { CLASS_DEF };
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
    public void visitToken(DetailAST astClasse) {
        String nomeClasse = recuperarNomeDaClasseOuInterface(astClasse);
        boolean ehClasseAbstrata = verificarSeEhClasseAbstrata(astClasse);
            
        if (ehClasseAbstrata && !nomeClasse.startsWith(prefixo)) {
            log(astClasse.getLineNo(), MSG_PREFIXO_CLASSE_ABSTRATA_AUSENTE, nomeClasse, prefixo);
        } else if (nomeClasse.startsWith(prefixo) && !ehClasseAbstrata) {
            log(astClasse.getLineNo(), MSG_PREFIXO_ABSTRACT_ERRADO, nomeClasse, prefixo);
        }
    }
    
    private boolean verificarSeEhClasseAbstrata(DetailAST astClasse) {
        DetailAST astModifiersAbstract = null;
        DetailAST astModifiers = findFirstAstOfType(astClasse, MODIFIERS);
        if (astModifiers != null) {
            astModifiersAbstract = findFirstAstOfType(astModifiers, ABSTRACT);
        }

        return astModifiersAbstract != null ? true : false;
    }

}
