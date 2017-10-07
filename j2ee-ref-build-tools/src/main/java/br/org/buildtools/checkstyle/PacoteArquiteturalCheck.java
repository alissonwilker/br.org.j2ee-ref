package br.org.buildtools.checkstyle;

import static br.org.buildtools.arquitetura.ArquiteturaUtils.buscarPacoteArquitetural;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.PACKAGE_DEF;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import br.org.buildtools.arquitetura.enums.PacoteArquitetural;

public class PacoteArquiteturalCheck extends CustomCheck {

    private static final String MSG_PREFIX = "restricaoArquitetural.";
    private static final String MSG_PACOTE_ARQUITETURAL = MSG_PREFIX + "pacoteArquitetural";

    @Override
    public int[] getAcceptableTokens() {
        return new int[] { PACKAGE_DEF };
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
    public void visitToken(DetailAST ast) {
        if (ast.getType() == PACKAGE_DEF) {
            verificarConformidadePacote(ast);
        }
    }

    private void verificarConformidadePacote(DetailAST astPacote) {
        String nomePacote = fullyQualifiedPackage(astPacote);

        verificarConformidadePacoteArquitetural(astPacote, nomePacote);
    }

    private void verificarConformidadePacoteArquitetural(DetailAST astPacote, String nomePacote) {
        PacoteArquitetural pacoteArquitetural = buscarPacoteArquitetural(nomePacote);

        if (pacoteArquitetural == null) {
            log(astPacote.getLineNo(), MSG_PACOTE_ARQUITETURAL, nomePacote);
        }
    }

}
