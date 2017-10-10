package br.org.buildtools.checkstyle;

import static br.org.buildtools.arquitetura.ArquiteturaUtils.buscarInterfaceArquitetural;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IMPLEMENTS_CLAUSE;

import java.util.Collection;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import br.org.buildtools.arquitetura.RestricoesArquiteturais;
import br.org.buildtools.arquitetura.TipoArquitetural;
import br.org.buildtools.arquitetura.enums.InterfaceArquitetural;

public class InterfacesArquiteturaisAusentesCheck extends CustomCheck {

    private static final String MSG_INTERFACES_AUSENTES = MSG_PREFIX + "interfacesAusentes";

    private RestricoesArquiteturais restricoesArquiteturais = RestricoesArquiteturais.getInstance();

    @Override
    public int[] getAcceptableTokens() {
        return new int[] { CLASS_DEF };
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
    public void visitToken(DetailAST astClasse) {
        TipoArquitetural tipo = new TipoArquiteturalCheck().recuperarTipoValido(astClasse);

        if (tipo != null) {
            verificarConformidadeInterfacesImplementadas(astClasse, tipo);
        }
    }

    private void verificarConformidadeInterfacesImplementadas(DetailAST astClasse, TipoArquitetural tipo) {
        DetailAST astImplements = findFirstAstOfType(astClasse, IMPLEMENTS_CLAUSE);
        if (astImplements != null) {
            List<DetailAST> astImplementsIdents = findAllAstsOfType(astImplements, IDENT);
            for (DetailAST astIdent : astImplementsIdents) {
                if (astIdent.getParent().getType() == IMPLEMENTS_CLAUSE) {
                    verificarConformidadeInterfaceImplementada(astIdent, tipo);
                }
            }
        }

        verificarInterfacesAusentesNoTipo(astClasse, tipo);
    }

    private void verificarInterfacesAusentesNoTipo(DetailAST astClasseOuInterface, TipoArquitetural tipo) {
        Collection<InterfaceArquitetural> interfacesAusentes = restricoesArquiteturais
            .recuperarInterfacesAusentes(tipo);
        if (!interfacesAusentes.isEmpty()) {

            StringBuilder listaNomesInterfacesAusentes = null;
            for (InterfaceArquitetural interfaceArquitetural : interfacesAusentes) {
                if (listaNomesInterfacesAusentes == null) {
                    listaNomesInterfacesAusentes = new StringBuilder();
                    listaNomesInterfacesAusentes.append(interfaceArquitetural.name());
                } else {
                    listaNomesInterfacesAusentes.append(", ").append(interfaceArquitetural.name());
                }
            }

            DetailAST identTokenClasseOuInterface = recuperarIdentTokenDaClasseOuInterface(astClasseOuInterface);
            log(identTokenClasseOuInterface.getLineNo(), MSG_INTERFACES_AUSENTES, listaNomesInterfacesAusentes);
        }
    }

    private void verificarConformidadeInterfaceImplementada(DetailAST astInterfaceIdent, TipoArquitetural tipo) {
        String nomeInterface = astInterfaceIdent.getText();
        InterfaceArquitetural interfaceArquitetural = buscarInterfaceArquitetural(nomeInterface);
        if (interfaceArquitetural != null
            && restricoesArquiteturais.ehInterfaceArquiteturalValida(tipo, interfaceArquitetural)) {
            tipo.adicionarInterface(interfaceArquitetural);
        }
    }

}
