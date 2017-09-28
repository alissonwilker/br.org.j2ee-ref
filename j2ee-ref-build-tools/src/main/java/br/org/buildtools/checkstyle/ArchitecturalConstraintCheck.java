package br.org.buildtools.checkstyle;

import static com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IMPORT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.PACKAGE_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.STATIC_IMPORT;

import java.util.Collection;
import java.util.HashSet;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

public class ArchitecturalConstraintCheck extends CustomCheck {
    
    // public static final String MSG_KEY = "checkstyle.methodlimit";
    
    private Collection<TipoArquitetural> tiposArquiteturais = new HashSet<TipoArquitetural>();
    
    public ArchitecturalConstraintCheck() {
        TipoArquitetural viewController = new TipoArquitetural(SufixoArquitetural.Controller,
                PacoteArquitetural.ViewController);
        // viewController.setAnotacoes(AnotacaoArquitetural.ManagedBean, AnotacaoArquitetural.ViewScoped);
        
        tiposArquiteturais.add(viewController);
        System.out.println("nova instancia");
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
    
    private String packageName;
    // private String importName;
    private String classOrInterfaceName;
    
    private PacoteArquitetural pacoteArquitetural;
    
    private SufixoArquitetural sufixoArquitetural;
    
    private void limpaAtributosInstancia() {
        packageName = null;
        // importName = null;
        classOrInterfaceName = null;
        pacoteArquitetural = null;
        sufixoArquitetural = null;
    }
    
    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == PACKAGE_DEF) {
            limpaAtributosInstancia();
            packageName = fullyQualifiedPackage(ast);
            System.out.println(packageName);
            pacoteArquitetural = getPacoteArquitetural(packageName);
            System.out.println(pacoteArquitetural.getNomePacote());
        }
        
        // if (ast.getType() == TokenTypes.IMPORT || ast.getType() == TokenTypes.STATIC_IMPORT) {
        // importName = fullyQualifiedPackage(ast);
        // }
        if (ast.getType() == CLASS_DEF || ast.getType() == INTERFACE_DEF) {
            DetailAST directChild = ast.getFirstChild();
            while (directChild != null && directChild.getType() != IDENT) {
                directChild = directChild.getNextSibling();
            }
            
            // DetailAST modifiers = findFirstAstOfType(ast, MODIFIERS);
            // DetailAST literalPublic = findFirstAstOfType(modifiers, LITERAL_PUBLIC);
            // DetailAST literalAbstract = findFirstAstOfType(modifiers, ABSTRACT);
            //
            // if (literalPublic != null && literalAbstract == null) {
            // containsAnnotation(ast, AnotacaoArquitetural.ManagedBean.name());
            // }
            // }
            if (directChild != null && directChild.getType() == IDENT) {
                classOrInterfaceName = directChild.getText();
            }
            System.out.println(classOrInterfaceName);
            sufixoArquitetural = getSufixoArquitetural(classOrInterfaceName);
            
            System.out.println(sufixoArquitetural);
        }
        
        if (sufixoArquitetural != null && pacoteArquitetural != null) {
            if (!tiposArquiteturais.contains(new TipoArquitetural(sufixoArquitetural, pacoteArquitetural))) {
                System.out.println("não está conforme a arquitetura");
            } else {
                System.out.println("está conforme a arquitetura");
            }
        }
        
        // if (packageName.endsWith(PacoteArquitetural.ViewController.getNomePacote())
        // && classOrInterfaceName.endsWith(SufixoArquitetural.Controller.name())) {
        // System.out.println("é um controller");
        // }
        
        // DetailAST objBlock = ast.findFirstToken(OBJBLOCK);
        // int methodDefs = objBlock.getChildCount(METHOD_DEF);
        // log(ast.getLineNo(), MSG_KEY, max);
    }
    
    public static SufixoArquitetural getSufixoArquitetural(String nomeClasseOuInterface) {
        SufixoArquitetural[] sufixos = SufixoArquitetural.values();
        int i;
        for (i = 0; i < sufixos.length; i++) {
            if (nomeClasseOuInterface.endsWith(sufixos[i].name())) {
                break;
            }
        }
        return i < sufixos.length ? sufixos[i] : null;
    }
    
    public static PacoteArquitetural getPacoteArquitetural(String nomePacote) {
        PacoteArquitetural[] pacotes = PacoteArquitetural.values();
        int i;
        for (i = 0; i < pacotes.length; i++) {
            if (nomePacote.endsWith(pacotes[i].getNomePacote())) {
                break;
            }
        }
        return i < pacotes.length ? pacotes[i] : null;
    }
    
}

enum PacoteArquitetural {
    Api("api"), ApiExcecaoMapper("api.excecao.mapper"), Dto("dto"), DtoMapper("dto.mapper"), Excecao(
            "excecao"), Mensageria("mensageria"), ModelBusiness("model.business"), ModelBusinessFacade(
                    "model.business.facade"), ModelPersistenceDao("model.persistence.dao"), ModelPersistenceEntity(
                            "model.persistence.entity"), ModelPersistenceEntityListener(
                                    "model.persistence.entity.listener"), ModelPersistenceEntityValidator(
                                            "model.persistence.entity.validator"), ModelPersistenceEntityValidatorAnnotation(
                                                    "model.persistence.entity.validator.annotation"), Utils(
                                                            "utils"), ViewController("view.controller"), ViewConverter(
                                                                    "view.converter"), ViewValidator("view.validator");
    
    private String nomePacote;
    
    PacoteArquitetural(String nome) {
        this.nomePacote = nome;
    }
    
    public String getNomePacote() {
        return nomePacote;
    }
    
}

enum AnotacaoArquitetural {
    ManagedBean, ViewScoped;
}

enum InterfaceArquitetural {
    IEntidade, IDto;
}

enum SufixoArquitetural {
    Dto, Controller, Api;
}

class TipoArquitetural {
    private SufixoArquitetural sufixo;
    private PacoteArquitetural pacote;
    // private AnotacaoArquitetural[] anotacoes;
    // private Collection<InterfaceArquitetural> interfaces;
    //
    // public TipoArquitetural(SufixoArquitetural sufixo, PacoteArquitetural pacote, AnotacaoArquitetural[] anotacoes,
    // Collection<InterfaceArquitetural> interfaces) {
    // this(sufixo, pacote);
    // this.anotacoes = anotacoes;
    // this.interfaces = interfaces;
    // }
    //
    // public void setAnotacoes(AnotacaoArquitetural... anotacoes) {
    // this.anotacoes = anotacoes;
    // }
    
    public TipoArquitetural(SufixoArquitetural controller, PacoteArquitetural viewcontroller) {
        this.sufixo = controller;
        this.pacote = viewcontroller;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pacote == null) ? 0 : pacote.hashCode());
        result = prime * result + ((sufixo == null) ? 0 : sufixo.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TipoArquitetural other = (TipoArquitetural) obj;
        if (pacote != other.pacote)
            return false;
        if (sufixo != other.sufixo)
            return false;
        return true;
    }
    
}
