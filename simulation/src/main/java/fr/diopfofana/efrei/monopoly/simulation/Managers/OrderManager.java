package fr.diopfofana.efrei.monopoly.simulation.Managers;

import fr.arsenelapostolet.efrei.monopoly.Location;
import fr.diopfofana.efrei.monopoly.simulation.Managers.BalanceManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static fr.arsenelapostolet.efrei.monopoly.Location.LocationKind.JAIL;

public class OrderManager {
    public void idle(int dicesResult, String playerName, List<String> playersName, GameManager gameManager, PositionManager positionManager, List<Location> board){
        String nextPlayerName = gameManager.getNextPlayerName(playerName, playersName);
        positionManager.toMove(dicesResult, nextPlayerName, board);
    }

    public void buy(String playerName, PositionManager positionManager, BalanceManager balanceManager){
        Location playerLocation = positionManager.getPlayerLocation(playerName);
        BigDecimal amount = BigDecimal.valueOf(playerLocation.getPrice());
        if (Objects.equals(playerLocation.getOwner(), "") && balanceManager.canAfford(amount, playerName)){
            if (playerLocation.getKind() == Location.LocationKind.PROPERTY ||
                    playerLocation.getKind() == Location.LocationKind.STATION ||
                    playerLocation.getKind() == Location.LocationKind.COMPANY ){
                balanceManager.toDebit(amount, playerName);
                playerLocation.setOwner(playerName);
            }
        }
    }

    public void pay_prison(String playerName, PositionManager positionManager , BalanceManager balanceManager){
        Location playerLocation = positionManager.getPlayerLocation(playerName);
        if (playerLocation.getKind() == JAIL){
            BigDecimal amount = BigDecimal.valueOf(50);
            balanceManager.toDebit(amount, playerName);
        }
    }
}
