package br.org.buildtools.checkstyle;

import static br.org.buildtools.arquitetura.ArquiteturaUtils.buscarPacoteArquitetural;
import static br.org.buildtools.arquitetura.ArquiteturaUtils.buscarSufixoArquitetural;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import br.org.buildtools.arquitetura.RestricoesArquiteturais;
import br.org.buildtools.arquitetura.TipoArquitetural;
import br.org.buildtools.arquitetura.enums.PacoteArquitetural;
import br.org.buildtools.arquitetura.enums.SufixoArquitetural;

public class TipoArquiteturalCheck extends CustomCheck {

    private static final String MSG_PREFIX = "restricaoArquitetural.";
    private static final String MSG_TIPO_INVALIDO = MSG_PREFIX + "tipoInvalido";

    private RestricoesArquiteturais restricoesArquiteturais = new RestricoesArquiteturais();

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
    public void visitToken(DetailAST ast) {
        if (ast.getType() == CLASS_DEF || ast.getType() == INTERFACE_DEF) {
            verificarConformidadeClasseOuInterface(ast);
        }
    }

    private void verificarConformidadeClasseOuInterface(DetailAST astClasseOuInterface) {
        verificarConfomidadeTipo(astClasseOuInterface);
    }

    private void verificarConfomidadeTipo(DetailAST astClasseOuInterface) {
        String nomeClasse = recuperarNomeDaClasseOuInterface(astClasseOuInterface);
        String nomePacote = recuperarNomeDoPacoteDoTipo(astClasseOuInterface);

        SufixoArquitetural sufixoArquitetural = buscarSufixoArquitetural(nomeClasse);
        PacoteArquitetural pacoteArquitetural = buscarPacoteArquitetural(nomePacote);
        TipoArquitetural tipo = null;

        if (sufixoArquitetural != null && pacoteArquitetural != null) {
            tipo = new TipoArquitetural(sufixoArquitetural, pacoteArquitetural);
        } else if (pacoteArquitetural != null) {
            tipo = new TipoArquitetural(pacoteArquitetural);
        } else {
            log(astClasseOuInterface.getLineNo(), MSG_TIPO_INVALIDO);
            return;
        }

        if (!restricoesArquiteturais.existeTipoArquitetural(tipo)) {
            log(astClasseOuInterface.getLineNo(), MSG_TIPO_INVALIDO);
        }

    }

}