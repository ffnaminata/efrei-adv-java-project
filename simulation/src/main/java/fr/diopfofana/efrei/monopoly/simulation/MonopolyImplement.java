package fr.diopfofana.efrei.monopoly.simulation;
import fr.arsenelapostolet.efrei.monopoly.*;
import fr.diopfofana.efrei.monopoly.simulation.Managers.BalanceManager;
import fr.diopfofana.efrei.monopoly.simulation.Managers.GameManager;
import fr.diopfofana.efrei.monopoly.simulation.Managers.OrderManager;
import fr.diopfofana.efrei.monopoly.simulation.Managers.PositionManager;

import java.math.BigDecimal;
import java.util.*;


public class MonopolyImplement implements Monopoly {
    private final Dices dices;
    private final Board board;
    private final Players players;
    private final BalanceManager balanceManager;
    private final OrderManager orderManager;
    private final GameManager gameManager;
    private final PositionManager positionManager;
    private String currentPlayer;
    private int dicesResult;

    public MonopolyImplement(Dices dices, List<String> playersName) {
        Map<String, Object> orderParameters = new HashMap<>();


        this.dices = dices;
        this.board = new Board();
        this.players = new Players(playersName);
        this.positionManager = new PositionManager(playersName, board.getBoard());
        this.balanceManager = new BalanceManager(playersName);
        this.orderManager = new OrderManager();
        this.gameManager = new GameManager();
        this.currentPlayer = playersName.get(playersName.size() - 1);
        submitOrder(currentPlayer, OrderKind.IDLE, orderParameters);
    }

    public List<Location> getBoard() {
        return board.getBoard();
    }

    @Override
    public Map<String, Location> getPlayersLocation() {
        return positionManager.getPlayersLocation();
    }

    @Override
    public Map<String, BigDecimal> getPlayersBalance() {
        return balanceManager.getPlayersBalance();
    }

    @Override
    public void submitOrder(String playerName, OrderKind order, Map<String, Object> orderParameters) {
        // Verification condition fin de jeu
        gameManager.throwExceptionIfGameFinished(positionManager);

        // Verification que le joueur est bien celui qui doit jouer
        if (!this.currentPlayer.equals(playerName)){ return; }

        // Applisquer l'ordre au joueur actuelle
        switch (order){
            case BUY -> orderManager.buy(playerName, players, positionManager, balanceManager, gameManager);
            case PAY_PRISON -> orderManager.pay_prison(playerName, players, positionManager, balanceManager, gameManager,  dicesResult, board.getBoard());
            case BUILD -> orderManager.build(playerName, orderParameters.get("propertyName").toString(), positionManager, balanceManager, gameManager, board);
        }

        // Faire avancer le prochain joueur
        dicesResult = dices.throwTwoSixSidedDices();
        orderManager.idle(dicesResult, playerName, players, gameManager, positionManager, balanceManager, board.getBoard());

        // Application des consequences
        String nextPlayerName = gameManager.getNextPlayerName(playerName, players.getPlayersName());
        gameManager.checkIfGoToJailAndGo(nextPlayerName, players, positionManager, board.getBoard());
        gameManager.updateRoundsInJail(nextPlayerName, players);
        gameManager.checkIfThirdRoundInJailAndPay(nextPlayerName, players, orderManager, positionManager, balanceManager, gameManager,  dicesResult, board.getBoard());
        gameManager.checkIfRentToPayAndPay(nextPlayerName, players, dicesResult, positionManager, balanceManager, gameManager, board.getBoard());
        gameManager.checkIfTaxToPayAndPay(nextPlayerName, positionManager, balanceManager, gameManager);

        // Mise a jour du current player
        this.currentPlayer = nextPlayerName;
    }
}

