package se.kth.daniel.blackjackapplication.model;

import se.kth.daniel.blackjackapplication.server.model.Hand;
import se.kth.daniel.blackjackapplication.server.model.TableState;
import se.kth.daniel.blackjackapplication.server.model.Deck;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-28T13:01:50")
@StaticMetamodel(TableState.class)
public class TableState_ { 

    public static volatile SingularAttribute<TableState, String> playerName;
    public static volatile SingularAttribute<TableState, Integer> coins;
    public static volatile SingularAttribute<TableState, Boolean> success;
    public static volatile SingularAttribute<TableState, Hand> playerHand;
    public static volatile SingularAttribute<TableState, Deck> deck;
    public static volatile SingularAttribute<TableState, String> errorMessage;
    public static volatile SingularAttribute<TableState, Hand> dealerHand;

}