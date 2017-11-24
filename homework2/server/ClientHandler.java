
package se.kth.homework2.server;

import java.io.IOException;


import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.kth.homework2.common.MessageSplitter;

/**
 *
 * @author Danie
 */
public final class ClientHandler implements Runnable {

    private final Hangmanserver server;
    private final SocketChannel clientChannel;
    private ServerController game;
    private int clientnumber;
    private final Queue<ByteBuffer> messagesToSend = new ArrayDeque<>();
    private ByteBuffer msgToServer = ByteBuffer.allocateDirect(8192);
    private final ByteBuffer msgFromClient = ByteBuffer.allocateDirect(8192);
    String message;
    MessageSplitter msgsplitter = new MessageSplitter();

    
    //constructor for the clienthandler
    ClientHandler(Hangmanserver server, SocketChannel clientSocket) throws IOException {
        this.server = server;
        this.clientChannel = clientSocket;
        server.nOfClients++;
        this.clientnumber = server.nOfClients;
        game = new ServerController(server.randomWord(), 0);
        talk("Hello and Welcome to this Hangman Game!", "Please enter a letter, the whole word or type quit to exit");

    }

    
    //run method for the clienthandler, this is where the data is handled
    @Override
    public void run() {

        try {
            playgame();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //checks if the letter is in the word and sends the found letters
    private void checkLetter(String msg) throws IOException {
        game.checkForLetter(msg);

        queueMsgToSend(game.printFoundLetter() + "\n");

    }

    
    //the actual game, the current message from the client will be stored in message
    public void playgame() throws IOException {
        
        if (message == null) {
            System.out.println("client" + clientnumber + " dissconnected");
            return;
        }
        System.out.println("client" + clientnumber + ": " + message + ", word: " + game.word);
        if (message.length() > 1) {
            if (message.equals("quit")) {
                clientChannel.close();
                return;
            } else if (message.equals(game.word)) {
                letterhit(true);
            } else {
                lettermiss();
            }
            return;
        }

        boolean found = game.checkForLetter(message);
        if (found) {
            letterhit(false);

        } else {
            game.incorrectLetters.add(message);
            lettermiss();
        }
        return;
    }

    
    //puts a string in the queue
    private void queueMsgToSend(String msg) {
        
        String messageWithLengthHeader = msgsplitter.addLengthHeader(msg);
        msgToServer = ByteBuffer.wrap(messageWithLengthHeader.getBytes());

        synchronized (messagesToSend) {
            messagesToSend.add(msgToServer.duplicate());
        }
    }

    //reads a buffer from the client and handles it in the run method
    public void recvMsg() throws IOException, InterruptedException {
        msgFromClient.clear();
        int numOfReadBytes;
        numOfReadBytes = clientChannel.read(msgFromClient);
        if (numOfReadBytes == -1) {
            throw new IOException("Client has closed connection.");
        }
        message = extractMessageFromBuffer();
   
        ForkJoinPool.commonPool().execute(this);

    }

    
    //sends all the messages that have been queued to the client
    void sendAll() throws IOException {
        ByteBuffer msg = null;
        synchronized (messagesToSend) {
            while ((msg = messagesToSend.peek()) != null) {
                clientChannel.write(msg);
                messagesToSend.remove();
            }
        }
    }
    
    //gets a string from the buffer
    private String extractMessageFromBuffer() {
        msgFromClient.flip();
        byte[] bytes = new byte[msgFromClient.remaining()];
        msgFromClient.get(bytes);
        return new String(bytes);
    }

    //queues 3 messages to be sent, most communication to the client is queued like this
    public void talk(String a, String b) throws IOException {
        queueMsgToSend(a);

        queueMsgToSend(b);

        queueMsgToSend("Word: " + game.printFoundLetter() + "Incorrect attempts: " + game.incorrectLetters);

    }
   
    //is called if the letter was in the word
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

    //is called if the letter was not in the word
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
