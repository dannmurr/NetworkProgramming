
package se.kth.daniel.blackjackapplication.server.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import se.kth.daniel.blackjackapplication.server.controller.TableController;
import se.kth.daniel.blackjackapplication.server.model.TableState;

@Path("blackJack")
public class BlackJackRest {

    @Context
    private UriInfo context;
    public BlackJackRest() {
    }


    @Path("newgame/{playerName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TableState newgame(@PathParam("playerName") String playerName) {

        TableController tbc = new TableController();

        TableState table = tbc.startPlayer(playerName);

        return table;
    }

    @Path("deal/{playerName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TableState deal(@PathParam("playerName") String playerName) {
        TableController tbc = new TableController();

        TableState table = tbc.deal(playerName);
        return table;
    }

    @Path("hit/{playerName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TableState hit(@PathParam("playerName") String playerName) {
        TableController tbc = new TableController();

        TableState table = tbc.hit(playerName);
        return table;
    }

    @Path("stand/{playerName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TableState stand(@PathParam("playerName") String playerName) {
        TableController tbc = new TableController();
        
        TableState table = tbc.stand(playerName);
        return table;
    }

}
