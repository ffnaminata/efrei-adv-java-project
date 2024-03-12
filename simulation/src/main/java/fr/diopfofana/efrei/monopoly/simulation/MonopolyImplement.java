package fr.diopfofana.efrei.monopoly.simulation;
import fr.arsenelapostolet.efrei.monopoly.*;

import java.math.BigDecimal;
import java.util.*;


public class MonopolyImplement implements Monopoly {
    private final Dices dices;
    private final List<Location> board;
    private final List<String> playersName;
    private final Map<String, Location> playersLocation;
    private final Map<String, BigDecimal> playersBalance;
    private int currentPlayerIndex;


    public MonopolyImplement(Dices dices, List<String> playersName) {
        this.dices = dices;
        this.board = new ArrayList<>();
        this.playersName = playersName;
        this.playersLocation = new HashMap<>();
        this.playersBalance = new HashMap<>();
        this.currentPlayerIndex = playersName.size() - 1;
        Parser.loadMonopolyDataFromCSV(this.board);
        initialisePlayersPosition();
        initialisePlayersBalance();
        submitOrder(playersName.get(this.currentPlayerIndex), OrderKind.IDLE);
        }

    private void initialisePlayersPosition(){
        for (String playerName : this.playersName) {
            this.playersLocation.put(playerName, this.board.get(0));
        }
    }

    private void initialisePlayersBalance(){
        BigDecimal playerBalance = new BigDecimal("1500");
        for (String playerName : this.playersName) {
            this.playersBalance.put(playerName, playerBalance);
        }
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
        return playersBalance;
    }

    @Override
    public void submitOrder(String playerName, OrderKind order) {
        GameManager.throwExceptionIfGameFinished(playersLocation);

        // Verification que le joueur est bien celui qui doit jouer
        int currentPlayerIndex = playersName.indexOf(playerName);
        if (this.currentPlayerIndex != currentPlayerIndex){
            return;
        }

        // Ordre d'acheter la case ou le joueur se trouve
        Location currentPlayerLocation = playersLocation.get(playerName);
        if (order == OrderKind.BUY){
            OrderManager.buy(playerName, currentPlayerLocation, playersBalance);
        }

        // Recuperer l'index et le nom du prochain joueur
        int nextPlayerIndex = (currentPlayerIndex + 1) % playersName.size();
        String nextPlayerName = playersName.get(nextPlayerIndex);

        // Ordre de faire avancer le prochain joueur
        int dicesResult = dices.throwTwoSixSidedDices();
        OrderManager.idle(dicesResult, nextPlayerName, playersLocation, board);

        // Payement de loyer si besoin
        Location nextPlayerLocation = playersLocation.get(nextPlayerName);
        if (!nextPlayerLocation.getOwner().isEmpty()){
            BigDecimal nextPlayerBalance = playersBalance.get(nextPlayerName);
            BigDecimal rentAmount = nextPlayerLocation.getRent(dicesResult, board);
            if (!BalanceManager.canAfford(rentAmount, nextPlayerName, playersBalance)){
                rentAmount = nextPlayerBalance;
                GameManager.removePlayer(nextPlayerName, playersLocation, playersBalance);
            } else {
                BalanceManager.toDebit(rentAmount, nextPlayerName, playersBalance);
            }
            BalanceManager.toCredit(rentAmount, nextPlayerLocation.getOwner(), playersBalance);
        }

        // Mise a jour du current player
        this.currentPlayerIndex = nextPlayerIndex;
    }
}
