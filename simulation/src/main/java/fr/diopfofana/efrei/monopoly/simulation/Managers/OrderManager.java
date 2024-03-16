package fr.diopfofana.efrei.monopoly.simulation.Managers;

import fr.arsenelapostolet.efrei.monopoly.Dices;
import fr.arsenelapostolet.efrei.monopoly.Location;
import fr.diopfofana.efrei.monopoly.simulation.Players;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static fr.arsenelapostolet.efrei.monopoly.Location.LocationKind.JAIL;

public class OrderManager {
    public void idle(int dicesResult, String playerName, Players players, GameManager gameManager, PositionManager positionManager, List<Location> board){
        String nextPlayerName = gameManager.getNextPlayerName(playerName, players.getPlayersName());
        positionManager.toMove(dicesResult, nextPlayerName, players, board);
    }

    public void buy(String playerName, PositionManager positionManager, BalanceManager balanceManager, GameManager gameManager){
        Location playerLocation = positionManager.getPlayerLocation(playerName);
        BigDecimal amount = BigDecimal.valueOf(playerLocation.getPrice());
        if (Objects.equals(playerLocation.getOwner(), "") && balanceManager.canAfford(amount, playerName)){
            if (playerLocation.getKind() == Location.LocationKind.PROPERTY ||
                    playerLocation.getKind() == Location.LocationKind.STATION ||
                    playerLocation.getKind() == Location.LocationKind.COMPANY ){
                balanceManager.toDebit(amount, playerName, gameManager, positionManager);
                playerLocation.setOwner(playerName);
            }
        }
    }

    public void pay_prison(String playerName, Players players, PositionManager positionManager , BalanceManager balanceManager, GameManager gameManager, int dicesResult, List<Location> board){
        if (players.getPlayerRoundsInJail(playerName) > 0 ){
            BigDecimal amount = BigDecimal.valueOf(50);
            balanceManager.toDebit(amount, playerName, gameManager, positionManager);
            players.setPlayerIsInJail(playerName, false);
            players.setPlayerRoundsInJail(playerName, 0);
            positionManager.toMove(dicesResult, playerName, players, board);
        }
    }
}
