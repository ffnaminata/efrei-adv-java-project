package fr.diopfofana.efrei.monopoly.simulation;

import java.util.ArrayList;
import java.util.List;

public class Players {
    private final List<String> playersName;
    private final List<Boolean> playersIsInJail;
    private final List<Integer> playersRoundsInJail;

    public Players(List<String> playersName) {
        this.playersName = playersName;
        this.playersIsInJail = new ArrayList<>();
        this.playersRoundsInJail = new ArrayList<>();
        for (String playerName : playersName){
            playersIsInJail.add(false);
            playersRoundsInJail.add(0);
        }
    }

    public List<String> getPlayersName(){
        return playersName;
    }

    public boolean getPlayerIsInJail(String playerName){
        int playerIndex = playersName.indexOf(playerName);
        return playersIsInJail.get(playerIndex);
    }
    public void setPlayerIsInJail(String playerName, boolean playerIsInJail){
        int playerIndex = playersName.indexOf(playerName);
        playersIsInJail.set(playerIndex, playerIsInJail);
    }

    public Integer getPlayerRoundsInJail(String playerName){
        int playerIndex = playersName.indexOf(playerName);
        return playersRoundsInJail.get(playerIndex);
    }
    public void setPlayerRoundsInJail(String playerName, Integer roundsInJail){
        int playerIndex = playersName.indexOf(playerName);
        playersRoundsInJail.set(playerIndex, roundsInJail);
    }
}
