package fr.diopfofana.efrei.monopoly.simulation;

import java.util.ArrayList;
import java.util.List;

public class Players {
    private final List<String> playersName;
    private final List<Boolean> playersIsInJail;
    private final List<Integer> playersRoundsInJail;
    private final List<Integer> playersNumberOfStationOwned;

    public Players(List<String> playersName) {
        this.playersName = playersName;
        this.playersIsInJail = new ArrayList<>();
        this.playersRoundsInJail = new ArrayList<>();
        this.playersNumberOfStationOwned = new ArrayList<>();
        for (String playerName : playersName){
            playersIsInJail.add(false);
            playersRoundsInJail.add(-1);
            playersNumberOfStationOwned.add(0);
        }
    }

    public List<String> getPlayersName(){
        return playersName;
    }

    public boolean getPlayerIsInJail(String playerName){
        int playerIndex = playersName.indexOf(playerName);
        return playersIsInJail.get(playerIndex);
    }
    public void setPlayerIsInJail(String playerName, boolean isInJail){
        int playerIndex = playersName.indexOf(playerName);
        playersIsInJail.set(playerIndex, isInJail);
    }

    public Integer getPlayerRoundsInJail(String playerName){
        int playerIndex = playersName.indexOf(playerName);
        return playersRoundsInJail.get(playerIndex);
    }
    public void setPlayerRoundsInJail(String playerName, Integer roundsInJail){
        int playerIndex = playersName.indexOf(playerName);
        playersRoundsInJail.set(playerIndex, roundsInJail);
    }

    public Integer getPlayersNumberOfStationOwned(String playerName){
        int playerIndex = playersName.indexOf(playerName);
        return playersNumberOfStationOwned.get(playerIndex);
    }
    public void setPlayersNumberOfStationOwned(String playerName, Integer numberOfStationOwned){
        int playerIndex = playersName.indexOf(playerName);
        playersNumberOfStationOwned.set(playerIndex, numberOfStationOwned);
    }
}
