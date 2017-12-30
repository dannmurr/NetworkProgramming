package se.kth.daniel.blackjackapplication.common;

import se.kth.daniel.blackjackapplication.model.Card;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.kth.daniel.blackjackapplication.model.CardConstants.CARDCOLOUR;
import se.kth.daniel.blackjackapplication.model.CardConstants.CARDFACE;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-28T13:01:50")
@StaticMetamodel(Card.class)
public class Card_ { 

    public static volatile SingularAttribute<Card, CARDCOLOUR> colour;
    public static volatile SingularAttribute<Card, CARDFACE> face;
    public static volatile SingularAttribute<Card, Long> id;

}