package com.mygdx.game;


public class HighscoreEintrag {

    int punktzahl;
    String name;

    public HighscoreEintrag(String name, int punktzahl) {
        this.name = name;
        this.punktzahl = punktzahl;

    }

    public String getName() {

        return this.name;
    }


    public String printValues(){

        return this.name + ": " + this.punktzahl + "\n";

    }

    public int getPunktzahl() {
        return this.punktzahl;
    }




}
