package se.kth.daniel.homework4.integration;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import se.kth.daniel.homework4.model.CurrencyData;

@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class CurrencyDAO {

    @PersistenceContext(unitName = "kth.daniel_Homework4")
    private EntityManager em;

    public CurrencyData findCurrencyByName(String name) {
        CurrencyData currdata = em.find(CurrencyData.class, name);

        return currdata;
    }

    public List<CurrencyData> findList() {

        Query q = em.createQuery("SELECT curr FROM CurrencyData curr");

        return (List) q.getResultList();

    }

    public void storeCurrencyData(CurrencyData currdata) {
        if (findCurrencyByName(currdata.getName()) == null) {
            em.persist(currdata);
        }
    }

}
