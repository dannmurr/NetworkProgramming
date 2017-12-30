package se.kth.daniel.blackjackapplication.common;

import se.kth.daniel.blackjackapplication.model.Deck;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.kth.daniel.blackjackapplication.common.Card;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-28T13:01:50")
@StaticMetamodel(Deck.class)
public class Deck_ { 

    public static volatile ListAttribute<Deck, Card> cards;
    public static volatile SingularAttribute<Deck, Long> id;

}