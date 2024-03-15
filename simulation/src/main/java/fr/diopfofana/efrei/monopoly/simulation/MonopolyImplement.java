package fr.diopfofana.efrei.monopoly.simulation;
import fr.arsenelapostolet.efrei.monopoly.*;

import java.math.BigDecimal;
import java.util.*;


public class MonopolyImplement implements Monopoly {
    private final Dices dices;
    private final List<Location> board;
    private final List<String> playersName;
    private final Map<String, Location> playersLocation;
    private final BalanceManager balanceManager;
    private final OrderManager orderManager;
    private final GameManager gameManager;
    private int currentPlayerIndex;


    public MonopolyImplement(Dices dices, List<String> playersName) {
        Parser parser = new Parser();
        this.dices = dices;
        this.board = parser.loadMonopolyDataFromCSV();
        this.playersName = playersName;
        this.playersLocation = initialisePlayersPosition();
        this.balanceManager = new BalanceManager(playersName);
        this.orderManager = new OrderManager();
        this.gameManager = new GameManager();
        this.currentPlayerIndex = playersName.size() - 1;
        submitOrder(playersName.get(this.currentPlayerIndex), OrderKind.IDLE);
    }

    private Map<String, Location> initialisePlayersPosition(){
        Map<String, Location> playersLocation = new HashMap<>();
        for (String playerName : this.playersName) {
            playersLocation.put(playerName, this.board.get(0));
        }
        return playersLocation;
    }

    public List<Location> getBoard() {
        return board;
    }
    @Override
    public Map<String, Location> getPlayersLocation() {
        return  new HashMap<>(playersLocation);
    }

    @Override
    public Map<String, BigDecimal> getPlayersBalance() {
        return balanceManager.getPlayersBalance();
    }

    @Override
    public void submitOrder(String playerName, OrderKind order) {
        gameManager.throwExceptionIfGameFinished(playersLocation);

        // Verification que le joueur est bien celui qui doit jouer
        int currentPlayerIndex = playersName.indexOf(playerName);
        if (this.currentPlayerIndex != currentPlayerIndex){
            return;
        }

        // Ordre d'acheter la case ou le joueur se trouve
        Location currentPlayerLocation = playersLocation.get(playerName);
        if (order == OrderKind.BUY){
            orderManager.buy(playerName, currentPlayerLocation, balanceManager);
        }

        // Recuperer l'index et le nom du prochain joueur
        int nextPlayerIndex = (currentPlayerIndex + 1) % playersName.size();
        String nextPlayerName = playersName.get(nextPlayerIndex);

        // Ordre de faire avancer le prochain joueur
        int dicesResult = dices.throwTwoSixSidedDices();
        orderManager.idle(dicesResult, nextPlayerName, playersLocation, board);

        // Payement de loyer si besoin
        Location nextPlayerLocation = playersLocation.get(nextPlayerName);
        if (!nextPlayerLocation.getOwner().isEmpty()){
            BigDecimal nextPlayerBalance = balanceManager.getPlayerBalance(nextPlayerName);
            BigDecimal rentAmount = nextPlayerLocation.getRent(dicesResult, board);
            if (!balanceManager.canAfford(rentAmount, nextPlayerName)){
                rentAmount = nextPlayerBalance;
                gameManager.removePlayer(nextPlayerName, playersLocation, balanceManager);
            } else {
                balanceManager.toDebit(rentAmount, nextPlayerName);
            }
            balanceManager.toCredit(rentAmount, nextPlayerLocation.getOwner());
        }

        // Mise a jour du current player
        this.currentPlayerIndex = nextPlayerIndex;
    }
}

