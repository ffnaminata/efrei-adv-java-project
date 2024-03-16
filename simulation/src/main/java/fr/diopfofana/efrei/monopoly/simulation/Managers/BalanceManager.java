package fr.diopfofana.efrei.monopoly.simulation.Managers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BalanceManager {
    private final Map<String, BigDecimal> playersBalance;

    public BalanceManager(List<String> playersName){
        this.playersBalance = initialisePlayersBalance(playersName);
    }

    private Map<String, BigDecimal> initialisePlayersBalance(List<String> playersName){
        Map<String, BigDecimal> playersBalance = new HashMap<>();
        BigDecimal playerBalance = new BigDecimal("1500");
        for (String playerName : playersName) {
            playersBalance.put(playerName, playerBalance);
        }
        return playersBalance;
    }

    public Map<String, BigDecimal> getPlayersBalance(){
        return playersBalance;
    }

    public BigDecimal getPlayerBalance(String playerName){
        return playersBalance.get(playerName);
    }

    public boolean canAfford(BigDecimal amount, String playerName){
        return playersBalance.get(playerName).compareTo(amount) >= 0;
    }

    public void toDebit(String playerName, BigDecimal amount, GameManager gameManager, PositionManager positionManager){
        BigDecimal playerBalance = playersBalance.get(playerName);
        if (!canAfford(amount, playerName)){
            gameManager.removePlayer(playerName, positionManager, this);
        } else {
            playersBalance.put(playerName, playerBalance.subtract(amount));
        }
    }
    public void toCredit(String playerName, BigDecimal amount){
        BigDecimal playerBalance = playersBalance.get(playerName);
        playersBalance.put(playerName, playerBalance.add(amount));
    }

    public void removePlayerFromPlayersBalance(String playerName){
        playersBalance.remove(playerName);
    }
}

