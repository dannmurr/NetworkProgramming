
package se.kth.daniel.blackjackapplication.server.integration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import se.kth.daniel.blackjackapplication.server.model.TableState;

public class BlackJackDAO {

    private EntityManager em;

    public TableState findTableByName(String name) {
        TableState state = getEntityManager().find(TableState.class, name);

        return state;
    }

    public void storeTableData(TableState state) {
        EntityManager eMan = getEntityManager();

        EntityTransaction trans = eMan.getTransaction();
        trans.begin();

        getEntityManager().persist(state);

        trans.commit();
    }

    private EntityManager getEntityManager() {

        if (em == null) {
            em = createLocalEm();
        }

        return em;
    }

    private EntityManager createLocalEm() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("blackJackPU");

        return emf.createEntityManager();
    }
}
