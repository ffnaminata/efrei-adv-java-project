package fr.diopfofana.efrei.monopoly.simulation.Locations;

import fr.arsenelapostolet.efrei.monopoly.Location;

import java.math.BigDecimal;
import java.util.List;

public class LocationStation extends LocationImplement{
    public LocationStation(String name, LocationKind kind, String color, int price, int cost, int oneHouse, int twoHouses, int threeHouses, int fourHouses, int fiveHouses, int bare) {
        super(name, kind, color, price, cost, oneHouse, twoHouses, threeHouses, fourHouses, fiveHouses, bare);
    }

    @Override
    public BigDecimal getRent(int dicesResult, int numberOfStationOwned, List<Location> board){
        return new BigDecimal("25").multiply(new BigDecimal("2").pow(numberOfStationOwned - 1));
    }
}
