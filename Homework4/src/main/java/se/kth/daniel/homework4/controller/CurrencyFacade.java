
package se.kth.daniel.homework4.controller;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import se.kth.daniel.homework4.integration.CurrencyDAO;
import se.kth.daniel.homework4.model.CurrencyData;


@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class CurrencyFacade {

    @EJB CurrencyDAO currDB;

    public List<CurrencyData> getCurrencyList() {
        return currDB.findList();
    }

    public void initializeBankValues() {
        currDB.storeCurrencyData(new CurrencyData("EUR", 9.94));
        currDB.storeCurrencyData(new CurrencyData("SEK", 1));
        currDB.storeCurrencyData(new CurrencyData("USD", 8.42));
        currDB.storeCurrencyData(new CurrencyData("GBP", 11.27));
        currDB.storeCurrencyData(new CurrencyData("SGD", 6.24));
    }

    public String conversion(double from, double to, double amount) {

        return "" + amount * (from / to);
    }
}
