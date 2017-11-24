
package se.kth.homework2.server;


import java.util.ArrayList;

/**
 *
 * @author Danie
 */
public class ServerController {

    String word;
    int score;
    int attemptsLeft;
    String[] foundLetters;
    ArrayList<String> incorrectLetters;

    public ServerController(String word, int score) {
        this.word = word;
        this.score = score;
        attemptsLeft = word.length();
        foundLetters = new String[word.length()];
        for (int i = 0; i < word.length(); i++) {
            foundLetters[i] = "_";
        }
        incorrectLetters = new ArrayList<>();
        
    }

    //checks for letters in the word given a letter (string) a
    public boolean checkForLetter(String a) {

        boolean found = false;
        for (int i = 0; i < word.length(); i++) {
            if (a.charAt(0) == word.charAt(i)) {
                foundLetters[i] = a;
                found = true;
            }
        }
        return found;
    }

    //checks if the game has been won
    public boolean checkForWin() {
        for (int i = 0; i < foundLetters.length; i++) {
            if (foundLetters[i].equals("_")) {
                return false;
            }
        }
        return true;
    }

    //returns the found letters in a string
    public String printFoundLetter() {
        StringBuilder list = new StringBuilder(foundLetters[0] + " ");
        for (int i = 1; i < foundLetters.length; i++) {
            list.append(foundLetters[i] + " ");
        }
        String list2 = new String(list);
        return list2;
    }

}
