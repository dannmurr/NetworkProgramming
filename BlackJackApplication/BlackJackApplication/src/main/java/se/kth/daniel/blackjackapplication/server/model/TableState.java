package se.kth.daniel.blackjackapplication.server.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class TableState implements Serializable {

    @Id
    String playerName;
    String errorMessage;
    boolean success = true;
    @OneToOne(cascade = CascadeType.PERSIST)
    Hand playerHand;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    Hand dealerHand;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    Deck deck;
    int coins = 5;

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setErrorMessage(String errMess) {
        this.errorMessage = errMess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public void setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public void setPlayerHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    @Override
    public String toString() {
        String toStr = "TableState{" + "playerName=" + playerName + ", errorMessage="
                + errorMessage + ", success=" + success + ", playerHand=" + playerHand + ", dealerHand=" + dealerHand + "}";
        return toStr;
    }

}
