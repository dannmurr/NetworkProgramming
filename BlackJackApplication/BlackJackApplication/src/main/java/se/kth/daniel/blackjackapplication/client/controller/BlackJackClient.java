
package se.kth.daniel.blackjackapplication.client.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import se.kth.daniel.blackjackapplication.server.controller.TableController;
import se.kth.daniel.blackjackapplication.server.model.TableState;


public class BlackJackClient {

    public TableController controller;

    public BlackJackClient() {

    }

    public static TableState newPlayer(String player) {
        TableState table = callRest("http://localhost:8080/BlackJackApplication/webresources/blackJack/newgame/" + player);
        return table;
    }

    public static TableState deal(String player) {
        TableState table = callRest("http://localhost:8080/BlackJackApplication/webresources/blackJack/deal/" + player);
        return table;
    }

    public static TableState hit(String player) {
        TableState table = callRest("http://localhost:8080/BlackJackApplication/webresources/blackJack/hit/" + player);
        return table;
    }

    public static TableState stand(String player) {
        TableState table = callRest("http://localhost:8080/BlackJackApplication/webresources/blackJack/stand/" + player);
        return table;
    }

    private static TableState callRest(String Url) {
        Client client = Client.create();
        TableState table = new TableState();
        WebResource webResource = client.resource(Url);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);

        if (response.getStatus() != 200) {
            System.out.println("error");
            table.setErrorMessage("Communication error status: " + response.getStatus());
            table.setSuccess(false);
        } else {
            table = response.getEntity(TableState.class);
        }

        return table;

    }

}
