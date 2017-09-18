package br.org.arquitetura.api.excecao.mapper;

import java.lang.invoke.MethodHandles;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ThrowableMapper implements ExceptionMapper<Throwable> {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public Response toResponse(Throwable th) {
        logger.error(th.getMessage(), th);
        return Response.serverError().build();
    }

}
