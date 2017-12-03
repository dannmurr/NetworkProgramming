
package se.kth.daniel.homework3.client.startup;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import se.kth.daniel.homework3.client.view.InputInterpreter;
import se.kth.daniel.homework3.common.Catalog;


public class Main {

    public static void main(String[] args) {
        try {
            Catalog catalog = (Catalog) Naming.lookup(Catalog.CATALOG_NAME_IN_REGISTRY);
            new InputInterpreter().start(catalog);
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.out.println("Could not start bank client.");
        }
    }
}