package com.mygdx.game;


import static java.lang.System.*;

public class Items {

    public final Runnable typeA;

    private boolean visible = false; //items only spawn on map if visible == true. In the beginning of each game there are no items on the map. They may appear withhin the next seconds though, randomly
    private int duration;
    private int spawnPosX, spawnPosY;
    private Tank owner;
    private int id;
    private Map map = new Map(1);

    public Items(int id){

        this.id = id;
        this.duration = 10000; //how long each item stays in one position in milliseconds
        this.spawnItem(map);

        typeA = new Runnable(){
            public void run(){

                randomSpawn();

            }

        };


    }

    public void spawnItem(Map map){

        int x = (int)(Math.random() * ((59 - 1) + 1)) + 1;
        int y = (int)(Math.random() * ((29 - 1) + 1)) + 1;

        while (map.getType(x,y) != 0){

            x = (int)(Math.random() * ((59 - 1) + 1)) + 1;
            y = (int)(Math.random() * ((29 - 1) + 1)) + 1;

        }

        this.spawnPosX = x*32;
        this.spawnPosY = y*32;

    }


    public void randomSpawn(){

        long currentTime = currentTimeMillis();

        while (currentTimeMillis() < currentTime + this.duration){

            //wait duration milliseconds until respawning the item somewhere else

        }

        if(Math.random() * 10 > 7) { //Items sollen mit 20% Wahrscheinlichkeit gespawnt werden (zufallswert 0-9, bei 8,9 spawn)

            this.spawnItem(map); //spawns the item randomly on the map
            this.visible = true;

        } else this.visible = false;

        randomSpawn(); //restart the whole thread

    }


    public int getX(){

        return this.spawnPosX;

    }

    public int getY(){

        return this.spawnPosY;

    }

    public int getId(){

        return this.id;

    }

    public void setVisible(boolean visible){

        this.visible = visible;

    }

    public boolean getVisible(){

        return this.visible;

    }

    public void checkPickup(Tank tank){

        if (this.spawnPosX == tank.getXPix() && this.spawnPosY == tank.getYPix() && this.visible){ //item wird aufgenommen wenn tank gleiche Position hat und item visible true ist

            this.spawnItem(map); //respawns item on a random new spot
            this.owner = tank; //assigns the tank to the item
            tank.setItem(this); //assigns the item to the tank
            this.executeSkill(); //starts double speed mode

            return;

        }

    }

    public void executeSkill(){

        int time = 10; //zeitabhängige skills (speed, firepower) dauern 10 Sekunden an

        Thread timer = new Thread(new BackgroundTimer(currentTimeMillis() / 1000, time, this.owner));

        timer.start(); //starte Thread der skill ausführt nebenläufig zum spiel

        return;

    }

    public Tank getOwner(){

        return this.owner;

    }

}
