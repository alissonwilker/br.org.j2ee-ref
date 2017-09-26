package br.org.arquitetura.utils;

public class LogSanitizer {
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
