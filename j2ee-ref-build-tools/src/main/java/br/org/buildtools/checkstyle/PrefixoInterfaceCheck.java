package br.org.buildtools.checkstyle;

import static com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import br.org.buildtools.arquitetura.RestricoesArquiteturais;
import br.org.buildtools.arquitetura.TipoArquitetural;

public class PrefixoInterfaceCheck extends CustomCheck {

    private static final String MSG_PREFIXO_INTERFACE = MSG_PREFIX + "prefixoInterface";

    private RestricoesArquiteturais restricoesArquiteturais = RestricoesArquiteturais.getInstance();

    private String prefixo = null;

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] { INTERFACE_DEF };
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
    public void visitToken(DetailAST astInterface) {
        TipoArquitetural tipo = new TipoArquiteturalCheck().recuperarTipoValido(astInterface);
        
        if (tipo != null && !restricoesArquiteturais.deveIgnorarPrefixo(tipo)) {
            verificarConformidadePrefixoInterface(astInterface);
        }
        
    }
    
    private void verificarConformidadePrefixoInterface(DetailAST astInterface) {
        DetailAST identTokenInterface = recuperarIdentTokenDaClasseOuInterface(astInterface);
        String nomeInterface = identTokenInterface.getText();
        
        if (!(nomeInterface.startsWith(prefixo)
                && nomeInterface.substring(prefixo.length(), prefixo.length() + 1).matches("[A-Z]"))) {
            log(identTokenInterface.getLineNo(), MSG_PREFIXO_INTERFACE, nomeInterface, prefixo);
        }
    }

}
