package fr.diopfofana.efrei.monopoly.simulation;

import fr.arsenelapostolet.efrei.monopoly.Location;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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

    public static void rentParser(List<String[]> rawData) {
        try (InputStream inputStream = Parser.class.getResourceAsStream("/rent.csv");
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            reader.lines()
                    .skip(1) // Skip the first line
                    .map(line -> line.split(","))
                    .forEach(rawData::add);

        } catch (IOException e) {
            throw new UncheckedIOException("Error: Problem while parsing the file", e);
        }
    }

    public static void parseCSV1(List<Location> board) {
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

                String name = parts1[0].trim();
                Location.LocationKind kind = Location.LocationKind.valueOf(parts1[1].trim().toUpperCase());
                String color = parts1.length > 2 ? parts1[2].trim() : "";
                int price = parts1.length > 3 ? Integer.parseInt(parts1[3].trim()) : 0;
                int bare = 0;

                if (kind == Location.LocationKind.PROPERTY){
                    line2 = reader2.readLine();
                    String[] parts2 = line2.split(",");
                    bare = parts2.length > 7 ? Integer.parseInt(parts2[7].trim()) : 0;
                }

                LocationImplement location = new LocationImplement(name, kind, color, price, bare);
                board.add(location);
            }

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Erreur lors de la lecture des fichiers CSV : " + e.getMessage(), e);
        }
    }

    public static void parseCSV2(List<Location> board) {
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

                if (parts1.length > 1) { // Vérifier la présence du champ 'kind'
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
