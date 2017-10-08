package br.org.buildtools.checkstyle;

import static com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

public class PrefixoInterfaceCheck extends CustomCheck {

    private static final String MSG_PREFIXO_INTERFACE = MSG_PREFIX + "prefixoInterface";

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
        String nomeInterface = recuperarNomeDaClasseOuInterface(astInterface);

        if (!(nomeInterface.startsWith(prefixo)
                        && nomeInterface.substring(prefixo.length(), prefixo.length() + 1).matches("[A-Z]"))) {
            log(astInterface.getLineNo(), MSG_PREFIXO_INTERFACE, nomeInterface, prefixo);
        }
    }

}
