package fr.diopfofana.efrei.monopoly.simulation;
import fr.arsenelapostolet.efrei.monopoly.*;

import java.math.BigDecimal;
import java.util.*;


public class MonopolyImplement implements Monopoly {
    private final Dices dices;
    private final List<Location> board;
    private final List<String> playersName;
    private final Map<String, Location> playersLocation;
    private final Map<String, BigDecimal> playersBalance;
    private int currentPlayerIndex;


    public MonopolyImplement(Dices dices, List<String> playersName) {
        this.dices = dices;
        this.board = new ArrayList<>();
        this.playersName = playersName;
        this.playersLocation = new HashMap<>();
        this.playersBalance = new HashMap<>();
        this.currentPlayerIndex = playersName.size() - 1;
        Parser.parseCSV(this.board);
        initialisePlayersPosition();
        initialisePlayersBalance();
        submitOrder(playersName.get(this.currentPlayerIndex), OrderKind.IDLE);
        }

    private void initialisePlayersPosition(){
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

    @Override
    public void submitOrder(String playerName, OrderKind order) {
        // Extraire dans MonopolyImplement
        if (playersBalance.size() < 2) {
            throw new GameFinishedException();
        }

        int currentPlayerIndex = playersName.indexOf(playerName);

        // Verification que le joueur est bien celui qui doit jouer
        if (this.currentPlayerIndex != currentPlayerIndex){
            return;
        }

        Location currentPlayerLocation = playersLocation.get(playerName);

        // Action Acheter une location
        // LocationImplement methode verifiant que la case est libre. BalanceManager methode comparant que la balance à une somme.
        if (order == OrderKind.BUY && Objects.equals(currentPlayerLocation.getOwner(), "") && playersBalance.get(playerName).compareTo(BigDecimal.valueOf(currentPlayerLocation.getPrice())) >= 0 ){
            // LocationImplement methode veridfiant si case achetable
            if (currentPlayerLocation.getKind() == Location.LocationKind.PROPERTY ||
                    currentPlayerLocation.getKind() == Location.LocationKind.STATION ||
                    currentPlayerLocation.getKind() == Location.LocationKind.COMPANY ){
                //BalanceManager methode (acheteur, prix) qui soustrait le prix au nouveau proprio
                playersBalance.put(playerName, playersBalance.get(playerName).subtract(BigDecimal.valueOf(currentPlayerLocation.getPrice())));
                currentPlayerLocation.setOwner(playerName);
                // if la location est une COMPANY verifier si l'acheteur possede l'autre COMPANY, si oui activer la majoration (majoration pour les deux companies = attribut specifique à la classe COMPANY)
            }
        }

        // Methode de MonopolyImp Idle
        // getNextPlayerIndex(player actuelle)
        int nextPlayerIndex = (currentPlayerIndex + 1) % playersName.size();
        String nextPlayerName = playersName.get(nextPlayerIndex);
        Location nextPlayerLocation = playersLocation.get(nextPlayerName);

        // Action Avancer un joueur
        int dicesResult = this.dices.throwTwoSixSidedDices();

        int indexOfNextPlayerLocation = this.board.indexOf(nextPlayerLocation);
        int  newIndexOfNextPlayerLocation = (indexOfNextPlayerLocation + dicesResult) % board.size();
        playersLocation.put(nextPlayerName, board.get(newIndexOfNextPlayerLocation));

            // Mise à jour de la nouvelle location (peut etre inutile si une autre classe s'occupe du payment
        nextPlayerLocation = playersLocation.get(nextPlayerName);

        //RentManager ?
        if (!nextPlayerLocation.getOwner().isEmpty()){
            //RentManager ? nextPlayerName£.toPayRent(
            BigDecimal nextPlayerBalance = playersBalance.get(nextPlayerName);
            // Reutiliser la methode qui dit si une case est achetable
            // Polymorphisme : Créer des classes filles a LocationImplement pour chaque type de case qui override une methode getRent
            // Donc rentAmount = nextPlayerLocation.getRent()
            // If (!nextPlayerName.canAfford(rentAmount)){
            //    rentAmount = nextPlayerBalance;
            //    nextPlayerName.getOutOfTheGame();
            // } else {
            //    nextPlayerName.toPay(rentAmount);
            // }
            // nextPlayerLocation.getOwner().toGain(rentAmount);
            switch (nextPlayerLocation.getKind()){
                case PROPERTY :
                    BigDecimal rentAmountProperty = BigDecimal.valueOf(nextPlayerLocation.getBare());
                    if (nextPlayerBalance.compareTo(rentAmountProperty) < 0){
                        rentAmountProperty = nextPlayerBalance;
                        playersBalance.remove(nextPlayerName);
                        playersLocation.remove(nextPlayerName);
                    } else {
                        playersBalance.put(nextPlayerName, nextPlayerBalance.subtract(rentAmountProperty));
                    }
                    playersBalance.put(nextPlayerLocation.getOwner(), playersBalance.get(nextPlayerLocation.getOwner()).add(rentAmountProperty));
                    break;

                case STATION :
                    BigDecimal rentAmountStation = BigDecimal.valueOf(25);
                    if (nextPlayerBalance.compareTo(rentAmountStation) < 0){
                        rentAmountStation = nextPlayerBalance;
                        playersBalance.remove(nextPlayerName);
                        playersLocation.remove(nextPlayerName);
                    } else {
                        playersBalance.put(nextPlayerName, nextPlayerBalance.subtract(rentAmountStation));
                    }
                    playersBalance.put(nextPlayerLocation.getOwner(), playersBalance.get(nextPlayerLocation.getOwner()).add(rentAmountStation));
                    break;

                case COMPANY:
                    if (Objects.equals(nextPlayerLocation.getOwner(), board.get(12).getOwner()) && Objects.equals(nextPlayerLocation.getOwner(), board.get(28).getOwner())){
                        BigDecimal rentAmountCompanyOwnerOwnTwo = BigDecimal.valueOf(10L * dicesResult);
                        if (nextPlayerBalance.compareTo(rentAmountCompanyOwnerOwnTwo) < 0){
                            rentAmountCompanyOwnerOwnTwo = nextPlayerBalance;
                            playersBalance.remove(nextPlayerName);
                            playersLocation.remove(nextPlayerName);
                        } else {
                            playersBalance.put(nextPlayerName, nextPlayerBalance.subtract(rentAmountCompanyOwnerOwnTwo));
                        }
                        playersBalance.put(nextPlayerLocation.getOwner(), playersBalance.get(nextPlayerLocation.getOwner()).add(rentAmountCompanyOwnerOwnTwo));
                    } else {
                        BigDecimal rentAmountCompanyOwnerOwnOne = BigDecimal.valueOf(4L * dicesResult);
                        if (nextPlayerBalance.compareTo(rentAmountCompanyOwnerOwnOne) < 0){
                            rentAmountCompanyOwnerOwnOne = nextPlayerBalance;
                            playersBalance.remove(nextPlayerName);
                            playersLocation.remove(nextPlayerName);
                        } else {
                            playersBalance.put(nextPlayerName, nextPlayerBalance.subtract(rentAmountCompanyOwnerOwnOne));
                        }
                        playersBalance.put(nextPlayerLocation.getOwner(), playersBalance.get(nextPlayerLocation.getOwner()).add(rentAmountCompanyOwnerOwnOne));
                    }
                    break;

                default:
                    break;
            }
        }

        // Mise a jour du current player
        this.currentPlayerIndex = nextPlayerIndex;
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
