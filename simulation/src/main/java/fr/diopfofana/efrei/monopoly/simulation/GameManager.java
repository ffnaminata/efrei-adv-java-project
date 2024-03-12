package fr.diopfofana.efrei.monopoly.simulation;

import fr.arsenelapostolet.efrei.monopoly.GameFinishedException;
import fr.arsenelapostolet.efrei.monopoly.Location;

import java.math.BigDecimal;
import java.util.Map;

public class GameManager {
    public static void throwExceptionIfGameFinished(Map<String, Location> playersLocation){
        if (playersLocation.size() < 2) {
            throw new GameFinishedException();
        }
    }

    public static void removePlayer(String playerToRemove, Map<String, Location> playersLocation, Map<String, BigDecimal> playersBalance){
        playersLocation.remove(playerToRemove);
        playersBalance.remove(playerToRemove);
    }
}
