package fr.arsenelapostolet.efrei.monopoly;

import java.math.BigDecimal;
import java.util.List;

public interface Location {

    String getName();

    LocationKind getKind();

    String getColor();

    int getPrice();

    public int getCost();

    public List<Integer> getLvl();

    int getBare();

    String getOwner();

    void setOwner(String playerName);

    public int getNumberOfBuilding();

    public void setNumberOfBuilding(int numberOfBuilding);

    BigDecimal getRent(int dicesResult, int numberOfStationOwned, List<Location> board);

    enum LocationKind {
        START,
        PROPERTY,
        TAX,
        STATION,
        CARD,
        JAIL,
        COMPANY,
        FREE_PARK,
        GO_TO_JAIL
    }

}
