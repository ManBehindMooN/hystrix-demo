package com.trivadis.demo.hystrix;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception-Mapper
 */
@Provider
public class RuntimeExceptionMapper implements ExceptionMapper<RuntimeException> {

	private static final Logger LOG = LoggerFactory.getLogger(RuntimeExceptionMapper.class);

	@Override
	public Response toResponse(RuntimeException exception) {
		LOG.error("uncatched RuntimeExcpetion occurred and mapped to INTERNAL_SERVER_ERROR", exception);
		return responseForInternalServerError(exception.getCause().getMessage());
	}

	public static Response responseForInternalServerError(String message) {
		Response.Status responseStatus = Response.Status.INTERNAL_SERVER_ERROR;

		ServerError serverError = new ServerError();
		serverError.httpStatusCode = responseStatus.getStatusCode();
		serverError.code = "INTERNAL_SERVER_ERROR";
		serverError.logMessage = message;

		return Response.status(responseStatus).entity(serverError).type(MediaType.APPLICATION_JSON).build();
	}

}
