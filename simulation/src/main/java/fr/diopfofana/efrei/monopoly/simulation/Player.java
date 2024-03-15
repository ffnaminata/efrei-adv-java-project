package fr.diopfofana.efrei.monopoly.simulation;

import java.util.List;

public class Player {
    private final String playerName;
    private boolean isInJail;

    public Player(String playerName){
        this.playerName = playerName;
    }

    public String getPlayerName(){
        return playerName;
    }

    public boolean getIsInJail(){
        return isInJail;
    }
}
