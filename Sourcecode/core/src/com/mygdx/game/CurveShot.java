package com.mygdx.game;

public class CurveShot extends Shot{

    public int anz = 0;

    public CurveShot(Tank panzer, int anz) {
        super(panzer);
        this.anz = anz;

    }

    public boolean fired(Map map)
    {
        if(getCount()>5)
        {

            CurveShot schuss = new CurveShot(getTank(), anz + 1);
            if (this.rotation == 315) {
                schuss.setRotation(0);
            } else {
                schuss.setRotation(this.rotation + 45);
            }
            schuss.x = this.x;
            schuss.y = this.y;
            getTank().shots.add(schuss);
            getTank().shots.remove(this);


        }
        return super.fired(map);
    }
}
