package com.mygdx.game;


public class DamageZone {
    //private Map map = new Map(1);

    int[] xtiles = new int[3];
    int[] ytiles = new int[3];

    public DamageZone() {
        for (int i = 0; i < xtiles.length; i++) {
            this.xtiles[i] = i + 5;
            this.ytiles[i] = i + 23;

        }

    }


    public void checkPickup(Tank tank) {

        for (int i = 0; i < xtiles.length; i++) {
            if ((tank.getX() == 5 || tank.getX() == 6 || tank.getX() == 7) && (tank.getY() == 23 || tank.getY() == 24 || tank.getY() == 25)) {
                tank.setDamageZoneMultiplier(2);
            } else tank.setDamageZoneMultiplier(1);
        }

    }



}
