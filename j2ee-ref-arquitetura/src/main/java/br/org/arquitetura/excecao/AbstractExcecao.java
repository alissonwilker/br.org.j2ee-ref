package br.org.arquitetura.excecao;

/**
 * Classe abstrata que representa uma exceção genérica do sistema.
 *
 * @see java.lang.Exception
 */
public abstract class AbstractExcecao extends Exception {

    private static final long serialVersionUID = 1L;

    private Throwable causaRaiz;

    public AbstractExcecao() {
    }

    public AbstractExcecao(Throwable eeex) {
        this.causaRaiz = eeex;
    }

    public Throwable getCausaRaiz() {
        return causaRaiz;
    }
}
