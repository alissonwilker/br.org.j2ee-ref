package br.org.arquitetura.model.persistence.entity.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.org.arquitetura.model.persistence.entity.validator.annotation.Isbn;
import br.org.arquitetura.utils.VerificadorIsbn;

/**
 * Validador de ISBN para Beans.
 *
 * @see javax.validation.ConstraintValidator
 */
public class IsbnBeanValidator implements ConstraintValidator<Isbn, String> {

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintContext) {
        return VerificadorIsbn.isValido(isbn);
    }

    @Override
    public void initialize(Isbn constraintAnnotation) {
    }
}
