package br.org.buildtools.checkstyle;

import static br.org.buildtools.arquitetura.ArquiteturaUtils.buscarHerancaArquitetural;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.EXTENDS_CLAUSE;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import br.org.buildtools.arquitetura.RestricoesArquiteturais;
import br.org.buildtools.arquitetura.TipoArquitetural;
import br.org.buildtools.arquitetura.enums.HerancaArquitetural;

public class HerancaArquiteturalCheck extends CustomCheck {

    private static final String MSG_HERANCA_INVALIDA = MSG_PREFIX + "herancaInvalida";

    private RestricoesArquiteturais restricoesArquiteturais = RestricoesArquiteturais.getInstance();

    @Override
    public int[] getAcceptableTokens() {
        return new int[] { EXTENDS_CLAUSE };
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
    public void visitToken(DetailAST astExtends) {
        DetailAST astClasseOuInterface = astExtends.getParent();
        TipoArquitetural tipo = new TipoArquiteturalCheck().recuperarTipoValido(astClasseOuInterface);

        if (tipo != null) {
            verificarConformidadeHeranca(astExtends, tipo);
        }
    }

    private void verificarConformidadeHeranca(DetailAST astExtends, TipoArquitetural tipo) {
        String nomePai = findFirstAstOfType(astExtends, IDENT).getText();

        HerancaArquitetural herancaArquitetural = buscarHerancaArquitetural(nomePai);
        if (herancaArquitetural != null
            && !restricoesArquiteturais.ehHerancaArquiteturalValida(tipo, herancaArquitetural)) {
            log(astExtends.getLineNo(), MSG_HERANCA_INVALIDA, nomePai);
        }
    }

}
