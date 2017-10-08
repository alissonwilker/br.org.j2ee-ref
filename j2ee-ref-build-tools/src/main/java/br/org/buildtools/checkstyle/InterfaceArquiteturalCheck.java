package br.org.buildtools.checkstyle;

import static br.org.buildtools.arquitetura.ArquiteturaUtils.buscarInterfaceArquitetural;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IMPLEMENTS_CLAUSE;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import br.org.buildtools.arquitetura.RestricoesArquiteturais;
import br.org.buildtools.arquitetura.TipoArquitetural;
import br.org.buildtools.arquitetura.enums.InterfaceArquitetural;

public class InterfaceArquiteturalCheck extends CustomCheck {

    private static final String MSG_INTERFACE_INVALIDA = MSG_PREFIX + "interfaceInvalida";

    private RestricoesArquiteturais restricoesArquiteturais = RestricoesArquiteturais.getInstance();

    @Override
    public int[] getAcceptableTokens() {
        return new int[] { IMPLEMENTS_CLAUSE };
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
    public void visitToken(DetailAST astImplements) {
        TipoArquitetural tipo = new TipoArquiteturalCheck().recuperarTipoValido(astImplements.getParent());

        if (tipo != null) {
            verificarConformidadeInterfacesImplementadas(astImplements, tipo);
        }
    }

    private void verificarConformidadeInterfacesImplementadas(DetailAST astImplements, TipoArquitetural tipo) {
        List<DetailAST> astImplementsIdents = findAllAstsOfType(astImplements, IDENT);
        for (DetailAST astIdent : astImplementsIdents) {
            if (astIdent.getParent().getType() == IMPLEMENTS_CLAUSE) {
                verificarConformidadeInterfaceImplementada(astIdent, tipo);
            }
        }
    }

    private void verificarConformidadeInterfaceImplementada(DetailAST astInterfaceIdent, TipoArquitetural tipo) {
        String nomeInterface = astInterfaceIdent.getText();
        InterfaceArquitetural interfaceArquitetural = buscarInterfaceArquitetural(nomeInterface);
        if (interfaceArquitetural != null
            && !restricoesArquiteturais.ehInterfaceArquiteturalValida(tipo, interfaceArquitetural)) {
            log(astInterfaceIdent.getLineNo(), MSG_INTERFACE_INVALIDA, interfaceArquitetural.name());
        }
    }

}
