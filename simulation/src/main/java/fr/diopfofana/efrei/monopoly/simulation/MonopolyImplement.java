package fr.diopfofana.efrei.monopoly.simulation;
import fr.arsenelapostolet.efrei.monopoly.*;

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

        if (order == OrderKind.BUY && Objects.equals(currentPlayerLocation.getOwner(), "") && playersBalance.get(playerName).compareTo(BigDecimal.valueOf(currentPlayerLocation.getPrice())) >= 0 ){
            if (currentPlayerLocation.getKind() == Location.LocationKind.PROPERTY ||
                    currentPlayerLocation.getKind() == Location.LocationKind.STATION ||
                    currentPlayerLocation.getKind() == Location.LocationKind.COMPANY ){
                playersBalance.put(playerName, playersBalance.get(playerName).subtract(BigDecimal.valueOf(currentPlayerLocation.getPrice())));
                currentPlayerLocation.setOwner(playerName);
            }
        }

        BigDecimal nullValue = new BigDecimal(0);
        if (playersBalance.get(playerName).compareTo(nullValue) <= 0){
            playersBalance.remove(playerName);
            playersLocation.remove(playerName);
        }

        int nextPlayerIndex = (currentPlayerIndex + 1) % playersName.size();
        String nextPlayerName = playersName.get(nextPlayerIndex);
        Location nextPlayerLocation = playersLocation.get(nextPlayerName);

        if (this.currentPlayerIndex == nextPlayerIndex){
            int IndexOfNextPlayerLocation = this.board.indexOf(nextPlayerLocation);

            int dicesResult = this.dices.throwTwoSixSidedDices();
            int newNextPlayerLocationIndex = (IndexOfNextPlayerLocation + dicesResult) % board.size();
            playersLocation.put(nextPlayerName, board.get(newNextPlayerLocationIndex));
            nextPlayerLocation = playersLocation.get(nextPlayerName);

            this.currentPlayerIndex = (nextPlayerIndex + 1) % playersName.size();

            if (!nextPlayerLocation.getOwner().isEmpty()){
                BigDecimal oldNextPlayerBalance = playersBalance.get(nextPlayerName);
                switch (nextPlayerLocation.getKind()){
                    case PROPERTY :
                        BigDecimal rentAmountProperty = BigDecimal.valueOf(nextPlayerLocation.getBare());
                        if (oldNextPlayerBalance.compareTo(rentAmountProperty) < 0){
                            rentAmountProperty = oldNextPlayerBalance;
                        }
                        playersBalance.put(nextPlayerName, oldNextPlayerBalance.subtract(rentAmountProperty));
                        playersBalance.put(nextPlayerLocation.getOwner(), playersBalance.get(nextPlayerLocation.getOwner()).add(rentAmountProperty));
                        break;

                    case STATION :
                        BigDecimal rentAmountStation = BigDecimal.valueOf(25);
                        if (oldNextPlayerBalance.compareTo(rentAmountStation) < 0){
                            rentAmountStation = oldNextPlayerBalance;
                        }
                        playersBalance.put(nextPlayerName, oldNextPlayerBalance.subtract(rentAmountStation));
                        playersBalance.put(nextPlayerLocation.getOwner(), playersBalance.get(nextPlayerLocation.getOwner()).add(rentAmountStation));
                        break;

                    case COMPANY:
                        if (Objects.equals(nextPlayerLocation.getOwner(), board.get(12).getOwner()) && Objects.equals(nextPlayerLocation.getOwner(), board.get(28).getOwner())){
                            BigDecimal rentAmountCompanyOwnerOwnTwo = BigDecimal.valueOf(10L * dicesResult);
                            if (oldNextPlayerBalance.compareTo(rentAmountCompanyOwnerOwnTwo) < 0){
                                rentAmountCompanyOwnerOwnTwo = oldNextPlayerBalance;
                            }
                            playersBalance.put(nextPlayerName, oldNextPlayerBalance.subtract(rentAmountCompanyOwnerOwnTwo));
                            playersBalance.put(nextPlayerLocation.getOwner(), playersBalance.get(nextPlayerLocation.getOwner()).add(rentAmountCompanyOwnerOwnTwo));
                        } else {
                            BigDecimal rentAmountCompanyOwnerOwnOne = BigDecimal.valueOf(4L * dicesResult);
                            if (oldNextPlayerBalance.compareTo(rentAmountCompanyOwnerOwnOne) < 0){
                                rentAmountCompanyOwnerOwnOne = oldNextPlayerBalance;
                            }
                            playersBalance.put(nextPlayerName, oldNextPlayerBalance.subtract(rentAmountCompanyOwnerOwnOne));
                            playersBalance.put(nextPlayerLocation.getOwner(), playersBalance.get(nextPlayerLocation.getOwner()).add(rentAmountCompanyOwnerOwnOne));
                        }
                        break;

                    default:
                        break;
                }

                if (playersBalance.get(nextPlayerName).compareTo(nullValue) <= 0){
                    playersBalance.remove(nextPlayerName);
                    playersLocation.remove(nextPlayerName);
                }
            }
        }
        if (playersBalance.size() < 2) {
            throw new GameFinishedException();
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
