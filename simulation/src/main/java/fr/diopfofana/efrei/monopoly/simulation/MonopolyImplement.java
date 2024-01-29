package fr.diopfofana.efrei.monopoly.simulation;
import fr.arsenelapostolet.efrei.monopoly.Dices;
import fr.arsenelapostolet.efrei.monopoly.Location;
import fr.arsenelapostolet.efrei.monopoly.Monopoly;
import fr.arsenelapostolet.efrei.monopoly.OrderKind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.HashMap;


public class MonopolyImplement implements Monopoly {
    private Dices dices;
    private Map<String, Location> board;

    public MonopolyImplement(Dices dices, String filePath) {
        this.dices = dices;
        this.board = new HashMap<>();
        parseCSV(filePath);
    }

    private void parseCSV(String filePath) {
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
                board.put(name, location);
            }

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Location> getBoard() {
        return board;
    }

    @Override
    public void submitOrder(String playerName, OrderKind order) {
        // Implémentez cette méthode pour gérer les actions des joueurs
    }

    @Override
    public Map<String, Location> getPlayersLocation() {
        // Implémentez cette méthode pour renvoyer l'emplacement des joueurs sur le plateau
        return null;
    }
}
