package fr.diopfofana.efrei.monopoly.simulation;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.util.Map;

public class BalanceManager {
    public static boolean canAfford(BigDecimal amount, String playerName, Map<String, BigDecimal> playersBalance){
        return playersBalance.get(playerName).compareTo(amount) >= 0;
    }

    public static void toDebit(BigDecimal amount, String playerName, Map<String, BigDecimal> playersBalance){
        BigDecimal playerBalance = playersBalance.get(playerName);
        playersBalance.put(playerName, playerBalance.subtract(amount));
    }
    public static void toCredit(BigDecimal amount, String playerName, Map<String, BigDecimal> playersBalance){
        BigDecimal playerBalance = playersBalance.get(playerName);
        playersBalance.put(playerName, playerBalance.add(amount));
    }
}
