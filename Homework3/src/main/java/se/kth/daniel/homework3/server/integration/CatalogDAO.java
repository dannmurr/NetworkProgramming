/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.daniel.homework3.server.integration;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import se.kth.daniel.homework3.server.model.Account;
import se.kth.daniel.homework3.server.model.FileData;

public class CatalogDAO {

    private final EntityManagerFactory emFactory;
    private final ThreadLocal<EntityManager> threadLocalEntityManager = new ThreadLocal<>();

    public CatalogDAO() {
        emFactory = Persistence.createEntityManagerFactory("kth.daniel_Homework3.persistence");
    }

    public Account findAccountByName(String holderName, boolean endTransactionAfterSearching) {
        if (holderName == null) {
            return null;
        }

        try {
            EntityManager em = beginTransaction();
            try {
                return em.createNamedQuery("findAccountByName", Account.class).
                        setParameter("holderName", holderName).getSingleResult();
            } catch (NoResultException noSuchAccount) {
                return null;
            }
        } finally {
            if (endTransactionAfterSearching) {
                commitTransaction();
            }
        }
    }

    public FileData findFileDataByName(String fileName, boolean endTransactionAfterSearching) {
        if (fileName == null) {
            return null;
        }

        try {
            EntityManager em = beginTransaction();
            try {
                return em.createNamedQuery("findFileDataByName", FileData.class).
                        setParameter("name", fileName).getSingleResult();
            } catch (NoResultException noSuchAccount) {
                return null;
            }
        } finally {
            if (endTransactionAfterSearching) {
                commitTransaction();
            }
        }
    }

    public List<FileData> findAllFileData() {
        try {
            EntityManager em = beginTransaction();
            try {
                return em.createNamedQuery("findAllFiles", FileData.class).getResultList();
            } catch (NoResultException noSuchAccount) {
                return new ArrayList<>();
            }
        } finally {
            commitTransaction();
        }
    }

    public List<Account> findAllAccounts() {
        try {
            EntityManager em = beginTransaction();
            try {
                return em.createNamedQuery("findAllAccounts", Account.class).getResultList();
            } catch (NoResultException noSuchAccount) {
                return new ArrayList<>();
            }
        } finally {
            commitTransaction();
        }
    }

    public void createAccount(Account account) {
        try {
            EntityManager em = beginTransaction();
            em.persist(account);
        } finally {
            commitTransaction();
        }
    }

    public void createFileData(FileData fileData) {
        try {
            EntityManager em = beginTransaction();
            em.persist(fileData);
        } finally {
            commitTransaction();
        }
    }

    public void updateAccount() {
        commitTransaction();
    }

    public void deleteAccount(String holderName) {
        try {
            EntityManager em = beginTransaction();
            em.createNamedQuery("deleteAccountByName", Account.class).
                    setParameter("holderName", holderName).executeUpdate();
        } finally {
            commitTransaction();
        }
    }

    private EntityManager beginTransaction() {
        EntityManager em = emFactory.createEntityManager();
        threadLocalEntityManager.set(em);
        EntityTransaction transaction = em.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        return em;
    }

    private void commitTransaction() {
        threadLocalEntityManager.get().getTransaction().commit();
    }

    public void deleteFileData(String filename) {
        try {
            EntityManager em = beginTransaction();
            em.createNamedQuery("deleteFileDataByName", FileData.class).
                    setParameter("name", filename).executeUpdate();
        } finally {
            commitTransaction();
        }

    }

}
