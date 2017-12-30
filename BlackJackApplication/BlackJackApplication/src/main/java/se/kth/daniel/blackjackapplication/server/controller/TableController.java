package se.kth.daniel.blackjackapplication.server.controller;

import se.kth.daniel.blackjackapplication.server.model.Deck;
import se.kth.daniel.blackjackapplication.server.model.Hand;
import se.kth.daniel.blackjackapplication.server.integration.BlackJackDAO;
import se.kth.daniel.blackjackapplication.server.model.TableState;

public class TableController {

    private BlackJackDAO blackjackDAO = new BlackJackDAO();

    public TableController() {

    }

    public TableState startPlayer(String playerName) {

        TableState tableState = new TableState();  // Reply
        tableState.setPlayerName(playerName);

        if (blackjackDAO.findTableByName(playerName) != null) {
            // Cant create new table with same player name.

            tableState.setSuccess(false);
            tableState.setErrorMessage("Player already active");
        } else {

            TableState tablestate = new TableState();
            tablestate.setPlayerName(playerName);
            blackjackDAO.storeTableData(tablestate);

        }

        return tableState;

    }

    public TableState hit(String playerName) {
        TableState state = blackjackDAO.findTableByName(playerName);

        if (blackjackDAO.findTableByName(playerName) == null) {   //no tablestate found for player
            state.setSuccess(false);
            state.setErrorMessage("No table active for " + playerName);
        } else {

            drawNewCard(state.getPlayerHand(), state.getDeck());
            blackjackDAO.storeTableData(state);
        }
        return state;
    }

    public TableState deal(String player) {
        TableState state = blackjackDAO.findTableByName(player);

        if (blackjackDAO.findTableByName(player) == null) { //no tablestate found for player

            state.setSuccess(false);
            state.setErrorMessage("No table active for " + player);
        } else {
            state.setDeck(new Deck());
            state.getDeck().shuffleDeck();
            state.setCoins(state.getCoins() - 1);

            state.setPlayerHand(new Hand());
            drawNewCard(state.getPlayerHand(), state.getDeck());
            drawNewCard(state.getPlayerHand(), state.getDeck());

            state.setDealerHand(new Hand());
            drawNewCard(state.getDealerHand(), state.getDeck());
            drawNewCard(state.getDealerHand(), state.getDeck());

            blackjackDAO.storeTableData(state);
        }
        return state;

    }

    public static void drawNewCard(Hand hand, Deck deck) {
        hand.addCard(deck.drawCard());
    }

    public TableState stand(String playerName) {
        TableState state = blackjackDAO.findTableByName(playerName);

        if (blackjackDAO.findTableByName(playerName) == null) {   //no tablestate found for player

            state.setSuccess(false);
            state.setErrorMessage("No table active for " + playerName);

        } else {

            int playerScore = state.getPlayerHand().getHandValue();

            //the dealer plays and tries to beat the player
            while (state.getDealerHand().getHandValue() < playerScore) {
                drawNewCard(state.getDealerHand(), state.getDeck());
                if (state.getDealerHand().getHandValue() > 21 || state.getDealerHand().getHandValue() == 17) {
                    break;
                }
            }
            //if the player has won
            if (state.getDealerHand().getHandValue() > 21) {
                state.setCoins(state.getCoins() + 3);
            }
            blackjackDAO.storeTableData(state);
        }

        return state;
    }

}
