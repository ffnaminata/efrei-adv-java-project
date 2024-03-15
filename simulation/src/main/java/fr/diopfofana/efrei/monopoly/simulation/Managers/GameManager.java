package fr.diopfofana.efrei.monopoly.simulation.Managers;

import fr.arsenelapostolet.efrei.monopoly.GameFinishedException;
import fr.arsenelapostolet.efrei.monopoly.Location;

import java.util.List;
import java.util.Map;

public class GameManager {
    public void throwExceptionIfGameFinished(PositionManager positionManager){
        if (positionManager.getPlayersLocation().size() < 2) {
            throw new GameFinishedException();
        }
    }

    public void removePlayer(String playerToRemove, PositionManager positionManager, BalanceManager balanceManager){
        positionManager.removePlayerFromPlayersPosition(playerToRemove);
        balanceManager.removePlayerFromPlayersBalance(playerToRemove);
    }

    public String getNextPlayerName(String playerName, List<String> playersName ){
        int currentPlayerIndex = playersName.indexOf(playerName);
        int nextPlayerIndex = (currentPlayerIndex + 1) % playersName.size();
        return playersName.get(nextPlayerIndex);
    }
}

