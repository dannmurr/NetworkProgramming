package se.kth.daniel.homework3.server.controller;

import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import se.kth.daniel.homework3.common.Catalog;
import se.kth.daniel.homework3.server.integration.CatalogDAO;
import se.kth.daniel.homework3.server.model.Account;
import se.kth.daniel.homework3.server.model.FileData;

public class Controller extends UnicastRemoteObject implements Catalog {

    private final CatalogDAO catalogDb;

    private static String basePath = "C:\\KTHdata\\Serverfiles";

    public Controller() throws RemoteException {
        super();
        catalogDb = new CatalogDAO();
    }

    @Override
    public List<Account> listAccounts() {
        return catalogDb.findAllAccounts();

    }

    @Override
    public void createAccount(String holderName, String password) {
        try {
            if (catalogDb.findAccountByName(holderName, true) != null) {
                System.out.println("Account for: " + holderName + " already exists");
            }
            catalogDb.createAccount(new Account(holderName, password));
        } catch (Exception e) {
            System.out.println("Could not create account for: " + holderName);
        }
    }

    @Override
    public Account getAccount(String holderName) {
        try {
            return catalogDb.findAccountByName(holderName, true);
        } catch (Exception e) {
            System.out.println("No user by this name");
            return null;
        }
    }

    @Override
    public void deleteAccount(String holderName) {
        try {
            catalogDb.deleteAccount(holderName);
        } catch (Exception e) {
            System.out.println("Could not delete account for: " + holderName);
        }
    }

    @Override
    public boolean logout(String holderName, String password) throws RemoteException {
        Account acc = catalogDb.findAccountByName(holderName, false);
        if (acc == null) {
            return false;
        }
        if (acc.getPassword().equals(password)) {
            acc.logout();
            catalogDb.updateAccount();
            return true;
        }
        catalogDb.updateAccount();
        return false;
    }

    @Override
    public boolean login(String holderName, String password) throws RemoteException {
        Account acc = catalogDb.findAccountByName(holderName, false);
        if (acc == null) {
            return false;
        }

        if (acc.getPassword().equals(password)) {
            acc.login();
            catalogDb.updateAccount();
            return true;

        }
        catalogDb.updateAccount();
        return false;
    }

    @Override
    public boolean uploadFile(String user, String fileName, long size, boolean fileprivate, boolean publicwrite) throws RemoteException {
        System.out.println(user + fileName + size + fileprivate + publicwrite);

        catalogDb.createFileData(new FileData(user, fileName, size, fileprivate, publicwrite));

        return false;
    }

    @Override
    public FileData getFileData(String name, String user) throws RemoteException {
        FileData filedata = catalogDb.findFileDataByName(name, true);
        if (filedata != null) {
            if (filedata.getPrivate() && !filedata.getOwner().equals(user)) {
                filedata = null; //private and not the owner of the file
            }
        }

        return filedata;
    }

    @Override
    public List<FileData> listFileData() throws RemoteException {
        return catalogDb.findAllFileData();
    }

    @Override
    public boolean deleteFile(String filename, String user) throws RemoteException {
        try {
            FileData filedata = catalogDb.findFileDataByName(filename, true);

            if (filedata.getOwner().equals(user)
                    || (!filedata.getPrivate() && filedata.getReadWrite())) {
                catalogDb.deleteFileData(filename);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Could not delete file for: " + filename);
        }
        return false;

    }

    @Override
    public boolean updateFile(String filename, String currentacc, boolean fileprivate, boolean publicwrite) {
        FileData filedata = catalogDb.findFileDataByName(filename, false);
        if(filedata != null){
            if (filedata.getOwner().equals(currentacc)
                    || (!filedata.getPrivate() && filedata.getReadWrite())) {
                filedata.setFilePrivate(fileprivate);
                filedata.setPublicWrite(publicwrite);
                catalogDb.updateAccount();
                return true;
            }
        }

        catalogDb.updateAccount();
        return false;
    }

}
