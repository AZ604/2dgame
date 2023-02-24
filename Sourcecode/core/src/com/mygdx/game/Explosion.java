package com.mygdx.game;

import static java.lang.System.currentTimeMillis;

public class Explosion {

    private long currentTime; //Klassenvariable, damit übergreifend in beiden Threads (typeA und typeB) erreichbar
    private boolean visible; //steuert in der render() die Anzeige
    private int x, y, animation; //Positionen und animation zählt das Texturearray durch

    public final Runnable typeA; //Threads um mit Timern zu arbeiten
    public final Runnable typeB;

    public Explosion(){

        this.visible = false; //initial soll erstmal keine Explosion gezeichnet werden

        typeA = new Runnable(){
            public void run(){

                animate();

            }

        };

        typeB = new Runnable(){
            public void run(){

                timeout();

            }
        };

    }

    public void animate(){ //animation wird von 0 bis 41 durchgezählt, alle 50ms wird das nächste Bild geladen. Dadurch entsteht die Animation

        currentTime = currentTimeMillis();

        while (currentTimeMillis() < currentTime + 50){

            //wait

        } if (animation == 41){

            animation = 0;

        } else animation++;

        animate();

    }

    public void timeout(){ //Animation soll nach 2 Sekunden nicht weiter gezeichnet werden, und stets mit dem ersten Bild anfangen

        this.animation = 0;

        currentTime = currentTimeMillis();

        while (currentTimeMillis() < currentTime + 2000){

            //wait

        } this.visible = false;

    }

    public void setPosition(int x, int y){

        this.x = x;
        this.y = y;

    }

    public int getX(){

        return this.x;

    }

    public int getY(){

        return this.y;

    }

    public int getAnimation(){

        return this.animation;

    }

    public void setVisible(boolean visible){

        this.visible = visible;

    }

    public boolean getVisible(){

        return this.visible;

    }

}
