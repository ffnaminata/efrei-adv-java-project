package fr.diopfofana.efrei.monopoly.simulation;

public class Case {
    private String name;
    private String kind;
    private String color;
    private int price;

    public Case(String name, String kind, String color, int price) {
        this.name = name;
        this.kind = kind;
        this.color = color;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getKind() {
        return kind;
    }

    public String getColor(){
        return color;
    }

    public int getPrice(){
        return price;
    }

}