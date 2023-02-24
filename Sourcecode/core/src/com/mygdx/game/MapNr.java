package com.mygdx.game;

public class MapNr {
    private int nr;
    public MapNr()
    {
        this.nr =1;
    }

    public void setNr(int nr)
    {
        if(nr>0 && nr<4)
        {
            this.nr = nr;
        }
    }

    public int getNr()
    {
        return this.nr;
    }
}
