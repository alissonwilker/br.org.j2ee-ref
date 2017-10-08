package br.org.buildtools.checkstyle;

import static br.org.buildtools.arquitetura.ArquiteturaUtils.buscarPacoteArquitetural;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IMPORT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.STATIC_IMPORT;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import br.org.buildtools.arquitetura.RestricoesArquiteturais;
import br.org.buildtools.arquitetura.enums.PacoteArquitetural;

public class PacoteRestritoCheck extends CustomCheck {

    private static final String MSG_PACOTE_RESTRITO = MSG_PREFIX + "pacoteRestrito";

    private RestricoesArquiteturais restricoesArquiteturais = RestricoesArquiteturais.getInstance();

    @Override
    public int[] getAcceptableTokens() {
        return new int[] { STATIC_IMPORT, IMPORT };
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
        verificarConformidadeImports(ast);
    }

    private void verificarConformidadeImports(DetailAST astImport) {
        String importComNomeClasse = fullyQualifiedPackage(astImport);
        String importApenasPacote = importComNomeClasse.substring(0, importComNomeClasse.lastIndexOf("."));

        String nomePacoteDoTipo = recuperarNomeDoPacoteDoTipo(astImport);
        PacoteArquitetural pacoteArquitetural = buscarPacoteArquitetural(nomePacoteDoTipo);

        if (restricoesArquiteturais.ehUmPacoteRestrito(importApenasPacote, pacoteArquitetural)) {
            log(astImport.getLineNo(), MSG_PACOTE_RESTRITO, pacoteArquitetural.getNomePacote(), importApenasPacote);
        }
    }

}
