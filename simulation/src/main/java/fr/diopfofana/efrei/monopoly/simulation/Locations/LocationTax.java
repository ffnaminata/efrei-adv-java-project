package fr.diopfofana.efrei.monopoly.simulation.Locations;

import fr.arsenelapostolet.efrei.monopoly.Location;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class LocationTax extends LocationImplement{
    public LocationTax(String name, LocationKind kind, String color, int price, int cost, int oneHouse, int twoHouses, int threeHouses, int fourHouses, int fiveHouses, int bare) {
        super(name, kind, color, price, cost, oneHouse, twoHouses, threeHouses, fourHouses, fiveHouses, bare);
    }
}
