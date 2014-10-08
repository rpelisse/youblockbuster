package org.belaran.redhat.youblockbuster.ws;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException>{

	@Override
	public Response toResponse(IllegalArgumentException exception) {
		return Response.status(Status.PRECONDITION_FAILED).build();
	}

}
