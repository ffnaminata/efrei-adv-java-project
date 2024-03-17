package fr.diopfofana.efrei.monopoly.simulation.Locations;

import fr.arsenelapostolet.efrei.monopoly.Location;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class LocationCompany extends LocationImplement {
    public LocationCompany(String name, LocationKind kind, String color, int price, int cost, int oneHouse, int twoHouses, int threeHouses, int fourHouses, int fiveHouses, int bare) {
        super(name, kind, color, price, cost, oneHouse, twoHouses, threeHouses, fourHouses, fiveHouses, bare);
    }

    @Override
    public BigDecimal getRent(int dicesResult, int numberOfStationOwned, List<Location> board){
        if (Objects.equals(board.get(12).getOwner(), board.get(28).getOwner())){
            return new BigDecimal(10 * dicesResult);
        } else {
            return new BigDecimal(4 * dicesResult);
        }
    }
}
