package rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exceptions.QuoteNotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import facade.Facade;
import java.util.AbstractMap;

@Path("quotes")
public class RestAPI {

    @Context
    private UriInfo context;
    
    Gson gson;
    
    /**
     * Creates a new instance of RestService
     */
    public RestAPI() {
        gson = new GsonBuilder().
                setPrettyPrinting().
                setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).
                create();
    }

    
    /**
     * Returns a quote.
     * 
     * @param id of the quote to return
     * @return 
     */
    @GET
    @Path("{id: [0-9]}")
    @Produces("application/json")
    public Response getQuote(@PathParam("id") int id) throws QuoteNotFoundException {

        JsonObject obj = new JsonObject();
        obj.addProperty("quote", Facade.getQuote(id));
        
        return Response.status(Response.Status.OK).entity(obj.toString()).build();
    }
    
    /**
     * Returns a random quote.
     * 
     * @param id of the quote to return
     * @return 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandomQuote() throws QuoteNotFoundException {
        
        JsonObject obj = new JsonObject();
        obj.addProperty("quote", Facade.getRandomQuote());
        
        return Response
                .status(Response.Status.OK)
                .entity(obj.toString())
                .build();
    }
    
    
    /**
     * Adds a new quote.
     * 
     * @param quote
     * @return 
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createQuote(String quote) {
        
        AbstractMap.SimpleEntry<Integer, String> newQuote = Facade.createQuote(getQuoteFromString(quote));
        
        JsonObject obj = new JsonObject();
        obj.addProperty("id", newQuote.getKey());
        obj.addProperty("quote", newQuote.getValue());
        
        return Response
                .status(Response.Status.CREATED)
                .entity(obj.toString())
                .build();
    }
    
    
    /**
     * 
     * 
     * @param id
     * @return 
     */
    @DELETE
    @Path("{id: [0-9]}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteQuote(@PathParam("id") int id) throws QuoteNotFoundException {
        
        String quote = Facade.deleteQuote(id);
        JsonObject obj = new JsonObject();
        obj.addProperty("quote", quote);
        
        return Response
                .status(Response.Status.OK)
                .entity(obj.toString())
                .build();
    }
    
    
    @PUT
    @Path("{id: [0-9]}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateQuote(@PathParam("id") int id, String quote) throws QuoteNotFoundException {

        AbstractMap.SimpleEntry<Integer, String> newQuote = Facade.updateQuote(id, getQuoteFromString(quote).toString());
        
        JsonObject obj = new JsonObject();
        obj.addProperty("id", newQuote.getKey());
        obj.addProperty("quote", newQuote.getValue());
        
        return Response
                .status(Response.Status.OK)
                .entity(obj.toString())
                .build();
    }
    
    
    
    private String getQuoteFromString(String str) {
        
        JsonElement je = new JsonParser().parse(str);
        String res = je.getAsJsonObject().get("quote").getAsString();
        
        return res;
    }
}
