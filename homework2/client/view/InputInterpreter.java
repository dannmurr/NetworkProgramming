
package se.kth.homework2.client.view;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.kth.homework2.client.net.ServerConnection;

/**
 *
 * @author Danie
 */
public class InputInterpreter implements Runnable {

    private final Scanner scanner = new Scanner(System.in);
    private boolean receivingCmds = false;
    private boolean running = true;
    private String message;
    private ServerConnection server;

    public InputInterpreter() {
    }

    //starts the interpriter
    public void start() throws IOException {
        if (receivingCmds) {
            return;
        }
        receivingCmds = true;
        server = new ServerConnection();
        server.connect("localhost", 8080); //hardcoded host and port for this exercise, should be parameterized
        new Thread(this).start();
    }

    //getting input from the user
    private String getInput() {
        // input tools
        scanner.useLocale(Locale.US);
        String input = scanner.nextLine();
        return input;
    }

    
    //starts the run method on the new thread
    @Override
    public void run() {
        while (running) {
            message = getInput();
            server.sendmessage(message);
            if (message.equals("quit")) {
                running = false;
                message = null;
            } 
        }
        System.out.println("Thankyou for playing!");
    }



}
