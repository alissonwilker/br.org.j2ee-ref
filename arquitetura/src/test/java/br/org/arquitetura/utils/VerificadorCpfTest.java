package br.org.arquitetura.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.org.arquitetura.utils.VerificadorCpf;

/**
 * Classe de teste que testa o comportamento de
 * {@link br.org.arquitetura.utils.VerificadorCpf VerificadorCpf}.
 *
 */
public class VerificadorCpfTest {

	/**
	 * Testa com um CPF válido.
	 * 
	 * Test method for
	 * {@link br.org.arquitetura.utils.VerificadorCpf#isValido(java.lang.String)}.
	 */
	@Test
	public void testIsValidoCpfOk() {
		assertTrue(VerificadorCpf.isValido("05374909495"));
	}

	/**
	 * Testa com um CPF válido, porém formatado com pontos e traços.
	 * 
	 * Test method for
	 * {@link br.org.arquitetura.utils.VerificadorCpf#isValido(java.lang.String)}.
	 */
	@Test
	public void testIsValidoCpfFormatado() {
		assertFalse(VerificadorCpf.isValido("053.749.094-95"));
	}

	/**
	 * Testa com um CPF válido, porém faltando zeros a esquerda.
	 * 
	 * Test method for
	 * {@link br.org.arquitetura.utils.VerificadorCpf#isValido(java.lang.String)}.
	 */
	@Test
	public void testIsValidoCpfFaltandoZeros() {
		assertFalse(VerificadorCpf.isValido("5374909495"));
	}

	/**
	 * Testa com CPF vazio ou nulo.
	 * 
	 * Test method for
	 * {@link br.org.arquitetura.utils.VerificadorCpf#isValido(java.lang.String)}.
	 */
	@Test
	public void testIsValidoCpfVazioOuNulo() {
		assertFalse(VerificadorCpf.isValido("           "));
		assertFalse(VerificadorCpf.isValido(""));
		assertFalse(VerificadorCpf.isValido(null));
	}

	/**
	 * Testa com CPF com dígito verificador errado.
	 * 
	 * Test method for
	 * {@link br.org.arquitetura.utils.VerificadorCpf#isValido(java.lang.String)}.
	 */
	@Test
	public void testIsValidoCpfDigitoVerificadorErrado() {
		assertFalse(VerificadorCpf.isValido("05374909494"));
	}

	/**
	 * Testa com CPF com letras e números.
	 * 
	 * Test method for
	 * {@link br.org.arquitetura.utils.VerificadorCpf#isValido(java.lang.String)}.
	 */
	@Test
	public void testIsValidoCpfLetrasNumeros() {
		assertFalse(VerificadorCpf.isValido("053ABCDEF95"));
	}

	/**
	 * Testa com CPF padrão.
	 * 
	 * Test method for
	 * {@link br.org.arquitetura.utils.VerificadorCpf#isValido(java.lang.String)}.
	 */
	@Test
	public void testIsValidoCpfPadrao() {
		assertFalse(VerificadorCpf.isValido("00000000000"));
		assertFalse(VerificadorCpf.isValido("11111111111"));
		assertFalse(VerificadorCpf.isValido("22222222222"));
		assertFalse(VerificadorCpf.isValido("33333333333"));
		assertFalse(VerificadorCpf.isValido("44444444444"));
		assertFalse(VerificadorCpf.isValido("55555555555"));
		assertFalse(VerificadorCpf.isValido("66666666666"));
		assertFalse(VerificadorCpf.isValido("77777777777"));
		assertFalse(VerificadorCpf.isValido("88888888888"));
		assertFalse(VerificadorCpf.isValido("99999999999"));
	}
}
