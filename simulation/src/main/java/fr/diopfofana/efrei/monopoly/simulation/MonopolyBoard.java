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

public class MonopolyBoard implements Monopoly {
        private List<Case> board;

        public MonopolyBoard(String filePath) {
            this.board = new ArrayList<>();
            parseCSV();
        }

        public MonopolyBoard(Dices dices, List<String> playersName){
            parseCSV();
        }

        private void parseCSV() {
            try (InputStream inputStream = getClass().getResourceAsStream("/Users/djilydiop/Documents/EFREI_LSI/Semestre6/JAVA_avancee/efrei-adv-java-project/core/src/main/resources/monopoly.csv");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String name = parts[0].trim();
                    String kind = parts[1].trim();
                    String color = parts[2].trim();
                    int price = parts.length > 3 ? Integer.parseInt(parts[3].trim()) : 0;

                    Case boardCase = new Case(name, kind, color, price);
                    board.add(boardCase);
                }

            } catch (IOException | NumberFormatException e) {
                e.printStackTrace(); // Gérez les exceptions en conséquence
            }
        }

        // Ajoutez les méthodes nécessaires pour accéder aux cases du plateau

        public List<Case> getBoard() {
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


