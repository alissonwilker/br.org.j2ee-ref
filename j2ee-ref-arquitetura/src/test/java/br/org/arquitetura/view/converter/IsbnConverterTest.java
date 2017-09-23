package br.org.arquitetura.view.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import br.org.arquitetura.view.converter.IsbnConverter;

/**
 * Classe de teste que testa o comportamento de
 * {@link br.org.arquitetura.view.converter.IsbnConverter IsbnConverter}.
 *
 */
public class IsbnConverterTest {
    private IsbnConverter isbnConverter = new IsbnConverter();

    /**
     * Testa recuperar não formatado com ISBN válido.
     * 
     * Test method for
     * {@link br.org.arquitetura.view.converter.IsbnConverter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)}.
     */
    @Test
    public void testGetAsObjectIsbnOk() {
        assertEquals("9782423423420", isbnConverter.getAsObject(null, null, "978-242342342-0"));
        assertEquals("9792423423429", isbnConverter.getAsObject(null, null, "979-242342342-9"));
    }

    /**
     * Testa recuperar não formatado com ISBN nulo ou vazio.
     * 
     * Test method for
     * {@link br.org.arquitetura.view.converter.IsbnConverter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)}.
     */
    @Test
    public void testGetAsObjectIsbnVazioOuNulo() {
        assertEquals("", isbnConverter.getAsObject(null, null, ""));
        assertNull(isbnConverter.getAsObject(null, null, null));
    }

    /**
     * Testa recuperar formatado com ISBN nulo ou vazio.
     * 
     * Test method for
     * {@link br.org.arquitetura.view.converter.IsbnConverter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)}.
     */
    @Test
    public void testGetAsString() {
        assertEquals("", isbnConverter.getAsString(null, null, ""));
        assertEquals("null", isbnConverter.getAsString(null, null, null));
    }

    /**
     * Testa recuperar formatado com ISBN válido.
     * 
     * Test method for
     * {@link br.org.arquitetura.view.converter.IsbnConverter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)}.
     */
    @Test
    public void testGetAsStringIsbnOk() {
        assertEquals("978-242342342-0", isbnConverter.getAsString(null, null, "9782423423420"));
        assertEquals("979-242342342-9", isbnConverter.getAsString(null, null, "9792423423429"));
    }

}
