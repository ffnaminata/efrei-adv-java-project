package fr.diopfofana.efrei.monopoly.simulation;

import fr.arsenelapostolet.efrei.monopoly.Dices;
import fr.arsenelapostolet.efrei.monopoly.Location;
import fr.arsenelapostolet.efrei.monopoly.OrderKind;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrderManager {
    public static void idle(int dicesResult, String playerName, Map<String, Location> playersLocation, List<Location> board){
        Location playerLocation = playersLocation.get(playerName);
        int playerLocationIndex = board.indexOf(playerLocation);
        int  newPlayerLocationIndex = (playerLocationIndex + dicesResult) % board.size();
        playersLocation.put(playerName, board.get(newPlayerLocationIndex));

    }

    public static void buy(String playerName, Location location, Map<String, BigDecimal> playersBalance){
        if (Objects.equals(location.getOwner(), "") && playersBalance.get(playerName).compareTo(BigDecimal.valueOf(location.getPrice())) >= 0 ){
            if (location.getKind() == Location.LocationKind.PROPERTY ||
                    location.getKind() == Location.LocationKind.STATION ||
                    location.getKind() == Location.LocationKind.COMPANY ){
                playersBalance.put(playerName, playersBalance.get(playerName).subtract(BigDecimal.valueOf(location.getPrice())));
                location.setOwner(playerName);
            }
        }
    }
}
