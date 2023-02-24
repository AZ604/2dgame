package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class UfoFeld {
    int x, yZiel,count;
    private Sprite feld1,feld2,feld3;
    int y;

    public UfoFeld(int x, int y) {
        count =0;
        this.x = x;
        this.yZiel = y;
        feld1= new Sprite(new Texture("Rahmen1.png"));
        feld2= new Sprite(new Texture("Rahmen2.png"));
        feld3= new Sprite(new Texture("Rahmen3.png"));
        positions();
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.yZiel = y;
        positions();
    }

    public void draw(Batch batch)
    {


        if(yZiel-y<=2)
        {
            feld1.draw(batch);
            return;
        }

        if(yZiel-y<=4)
        {
            feld2.draw(batch);
            return;
        }

        if(yZiel-y<=6)
        {
            feld3.draw(batch);
            return;
        }

    }

    public void positions()
    {
        feld1.setPosition(x*32,yZiel*32);
        int o = (int) (Math.random()*2);
        if(o==1)
        {
            int k = (int) (Math.random()*2);
            feld2.setPosition((x)*32,(yZiel-1+k)*32);
            int l = (int) (Math.random()*3);
            feld3.setPosition((x)*32,(yZiel-1+l)*32);

        }
        if(o==0)
        {
            int k = (int) (Math.random()*2);
            feld2.setPosition((x)*32,(yZiel-1-k)*32);
            int l = (int) (Math.random()*3);
            feld3.setPosition((x)*32,(yZiel-1-l)*32);
        }

    }

    public void setY(int y)
    {
        this.y=y;
    }



}
