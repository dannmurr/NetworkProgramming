/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.homework2.client.startup;


import java.io.IOException;
import se.kth.homework2.client.view.InputInterpreter;

/**
 *
 * @author Danie
 */
public class ClientStarter {

    public static void main(String[] args) throws IOException {
        new InputInterpreter().start();
        
    }

}
