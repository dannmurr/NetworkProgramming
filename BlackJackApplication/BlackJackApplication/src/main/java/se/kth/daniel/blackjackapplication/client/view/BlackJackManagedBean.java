package se.kth.daniel.blackjackapplication.client.view;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import se.kth.daniel.blackjackapplication.client.controller.BlackJackClient;
import se.kth.daniel.blackjackapplication.server.model.TableState;

@Named(value = "blackJackManagedBean")
@SessionScoped
public class BlackJackManagedBean implements Serializable {

    private String playerName;
    private String errorMessage;
    private TableState state;
    private String hiddenCard;
    
    //booleans for output labels
    private boolean startedGame = false;
    private boolean bust = false;
    private boolean win = false;
    private boolean loose = false;
    
    public String newGame() {

        state = BlackJackClient.newPlayer(playerName);
        if (!state.getSuccess()) {
            errorMessage = state.getErrorMessage();
            return "errorPage";
        }

        return "blackJack";

    }

    public String deal() {
        startedGame = true;
        bust = false;
        win = false;
        loose = false;
        state = BlackJackClient.deal(playerName);

        if(state.getCoins() == -1){
            playerName = null;
            return "startPage";
        }
        
        if (!state.getSuccess()) {
            errorMessage = state.getErrorMessage();
            return "errorPage";
        }

        return "blackJack";
    }

    public String hit() {

        state = BlackJackClient.hit(playerName);
        if (!state.getSuccess()) {
            errorMessage = state.getErrorMessage();
            return "errorPage";
        }

        if (state.getPlayerHand().getHandValue() > 21) {
            startedGame = false;
            bust = true;
        }
        return "blackJack";
    }

    public String stand() {

        state = BlackJackClient.stand(playerName);
        if (!state.getSuccess()) {
            errorMessage = state.getErrorMessage();
            return "errorPage";
        }

        if (state.getDealerHand().getHandValue() > 21) {
            win = true;
        } else {
            loose = true;
        }
        startedGame = false;

        return "blackJack";

    }

    public boolean isLoose() {
        return loose;
    }

    public void setLoose(boolean loose) {
        this.loose = loose;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public TableState getState() {
        return state;
    }

    public void setState(TableState state) {
        this.state = state;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isStartedGame() {
        return startedGame;
    }

    public void setStartedGame(boolean startedGame) {
        this.startedGame = startedGame;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isBust() {
        return bust;
    }

    public void setBust(boolean bust) {
        this.bust = bust;
    }

    public String getHiddenCard() {
        return state.getDealerHand().getCards().get(0) + ", [UNKNOWN CARD]";
    }

    public void setHiddenCard(String hiddenCard) {
        this.hiddenCard = hiddenCard;
    }


    public BlackJackManagedBean() {
    }

    public void test() {
        System.out.println("testing testing");
    }

}
