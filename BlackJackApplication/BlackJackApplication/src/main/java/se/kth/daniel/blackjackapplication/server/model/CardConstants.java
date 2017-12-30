
package se.kth.daniel.blackjackapplication.server.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CardConstants implements Serializable {

    @Id
    private Long id;

    public enum CARDFACE {
        ACE(11), KING(10), QUEEN(10), JACK(10), TEN(10), NINE(9), EIGHT(8), SEVEN(7), SIX(6), FIVE(5), FOUR(4), THREE(3), TWO(2);

        private final int cardValue;

        private CARDFACE(int value) {
            this.cardValue = value;
        }

        public int getCardValue() {
            return cardValue;
        }
    }

    public enum CARDCOLOUR {
        HEARTS, DIAMONDS, SPADES, CLUBS
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
