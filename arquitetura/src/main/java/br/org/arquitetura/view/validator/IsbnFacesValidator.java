package br.org.arquitetura.view.validator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.org.arquitetura.utils.VerificadorIsbn;
import br.org.arquitetura.view.utils.FacesMessageUtils;

/**
 * Validador de ISBN utilizado pelo JSF.
 *
 * @see javax.faces.validator.Validator
 */
@FacesValidator(value = "isbnValidator")
public class IsbnFacesValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object isbn) throws ValidatorException {
		if (isbn == null) {
			return;
		}
		
		if (!VerificadorIsbn.isValido((String)isbn)) {
			throw new ValidatorException(FacesMessageUtils.getInfoFacesMessage("livros.isbnValidator"));
		}
	}

}