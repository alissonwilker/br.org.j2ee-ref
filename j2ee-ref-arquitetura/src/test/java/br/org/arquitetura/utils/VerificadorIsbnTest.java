package br.org.arquitetura.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.org.arquitetura.utils.VerificadorIsbn;

/**
 * Classe de teste que testa o comportamento de
 * {@link br.org.arquitetura.utils.VerificadorIsbn VerificadorIsbn}.
 *
 */
public class VerificadorIsbnTest {

    /**
     * Testa com um ISBN válido.
     * 
     * Test method for
     * {@link br.org.arquitetura.utils.VerificadorIsbn#isValido(java.lang.String)}.
     */
    @Test
    public void testIsValidoIsbnOk() {
        assertTrue(VerificadorIsbn.isValido("9782423423420"));
        assertTrue(VerificadorIsbn.isValido("9792423423429"));
    }

    /**
     * Testa com um ISBN válido, porém formatado com pontos ou traços.
     * 
     * Test method for
     * {@link br.org.arquitetura.utils.VerificadorIsbn#isValido(java.lang.String)}.
     */
    @Test
    public void testIsValidoIsbnFormatado() {
        assertFalse(VerificadorIsbn.isValido("978-242342342-0"));
    }

    /**
     * Testa com ISBN vazio ou nulo.
     * 
     * Test method for
     * {@link br.org.arquitetura.utils.VerificadorIsbn#isValido(java.lang.String)}.
     */
    @Test
    public void testIsValidoIsbnVazioOuNulo() {
        assertFalse(VerificadorIsbn.isValido("           "));
        assertFalse(VerificadorIsbn.isValido(""));
        assertFalse(VerificadorIsbn.isValido(null));
    }

    /**
     * Testa com ISBN com dígito verificador errado.
     * 
     * Test method for
     * {@link br.org.arquitetura.utils.VerificadorIsbn#isValido(java.lang.String)}.
     */
    @Test
    public void testIsValidoIsbnDigitoVerificadorErrado() {
        assertFalse(VerificadorIsbn.isValido("9782423423421"));
    }

    /**
     * Testa com ISBN com letras e números.
     * 
     * Test method for
     * {@link br.org.arquitetura.utils.VerificadorIsbn#isValido(java.lang.String)}.
     */
    @Test
    public void testIsValidoIsbnLetrasNumeros() {
        assertFalse(VerificadorIsbn.isValido("978ABCDEFGHI1"));
    }
}