package se.kth.homework2.client.net;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import java.util.logging.Level;
import java.util.logging.Logger;
import se.kth.homework2.client.view.InputInterpreter;
import se.kth.homework2.common.MessageSplitter;

/**
 *
 * @author Danie
 */
public class ServerConnection implements Runnable {

    private final ByteBuffer msgFromServer = ByteBuffer.allocateDirect(8192);
    private ByteBuffer msgToServer = ByteBuffer.allocateDirect(8192);

    private InetSocketAddress serverAddress;
    SocketChannel socketChannel;
    private Selector selector;
    private boolean connected;
    public volatile boolean timeToSend = false;
    private String message;
    private MessageSplitter msgSplitter = new MessageSplitter();

    //The selectors options in the runtime
    @Override
    public void run() {
        try {
            initialize();
            while (connected) {
                if (timeToSend) {
                    socketChannel.keyFor(selector).interestOps(SelectionKey.OP_WRITE);
                    timeToSend = false;
                }

                selector.select();
                for (SelectionKey key : selector.selectedKeys()) {
                    selector.selectedKeys().remove(key);
                    if (key.isConnectable()) {
                        completeConnection(key);
                    } else if (key.isReadable()) {
                        listenServer(key);
                    } else if (key.isWritable()) {
                        writeServer(key);
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //finishes the connection to the server and channel
    private void completeConnection(SelectionKey key) throws IOException {
        socketChannel.finishConnect();
        key.interestOps(SelectionKey.OP_READ);

    }

    //initializez the socket and selector
    private void initialize() throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(serverAddress);
        connected = true;
        selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }

    //connects and starts the new selector thread
    public void connect(String host, int port) {
        serverAddress = new InetSocketAddress(host, port);
        new Thread(this).start();
    }

    //extracts a message from the buffer
    private String getBufferMessage() {
        msgFromServer.flip();
        byte[] bytes = new byte[msgFromServer.remaining()];
        msgFromServer.get(bytes);
        return new String(bytes);
    }

    //the method for recieving data from the server
    private void listenServer(SelectionKey key) throws IOException {
        msgFromServer.clear();
        int numOfReadBytes = socketChannel.read(msgFromServer);
        if (numOfReadBytes == -1) {
            System.out.println("server failure");
            throw new IOException();
        }

        String recvdString = getBufferMessage();

        msgSplitter.appendRecvdString(recvdString);
        while (msgSplitter.hasNext()) {
            String msg = msgSplitter.nextMsg();

            Executor pool = ForkJoinPool.commonPool();
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(msg);
                }
            });
        }

    }

    //will make the task for the selector to write, is called when a message has been inputted by the user
    public void sendmessage(String msg) {

        message = msg;
        timeToSend = true;
        selector.wakeup();
    }

    //method for writing to the server, is called when a key is writeable
    private void writeServer(SelectionKey key) throws IOException, InterruptedException {
        msgToServer.clear();
        msgToServer = ByteBuffer.wrap(message.getBytes());
        socketChannel.write(msgToServer);

        if(message.equals("quit")){
            endConnection();
            return;
        }
        key.interestOps(SelectionKey.OP_READ);

    }

    public void endConnection() throws IOException, InterruptedException {

        Thread.sleep(100);

        socketChannel.close();
        socketChannel.keyFor(selector).cancel();
        connected = false;
    }

}
