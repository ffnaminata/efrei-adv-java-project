package fr.diopfofana.efrei.monopoly.simulation.Locations;

import fr.arsenelapostolet.efrei.monopoly.Location;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class LocationCompany extends LocationImplement {
    public LocationCompany(String name, LocationKind kind, String color, int price, int cost, int lvl1, int lvl2, int lvl3, int lvl4, int lvl5, int bare) {
        super(name, kind, color, price, cost, lvl1, lvl2, lvl3, lvl4, lvl5, bare);
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
