package br.org.arquitetura.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Conversor de ISBN utilizado pelo JSF. Adiciona/remove '.' e '-' para formatar
 * o ISBN.
 *
 * @see javax.faces.convert.Converter
 */
@FacesConverter(value = "isbnConverter")
public class IsbnConverter implements Converter {
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        /*
         * Irá converter ISBN formatado para um sem traço. Ex.: 978-242342342-0 torna-se
         * 9782423423420.
         */
        String isbn = value;
        if (value != null && !value.equals(""))
            isbn = value.replaceAll("\\-", "");

        return isbn;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        /*
         * Irá converter ISBN não formatado para um com traço. Ex.: 9782423423420
         * torna-se 978-242342342-0.
         */
        String isbn = String.valueOf(value);
        if (isbn.length() == 13)
            isbn = isbn.substring(0, 3) + "-" + isbn.substring(3, 12) + "-" + isbn.charAt(12);

        return isbn;
    }
}
