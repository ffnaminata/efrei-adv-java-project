package fr.diopfofana.efrei.monopoly.simulation;

import fr.arsenelapostolet.efrei.monopoly.Location;

public class LocationImplement implements Location {
    private final String name;
    private final LocationKind kind;
    private final  String color;
    private final int price;
    private final String owner;
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

    public String getColor(){
        return color;
    }

    public int getPrice(){
        return price;
    }

    public int getBare(){
        return bare;
    }
}
