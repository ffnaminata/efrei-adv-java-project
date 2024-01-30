package fr.diopfofana.efrei.monopoly.simulation;
import fr.arsenelapostolet.efrei.monopoly.Dices;
import fr.arsenelapostolet.efrei.monopoly.Location;
import fr.arsenelapostolet.efrei.monopoly.Monopoly;
import fr.arsenelapostolet.efrei.monopoly.OrderKind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class MonopolyImplement implements Monopoly {
    private Dices dices;
    private List<Location> board;
    private List<String> playersName;
    private Map<String, Location> playersLocation;


    public MonopolyImplement(Dices dices, List<String> playersName) {
        this.dices = dices;
        this.playersName = playersName;
        this.board = new ArrayList<>();
        parseCSV();

        // Initialiser la position de chaque joueur
        for (String playerName : playersName) {
                // Initialiser la position du joueur au début du plateau
                Location playerLocation = board.get(1); // Position de départ
                this.playersLocation.put(playerName, playerLocation);
            }
        }



    private void parseCSV() {
        String filePath ="/monopoly.csv";
        try (InputStream inputStream = getClass().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0].trim();
                Location.LocationKind kind = Location.LocationKind.valueOf(parts[1].trim().toUpperCase());
                String color = parts[2].trim();
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
        Location currentLocation = playersLocation.get(playerName);
        int IndexOfCurrentLocation= this.board.indexOf(currentLocation);
       if (order == OrderKind.IDLE){
           if (IndexOfCurrentLocation== board.size()){//If we get the end of the board we restart at the index 0
               playersLocation.put(playerName, board.get(this.dices.throwTwoSixSidedDices()));
           }
           else {
               playersLocation.put(playerName, board.get(IndexOfCurrentLocation + this.dices.throwTwoSixSidedDices()));
           }
       }
    }

    @Override
    public Map<String, Location> getPlayersLocation() {
        return  playersLocation;
    }
}
