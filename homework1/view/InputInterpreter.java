/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.homework1.view;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.kth.homework1.Client.ClientController;

/**
 *
 * @author Danie
 */
public class InputInterpreter implements Runnable {

    private final Scanner scanner = new Scanner(System.in);
    private boolean receivingCmds = false;
    private ClientController contr;
    private boolean running = true;
    private String message;

    public void start() {
        if (receivingCmds) {
            return;
        }
        receivingCmds = true;
        //  contr = new ClientController();
        new Thread(this).start();
    }

    public InputInterpreter(ClientController contr) {
        this.contr = contr;
    }

    //getting input from the user
    private String getInput() {
        // input tools
        scanner.useLocale(Locale.US);
        String input = scanner.nextLine();
        return input + "\n";
    }

    @Override
    public void run() {
        while (running) {
            message = getInput();
            
            
            if (message.equals("quit" + "\n")) {
                running = false;
                contr.endConnection();
                message = null;
            } else {
                try {
                    contr.sendMessage(message);
                } catch (IOException ex) {
                    Logger.getLogger(InputInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        System.out.println("Thankyou for playing!");
    }

}
