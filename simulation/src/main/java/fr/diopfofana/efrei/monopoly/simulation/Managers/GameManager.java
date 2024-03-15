package fr.diopfofana.efrei.monopoly.simulation.Managers;

import fr.arsenelapostolet.efrei.monopoly.GameFinishedException;
import fr.arsenelapostolet.efrei.monopoly.Location;

import java.util.Map;

public class GameManager {
    public void throwExceptionIfGameFinished(Map<String, Location> playersLocation){
        if (playersLocation.size() < 2) {
            throw new GameFinishedException();
        }
    }

    public void removePlayer(String playerToRemove, Map<String, Location> playersLocation, BalanceManager balanceManager){
        playersLocation.remove(playerToRemove);
        balanceManager.removePlayerFromPlayersBalance(playerToRemove);
    }
}

