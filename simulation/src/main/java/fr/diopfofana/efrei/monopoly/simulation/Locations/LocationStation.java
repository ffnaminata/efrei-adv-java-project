package fr.diopfofana.efrei.monopoly.simulation.Locations;

import fr.arsenelapostolet.efrei.monopoly.Location;

import java.math.BigDecimal;
import java.util.List;

public class LocationStation extends LocationImplement{
    public LocationStation(String name, LocationKind kind, String color, int price, int cost, int lvl1, int lvl2, int lvl3, int lvl4, int lvl5, int bare) {
        super(name, kind, color, price, cost, lvl1, lvl2, lvl3, lvl4, lvl5, bare);
    }

    @Override
    public BigDecimal getRent(int dicesResult, int numberOfStationOwned, List<Location> board){
        return new BigDecimal("25").multiply(new BigDecimal("2").pow(numberOfStationOwned - 1));
    }
}
