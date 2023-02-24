package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Ufo {


    private Sprite ufo;
    private int y, xZiel, yZiel;
    private long time;
    private boolean visible = true;
    private UfoFeld feld;


    public Ufo(Map map) {
        ufo = new Sprite(new Texture("UFO2.png"));

        feld = new UfoFeld(xZiel, yZiel);
        neu(map);


    }

    public void draw(Batch batch) {
        if (visible == true) {
            ufo.setPosition(xZiel * 32, y * 32);
            ufo.draw(batch);
            feld.draw(batch);
            feld.setY(this.y);
        }

    }


    public void move(Map map, ArrayList<Tank> tanks, ArrayList<Tank> enemyTanks) {

        if (visible == false) {

            if (time + 2000 < System.currentTimeMillis()) neu(map); //wartezeit
            return;
        }
        if (time + 1000 < System.currentTimeMillis()) { //fortbewegung
            time = System.currentTimeMillis();
            if (yZiel == y) {
                damage(map, tanks, enemyTanks);
                this.visible = false;
            }
            while (y < yZiel) {
                y++;
                return;
            }
        }

    }

    public void damage(Map map, ArrayList<Tank> tanks, ArrayList<Tank> enemyTanks) {
        int i = map.searchTank(new Vector3(xZiel, yZiel, 100), tanks);
        if (i != -1) {
            if (visible == true) tanks.get(i).takeDamage(999);

        }

        i = map.searchTank(new Vector3(xZiel, yZiel, 100), enemyTanks);
        if (i != -1) {
            if (visible == true) enemyTanks.get(i).takeDamage(999);
        }
    }

    public void neu(Map map) {
        int c = (int) (Math.random() * 30 + 15);
        int v = (int) (Math.random() * 10 + 10);
        while(map.getType(c,v)==1||map.getType(c,v)==2)
        {
             c = (int) (Math.random() * 30 + 15);
             v = (int) (Math.random() * 10 + 10);
        }
        xZiel = c;
        yZiel = v;
        this.y = 0;
        visible = true;
        time = System.currentTimeMillis();
        feld.setXY(xZiel, yZiel);
    }

}
