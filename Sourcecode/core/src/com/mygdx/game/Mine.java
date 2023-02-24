package com.mygdx.game;

import static java.lang.System.currentTimeMillis;

public class Mine {

    private long currentTime;
    private boolean visible;
    private int x, y;
    private Explosion explosion = new Explosion(); //damit wir Explosionen pro Mine starten und verwalten können

    public final Runnable typeA;

    public Mine(){

        this.visible = false;

        typeA = new Runnable(){
            public void run(){

                checkTimeOut();

            }

        };

    }

    public void placeMine(Tank tank, Map map){

        //alex: place mine behind the tank, therefor ask for tank rotation. Also check that mine can only be placed on normal solid ground

        switch (tank.getRotation()) {

            case 0:

                if (map.getType(tank.getXPix()/32 - 1, tank.getYPix()/32) == 0) {

                    this.x = tank.getXPix() - 32;
                    this.y = tank.getYPix();
                    SpreadingPeace.changeStatAt(5, 1);
                }

                break;

            case 90:

                if (map.getType(tank.getXPix()/32, tank.getYPix()/32 - 1) == 0) {

                    this.x = tank.getXPix();
                    this.y = tank.getYPix() - 32;
                    SpreadingPeace.changeStatAt(5, 1);
                }

                break;

            case 180:

                if (map.getType(tank.getXPix()/32 + 1, tank.getYPix()/32) == 0) {

                    this.x = tank.getXPix() + 32;
                    this.y = tank.getYPix();
                    SpreadingPeace.changeStatAt(5, 1);
                }

                break;

            case 270:

                if (map.getType(tank.getXPix()/32, tank.getYPix()/32 + 1) == 0) {

                    this.x = tank.getXPix();
                    this.y = tank.getYPix() + 32;
                    SpreadingPeace.changeStatAt(5, 1);
                }

                break;
        }

        this.explosion.setPosition(this.x, this.y); //damit die Explosion an der Stelle animiert wird, an der die Mine zuletzt war
        this.visible = true;
        currentTime = currentTimeMillis();

    }

    public void checkTimeOut(){

        currentTime = currentTimeMillis();

        while (currentTimeMillis() < currentTime + 20000){

            //wait 10 seconds until mine disappears

        }

        this.visible = false; //make sprite invisible again

        checkTimeOut(); //restart the whole thread

    }

    public void checkPickup(Tank tank){

        if (this.x == tank.getXPix() && this.y == tank.getYPix() && this.visible){ //mine detoniert wenn tank gleiche Position hat und mine visible true ist

            tank.setHealth(0);
            this.visible = false; //alex: sets mine visibility to false, so it wont be drawn to the game batch any longer
            this.explosion.setVisible(true); //alex: sets explosion visibility to true, so it now gets drawn to game batch
            new Thread(this.explosion.typeB).start(); //alex: starts new Thread that counts down how long the explosion lasts

            SpreadingPeace.mineExplosion.play(SpreadingPeace.volume);

            return;

        }

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

    public Explosion getExplosion(){

        return this.explosion;

    }


}
