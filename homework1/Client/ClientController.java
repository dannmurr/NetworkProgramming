/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.homework1.Client;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.kth.homework1.view.InputInterpreter;

/**
 *
 * @author Danie
 */
public class ClientController {

    ServerConnection connection;

    //constructor for the controller
    public ClientController(ServerConnection connection) {
        this.connection = connection;
        Scanner scanner = new Scanner(System.in);
    }

    //Send a message to the server 
    public void sendMessage(String message) throws IOException {
        connection.hangmanWriter.write(message);
        connection.hangmanWriter.flush();
    }

    //The communication between the server and the client, first listen, then check if the input equals "quit", if not, send message
    public void serverCommunication() throws IOException {
        InputInterpreter input = new InputInterpreter(this);
        input.start();
        

    }

    public void endConnection() {
        try {
            connection.socket.close();
            connection.socket = null;

        } catch (IOException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
