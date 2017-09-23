package br.org.arquitetura.view.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import br.org.arquitetura.view.converter.CpfConverter;

/**
 * Classe de teste que testa o comportamento de
 * {@link br.org.arquitetura.view.converter.CpfConverter CpfConverter}.
 *
 */
public class CpfConverterTest {
    private CpfConverter cpfConverter = new CpfConverter();

    /**
     * Testa recuperar não formatado com CPF válido.
     * 
     * Test method for
     * {@link br.org.arquitetura.view.converter.CpfConverter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)}.
     */
    @Test
    public void testGetAsObjectCpfOk() {
        assertEquals("05374909495", cpfConverter.getAsObject(null, null, "053.749.094-95"));
    }

    /**
     * Testa recuperar não formatado com CPF nulo ou vazio.
     * 
     * Test method for
     * {@link br.org.arquitetura.view.converter.CpfConverter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)}.
     */
    @Test
    public void testGetAsObjectCpfVazioOuNulo() {
        assertEquals("", cpfConverter.getAsObject(null, null, ""));
        assertNull(cpfConverter.getAsObject(null, null, null));
    }

    /**
     * Testa recuperar formatado com CPF nulo ou vazio.
     * 
     * Test method for
     * {@link br.org.arquitetura.view.converter.CpfConverter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)}.
     */
    @Test
    public void testGetAsString() {
        assertEquals("", cpfConverter.getAsString(null, null, ""));
        assertEquals("null", cpfConverter.getAsString(null, null, null));
    }

    /**
     * Testa recuperar formatado com CPF válido.
     * 
     * Test method for
     * {@link br.org.arquitetura.view.converter.CpfConverter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)}.
     */
    @Test
    public void testGetAsStringCpfOk() {
        assertEquals("053.749.094-95", cpfConverter.getAsString(null, null, "05374909495"));
    }

}
