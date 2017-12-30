package se.kth.daniel.blackjackapplication.common;

import se.kth.daniel.blackjackapplication.model.Hand;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import se.kth.daniel.blackjackapplication.common.Card;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-28T13:01:50")
@StaticMetamodel(Hand.class)
public class Hand_ { 

    public static volatile ListAttribute<Hand, Card> cards;
    public static volatile SingularAttribute<Hand, Long> id;

}