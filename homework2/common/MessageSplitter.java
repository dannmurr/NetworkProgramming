
package se.kth.homework2.common;

/**
 *
 * @author Danie
 */
import java.util.ArrayDeque;
import java.util.Queue;

public class MessageSplitter {

    private StringBuilder recvdChars = new StringBuilder();
    private final Queue<String> messages = new ArrayDeque<>();


    public synchronized void appendRecvdString(String recvdString) {
        recvdChars.append(recvdString);
        while (extractMsg());
    }

  
    public synchronized String nextMsg() {
        return messages.poll();
    }

   
    public synchronized boolean hasNext() {
        return !messages.isEmpty();
    }

    //adds the length of the string in front followed by "###"
    public static String addLengthHeader(String msgWithoutHeader) {
        StringBuilder builder = new StringBuilder();
        builder.append(msgWithoutHeader.length());
        builder.append("###");
        builder.append(msgWithoutHeader);
        return builder.toString();
    }

    //extracts the message from the queue
    private boolean extractMsg() {
        String allRecvdChars = recvdChars.toString();
        String[] splitAtHeader = allRecvdChars.split("###");
        if (splitAtHeader.length < 2) {
            return false;
        }
        String lengthHeader = splitAtHeader[0];
        int lengthOfFirstMsg = Integer.parseInt(lengthHeader);
        if (hasCompleteMsg(lengthOfFirstMsg, splitAtHeader[1])) {
            String completeMsg = splitAtHeader[1].substring(0, lengthOfFirstMsg);
            messages.add(completeMsg);
            recvdChars.delete(0, lengthHeader.length()
                    + "###".length() + lengthOfFirstMsg);
            return true;
        }
        return false;
    }
    private boolean hasCompleteMsg(int msgLen, String recvd) {
        return recvd.length() >= msgLen;
    }

}
