package fr.diopfofana.efrei.monopoly.simulation;

import fr.arsenelapostolet.efrei.monopoly.Location;

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

            String line1;
            while ((line1 = monopolyReader.readLine()) != null) {
                processMonopolyCSVLine(line1, rentReader, board);
            }

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Error while reading CSV files: " + e.getMessage(), e);
        }
        return board;
    }

    private static void processMonopolyCSVLine(String line1, BufferedReader reader2, List<Location> board) throws IOException, NumberFormatException {
        String[] parts1 = line1.split(",");
        if (parts1.length > 1) {
            String name = parts1[0].trim();
            Location.LocationKind kind = Location.LocationKind.valueOf(parts1[1].trim().toUpperCase());
            String color = parts1.length > 2 ? parts1[2].trim() : "";
            int price = parts1.length > 3 ? Integer.parseInt(parts1[3].trim()) : 0;
            int bare = (kind == Location.LocationKind.PROPERTY) ? readBareFromRentFile(reader2) : 0;

            Location location = createLocation(name, kind, color, price, bare);
            board.add(location);
        }
    }

    private static int readBareFromRentFile(BufferedReader reader) throws IOException, NumberFormatException {
        String line = reader.readLine();
        String[] parts = (line != null) ? line.split(",") : new String[0];
        return (parts.length > 7) ? Integer.parseInt(parts[7].trim()) : 0;
    }

    private static Location createLocation(String name, Location.LocationKind kind, String color, int price, int bare) {
        Location location;
        switch (kind) {
            case COMPANY -> location = new LocationCompany(name, kind, color, price, bare);
            case PROPERTY -> location = new LocationProperty(name, kind, color, price, bare);
            case STATION -> location = new LocationStation(name, kind, color, price, bare);
            default -> location = new LocationImplement(name, kind, color, price, bare);
        }
        return location;
    }
}

