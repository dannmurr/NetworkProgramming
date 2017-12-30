
package se.kth.daniel.blackjackapplication.server.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import se.kth.daniel.blackjackapplication.server.model.CardConstants.CARDCOLOUR;
import se.kth.daniel.blackjackapplication.server.model.CardConstants.CARDFACE;


@Entity
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private CARDCOLOUR colour;
    private CARDFACE face;

    public Card() {

    }

    public Card(CARDCOLOUR colour, CARDFACE face) {
        this.colour = colour;
        this.face = face;
    }

    public CARDCOLOUR getColour() {
        return colour;
    }

    public void setColour(CARDCOLOUR colour) {
        this.colour = colour;
    }

    public void setColour(String colour) {
        this.colour = CARDCOLOUR.valueOf(colour);
    }

    public CARDFACE getFace() {
        return face;
    }

    public void setFace(CARDFACE face) {
        this.face = face;
    }

    public void setFace(String face) {
        this.face = CARDFACE.valueOf(face);

    }

    public int cardValue() {
        return face.getCardValue();
    }

    @Override
    public String toString() {
        return "[" + face + " of " + colour + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
