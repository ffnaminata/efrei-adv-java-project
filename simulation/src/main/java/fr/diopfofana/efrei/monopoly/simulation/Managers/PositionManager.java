package fr.diopfofana.efrei.monopoly.simulation.Managers;

import fr.arsenelapostolet.efrei.monopoly.Location;
import fr.diopfofana.efrei.monopoly.simulation.Players;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionManager {
    private final Map<String, Location> playersLocation;

    public PositionManager(List<String> playersName, List<Location> board) {
        playersLocation = initialisePlayersPosition(playersName, board);
    }

    private Map<String, Location> initialisePlayersPosition(List<String> playersName, List<Location> board){
        Map<String, Location> playersLocation = new HashMap<>();
        for (String playerName : playersName) {
            playersLocation.put(playerName, board.get(0));
        }
        return playersLocation;
    }

    public Map<String, Location> getPlayersLocation(){
        return playersLocation;
    }

    public Location getPlayerLocation(String playerName){
        return playersLocation.get(playerName);
    }

    public void setPlayerLocation(String playerName, Location playerLocation){
        playersLocation.put(playerName, playerLocation);
    }

    public void toMove(int dicesResult, String playerName, Players players, GameManager gameManager, BalanceManager balanceManager, List<Location> board){
        if (!players.getPlayerIsInJail(playerName)){
            Location playerLocation = playersLocation.get(playerName);
            int playerLocationIndex = board.indexOf(playerLocation);
            int tempPlayerNewLocationIndex = playerLocationIndex + dicesResult;
            gameManager.checkIfPassByStartAndGain(playerName, tempPlayerNewLocationIndex, balanceManager);
            int playerNewLocationIndex = tempPlayerNewLocationIndex % board.size();
            playersLocation.put(playerName, board.get(playerNewLocationIndex));
        }
    }

    public void removePlayerFromPlayersPosition(String playerName){
        playersLocation.remove(playerName);
    }

}
