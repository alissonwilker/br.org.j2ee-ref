package br.org.buildtools.checkstyle;

import static com.puppycrawl.tools.checkstyle.api.TokenTypes.ANNOTATION;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.EXTENDS_CLAUSE;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IMPLEMENTS_CLAUSE;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IMPORT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.MODIFIERS;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.PACKAGE_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.STATIC_IMPORT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import br.org.buildtools.arquitetura.RestricoesArquiteturais;
import br.org.buildtools.arquitetura.TipoArquitetural;

public class ArchitecturalConstraintCheck extends CustomCheck {

    private static final String MSG_PREFIX = "restricaoArquitetural.";
    private static final String MSG_PACOTE_BASE = MSG_PREFIX + "pacoteBase";
    private static final String MSG_PACOTE_ARQUITETURAL = MSG_PREFIX + "pacoteArquitetural";
    private static final String MSG_PACOTE_RESTRITO = MSG_PREFIX + "pacoteRestrito";
    private static final String MSG_TIPO_INVALIDO = MSG_PREFIX + "tipoInvalido";
    private static final String MSG_ANOTACAO_INVALIDA = MSG_PREFIX + "anotacaoInvalida";
    private static final String MSG_ANOTACOES_AUSENTES = MSG_PREFIX + "anotacoesAusentes";
    private static final String MSG_HERANCA_INVALIDA = MSG_PREFIX + "herancaInvalida";
    private static final String MSG_HERANCAS_AUSENTES = MSG_PREFIX + "herancasAusentes";
    private static final String MSG_INTERFACE_INVALIDA = MSG_PREFIX + "interfaceInvalida";
    private static final String MSG_INTERFACES_AUSENTES = MSG_PREFIX + "interfacesAusentes";

    private RestricoesArquiteturais restricoesArquiteturais;

    private TipoArquitetural tipo;
    private String pacoteArquitetural;

    public ArchitecturalConstraintCheck() {
        restricoesArquiteturais = new RestricoesArquiteturais();
    }

    public void setTipo(String tipoProperty) {
        List<String> tipo = getListFromProperty(tipoProperty);
        restricoesArquiteturais.adicionarTipo(tipo.get(0), tipo.get(1), tipo.get(2));
    }

    public void setInterfacesTipo(String interfacesTipoProperty) {
        List<String> interfacesTipo = getListFromProperty(interfacesTipoProperty);
        String tipo = interfacesTipo.get(0);
        interfacesTipo.remove(0);
        restricoesArquiteturais.adicionarInterfacesTipo(tipo, interfacesTipo);
    }

    public void setAnotacoesObrigatoriasTipo(String anotacoesObrigatoriasTipoProperty) {
        List<String> anotacoesTipo = getListFromProperty(anotacoesObrigatoriasTipoProperty);
        String tipo = anotacoesTipo.get(0);
        anotacoesTipo.remove(0);
        restricoesArquiteturais.adicionarHerancasTipo(tipo, anotacoesTipo);
    }

    public void setHerancasTipo(String herancasTipoProperty) {
        List<String> herancasTipo = getListFromProperty(herancasTipoProperty);
        String tipo = herancasTipo.get(0);
        herancasTipo.remove(0);
        restricoesArquiteturais.adicionarHerancasTipo(tipo, herancasTipo);
    }

    public void setRestricaoPacote(String restricaoPacoteProperty) {
        List<String> restricaoPacote = getListFromProperty(restricaoPacoteProperty);
        String pacote = restricaoPacote.get(0);
        restricaoPacote.remove(0);
        restricoesArquiteturais.adicionarRestricaoPacote(pacote, restricaoPacote);
    }

    public void setPacotes(String pacotesProperty) {
        List<String> pacotes = getListFromProperty(pacotesProperty);
        restricoesArquiteturais.setPacoteBase(pacotes.get(0));
        pacotes.remove(0);
        restricoesArquiteturais.adicionarPacotes(pacotes);
    }

    public void setAnotacoes(String anotacoesProperty) {
        restricoesArquiteturais.adicionarAnotacoes(getListFromProperty(anotacoesProperty));
    }

    public void setSufixos(String sufixosProperty) {
        restricoesArquiteturais.adicionarSufixos(getListFromProperty(sufixosProperty));
    }

    public void setHerancas(String herancasProperty) {
        restricoesArquiteturais.adicionarHerancas(getListFromProperty(herancasProperty));
    }
    
    public void setInterfaces(String interfacesProperty) {
        restricoesArquiteturais.adicionarInterfaces(getListFromProperty(interfacesProperty));
    }

    private List<String> getListFromProperty(String property) {
        ArrayList<String> lista = new ArrayList<String>();
        lista.addAll(Arrays.asList(property.replaceAll(" ", "").split(",")));
        return lista;
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] { CLASS_DEF, INTERFACE_DEF, STATIC_IMPORT, IMPORT, PACKAGE_DEF };
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    /**
     * Limpa os atributos da instância. Esse método deve ser executado sempre que for iniciada a análise
     * de um novo arquivo, pois a instância é a mesma para todas as análises (mesmo de arquivos
     * diferentes). A análise de um novo arquivo pode ser identificada através do TokenType.PACKAGE_DEF,
     * que ocorre sempre na primeira linha do arquivo.
     */
    private void limpaAtributosInstancia() {
        tipo = null;
        pacoteArquitetural = null;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == PACKAGE_DEF) {
            verificarConformidadePacote(ast);
        }

        if (ast.getType() == IMPORT || ast.getType() == STATIC_IMPORT) {
            verificarConformidadeImports(ast);
        }

        if (ast.getType() == CLASS_DEF || ast.getType() == INTERFACE_DEF) {
            verificarConformidadeClasseOuInterface(ast);
        }
    }

    private void verificarConformidadeClasseOuInterface(DetailAST astClasseOuInterface) {
        boolean tipoEhValido = verificarConfomidadeTipo(astClasseOuInterface);

        if (tipoEhValido) {
            verificarConformidadeAnotacoes(astClasseOuInterface);

            verificarConformidadeHeranca(astClasseOuInterface);

            verificarConformidadeInterfacesImplementadas(astClasseOuInterface);
        }
    }

    private void verificarConformidadeInterfacesImplementadas(DetailAST astClasseOuInterface) {
        DetailAST astImplements = findFirstAstOfType(astClasseOuInterface, IMPLEMENTS_CLAUSE);
        if (astImplements != null) {
            List<DetailAST> astImplementsIdents = findAllAstsOfType(astImplements, IDENT);
            for (DetailAST astIdent : astImplementsIdents) {
                if (astIdent.getParent().getType() == IMPLEMENTS_CLAUSE) {
                    verificarConformidadeInterfaceImplementada(astIdent);
                }
            }
        }

        verificarInterfacesAusentesNoTipo(astClasseOuInterface);
    }

    private void verificarInterfacesAusentesNoTipo(DetailAST astClasseOuInterface) {
        Collection<String> interfacesAusentes = restricoesArquiteturais
            .recuperarInterfacesAusentes(tipo);
        if (!interfacesAusentes.isEmpty()) {

            StringBuilder listaNomesInterfacesAusentes = null;
            for (String interfaceArquitetural : interfacesAusentes) {
                if (listaNomesInterfacesAusentes == null) {
                    listaNomesInterfacesAusentes = new StringBuilder();
                    listaNomesInterfacesAusentes.append(interfaceArquitetural);
                } else {
                    listaNomesInterfacesAusentes.append(", ").append(interfaceArquitetural);
                }
            }

            log(astClasseOuInterface.getLineNo(), MSG_INTERFACES_AUSENTES, listaNomesInterfacesAusentes);
        }
    }

    private void verificarConformidadeInterfaceImplementada(DetailAST astInterfaceIdent) {
        String nomeInterface = astInterfaceIdent.getText();
        String interfaceArquitetural = restricoesArquiteturais.buscarInterfaceArquitetural(nomeInterface);
        if (interfaceArquitetural != null) {
            if (restricoesArquiteturais.ehInterfaceArquiteturalValida(tipo, interfaceArquitetural)) {
                tipo.adicionarInterface(interfaceArquitetural);
            } else {
                log(astInterfaceIdent.getLineNo(), MSG_INTERFACE_INVALIDA, interfaceArquitetural);
            }
        }
    }

    private void verificarConformidadeHeranca(DetailAST astClasseOuInterface) {
        DetailAST astExtends = findFirstAstOfType(astClasseOuInterface, EXTENDS_CLAUSE);
        if (astExtends != null) {
            String nomePai = findFirstAstOfType(astExtends, IDENT).getText();

            String herancaArquitetural = restricoesArquiteturais.buscarHerancaArquitetural(nomePai);
            if (herancaArquitetural != null) {
                if (restricoesArquiteturais.ehHerancaArquiteturalValida(tipo, herancaArquitetural)) {
                    tipo.adicionarHeranca(herancaArquitetural);
                } else {
                    log(astClasseOuInterface.getLineNo(), MSG_HERANCA_INVALIDA, nomePai);
                }
                return;
            }
        }

        if (tipo.getHerancas().isEmpty()) {
            verificarHerancasAusentesNoTipo(astClasseOuInterface);
        }
    }

    private void verificarHerancasAusentesNoTipo(DetailAST astClasseOuInterface) {
        Collection<String> herancasAusentes = restricoesArquiteturais.recuperarHerancas(tipo);

        if (!herancasAusentes.isEmpty()) {

            StringBuilder listaNomesHerancasAusentes = null;
            for (String herancaArquitetural : herancasAusentes) {
                if (listaNomesHerancasAusentes == null) {
                    listaNomesHerancasAusentes = new StringBuilder();
                    listaNomesHerancasAusentes.append(herancaArquitetural);
                } else {
                    listaNomesHerancasAusentes.append(", ").append(herancaArquitetural);
                }
            }

            log(astClasseOuInterface.getLineNo(), MSG_HERANCAS_AUSENTES, listaNomesHerancasAusentes);
        }
    }

    private void verificarConformidadeAnotacoes(DetailAST astClasseOuInterface) {
        Collection<String> anotacoes = new HashSet<String>();

        DetailAST astModifiers = findFirstAstOfType(astClasseOuInterface, MODIFIERS);
        if (astModifiers != null) {
            List<DetailAST> astModifiersIdents = findAllAstsOfType(astModifiers, IDENT);
            for (DetailAST astIdent : astModifiersIdents) {
                if (astIdent.getParent().getType() == ANNOTATION) {
                    String anotacao = verificarConformidadeAnotacao(astIdent);
                    if (anotacao != null) {
                        anotacoes.add(anotacao);
                    }
                }
            }
        }

        boolean ehClasseAbstrata = verificarSeEhClasseAbstrata(astClasseOuInterface);
        if (!ehClasseAbstrata) {
            verificarSeFaltamAnotacoesNoTipo(astClasseOuInterface, anotacoes);
        }
    }

    private void verificarSeFaltamAnotacoesNoTipo(DetailAST astClasseOuInterface,
        Collection<String> anotacoes) {
        Collection<String> anotacoesAusentes = restricoesArquiteturais
            .recuperarAnotacoesObrigatoriasAusentes(tipo, anotacoes);
        if (!anotacoesAusentes.isEmpty()) {

            StringBuilder listaNomesAnotacoesAusentes = null;
            for (String anotacaoArquitetural : anotacoesAusentes) {
                if (listaNomesAnotacoesAusentes == null) {
                    listaNomesAnotacoesAusentes = new StringBuilder();
                    listaNomesAnotacoesAusentes.append("@").append(anotacaoArquitetural);
                } else {
                    listaNomesAnotacoesAusentes.append(", @").append(anotacaoArquitetural);
                }
            }

            log(astClasseOuInterface.getLineNo(), MSG_ANOTACOES_AUSENTES, listaNomesAnotacoesAusentes);
        }
    }

    private String verificarConformidadeAnotacao(DetailAST astAnnotationIdent) {
        String nomeAnotacao = astAnnotationIdent.getText();
        String anotacaoArquitetural = restricoesArquiteturais.buscarAnotacaoArquitetural(nomeAnotacao);
        if (anotacaoArquitetural != null) {
            if (restricoesArquiteturais.ehAnotacaoArquiteturalValida(tipo, anotacaoArquitetural)) {
                return anotacaoArquitetural;
            } else {
                log(astAnnotationIdent.getLineNo(), MSG_ANOTACAO_INVALIDA, anotacaoArquitetural);
            }
        }

        return null;
    }

    private boolean verificarConfomidadeTipo(DetailAST astClasseOuInterface) {
        String nomeClasse = recuperarNomeDaClasseOuInterface(astClasseOuInterface);

        String sufixoArquitetural = restricoesArquiteturais.buscarSufixoArquitetural(nomeClasse);

        if (sufixoArquitetural != null && pacoteArquitetural != null) {
            tipo = new TipoArquitetural(sufixoArquitetural, pacoteArquitetural);
        } else if (pacoteArquitetural != null) {
            tipo = new TipoArquitetural(pacoteArquitetural);
        } else {
            log(astClasseOuInterface.getLineNo(), MSG_TIPO_INVALIDO);
            return false;
        }

        if (!restricoesArquiteturais.existeTipoArquitetural(tipo)) {
            log(astClasseOuInterface.getLineNo(), MSG_TIPO_INVALIDO);
            return false;
        }

        return true;
    }

    private void verificarConformidadeImports(DetailAST astImport) {
        String importComNomeClasse = fullyQualifiedPackage(astImport);
        String importApenasPacote = importComNomeClasse.substring(0, importComNomeClasse.lastIndexOf("."));

        if (restricoesArquiteturais.ehUmPacoteRestrito(importApenasPacote, pacoteArquitetural)) {
            log(astImport.getLineNo(), MSG_PACOTE_RESTRITO, pacoteArquitetural, importApenasPacote);
        }
    }

    private void verificarConformidadePacote(DetailAST astPacote) {
        limpaAtributosInstancia();

        String nomePacote = fullyQualifiedPackage(astPacote);

        verificarConformidadePacoteBase(astPacote, nomePacote);
        verificarConformidadePacoteArquitetural(astPacote, nomePacote);
    }

    private void verificarConformidadePacoteArquitetural(DetailAST astPacote, String nomePacote) {
        pacoteArquitetural = restricoesArquiteturais.buscarPacoteArquitetural(nomePacote);

        if (pacoteArquitetural == null) {
            log(astPacote.getLineNo(), MSG_PACOTE_ARQUITETURAL, nomePacote);
        }
    }

    private void verificarConformidadePacoteBase(DetailAST astPacote, String nomePacote) {
        String pacoteBase = restricoesArquiteturais.getPacoteBase();
        if (!nomePacote.startsWith(pacoteBase)) {
            log(astPacote.getLineNo(), MSG_PACOTE_BASE, nomePacote, pacoteBase);
        }
    }

}
