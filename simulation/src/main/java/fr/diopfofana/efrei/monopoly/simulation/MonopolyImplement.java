package fr.diopfofana.efrei.monopoly.simulation;
import fr.arsenelapostolet.efrei.monopoly.Dices;
import fr.arsenelapostolet.efrei.monopoly.Location;
import fr.arsenelapostolet.efrei.monopoly.Monopoly;
import fr.arsenelapostolet.efrei.monopoly.OrderKind;

import java.math.BigDecimal;
import java.util.*;


public class MonopolyImplement implements Monopoly {
    private final Dices dices;
    private final List<Location> board;
    private final List<String> playersName;
    private final Map<String, Location> playersLocation;
    private  Map<String, BigDecimal> playersBalance;
    private int currentPlayerIndex;
    //private boolean firstOrderSubmitted;


    public MonopolyImplement(Dices dices, List<String> playersName) {
        this.dices = dices;
        this.playersName = playersName;
        this.board = new ArrayList<>();
        this.playersLocation = new HashMap<>();
        this.playersBalance = new HashMap<>();
        this.currentPlayerIndex = 0;
        Parser.parseCSV2(this.board);
        initialisePlayersPosition();
        initialisePlayersBalance();
        submitOrder(playersName.get(playersName.size() - 1), OrderKind.IDLE);
        //firstOrderSubmitted(playersName.get(this.currentPlayerIndex), OrderKind.IDLE);
        }

    public void initialisePlayersPosition(){
        for (String playerName : this.playersName) {
            Location playerLocation = this.board.get(0);
            this.playersLocation.put(playerName, playerLocation);
        }
    }

    private void initialisePlayersBalance(){
        BigDecimal playerBalance = new BigDecimal("1500");
        for (String playerName : this.playersName) {
            this.playersBalance.put(playerName, playerBalance);
        }
    }

    public void firstOrderSubmitted (String playerName, OrderKind order){
        Location currentLocation = playersLocation.get(playerName);
        int IndexOfCurrentLocation = this.board.indexOf(currentLocation);

        if (order == OrderKind.IDLE) {
            playersLocation.put(playerName, board.get(IndexOfCurrentLocation + this.dices.throwTwoSixSidedDices()));
        }
    }
    @Override
    public void submitOrder(String playerName, OrderKind order) {
        int currentPlayerIndex = playersName.indexOf(playerName);
        Location currentPlayerLocation = playersLocation.get(playerName);

        if (order == OrderKind.BUY && Objects.equals(currentPlayerLocation.getOwner(), "")){
            if (currentPlayerLocation.getKind() == Location.LocationKind.PROPERTY ||
                    currentPlayerLocation.getKind() == Location.LocationKind.STATION ||
                    currentPlayerLocation.getKind() == Location.LocationKind.COMPANY ){
                playersBalance.put(playerName, playersBalance.get(playerName).subtract(BigDecimal.valueOf(currentPlayerLocation.getPrice())));
                currentPlayerLocation.setOwner(playerName);
            }
        }

        int nextPlayerIndex = (currentPlayerIndex + 1) % playersName.size();
        String nextPlayerName = playersName.get(nextPlayerIndex);
        Location nextPlayerLocation = playersLocation.get(nextPlayerName);

        if (this.currentPlayerIndex == nextPlayerIndex){
            int IndexOfNextPlayerLocation = this.board.indexOf(nextPlayerLocation);

            int newNextPlayerLocationIndex = (IndexOfNextPlayerLocation + this.dices.throwTwoSixSidedDices()) % board.size();
            playersLocation.put(nextPlayerName, board.get(newNextPlayerLocationIndex));
            nextPlayerLocation = playersLocation.get(nextPlayerName);

            this.currentPlayerIndex = (nextPlayerIndex + 1) % playersName.size();
        }

        String s = nextPlayerLocation.getOwner();
        Boolean b = nextPlayerLocation.getOwner().isEmpty();
        if (!nextPlayerLocation.getOwner().isEmpty()){
            switch (nextPlayerLocation.getKind()){
                case PROPERTY :
                    BigDecimal oldNextPlayerBalance = playersBalance.get(nextPlayerName);
                    playersBalance.put(nextPlayerName, oldNextPlayerBalance.subtract(BigDecimal.valueOf(nextPlayerLocation.getBare())));
                    playersBalance.put(nextPlayerLocation.getOwner(), playersBalance.get(nextPlayerLocation.getOwner()).add(BigDecimal.valueOf(nextPlayerLocation.getBare())));
                    break;
                case STATION :
                    playersBalance.put(nextPlayerName, playersBalance.get(nextPlayerName).subtract(BigDecimal.valueOf(25)));
                    playersBalance.put(nextPlayerLocation.getOwner(), playersBalance.get(nextPlayerLocation.getOwner()).add(BigDecimal.valueOf(25)));
                    break;
                case COMPANY:

                    break;
                default:
                    break;
            }

        }
    }

    public List<Location> getBoard() {
        return board;
    }
    @Override
    public Map<String, Location> getPlayersLocation() {
        return  new HashMap<>(playersLocation);
    }

    @Override
    public Map<String, BigDecimal> getPlayersBalance() {
        return playersBalance;
    }

}
