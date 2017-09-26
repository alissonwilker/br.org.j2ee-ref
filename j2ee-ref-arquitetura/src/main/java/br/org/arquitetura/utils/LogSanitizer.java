package br.org.arquitetura.utils;

public class LogSanitizer {
    /**
     * Limpa o conteudo de uma mensagem de log para evitar ataques com uso do log.
     * 
     * @param logMessage
     *            A mensagem a ser limpa.
     * @return A mensagem limpa de caracteres que podem ser usados para ataque.
     */
    public static String sanitize(String logMessage) {
        String sanitizedLogMessage = null;
        if (logMessage != null) {
            sanitizedLogMessage = logMessage.replace('\n', '_').replace('\r', '_');
            if (!logMessage.equals(sanitizedLogMessage)) {
                sanitizedLogMessage += " (Sanitized log message)";
            }
        }
        return sanitizedLogMessage;
    }
}
