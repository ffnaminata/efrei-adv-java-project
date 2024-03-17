package fr.diopfofana.efrei.monopoly.simulation;

import fr.arsenelapostolet.efrei.monopoly.Location;
import fr.diopfofana.efrei.monopoly.simulation.Locations.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Parser {
    public List<Location> loadMonopolyDataFromCSV() {
        List<Location> board = new ArrayList<>();

        String monopolyFilePath = "/monopoly.csv";
        String rentFilePath = "/rent.csv";

        try (
                InputStream monopolyStream = Parser.class.getResourceAsStream(monopolyFilePath);
                BufferedReader monopolyReader = (monopolyStream != null) ? new BufferedReader(new InputStreamReader(monopolyStream)) : null;

                InputStream rentStream = Parser.class.getResourceAsStream(rentFilePath);
                BufferedReader rentReader = (rentStream != null) ? new BufferedReader(new InputStreamReader(rentStream)) : null
        ) {
            if (monopolyReader == null || rentReader == null) {
                throw new RuntimeException("Error while reading CSV files: Unable to open the files.");
            }

            monopolyReader.readLine();
            rentReader.readLine();

            String line1, line2;
            while ((line1 = monopolyReader.readLine()) != null) {
                String[] parts1 = line1.split(",");
                if (parts1.length > 1) {
                    String name = parts1[0].trim();
                    Location.LocationKind kind = Location.LocationKind.valueOf(parts1[1].trim().toUpperCase());
                    String color = parts1.length > 2 ? parts1[2].trim() : "";
                    int price = parts1.length > 3 ? Integer.parseInt(parts1[3].trim()) : 0;
                    int cost = 0;
                    int oneHouse = 0;
                    int twoHouses = 0;
                    int threeHouses = 0;
                    int fourHouses = 0;
                    int fiveHouses = 0;
                    int bare = 0;
                    if (kind == Location.LocationKind.PROPERTY){
                        line2 = rentReader.readLine();
                        String[] parts2 = line2.split(",");
                        if (parts2.length > 1){
                            cost = Integer.parseInt(parts2[1].trim());
                            oneHouse = Integer.parseInt(parts2[2].trim());
                            twoHouses = Integer.parseInt(parts2[3].trim());
                            threeHouses = Integer.parseInt(parts2[4].trim());
                            fourHouses = Integer.parseInt(parts2[5].trim());
                            fiveHouses = Integer.parseInt(parts2[6].trim());
                            bare = Integer.parseInt(parts2[7].trim());
                        }
                    }
                    Location location = createLocation(name, kind, color, price, cost, oneHouse, twoHouses, threeHouses, fourHouses, fiveHouses, bare);
                    board.add(location);
                }
            }

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error while reading CSV files: " + e.getMessage(), e);
        }
        return board;
    }

    private static Location createLocation(String name, Location.LocationKind kind, String color, int price, int cost, int oneHouse, int twoHouses, int threeHouses, int fourHouses, int fiveHouses, int bare) {
        Location location;
        switch (kind) {
            case COMPANY -> location = new LocationCompany(name, kind, color, price, cost, oneHouse, twoHouses, threeHouses, fourHouses, fiveHouses, bare);
            case PROPERTY -> location = new LocationProperty(name, kind, color, price, cost, oneHouse, twoHouses, threeHouses, fourHouses, fiveHouses,  bare);
            case STATION -> location = new LocationStation(name, kind, color, price, cost, oneHouse, twoHouses, threeHouses, fourHouses, fiveHouses, bare);
            case TAX -> location = new LocationTax(name, kind, color, price, cost, oneHouse, twoHouses, threeHouses, fourHouses, fiveHouses, bare);
            default -> location = new LocationImplement(name, kind, color, price, cost, oneHouse, twoHouses, threeHouses, fourHouses, fiveHouses, bare);
        }
        return location;
    }
}
