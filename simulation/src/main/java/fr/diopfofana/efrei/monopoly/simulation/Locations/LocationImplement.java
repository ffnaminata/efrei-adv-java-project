package fr.diopfofana.efrei.monopoly.simulation.Locations;

import fr.arsenelapostolet.efrei.monopoly.Location;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LocationImplement implements Location {
    private final String name;
    private final LocationKind kind;
    private final String color;
    private final int price;
    private final int cost;
    private final List<Integer> lvl;
    private final int bare;
    private String owner;
    private int numberOfBuilding;

    public LocationImplement(String name, LocationKind kind, String color, int price, int cost, int lvl1, int lvl2, int lvl3, int lvl4, int lvl5, int bare) {
        this.name = name;
        this.kind = kind;
        this.color = color;
        this.price = price;
        this.cost = cost;
        this.lvl = createList(lvl1, lvl2, lvl3, lvl4, lvl5);
        this.bare = bare;
        this.owner = "";
        this.numberOfBuilding = 0;
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
    public String getColor(){
        return color;
    }

    @Override
    public int getPrice(){
        return price;
    }

    @Override
    public int getCost() { return cost; }

    @Override
    public List<Integer> getLvl() { return lvl; }

    @Override
    public int getBare(){ return bare; }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public void setOwner(String owner){
        this.owner = owner;
    }

    @Override
    public int getNumberOfBuilding() { return numberOfBuilding; }

    @Override
    public void setNumberOfBuilding(int numberOfBuilding) { this.numberOfBuilding = numberOfBuilding; }

    @Override
    public BigDecimal getRent(int dicesResult, int numberOfStationOwned, List<Location> board){
        return new BigDecimal(0);
    }

    private List<Integer> createList(int num1, int num2, int num3, int num4, int num5) {
        List<Integer> list = new ArrayList<>();
        list.add(num1);
        list.add(num2);
        list.add(num3);
        list.add(num4);
        list.add(num5);
        return list;
    }
}
