package fr.diopfofana.efrei.monopoly.simulation;

import fr.arsenelapostolet.efrei.monopoly.Location;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class Parser {
    public static void parseCSV(List<Location> board) {
        String filePath1 = "/monopoly.csv";
        String filePath2 = "/rent.csv";

        try (
                InputStream inputStream1 = Parser.class.getResourceAsStream(filePath1);
                BufferedReader reader1 = new BufferedReader(new InputStreamReader(inputStream1));

                InputStream inputStream2 = Parser.class.getResourceAsStream(filePath2);
                BufferedReader reader2 = new BufferedReader(new InputStreamReader(inputStream2))
        ) {
            reader1.readLine();
            reader2.readLine();

            String line1, line2;
            while ((line1 = reader1.readLine()) != null) {
                String[] parts1 = line1.split(",");

                if (parts1.length > 1) {
                    String name = parts1[0].trim();
                    Location.LocationKind kind = Location.LocationKind.valueOf(parts1[1].trim().toUpperCase());
                    String color = parts1.length > 2 ? parts1[2].trim() : "";
                    int price = parts1.length > 3 ? Integer.parseInt(parts1[3].trim()) : 0;
                    int bare = 0;

                    if (kind == Location.LocationKind.PROPERTY) {
                        line2 = reader2.readLine();
                        if (line2 != null) {
                            String[] parts2 = line2.split(",");
                            bare = parts2.length > 7 ? Integer.parseInt(parts2[7].trim()) : 0;
                        }
                    }

                    LocationImplement location = new LocationImplement(name, kind, color, price, bare);
                    board.add(location);
                }
            }

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Erreur lors de la lecture des fichiers CSV : " + e.getMessage(), e);
        }
    }
}
