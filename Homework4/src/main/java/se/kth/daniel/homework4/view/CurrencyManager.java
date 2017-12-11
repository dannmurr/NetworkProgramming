
package se.kth.daniel.homework4.view;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;
import se.kth.daniel.homework4.controller.CurrencyFacade;
import se.kth.daniel.homework4.model.CurrencyData;


@Named(value = "currencymanager")
@ConversationScoped
public class CurrencyManager implements Serializable {

    @EJB
    private CurrencyFacade currencyFacade;
    private String currencyFrom;
    private String currencyTo = "other currency";

    private double amountToConvert;
    private String resultAmount = "0.0";
    private boolean init = true;
    private List<CurrencyData> currlist;
    
    @Inject
    private Conversation conversation;

    private void startConversation() {
        if (!conversation.isTransient()) {
            conversation.begin();
        }
    }

    public String getResultAmount() {
        return resultAmount;
    }

    public void setResultAmount(String resultAmount) {
        this.resultAmount = resultAmount;
    }

    public double getAmountToConvert() {
        return amountToConvert;
    }

    public void setAmountToConvert(double amountToConvert) {
        this.amountToConvert = amountToConvert;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public List<String> getCurrencyList() {
        startConversation();
        if (init) {
            init = false;
            currencyFacade.initializeBankValues();
        }
        List<String> list = new ArrayList<>();
        currlist = currencyFacade.getCurrencyList();
        for (int i = 0; i < currlist.size(); i++) {
            list.add(currlist.get(i).getName());
        }
        return list;
    }

    public void testAction() {
        double courseFrom = 0.0;
        double courseTo = 0.0;
        for(int i = 0; i < currlist.size(); i++){
            if(currlist.get(i).getName().equals(currencyFrom)){
                courseFrom = currlist.get(i).getRate();
            }
            if(currlist.get(i).getName().equals(currencyTo)){
                courseTo =  currlist.get(i).getRate();
            }
        }
        
        
        resultAmount = currencyFacade.conversion(courseFrom, courseTo, amountToConvert);

    }

}
