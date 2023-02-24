package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

import static com.mygdx.game.MainPlayingScreen.HEIGHT;
import static com.mygdx.game.MainPlayingScreen.WIDTH;

public class Barrier  {

    public int x,y;


    public Barrier (int x,int y,Map map)
    {
        this.x = x;
        this.y = y;
        this.place(map);

    }





    public void place(Map map)
    {
        map.setObj(this.x,this.y,1);
    }

    public int getXPix()
    {
        double k = (double)this.x*WIDTH/60;
        return (int)k;
    }

    public int getYPix()
    {
        double k = (double)this.y*(HEIGHT*4/135);
        return (int)k;
    }



}
