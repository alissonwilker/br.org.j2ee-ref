package br.org.buildtools.checkstyle;

import static br.org.buildtools.arquitetura.ArquiteturaUtils.buscarHerancaArquitetural;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.EXTENDS_CLAUSE;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF;

import java.util.Collection;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import br.org.buildtools.arquitetura.RestricoesArquiteturais;
import br.org.buildtools.arquitetura.TipoArquitetural;
import br.org.buildtools.arquitetura.enums.HerancaArquitetural;

public class HerancasArquiteturaisAusentesCheck extends CustomCheck {

    private static final String MSG_HERANCAS_AUSENTES = MSG_PREFIX + "herancasAusentes";

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
            verificarConformidadeHeranca(astClasseOuInterface, tipo);
        }
    }

    private void verificarConformidadeHeranca(DetailAST astClasseOuInterface, TipoArquitetural tipo) {
        DetailAST astExtends = findFirstAstOfType(astClasseOuInterface, EXTENDS_CLAUSE);
        if (astExtends != null) {
            String nomePai = findFirstAstOfType(astExtends, IDENT).getText();

            HerancaArquitetural herancaArquitetural = buscarHerancaArquitetural(nomePai);
            if (herancaArquitetural != null) {
                /* quando a classe estende de uma heranca arquitetural, ela sera validada pela
                 * regra HerancaArquiteturalCheck.
                */
                return;
            }
        }

        // classe nao possui heranca ou nao possui heranca arquitetural
        verificarHerancasArquiteturaisAusentesNoTipo(astClasseOuInterface, tipo);
    }

    private void verificarHerancasArquiteturaisAusentesNoTipo(DetailAST astClasseOuInterface, TipoArquitetural tipo) {
        Collection<HerancaArquitetural> herancasArquiteturaisAusentes = restricoesArquiteturais.recuperarHerancas(tipo);

        if (!herancasArquiteturaisAusentes.isEmpty()) {

            StringBuilder listaNomesHerancasAusentes = null;
            for (HerancaArquitetural herancaArquitetural : herancasArquiteturaisAusentes) {
                if (listaNomesHerancasAusentes == null) {
                    listaNomesHerancasAusentes = new StringBuilder();
                    listaNomesHerancasAusentes.append(herancaArquitetural.name());
                } else {
                    listaNomesHerancasAusentes.append(", ").append(herancaArquitetural.name());
                }
            }

            log(astClasseOuInterface.getLineNo(), MSG_HERANCAS_AUSENTES, listaNomesHerancasAusentes);
        }
    }

}
