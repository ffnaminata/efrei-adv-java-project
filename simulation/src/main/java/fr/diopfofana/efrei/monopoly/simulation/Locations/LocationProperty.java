package fr.diopfofana.efrei.monopoly.simulation.Locations;

import fr.arsenelapostolet.efrei.monopoly.Location;

import java.math.BigDecimal;
import java.util.List;

public class LocationProperty extends LocationImplement{
    public LocationProperty(String name, LocationKind kind, String color, int price, int bare) {
        super(name, kind, color, price, bare);
    }

    @Override
    public BigDecimal getRent(int dicesResult, int numberOfStationOwned, List<Location> board){
        return BigDecimal.valueOf(getBare());
    }
}
