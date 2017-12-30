
package se.kth.daniel.blackjackapplication.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import se.kth.daniel.blackjackapplication.server.model.CardConstants.CARDCOLOUR;
import se.kth.daniel.blackjackapplication.server.model.CardConstants.CARDFACE;


@Entity
public class Deck implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToMany(cascade = CascadeType.PERSIST)
    private ArrayList<Card> cards = new ArrayList<>();

    public Deck() {
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Card drawCard() {
        if (cards.size() <= 0) {
            shuffleDeck();
        }
        Card card = cards.get(0);
        cards.remove(card);
        return card;
    }

    public void shuffleDeck() {
        cards = new ArrayList<Card>();
        for (CARDCOLOUR cardcolour : CARDCOLOUR.values()) {
            for (CARDFACE cardface : CARDFACE.values()) {
                cards.add(new Card(cardcolour, cardface));
            }
        }
        Collections.shuffle(cards);
    }

    @Override
    public String toString() {
        return "Deck{" + "cards=" + cards + '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
