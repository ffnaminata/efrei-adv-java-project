package fr.diopfofana.efrei.monopoly.simulation;
import fr.arsenelapostolet.efrei.monopoly.Dices;
import fr.arsenelapostolet.efrei.monopoly.Location;
import fr.arsenelapostolet.efrei.monopoly.Monopoly;
import fr.arsenelapostolet.efrei.monopoly.OrderKind;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;



public class MonopolyImplement implements Monopoly {
    private final Dices dices;
    private final List<Location> board;
    private final List<String> playersName;
    private final Map<String, Location> playersLocation;
    private int currentPlayerIndex;
    private boolean firstOrderSubmitted;


    public MonopolyImplement(Dices dices, List<String> playersName) {
        this.dices = dices;
        this.playersName = playersName;
        this.board = new ArrayList<>();
        this.playersLocation = new HashMap<>();
        this.currentPlayerIndex=0;
        Parser.parseCSV(this.board);
        initialisePlayersPosition();
        firstOrderSubmitted(playersName.get(this.currentPlayerIndex), OrderKind.IDLE);
        }



    // Initialiser la position de chaque joueur
    public void initialisePlayersPosition(){
        for (String playerName : this.playersName) {
            // Initialiser la position du joueur au début du plateau
            Location playerLocation = this.board.get(0); // Position de départ
            this.playersLocation.put(playerName, playerLocation);
        }
    }


    public List<Location> getBoard() {
        return board;
    }

    public void firstOrderSubmitted (String playerName, OrderKind order){
        Location currentLocation = playersLocation.get(playerName);
        int IndexOfCurrentLocation = this.board.indexOf(currentLocation);

        if (order == OrderKind.IDLE) {
            playersLocation.put(playerName, board.get(IndexOfCurrentLocation + this.dices.throwTwoSixSidedDices()));
        }
    }
    @Override
    public void submitOrder(String playerName, OrderKind order) {
        int nextPlayerIndex = (currentPlayerIndex + 1) % playersName.size();
            if(!playersName.get(this.currentPlayerIndex).equals(playerName)){
                //
            }
            else {
                playerName= playersName.get(nextPlayerIndex);//FaireAvancer le joueur suivant
                Location currentLocation = playersLocation.get(playerName);
                int IndexOfCurrentLocation = this.board.indexOf(currentLocation);

                if (order == OrderKind.IDLE) {
                        int nextLocationIndex = (IndexOfCurrentLocation + this.dices.throwTwoSixSidedDices()) %board.size();
                        playersLocation.put(playerName, board.get(nextLocationIndex));
                    }

                currentPlayerIndex = nextPlayerIndex ;
                }
    }

    @Override
    public Map<String, Location> getPlayersLocation() {
        return  new HashMap<>(playersLocation);
    }
}
