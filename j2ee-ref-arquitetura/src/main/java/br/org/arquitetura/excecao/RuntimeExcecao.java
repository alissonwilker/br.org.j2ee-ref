package br.org.arquitetura.excecao;

/**
 * Classe abstrata que representa uma exceção genérica do sistema.
 *
 * @see java.lang.Exception
 */
public class RuntimeExcecao extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Throwable causaRaiz;

    public RuntimeExcecao() {
    }

    public RuntimeExcecao(Throwable eeex) {
        this.causaRaiz = eeex;
    }

    public Throwable getCausaRaiz() {
        return causaRaiz;
    }
}
