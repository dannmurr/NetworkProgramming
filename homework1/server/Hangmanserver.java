/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.homework1.server;

/**
 *
 * @author Danie
 */
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class Hangmanserver {

    public static final Random rand = new Random();

    private static final int LINGER_TIME = 5000;
    private static final int TIMEOUT_HALF_HOUR = 1800000;
    public int nOfClients = 0;
    private int portNo = 8080;
    public ArrayList<String> wordlist;

    public static void main(String[] args) throws IOException {

        Hangmanserver server = new Hangmanserver();
        server.makelist();

        server.parseArguments(args);
        server.serve();
    }

    private void serve() {
        try {
            ServerSocket listeningSocket = new ServerSocket(portNo);
            while (true) {
                System.out.println("waiting for connection");
                Socket clientSocket = listeningSocket.accept();
                startHandler(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Server failure.");
        }
    }

    private void startHandler(Socket clientSocket) throws SocketException {
        clientSocket.setSoLinger(true, LINGER_TIME);
        clientSocket.setSoTimeout(TIMEOUT_HALF_HOUR);
        ClientHandler handler = new ClientHandler(this, clientSocket);

        Thread handlerThread = new Thread(handler);
        handlerThread.setPriority(Thread.MAX_PRIORITY);
        handlerThread.start();
    }

    private void parseArguments(String[] arguments) {
        if (arguments.length > 0) {
            portNo = Integer.parseInt(arguments[0]);
        }
    }

    private void makelist() throws FileNotFoundException, IOException {
        Scanner in = new Scanner(new FileReader("C:\\Users\\Danie\\OneDrive\\Documents\\NetBeansProjects\\Homework1\\src\\main\\java\\se\\kth\\homework1\\server\\words.txt"));
        wordlist = new ArrayList<String>();
        while (in.hasNext()) {
            wordlist.add(in.next());
        }
        in.close();
    }

    public String randomWord() {
        return wordlist.get(rand.nextInt(51528));
    }

}
