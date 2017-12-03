/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.daniel.homework3.client.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.kth.daniel.homework3.common.Catalog;
import se.kth.daniel.homework3.server.model.Account;
import se.kth.daniel.homework3.server.model.FileData;

/**
 *
 * @author Danie
 */
public class InputInterpreter implements Runnable {

    private static final String PROMPT = "> ";
    private final Scanner console = new Scanner(System.in);
    private Catalog catalog;
    private boolean receivingCmds = false;
    private String command;
    private String accname;
    public String currentacc;

    public void start(Catalog catalog) {
        this.catalog = catalog;
        if (receivingCmds) {
            return;
        }
        receivingCmds = true;
        new Thread(this).start();
    }

    /**
     * Interprets and performs user commands.
     */
    @Override
    public void run() {
        Account acct = null;
        System.out.println("Hello and welcome to this file catalog");
        while (receivingCmds) {
            System.out.println("\nWhat would you like to do next? type help for possible commands");
            String message = console.nextLine();
            getCommand(message);

            try {
                switch (command) {
                    case "HELP":
                        System.out.println("Commands: \n help \n quit \n create + account name\n delete + account name \n login + account name \n logout + account name\n checklog + accname");
                        System.out.println(" upload \n download\n listfiles\n deletefile\n updatefile");
                        break;
                    case "QUIT":
                        receivingCmds = false;
                        break;
                    case "CREATE":
                        catalog.createAccount(accname, getPassword());
                        break;
                    case "LOGIN":
                        login();
                        break;
                    case "LOGOUT":
                        logout();
                        break;
                    case "CHECKLOG":
                        checklogin();
                        break;
                    case "DELETE":
                        catalog.deleteAccount(accname);
                        break;
                    case "LIST":
                        List<Account> accounts = catalog.listAccounts();
                        for (Account account : accounts) {
                            System.out.println(account.getUsername());
                        }
                        break;
                    case "UPLOAD":
                        uploadFile();
                        break;
                    case "DOWNLOAD":
                        downloadFile();
                        break;
                    case "LISTFILES":
                        listFiles();
                        break;
                    case "DELETEFILE":
                        deletefile();
                        break;
                    case "UPDATEFILE":
                        updatefile();
                        break;
                    default:
                        System.out.println("illegal command");
                }

            } catch (RemoteException ex) {
                Logger.getLogger(InputInterpreter.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(InputInterpreter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void login() throws RemoteException {
        if (currentacc != null) {
            System.out.println(currentacc + " is currently logged in");

            return;
        }
        boolean log = catalog.login(accname, getPassword());
        if (log) {
            currentacc = accname;
            System.out.println("login for " + accname + " successfull");
        } else {

            System.out.println("invalid username or password");
        }

    }

    private void logout() throws RemoteException {
        boolean log = catalog.logout(accname, getPassword());
        if (log) {
            System.out.println("logout for " + accname + " successfull");
            currentacc = null;
        } else {

            System.out.println("invalid username or password");
        }

    }

    private void checklogin() throws RemoteException {
        Account acc = catalog.getAccount(accname);
        if (acc.checklog()) {
            System.out.println(accname + " is logged in");
        } else {
            System.out.println(accname + " is not logged in");
        }

    }

    private String getPassword() {
        System.out.println("please enter a password");
        return console.nextLine();
    }

    private void getCommand(String message) {
        String[] param = message.split(" ");
        if (param.length > 1) {
            command = param[0].toUpperCase();
            accname = param[1];
        } else {
            command = message.toUpperCase();
        }

    }

    private void uploadFile() throws FileNotFoundException, RemoteException {
        System.out.println("please enter the name of your file");
        String filename = console.nextLine();
        String filePath = "C:\\KTHdata\\Clientfiles\\upload\\" + filename;

        File testfile = new File(filePath);

        if (testfile.exists()) {
            boolean fileprivate = askFilePrivate();
            boolean publicwrite = askPublicWrite();
            long size = testfile.length();
            //FileInputStream fis = new FileInputStream(filePath);
            catalog.uploadFile(currentacc, filename, size, fileprivate, publicwrite);
        } else {
            System.out.println("no file by this name: " + filename);
        }

    }

    private boolean askFilePrivate() {
        System.out.println("Is the file a private file? y/n");
        String answer = console.nextLine();
        return answer.toUpperCase().equals("Y");
    }

    private boolean askPublicWrite() {
        System.out.println("Can anyone edit to the file? y/n");
        String answer = console.nextLine();
        return answer.toUpperCase().equals("Y");

    }
//String user, String fileName, long size, boolean fileprivate, boolean publicwrite

    private void downloadFile() throws RemoteException {
        System.out.println("please enter the name of the file you want to download");
        String filename = console.nextLine();
        FileData filedata = catalog.getFileData(filename, currentacc);
        if (filedata == null) {
            System.out.println("file not found or not permitted");
        } else {
            System.out.println("found file: " + filedata);
        }

    }

    private void listFiles() throws RemoteException {
        List<FileData> fileDatas = catalog.listFileData();
        for (FileData fdata : fileDatas) {
            if (!fdata.getPrivate() || currentacc.equals(fdata.getOwner())) {
                System.out.println(fdata);
            }
        }

    }

    private void deletefile() throws RemoteException {
        System.out.println("please enter the name of the file you want to delete");
        String filename = console.nextLine();
        boolean success = catalog.deleteFile(filename, currentacc);
        if (success) {
            System.out.println("deleted successfully");
        } else {
            System.out.println("unable to delete");
        }
    }

    private void updatefile() throws RemoteException {
        System.out.println("please enter the name of the file you want to update");
        String filename = console.nextLine();
        boolean fileprivate = askFilePrivate();
        boolean publicwrite = askPublicWrite();
        catalog.updateFile(filename, currentacc, fileprivate, publicwrite);
    }
}
