package fr.diopfofana.efrei.monopoly.simulation.implementation;

import fr.arsenelapostolet.efrei.monopoly.BaseMonopolyTests;
import fr.arsenelapostolet.efrei.monopoly.Dices;
import fr.arsenelapostolet.efrei.monopoly.Monopoly;
import fr.diopfofana.efrei.monopoly.simulation.MonopolyImplement;


import java.util.List;

public class MonopolyTests extends BaseMonopolyTests {


    @Override
    public Monopoly createMonopoly(Dices dices, List<String> playerIds) {
        return new MonopolyImplement(dices, playerIds);
    }
}
