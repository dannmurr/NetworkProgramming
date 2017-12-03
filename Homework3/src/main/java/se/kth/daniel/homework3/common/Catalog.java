/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.daniel.homework3.common;

import java.io.FileInputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import se.kth.daniel.homework3.server.model.Account;
import se.kth.daniel.homework3.server.model.FileData;

/**
 * Specifies the bank's remote methods.
 */
public interface Catalog extends Remote {

    public static final String CATALOG_NAME_IN_REGISTRY = "catalog";

    public boolean logout(String holderName, String password) throws RemoteException;

    public boolean login(String holderName, String password) throws RemoteException;

    public void createAccount(String name, String password) throws RemoteException;

    public FileData getFileData(String name, String user) throws RemoteException;

    public Account getAccount(String name) throws RemoteException;

    public void deleteAccount(String holderName) throws RemoteException;

    public List<Account> listAccounts() throws RemoteException;

    public boolean uploadFile(String user, String fileName, long size, boolean fileprivate, boolean publicwrite) throws RemoteException;

    public List<FileData> listFileData() throws RemoteException;

    public boolean deleteFile(String filename, String currentacc) throws RemoteException;

    public boolean updateFile(String filename, String currentacc, boolean fileprivate, boolean publicwrite) throws RemoteException;
}
