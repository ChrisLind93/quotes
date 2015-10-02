package exceptions;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Context
    ServletContext context;
    @Override
    public Response toResponse(Throwable e) {
        
        JsonObject jo = new JsonObject();
        
        if (Boolean.valueOf(context.getInitParameter("debug"))) {
            
            String err = "";
            
            StackTraceElement[] stack = e.getStackTrace();
            
            for (StackTraceElement elm : stack) {
                err += elm.toString();
            }
            
            jo.addProperty("stackTrace", err);
        }
        
        jo.addProperty("code", 500);
        jo.addProperty("message", "An unknown error occured, check the logfiles");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(jo.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}
