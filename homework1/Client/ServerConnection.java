/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.homework1.Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Danie
 */
public class ServerConnection {

    public Socket socket;
    private static final int LINGER_TIME = 5000;
    private static final int TIMEOUT_HALF_HOUR = 1800000;
    private static final int TIMEOUT_HALF_MINUTE = 30000;
    boolean connected = false;
    private ClientController contr;
    public BufferedWriter hangmanWriter;
    public BufferedReader hangmanReader;

    public void connect(String host, int port) throws
            IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), TIMEOUT_HALF_MINUTE);
        socket.setSoTimeout(TIMEOUT_HALF_HOUR);
        contr = new ClientController(this);
        System.out.println("client connected to server");

        hangmanWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        hangmanReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(new Listener()).start();

    }

    public static void main(String[] args) {
        ServerConnection hc = new ServerConnection();

        try {
            hc.connect("localhost", 8080);
            hc.contr.serverCommunication();
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void serverListen() throws IOException {
        System.out.println(hangmanReader.readLine());

        System.out.println(hangmanReader.readLine());
        printSpaces();
        System.out.println(hangmanReader.readLine());

    }

    public void printSpaces() {
        for (int i = 0; i < 4; i++) {
            System.out.println();
        }
    }

    private class Listener implements Runnable {

        @Override
        public void run() {
            try {
                for (;;) {
                    serverListen();
                }
            } catch (Throwable connectionFailure) {

            }
        }

    }
}
