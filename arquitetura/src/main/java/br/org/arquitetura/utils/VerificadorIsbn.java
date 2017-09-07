package br.org.arquitetura.utils;

/**
 * Classe que verifica se um ISBN é válido.
 *
 */
public class VerificadorIsbn {

	/**
	 * Verifica se um ISBN é válido.
	 * 
	 * @param isbn
	 *            um ISBN não formatado (apenas números).
	 * @return <i>true</i> se o ISBN é válido. <i>false</i>, caso contrário.
	 */
	public static boolean isValido(String isbn) {
		if (isbn == null) {
			return false;
		}

		try {
			return (isbn.length() == 13 && (isbn.startsWith("978") || isbn.startsWith("979"))
					&& calcularDigitoVerificador(isbn.substring(0, 12)).equals(isbn.substring(12, 13)));
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	private static String calcularDigitoVerificador(String num) {
		Integer digitoVerificador;
		int soma = 0;
		for (int i = 0; i < num.length(); i += 2) {
			soma += Integer.parseInt(num.substring(i, i + 1)) + Integer.parseInt(num.substring(i + 1, i + 2)) * 3;
		}
		digitoVerificador = (10 - soma % 10) % 10;
		return String.valueOf(digitoVerificador);
	}

}