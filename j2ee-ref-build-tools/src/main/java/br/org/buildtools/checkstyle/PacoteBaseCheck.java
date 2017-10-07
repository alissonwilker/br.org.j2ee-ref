package br.org.buildtools.checkstyle;

import static com.puppycrawl.tools.checkstyle.api.TokenTypes.PACKAGE_DEF;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import br.org.buildtools.arquitetura.enums.PacoteArquitetural;

public class PacoteBaseCheck extends CustomCheck {

    private static final String MSG_PREFIX = "restricaoArquitetural.";
    private static final String MSG_PACOTE_BASE = MSG_PREFIX + "pacoteBase";

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

        verificarConformidadePacoteBase(astPacote, nomePacote);
    }

    private void verificarConformidadePacoteBase(DetailAST astPacote, String nomePacote) {
        if (!nomePacote.startsWith(PacoteArquitetural.Base.getNomePacote())) {
            log(astPacote.getLineNo(), MSG_PACOTE_BASE, nomePacote, PacoteArquitetural.Base.getNomePacote());
        }
    }

}
