
package se.kth.daniel.blackjackapplication.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Hand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST)
    ArrayList<Card> cards = new ArrayList<Card>();

    public Hand() {
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return cards + " value: " + getHandValue() + '}';
    }

    public int getHandValue() {
        int value = 0;
        int aceCounter = 0;
        for (Card card : cards) {
            if (card.cardValue() == 11) {
                aceCounter++;
            } else {
                value += card.cardValue();
            }
        }
        if (aceCounter > 0) {
            if ((value + 11 + aceCounter - 1) < 22) {
                return value + 11 + aceCounter - 1;
            } else {
                return value + aceCounter;
            }

        }
        return value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
