package exceptions;

import com.google.gson.JsonObject;
import javax.servlet.ServletContext;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InternalServerErrorExceptionMapper implements ExceptionMapper<InternalServerErrorException> {
    
    @Context
    ServletContext context;
    @Override
    public Response toResponse(InternalServerErrorException e) {
        JsonObject jo = new JsonObject();
        
        if (Boolean.valueOf(context.getInitParameter("debug"))) {
            jo.addProperty("StackTrace", e.getStackTrace().toString());
        }
        
        jo.addProperty("code", 500);
        jo.addProperty("message", "Internal server Error, we are very sorry for the inconvenience");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}