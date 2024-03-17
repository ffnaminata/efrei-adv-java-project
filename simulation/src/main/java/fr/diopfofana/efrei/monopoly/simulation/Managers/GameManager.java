package fr.diopfofana.efrei.monopoly.simulation.Managers;

import fr.arsenelapostolet.efrei.monopoly.GameFinishedException;
import fr.arsenelapostolet.efrei.monopoly.Location;
import fr.diopfofana.efrei.monopoly.simulation.Locations.LocationTax;
import fr.diopfofana.efrei.monopoly.simulation.Players;

import java.math.BigDecimal;
import java.util.List;

public class GameManager {
    public void throwExceptionIfGameFinished(PositionManager positionManager){
        if (positionManager.getPlayersLocation().size() < 2) {
            throw new GameFinishedException();
        }
    }

    public void removePlayer(String playerToRemove, PositionManager positionManager, BalanceManager balanceManager){
        positionManager.removePlayerFromPlayersPosition(playerToRemove);
        balanceManager.removePlayerFromPlayersBalance(playerToRemove);
    }

    public String getNextPlayerName(String playerName, List<String> playersName ){
        int currentPlayerIndex = playersName.indexOf(playerName);
        int nextPlayerIndex = (currentPlayerIndex + 1) % playersName.size();
        return playersName.get(nextPlayerIndex);
    }

    public void checkIfGoToJailAndGo(String playerName, Players players, PositionManager positionManager, List<Location> board){
        Location playerLocation = positionManager.getPlayerLocation(playerName);
        if (playerLocation.getKind() == Location.LocationKind.GO_TO_JAIL){
            positionManager.setPlayerLocation(playerName, board.get(10));
            players.setPlayerIsInJail(playerName, true);
        }
    }

    public void updateRoundsInJail(String playerName, Players players){
        if (players.getPlayerIsInJail(playerName)) {
            players.setPlayerRoundsInJail(playerName, players.getPlayerRoundsInJail(playerName) + 1);
        }
    }

    public void checkIfThirdRoundInJailAndPay(String playerName, Players players, OrderManager orderManager, PositionManager positionManager, BalanceManager balanceManager, GameManager gameManager, int dicesResult, List<Location> board){
        if (players.getPlayerRoundsInJail(playerName) == 2){
            orderManager.pay_prison(playerName, players, positionManager, balanceManager, gameManager,  dicesResult, board);
        }
    }

    public void checkIfRentToPayAndPay(String playerName, Players players, int dicesResult, PositionManager positionManager, BalanceManager balanceManager, GameManager gameManager, List<Location> board){
        Location playerLocation = positionManager.getPlayerLocation(playerName);
        if (!playerLocation.getOwner().isEmpty()){
            int numberOfStationOwnedByLocationOwner = players.getPlayersNumberOfStationOwned(playerLocation.getOwner());
            BigDecimal rentAmount = playerLocation.getRent(dicesResult, numberOfStationOwnedByLocationOwner, board);
            balanceManager.toDeal(playerName, playerLocation.getOwner(), rentAmount, gameManager, positionManager);
        }
    }

    public void checkIfTaxToPayAndPay(String playerName, PositionManager positionManager, BalanceManager balanceManager, GameManager gameManager){
        Location playerLocation = positionManager.getPlayerLocation(playerName);
        if (playerLocation instanceof LocationTax){
            BigDecimal taxAmount = BigDecimal.valueOf(playerLocation.getPrice());
            balanceManager.toDebit(playerName, taxAmount, gameManager, positionManager);
        }
    }

    public void checkIfPassByStartAndGain(String playerName, int tempPlayerNewLocationIndex, BalanceManager balanceManager){
        int startPassCount = tempPlayerNewLocationIndex / 40;
        if (startPassCount > 0){
            BigDecimal gain = new BigDecimal(200 * startPassCount);
            balanceManager.toCredit(playerName, gain);
        }
    }
}

