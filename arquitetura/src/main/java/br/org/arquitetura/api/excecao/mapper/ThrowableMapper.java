package br.org.arquitetura.api.excecao.mapper;

import java.lang.invoke.MethodHandles;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.org.arquitetura.utils.LogSanitizer;

@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable> {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Response toResponse(Throwable th) {
        logger.error(LogSanitizer.sanitize(th.getMessage()), th);
        return Response.serverError().build();
    }

}
