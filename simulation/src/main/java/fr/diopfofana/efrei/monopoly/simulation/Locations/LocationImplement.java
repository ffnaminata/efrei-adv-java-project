package fr.diopfofana.efrei.monopoly.simulation.Locations;

import fr.arsenelapostolet.efrei.monopoly.Location;

import java.math.BigDecimal;
import java.util.List;

public class LocationImplement implements Location {
    private final String name;
    private final LocationKind kind;
    private final  String color;
    private final int price;
    private String owner;
    private final int bare;

    public LocationImplement(String name, LocationKind kind, String color, int price, int bare) {
        this.name = name;
        this.kind = kind;
        this.color = color;
        this.price = price;
        this.bare = bare;
        this.owner = "";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocationKind getKind() {
        return kind;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public String getColor(){
        return color;
    }

    @Override
    public int getPrice(){
        return price;
    }

    @Override
    public int getBare(){
        return bare;
    }

    @Override
    public void setOwner(String playerName){
        owner = playerName;
    }

    @Override
    public BigDecimal getRent(int dicesResult, List<Location> board){
        return new BigDecimal(0);
    }

}
