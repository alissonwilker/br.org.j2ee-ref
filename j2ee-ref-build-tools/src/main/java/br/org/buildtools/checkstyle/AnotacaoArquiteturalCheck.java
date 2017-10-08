package br.org.buildtools.checkstyle;

import static br.org.buildtools.arquitetura.ArquiteturaUtils.buscarAnotacaoArquitetural;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import br.org.buildtools.arquitetura.RestricoesArquiteturais;
import br.org.buildtools.arquitetura.TipoArquitetural;
import br.org.buildtools.arquitetura.enums.AnotacaoArquitetural;

public class AnotacaoArquiteturalCheck extends CustomCheck {

    private static final String MSG_ANOTACAO_INVALIDA = MSG_PREFIX + "anotacaoInvalida";

    private RestricoesArquiteturais restricoesArquiteturais = RestricoesArquiteturais.getInstance();

    @Override
    public int[] getAcceptableTokens() {
        return new int[] { ANNOTATION };
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
    public void visitToken(DetailAST astAnotacao) {
        if (ehAnotacaoDeClasseOuInterface(astAnotacao)) {
            DetailAST astClasseOuInterface = astAnotacao.getParent().getParent();
            TipoArquitetural tipo = new TipoArquiteturalCheck().recuperarTipoValido(astClasseOuInterface);

            if (tipo != null) {
                verificarConformidadeAnotacao(astAnotacao, tipo);
            }
        }
    }

    private AnotacaoArquitetural verificarConformidadeAnotacao(DetailAST astAnotacao, TipoArquitetural tipo) {
        DetailAST astAnnotationIdent = findFirstAstOfType(astAnotacao, IDENT);
        String nomeAnotacao = astAnnotationIdent.getText();
        AnotacaoArquitetural anotacaoArquitetural = buscarAnotacaoArquitetural(nomeAnotacao);
        if (anotacaoArquitetural != null) {
            if (restricoesArquiteturais.ehAnotacaoArquiteturalValida(tipo, anotacaoArquitetural)) {
                return anotacaoArquitetural;
            } else {
                log(astAnnotationIdent.getLineNo(), MSG_ANOTACAO_INVALIDA, anotacaoArquitetural.name());
            }
        }

        return null;
    }

}