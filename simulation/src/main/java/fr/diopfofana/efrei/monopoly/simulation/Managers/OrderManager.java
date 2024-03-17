package fr.diopfofana.efrei.monopoly.simulation.Managers;

import fr.arsenelapostolet.efrei.monopoly.Dices;
import fr.arsenelapostolet.efrei.monopoly.Location;
import fr.diopfofana.efrei.monopoly.simulation.Board;
import fr.diopfofana.efrei.monopoly.simulation.Locations.LocationImplement;
import fr.diopfofana.efrei.monopoly.simulation.Players;

import javax.swing.plaf.PanelUI;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static fr.arsenelapostolet.efrei.monopoly.Location.LocationKind.JAIL;
import static fr.arsenelapostolet.efrei.monopoly.Location.LocationKind.STATION;

public class OrderManager {
    public void idle(int dicesResult, String playerName, Players players, GameManager gameManager, PositionManager positionManager, BalanceManager balanceManager, List<Location> board){
        String nextPlayerName = gameManager.getNextPlayerName(playerName, players.getPlayersName());
        positionManager.toMove(dicesResult, nextPlayerName, players, gameManager, balanceManager, board);
    }

    public void buy(String playerName, Players players, PositionManager positionManager, BalanceManager balanceManager, GameManager gameManager){
        Location playerLocation = positionManager.getPlayerLocation(playerName);
        BigDecimal amount = BigDecimal.valueOf(playerLocation.getPrice());
        if (Objects.equals(playerLocation.getOwner(), "") && balanceManager.canAfford(amount, playerName)){
            if (playerLocation.getKind() == Location.LocationKind.PROPERTY ||
                    playerLocation.getKind() == Location.LocationKind.STATION ||
                    playerLocation.getKind() == Location.LocationKind.COMPANY ){
                balanceManager.toDebit(playerName, amount, gameManager, positionManager);
                playerLocation.setOwner(playerName);
                if (playerLocation.getKind() == STATION){
                    players.setPlayersNumberOfStationOwned( playerName, players.getPlayersNumberOfStationOwned(playerName) + 1);
                }
            }
        }
    }

    public void pay_prison(String playerName, Players players, PositionManager positionManager , BalanceManager balanceManager, GameManager gameManager, int dicesResult, List<Location> board){
        if (players.getPlayerRoundsInJail(playerName) > 0 ){
            BigDecimal amount = BigDecimal.valueOf(50);
            balanceManager.toDebit(playerName, amount, gameManager, positionManager);
            players.setPlayerIsInJail(playerName, false);
            players.setPlayerRoundsInJail(playerName, 0);
            positionManager.toMove(dicesResult, playerName, players, gameManager, balanceManager, board);
        }
    }
    public void build(String playerName, String locationName, PositionManager positionManager, BalanceManager balanceManager, GameManager gameManager, Board board){
        Location location = positionManager.getLocationFromLocationName(locationName, board.getBoard());
        if (board.isOwnerOfAllColorLocation(playerName, location.getColor()) && location.getNumberOfBuilding() < 5){
            balanceManager.toDebit(playerName, BigDecimal.valueOf(location.getCost()), gameManager, positionManager);
            if (balanceManager.getPlayersBalance().containsKey(playerName)){
                location.setNumberOfBuilding(location.getNumberOfBuilding() + 1);
            }
        }
    }
}
