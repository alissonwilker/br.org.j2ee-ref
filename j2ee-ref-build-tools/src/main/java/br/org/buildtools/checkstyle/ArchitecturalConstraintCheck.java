package br.org.buildtools.checkstyle;

import static com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IMPORT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.INTERFACE_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.PACKAGE_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.STATIC_IMPORT;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ArchitecturalConstraintCheck extends CustomCheck {
    
    // public static final String MSG_KEY = "checkstyle.methodlimit";
    
    private Collection<TipoArquitetural> tiposArquiteturais = new HashSet<TipoArquitetural>();
    
    private String packageName;
    // private String importName;
    private String classOrInterfaceName;
    
    private PacoteArquitetural pacoteArquitetural;
    
    private SufixoArquitetural sufixoArquitetural;
    
    private Collection<AnotacaoArquitetural> anotacoesBlab;
    
    private TipoArquiteturalAbstrato pai;
    
    public ArchitecturalConstraintCheck() {
        TipoArquitetural viewController = new TipoArquitetural(SufixoArquitetural.Controller,
                PacoteArquitetural.ViewController, TipoArquiteturalAbstrato.AbstractController);
        Collection<AnotacaoArquitetural> anotacoesArquiteturais = new HashSet<AnotacaoArquitetural>();
        anotacoesArquiteturais.add(AnotacaoArquitetural.ManagedBean);
        anotacoesArquiteturais.add(AnotacaoArquitetural.ViewScoped);
        viewController.setAnotacoes(anotacoesArquiteturais);
        
        // TipoArquitetural viewController = new TipoArquitetural(SufixoArquitetural.Controller,
        // PacoteArquitetural.ViewController, TipoArquiteturalAbstrato.AbstractController,
        // AnotacaoArquitetural.ManagedBean, AnotacaoArquitetural.ViewScoped);
        
        TipoArquitetural api = new TipoArquitetural(SufixoArquitetural.Api, PacoteArquitetural.Api,
                TipoArquiteturalAbstrato.AbstractApi);
        Collection<AnotacaoArquitetural> anotacoesArquiteturais2 = new HashSet<AnotacaoArquitetural>();
//        anotacoesArquiteturais2.add(AnotacaoArquitetural.Path);
//        anotacoesArquiteturais2.add(AnotacaoArquitetural.Api);
//        api.setAnotacoes(anotacoesArquiteturais);
        
        tiposArquiteturais.add(api);
        tiposArquiteturais.add(viewController);
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
    
    private void limpaAtributosInstancia() {
        packageName = null;
        // importName = null;
        classOrInterfaceName = null;
        pacoteArquitetural = null;
        sufixoArquitetural = null;
        pai = null;
        anotacoesBlab = new HashSet<AnotacaoArquitetural>();
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
            
            DetailAST classModifiers = findFirstAstOfType(ast, TokenTypes.MODIFIERS);
            if (classModifiers != null) {
                List<DetailAST> astAnnotations = findAllAstsOfType(classModifiers, TokenTypes.IDENT);
                if (astAnnotations != null) {
                    for (DetailAST detailAST : astAnnotations) {
                        if (detailAST.getParent().getType() == TokenTypes.ANNOTATION) {
                            String anotacao = detailAST.getText();
                            for (AnotacaoArquitetural anotacaoArquitetural : AnotacaoArquitetural.values()) {
                                if (anotacaoArquitetural.name().equals(anotacao)) {
                                    anotacoesBlab.add(anotacaoArquitetural);
                                    System.out.println("@" + anotacaoArquitetural.name());
                                }
                            }
                        }
                    }
                }
            }
            
            DetailAST astExtends = findFirstAstOfType(ast, TokenTypes.EXTENDS_CLAUSE);
            if (astExtends != null) {
                String nomePai = findFirstAstOfType(astExtends, IDENT).getText();
                System.out.println(nomePai);
                
                for (TipoArquiteturalAbstrato tipoArquiteturalAbstrato : TipoArquiteturalAbstrato.values()) {
                    if (tipoArquiteturalAbstrato.name().equals(nomePai)) {
                        pai = tipoArquiteturalAbstrato;
                    }
                }
                
            }
        }
        
        if (sufixoArquitetural != null && pacoteArquitetural != null) {
            TipoArquitetural ta = new TipoArquitetural(sufixoArquitetural, pacoteArquitetural, pai);
            ta.setAnotacoes(anotacoesBlab);
            System.out.println("printing-----");
            System.out.println(sufixoArquitetural != null ? sufixoArquitetural.name() : "null");
            System.out.println(pacoteArquitetural != null ? pacoteArquitetural.getNomePacote() : "null");
            System.out.println(pai != null ? pai.name() : "null");
            for (AnotacaoArquitetural anotacaoArquitetural : anotacoesBlab) {
                System.out.println(anotacaoArquitetural != null ? anotacaoArquitetural.name() : "null");
            }
            System.out.println("------printing");
            
            System.out.println("#############");
            for (TipoArquitetural tipo : tiposArquiteturais) {
                System.out.println("um tipo");
                System.out.println(tipo.getSufixo().name() != null ? tipo.getSufixo().name() : "null");
                System.out.println(tipo.getPacote().getNomePacote() != null ? tipo.getPacote().getNomePacote() : "null");
                System.out.println(tipo.getPai().name() != null ? tipo.getPai().name() : "null");
                if (tipo.getAnotacoes() != null) {
                    for (AnotacaoArquitetural anotacaoArquitetural : tipo.getAnotacoes()) {
                        System.out.println(anotacaoArquitetural != null ? anotacaoArquitetural.name() : "null");
                    }
                }
            }
            System.out.println("#############");
            
            if (!tiposArquiteturais.contains(ta)) {
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
    ManagedBean, ViewScoped;//, Path, Api;
}

enum InterfaceArquitetural {
    IEntidade, IDto;
}

enum SufixoArquitetural {
    Dto, Controller, Api;
}

enum TipoArquiteturalAbstrato {
    AbstractController, AbstractApi;
}

class TipoArquitetural {
    private SufixoArquitetural sufixo;
    private PacoteArquitetural pacote;
    private Collection<AnotacaoArquitetural> anotacoes;
    private TipoArquiteturalAbstrato pai;
    
    // private Collection<InterfaceArquitetural> interfaces;
    //
    // public TipoArquitetural(SufixoArquitetural sufixo, PacoteArquitetural pacote, AnotacaoArquitetural[] anotacoes,
    // Collection<InterfaceArquitetural> interfaces) {
    // this(sufixo, pacote);
    // this.anotacoes = anotacoes;
    // this.interfaces = interfaces;
    // }
    //
    public void setAnotacoes(Collection<AnotacaoArquitetural> anotacoes) {
        this.anotacoes = anotacoes;
    }
    
    public TipoArquitetural(SufixoArquitetural controller, PacoteArquitetural viewcontroller,
            TipoArquiteturalAbstrato pai) {
        this.sufixo = controller;
        this.pacote = viewcontroller;
        this.pai = pai;
    }
    
//    public TipoArquitetural(SufixoArquitetural controller, PacoteArquitetural viewcontroller,
//            TipoArquiteturalAbstrato pai, AnotacaoArquitetural... anotacoes) {
//        this(controller, viewcontroller, pai);
//        setAnotacoes(Arrays.asList(anotacoes));
//    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((anotacoes == null) ? 0 : anotacoes.hashCode());
        result = prime * result + ((pacote == null) ? 0 : pacote.hashCode());
        result = prime * result + ((pai == null) ? 0 : pai.hashCode());
        result = prime * result + ((sufixo == null) ? 0 : sufixo.hashCode());
        return result;
    }
    
    public SufixoArquitetural getSufixo() {
        return sufixo;
    }

    public PacoteArquitetural getPacote() {
        return pacote;
    }

    public Collection<AnotacaoArquitetural> getAnotacoes() {
        return anotacoes;
    }

    public TipoArquiteturalAbstrato getPai() {
        return pai;
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
        if (anotacoes == null) {
            if (other.anotacoes != null)
                return false;
            System.out.println("anotacoes de ambos igual a null");
        } else if (!anotacoes.equals(other.anotacoes)) {
            System.out.println("anotacoes nao sao iguais");
            return false;
        }
        if (pacote != other.pacote)
            return false;
        if (pai != other.pai)
            return false;
        if (sufixo != other.sufixo)
            return false;
        return true;
    }
    
}
