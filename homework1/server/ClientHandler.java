/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.homework1.server;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Danie
 */
public class ClientHandler implements Runnable {

    private final Hangmanserver server;
    private final Socket clientSocket;
    private BufferedReader serverReader;
    private BufferedWriter serverWrite;
    private final boolean connected;
    private ServerController game;
    private int clientnumber;

    ClientHandler(Hangmanserver server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        connected = true;
        server.nOfClients++;
        this.clientnumber = server.nOfClients;
    }

    @Override
    public void run() {

        System.out.println("client" + clientnumber + " connected");

        try {
            game = new ServerController(server.randomWord(), 0);

            serverReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            serverWrite = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            talk("Hello and Welcome to this Hangman Game!", "Please enter a letter, the whole word or type quit to exit");
        
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        } 
        boolean playing = true;
        
        
        while (playing) {
            try {
                playing = playgame();
            } catch (IOException ex) {
                //Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                playing = false;
            }
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void checkLetter(String msg) throws IOException {
        game.checkForLetter(msg);

        serverWrite.write(game.printFoundLetter() + "\n");
        serverWrite.flush();

    }

    public boolean playgame() throws IOException {

        String msg = serverReader.readLine();

        if (msg == null) {
            System.out.println("client" + clientnumber+   " dissconnected");
            return false;
        }
        System.out.println("client" + clientnumber + ": " + msg);
        if (msg.length() > 1) {
            if (msg.equals("quit")) {
                return false;
            } else if (msg.equals(game.word)) {
                letterhit(true);
            } else {
                lettermiss();
            }
            return true;
        }

        boolean found = game.checkForLetter(msg);
        if (found) {
            letterhit(false);

        } else {
            game.incorrectLetters.add(msg);
            lettermiss();
        }
        return true;
    }

    public void talk(String a, String b) throws IOException {
        serverWrite.write(a + "\n");
        serverWrite.flush();
        serverWrite.write(b + "\n");
        serverWrite.flush();
        serverWrite.write("Word: " + game.printFoundLetter() + "Incorrect attempts: " + game.incorrectLetters + "\n");
        serverWrite.flush();

    }

    public void letterhit(boolean win) throws IOException {

        if (!win) {
            win = game.checkForWin();
        }
        if (win) {
            game.score++;
            String oldword = game.word;
            game = new ServerController(server.randomWord(), game.score);
            talk("CORRECT the word was: " + oldword + "!!GAME WON!!" + "(Score " + game.score + ")", "Lets play again! Enter another letter, or the whole word");
        } else {
            talk("CORRECT attempts left: " + game.attemptsLeft + "(Score " + game.score + ")", "Please enter another letter, or the whole word");
        }
    }

    public void lettermiss() throws IOException {
        game.attemptsLeft--;
        if (game.attemptsLeft <= 0) {
            game.score--;
            String oldword = game.word;
            game = new ServerController(server.randomWord(), game.score);
            talk("Incorrect, YOU LOST. The word was: " + oldword + "(Score " + game.score + ")", "Lets play again! Enter another letter, or the whole word");
        } else {
            talk("Incorrect, Attempts left: " + game.attemptsLeft + "(Score " + game.score + ")", "Please enter another letter, the whole word or type quit to exit");
        }
    }

}
