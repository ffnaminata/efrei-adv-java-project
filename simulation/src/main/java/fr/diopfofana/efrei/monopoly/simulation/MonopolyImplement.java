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
    private final List<Location> board;
    private final Players players;
    private final BalanceManager balanceManager;
    private final OrderManager orderManager;
    private final GameManager gameManager;
    private final PositionManager positionManager;
    private String currentPlayer;
    private int dicesResult;

    public MonopolyImplement(Dices dices, List<String> playersName) {
        Parser parser = new Parser();
        this.dices = dices;
        this.board = parser.loadMonopolyDataFromCSV();
        this.players = new Players(playersName);
        this.positionManager = new PositionManager(playersName, board);
        this.balanceManager = new BalanceManager(playersName);
        this.orderManager = new OrderManager();
        this.gameManager = new GameManager();
        this.currentPlayer = playersName.get(playersName.size() - 1);
        submitOrder(currentPlayer, OrderKind.IDLE);
    }

    public List<Location> getBoard() {
        return board;
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
    public void submitOrder(String playerName, OrderKind order) {
        gameManager.throwExceptionIfGameFinished(positionManager);

        // Verification que le joueur est bien celui qui doit jouer
        if (!Objects.equals(this.currentPlayer, playerName)){
            return;
        }

        if (players.getPlayerIsInJail(playerName)){
            players.setPlayerRoundsInJail(playerName, players.getPlayerRoundsInJail(playerName) + 1);
        }

        // Ordre d'acheter la case ou le joueur se trouve
        if (order == OrderKind.BUY){
            orderManager.buy(playerName, positionManager, balanceManager, gameManager);
        }
        else if (order == OrderKind.PAY_PRISON){
            orderManager.pay_prison(playerName, players, positionManager, balanceManager, gameManager,  dicesResult, board);
        }

        // Ordre de faire avancer le prochain joueur
        dicesResult = dices.throwTwoSixSidedDices();
        orderManager.idle(dicesResult, playerName, players, gameManager, positionManager, board);

        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        String nextPlayerName = gameManager.getNextPlayerName(playerName, players.getPlayersName());

        if (isThirdRoundInJailAndPay(nextPlayerName)){
            positionManager.toMove(dicesResult, playerName, players, board);
        }

        // Payement de loyer si besoin
        checkIfRentToPayAndPay(nextPlayerName, dicesResult);

        // Aller en prison si besoin
        checkIfGoToJailAndGo(nextPlayerName);

        // Mise a jour du current player
        this.currentPlayer = nextPlayerName;
    }

    private void checkIfRentToPayAndPay(String playerName, int dicesResult){
        Location playerLocation = positionManager.getPlayerLocation(playerName);
        if (!playerLocation.getOwner().isEmpty()){
            BigDecimal playerBalance = balanceManager.getPlayerBalance(playerName);
            BigDecimal rentAmount = playerLocation.getRent(dicesResult, board);
            if (!balanceManager.canAfford(rentAmount, playerName)){
                rentAmount = playerBalance;
                gameManager.removePlayer(playerName, positionManager, balanceManager);
            } else {
                balanceManager.toDebit(rentAmount, playerName, gameManager, positionManager);
            }
            balanceManager.toCredit(rentAmount, playerLocation.getOwner());
        }
    }
    private void checkIfGoToJailAndGo(String playerName){
        Location playerLocation = positionManager.getPlayerLocation(playerName);
        if (playerLocation.getKind() == Location.LocationKind.GO_TO_JAIL){
            positionManager.setPlayerLocation(playerName, board.get(10));
            players.setPlayerIsInJail(playerName, true);
        }
    }

    private boolean isThirdRoundInJailAndPay(String playerName){
        return players.getPlayerRoundsInJail(playerName) == 3;
    }
}

