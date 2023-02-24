package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import static com.mygdx.game.MainPlayingScreen.HEIGHT;
import static com.mygdx.game.MainPlayingScreen.WIDTH;

public class Shot {

    int x, y, rotation, power, count;
    private Tank tank;
    private boolean shotgun = false;
    private boolean specialeffekt = false;
    int f = 0;


    public Shot(Tank panzer) {

        this.tank = panzer;
        this.x = panzer.getX();
        this.y = panzer.getY();
        this.rotation = panzer.getTurret().getRotation(); //Richtung vom Schuss ist rotation vom Turm des Panzers
        this.power = tank.getFirepower();

    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getPower(){

        return this.power;

    }

    public boolean fired(Map map) { //Morten: Methode zur Fortbewegung Schuss nach Ausführung Tank.fire()
        count++;
        if (shotgun == true && count >= 7) {
            return false;
        }
        if (shotgun == true && f == 1) {
            f = 0;
            return true;
        }
        f++;


        if (specialeffekt == true && count >= 2)
        {
            return false;
        }
        switch (this.rotation) {     //Rotation von Panzer/Turm übernehmen, damit Textur richtige Richtung hat

            case 315:
                this.x++;this.y--; //Blume fliegt nach rechts-unten
                break;

            case 270:
                this.y--; //Blume fliegt nach unten
                break;

            case 225:
                this.x--;this.y--; //Blume fliegt nach links-unten
                break;

            case 180:
                this.x--; //Blume fliegt nach links
                break;

            case 135:
                this.x--;this.y++; //Blume fliegt nach links-oben
                break;

            case 90:
                this.y++; //Blume fliegt nach oben
                break;

            case 45:
                this.y++;this.x++; //Blume fliegt nach rechts-oben
                break;

            case 0:
                this.x++; //Blume fliegt nach rechts
                break;
        }
        if(map.getType(x, y, 1) == 1)
        {
            this.getTank().damageDealt += power;
            return false;
        }
        if(map.getType(x,y)==1|map.getType(x,y,1)==1)
        {
            return false;
        }
        return true;

    }


    public int getXPix() {

        double k = (double)this.x*WIDTH/60;
        return (int)k;
    }


    public int getYPix() {

        double k = (double)this.y*(HEIGHT*4/135);
        return (int)k;
    }


    public int getRotation() {

        return this.rotation-90;

    }

    public void setRotation(int rotation) {

        this.rotation = rotation;
    }

    public Tank getTank(){

        return this.tank;

    }

    public Vector2 getNext()
    {
        switch (this.rotation) {     //Rotation von Panzer übernehmen, damit Textur richtige Richtung hat

            case 315:
                return new Vector2(x+1,y-1);


            case 270:
                return new Vector2(x,y-1);


            case 225:
                return new Vector2(x-1,y-1);


            case 180:
                return new Vector2(x-1,y);


            case 135:
                return new Vector2(x-1,y+1);


            case 90:
                return new Vector2(x,y+1);


            case 45:
                return new Vector2(x+1,y+1);


            case 0:
                return new Vector2(x+1,y);

        }
        return null;
    }
    public void setXY(int x,int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {

        return this.x;
    }

    public int getY() {

        return this.y;
    }

    public void setCount(int wurst) {
        this.count = wurst;
    }

    public int getCount() {
        return this.count;
    }

    public boolean getShotgun() {
        return this.shotgun;
    }

    public void setShotgun(boolean x) {
        this.shotgun = x;
    }

    public void setSpecialeffekt(boolean a){this.specialeffekt = a;}





}
