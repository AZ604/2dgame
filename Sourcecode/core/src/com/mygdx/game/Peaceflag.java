package com.mygdx.game;

import static java.lang.System.currentTimeMillis;

public class Peaceflag {

    boolean visible;
    private int x, y;



    public Peaceflag(){

        this.visible = false; //alex: so the sprite only gets drawn when flag should be visible

    }


    public void placeFlag(Tank tank){

        this.x = tank.getXPix(); //alex: setting the flag to the defeated tank position
        this.y = tank.getYPix();

        this.visible = true; //alex: again, so the sprite can be drawn to map now

    }

    public int getX(){

        return this.x;

    }

    public int getY(){

        return this.y;

    }

    public boolean getVisible(){

        return this.visible;

    }

}
