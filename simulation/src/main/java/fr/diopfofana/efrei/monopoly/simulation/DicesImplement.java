package fr.diopfofana.efrei.monopoly.simulation;


import fr.arsenelapostolet.efrei.monopoly.Dices;

import java.util.Random;
public class DicesImplement implements Dices{
    public int throwOneSixSidedDices(){
        Random randomNumber = new Random();
        return randomNumber.nextInt(6) + 1;
    }
    public int throwTwoSixSidedDices(){
        return throwOneSixSidedDices() + throwOneSixSidedDices();
    }
}
