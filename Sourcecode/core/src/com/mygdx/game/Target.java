package com.mygdx.game;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

import java.util.concurrent.ThreadLocalRandom;

public class Target {

    private int health;
    private int x, y;


    public Target(int health,Map map){

        this.health = health;
        respawn(map);

    }

    public Vector2 checkAlive(Map map){

        if (this.health <= 0){

            respawn(map);
            return new Vector2(this.x,this.y);
        }
        return new Vector2(-1,-1);

    }


    public void respawn(Map map){

        this.health = 100; //reset health after respawn

        map.setClear(x,y);
        int x = ThreadLocalRandom.current().nextInt(1, 60-1); //alex: spawns target randomly on map
        int y = ThreadLocalRandom.current().nextInt(1, 30-1); //alex: spawns target randomly on map
        if(map.getType(x,y)==0)
        {
            map.setObj(x,y,1);
            this.x = x;
            this.y = y;
            return;
        }
        else
        {
            respawn(map);
        }




    }

    public void takeDamage(int damage){ //when bullet hits target, its health decreases

        this.health -= damage;

    }

    public int getX(){

        return this.x;

    }

    public int getY(){

        return this.y;

    }

    public int getXPix(){

        return this.x*32;

    }

    public int getYPix(){

        return this.y*32;

    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
