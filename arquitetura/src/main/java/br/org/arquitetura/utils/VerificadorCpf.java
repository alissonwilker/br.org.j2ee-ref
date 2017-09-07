package br.org.arquitetura.utils;

/**
 * Classe que verifica se um CPF é válido.
 *
 */
public class VerificadorCpf {

	/**
	 * Verifica se um CPF é válido.
	 * 
	 * @param cpf
	 *            um CPF não formatado (apenas números).
	 * @return <i>true</i> se o CPF é válido. <i>false</i>, caso contrário.
	 */
	public static boolean isValido(String cpf) {
		if (cpf == null) {
			return false;
		}

		try {
			return (cpf.length() == 11 && !isCpfPadrao(cpf)
					&& calcularDigitoVerificador(cpf.substring(0, 9)).equals(cpf.substring(9, 11)));
		} catch (NumberFormatException nfe) {
			return false;
		}

	}

	private static boolean isCpfPadrao(String cpf) {
		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999")) {
			return true;
		}

		return false;
	}

	// Créditos ao JavaFree.org pelo algoritmo de validação de CPF
	private static String calcularDigitoVerificador(String num) {

		Integer primDig, segDig;
		int soma = 0;
		int peso = 10;
		for (int i = 0; i < num.length(); i++) {
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
		}
		if (soma % 11 == 0 | soma % 11 == 1) {
			primDig = new Integer(0);
		} else {
			primDig = new Integer(11 - (soma % 11));
		}
		soma = 0;
		peso = 11;
		for (int i = 0; i < num.length(); i++) {
			soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
		}
		soma += primDig.intValue() * 2;
		if (soma % 11 == 0 | soma % 11 == 1) {
			segDig = new Integer(0);
		} else {
			segDig = new Integer(11 - (soma % 11));
		}
		return primDig.toString() + segDig.toString();
	}
}