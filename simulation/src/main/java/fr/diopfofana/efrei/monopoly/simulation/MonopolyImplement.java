package fr.diopfofana.efrei.monopoly.simulation;
import fr.arsenelapostolet.efrei.monopoly.Dices;
import fr.arsenelapostolet.efrei.monopoly.Location;
import fr.arsenelapostolet.efrei.monopoly.Monopoly;
import fr.arsenelapostolet.efrei.monopoly.OrderKind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MonopolyImplement implements Monopoly {
    private Dices dices;
    private List<LocationImplement> board;

    public MonopolyImplement(String filePath, Dices dices) {
        this.dices = dices;
        this.board = new ArrayList<>();
        parseCSV();
    }

    public MonopolyImplement(Dices dices, List<String> playersName){
        parseCSV();
        this.dices = dices;
    }

    private void parseCSV() {
        String filePath = "/Users/aminata/Documents/cours/Semestre 6/JavaAvance/efrei-adv-java-project/core/src/main/resources/monopoly.csv";
        try (InputStream inputStream = getClass().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0].trim();
                String kind = parts[1].trim().toUpperCase();
                String color = parts[2].trim();
                int price = parts.length > 3 ? Integer.parseInt(parts[3].trim()) : 0;

                LocationImplement boardCase = new LocationImplement(name, kind, color, price);
                board.add(boardCase);
            }

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public List<LocationImplement> getBoard() {
        return board;
    }

    @Override
    public void submitOrder(String playerName, OrderKind order) {

    }

    @Override
    public Map<String, Location> getPlayersLocation() {
        return null;
    }
}


