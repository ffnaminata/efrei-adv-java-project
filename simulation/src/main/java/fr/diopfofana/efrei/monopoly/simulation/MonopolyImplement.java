package fr.diopfofana.efrei.monopoly.simulation;
import fr.arsenelapostolet.efrei.monopoly.Dices;
import fr.arsenelapostolet.efrei.monopoly.Location;
import fr.arsenelapostolet.efrei.monopoly.Monopoly;
import fr.arsenelapostolet.efrei.monopoly.OrderKind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;



public class MonopolyImplement implements Monopoly {
    private Dices dices;
    private List<Location> board;
    private List<String> playersName;
    private Map<String, Location> playersLocation;
    private int currentPlayerIndex;
    private boolean firstOrderSubmitted;


    public MonopolyImplement(Dices dices, List<String> playersName) {
        this.dices = dices;
        this.playersName = playersName;
        this.board = new ArrayList<>();
        this.playersLocation = new HashMap<>();
        this.currentPlayerIndex=0;
        this.firstOrderSubmitted = false;
        parseCSV();

        // Initialiser la position de chaque joueur
        for (String playerName : playersName) {
                // Initialiser la position du joueur au début du plateau
                Location playerLocation = board.get(0); // Position de départ
                this.playersLocation.put(playerName, playerLocation);
            }

        submitOrder(playersName.get(this.currentPlayerIndex), OrderKind.IDLE);
        }



    private void parseCSV() {
        String filePath ="/monopoly.csv";
        try (InputStream inputStream = getClass().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {//Pour ignorer la premiere ligne du fichier csv
                    isFirstLine = false;
                    continue; // Passer à la ligne suivante
                }
                String[] parts = line.split(",");
                String name = parts[0].trim();
                Location.LocationKind kind = Location.LocationKind.valueOf(parts[1].trim().toUpperCase());
                String color = parts.length > 2 ? parts[2].trim() : "";
                int price = parts.length > 3 ? Integer.parseInt(parts[3].trim()) : 0;

                LocationImplement location = new LocationImplement(name, kind, color, price);
                this.board.add(location);
            }

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public List<Location> getBoard() {
        return board;
    }

    @Override
    public void submitOrder(String playerName, OrderKind order) {

        // Vérifiez si c'est le premier appel de la fonction
        if (!firstOrderSubmitted) {
            Location currentLocation = playersLocation.get(playerName);
            int IndexOfCurrentLocation = this.board.indexOf(currentLocation);

            if (order == OrderKind.IDLE) {
                    playersLocation.put(playerName, board.get(IndexOfCurrentLocation + this.dices.throwTwoSixSidedDices()));
                }

            firstOrderSubmitted = true; //  le premier ordre a été soumis
        }else{
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
    }

    @Override
    public Map<String, Location> getPlayersLocation() {
        return  playersLocation;
    }
}
