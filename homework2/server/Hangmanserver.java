/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.homework2.server;

/**
 *
 * @author Danie
 */
import java.util.Random;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Hangmanserver {

    private int portNo = 8080;
    private Selector selector;
    private ServerSocketChannel listeningSocketChannel;

    public static final Random rand = new Random();
    private static final int LINGER_TIME = 5000;
    private static final int TIMEOUT_HALF_HOUR = 1800000;
    public int nOfClients = 0;
    public ArrayList<String> wordlist;

    public static void main(String[] args) throws IOException, InterruptedException {

        Hangmanserver server = new Hangmanserver();
        server.makelist();

        server.parseArguments(args);
        server.serve();
    }

    private void serve() throws InterruptedException {
        try {
            initialize();
            while (true) {

                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if (key.isAcceptable()) {
                        startHandler(key);
                    } else if (key.isReadable()) {
                        recvFromClient(key);
                    } else if (key.isWritable()) {
                        sendToClient(key);
                    }
                }

            }
        } catch (IOException e) {
            System.err.println("Server failure.");
        }
    }

    //starts to read a message from the client
    private void recvFromClient(SelectionKey key) throws IOException, InterruptedException {
        ClientHandler handler = (ClientHandler) key.attachment();

        handler.recvMsg();
        key.interestOps(SelectionKey.OP_WRITE);

    }
    
    //starts to send a message to the client
    private void sendToClient(SelectionKey key) throws IOException, InterruptedException {
        ClientHandler client = (ClientHandler) key.attachment();
        client.sendAll();
        key.interestOps(SelectionKey.OP_READ);
    }

    //creates a handler and configures its socketschannels
    private void startHandler(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverSocketChannel.accept();
        clientChannel.configureBlocking(false);
        ClientHandler handler = new ClientHandler(this, clientChannel);
        clientChannel.register(selector, SelectionKey.OP_WRITE, handler);
        clientChannel.setOption(StandardSocketOptions.SO_LINGER, LINGER_TIME);
    }

    
    //parses arguments
    private void parseArguments(String[] arguments) {
        if (arguments.length > 0) {
            portNo = Integer.parseInt(arguments[0]);
        }
    }

    //initializes selector and socketchannels
    private void initialize() throws IOException {
        selector = Selector.open();

        listeningSocketChannel = ServerSocketChannel.open();
        listeningSocketChannel.configureBlocking(false);
        listeningSocketChannel.bind(new InetSocketAddress(portNo));
        listeningSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    
    //generates a random word from the list of words
    public String randomWord() {
        return wordlist.get(rand.nextInt(51528));
    }

    //creates the list of words
    private void makelist() throws FileNotFoundException, IOException {
        Scanner in = new Scanner(new FileReader("C:\\Users\\Danie\\OneDrive\\Documents\\NetBeansProjects\\Homework1\\src\\main\\java\\se\\kth\\homework1\\server\\words.txt"));
        wordlist = new ArrayList<String>();
        while (in.hasNext()) {
            wordlist.add(in.next());
        }
        in.close();
    }

}
