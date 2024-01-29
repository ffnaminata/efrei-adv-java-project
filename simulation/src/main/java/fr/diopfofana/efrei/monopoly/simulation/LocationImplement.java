package fr.diopfofana.efrei.monopoly.simulation;

import fr.arsenelapostolet.efrei.monopoly.Location;

public class LocationImplement implements Location {
    private String name;
    private LocationKind kind;
    private String color;
    private int price;

    public LocationImplement(String name, LocationKind kind, String color, int price) {
        this.name = name;
        this.kind = kind;
        this.color = color;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocationKind getKind() {
        return kind;
    }

    public String getColor(){
        return color;
    }

    public int getPrice(){
        return price;
    }
}