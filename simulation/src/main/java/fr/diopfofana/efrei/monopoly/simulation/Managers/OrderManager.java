package fr.diopfofana.efrei.monopoly.simulation.Managers;

import fr.arsenelapostolet.efrei.monopoly.Location;
import fr.diopfofana.efrei.monopoly.simulation.Managers.BalanceManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrderManager {
    public void idle(int dicesResult, String playerName, Map<String, Location> playersLocation, List<Location> board){
        Location playerLocation = playersLocation.get(playerName);
        int playerLocationIndex = board.indexOf(playerLocation);
        int  newPlayerLocationIndex = (playerLocationIndex + dicesResult) % board.size();
        playersLocation.put(playerName, board.get(newPlayerLocationIndex));

    }

    public void buy(String playerName, Location location, BalanceManager balanceManager){
        if (Objects.equals(location.getOwner(), "") && balanceManager.canAfford(BigDecimal.valueOf(location.getPrice()), playerName)){
            if (location.getKind() == Location.LocationKind.PROPERTY ||
                    location.getKind() == Location.LocationKind.STATION ||
                    location.getKind() == Location.LocationKind.COMPANY ){
                balanceManager.setPlayerBalance(playerName, balanceManager.getPlayerBalance(playerName).subtract(BigDecimal.valueOf(location.getPrice())));
                location.setOwner(playerName);
            }
        }
    }
}
