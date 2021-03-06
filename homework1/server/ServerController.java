/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.homework1.server;


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
        incorrectLetters = new ArrayList<String>();
        
    }

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

    public boolean checkForWin() {
        for (int i = 0; i < foundLetters.length; i++) {
            if (foundLetters[i].equals("_")) {
                return false;
            }
        }
        return true;
    }

    public String printFoundLetter() {
        StringBuilder list = new StringBuilder(foundLetters[0] + " ");
        for (int i = 1; i < foundLetters.length; i++) {
            list.append(foundLetters[i] + " ");
        }
        System.out.println("word "+ word + " found letters: " + list);
        String list2 = new String(list);
        return list2;
    }

}
