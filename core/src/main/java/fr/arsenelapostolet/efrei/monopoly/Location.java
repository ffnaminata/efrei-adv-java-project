package fr.arsenelapostolet.efrei.monopoly;

import java.math.BigDecimal;
import java.util.List;

public interface Location {

    String getName();

    LocationKind getKind();

    String getOwner();
    String getColor();

    int getBare();

    int getPrice();

    void setOwner(String playerName);

    BigDecimal getRent(int dicesResult, List<Location> board);

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
