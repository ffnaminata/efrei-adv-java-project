package fr.diopfofana.efrei.monopoly.simulation;

import fr.arsenelapostolet.efrei.monopoly.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


public class Parser {
    public static void parseCSV(List<Location> board) {
        String filePath = "/monopoly.csv";
        try (InputStream inputStream = Parser.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            // Ignorer la première ligne du fichier CSV
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0].trim();
                Location.LocationKind kind = Location.LocationKind.valueOf(parts[1].trim().toUpperCase());
                String color = parts.length > 2 ? parts[2].trim() : "";
                int price = parts.length > 3 ? Integer.parseInt(parts[3].trim()) : 0;

                LocationImplement location = new LocationImplement(name, kind, color, price,0);
                board.add(location);
            }

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier CSV : " + e.getMessage(), e);
        }
    }

}
