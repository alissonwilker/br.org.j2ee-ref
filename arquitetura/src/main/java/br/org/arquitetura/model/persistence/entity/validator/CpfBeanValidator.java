package br.org.arquitetura.model.persistence.entity.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.org.arquitetura.model.persistence.entity.validator.annotation.Cpf;
import br.org.arquitetura.utils.VerificadorCpf;

/**
 * Validador de CPF para Beans.
 *
 * @see javax.validation.ConstraintValidator
 */
public class CpfBeanValidator implements ConstraintValidator<Cpf, String> {

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext constraintContext) {
        return VerificadorCpf.isValido(cpf);
    }

    @Override
    public void initialize(Cpf constraintAnnotation) {
    }
}
