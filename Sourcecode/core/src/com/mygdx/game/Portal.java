package com.mygdx.game;

import java.util.Random;

public class Portal {
//testpush
    private int spawnPosX, spawnPosY; //beschreibt Position von Portal 1
    private int newPosX, newPosY; //beschreibt Position von Portal 2
    private Map map = new Map(1); //holt die Map für spawnItem() um spawn position zu prüfen

    public Portal() {

        this.spawnItem(map); //Portal wird direkt am Anfang mit zufälliger Spawn Position auf Map ausgestattet

    }

    public void spawnItem(Map map){
        //Setzt das erste Portal zufällig auf der Map ab
        Random rand = new Random();
        int x = rand.nextInt(map.getWidth());//zwischen 0 bis 59
        int y = rand.nextInt(map.getHeight());// zwischen 0 bis 29

        while (map.getType(x, y) != Map.BODEN) {
            x = rand.nextInt(map.getWidth());
            y = rand.nextInt(map.getHeight());
        }

        this.spawnPosX = x * 32;// umgewandelt in pix
        this.spawnPosY = y * 32;

        //Setzt das zweite Portal zufällig auf der Map ab.
        int newx = rand.nextInt(map.getWidth());
        int newy = rand.nextInt(map.getHeight());

        //sorgt dafür, dass zweites Portal nicht auf dem ersten spawnen kann
        while (map.getType(newx, newy) != 0 || (newx == x && newy == y)) {
            newx = rand.nextInt(map.getWidth());
            newy = rand.nextInt(map.getHeight());

        }

        this.newPosX = newx*32;
        this.newPosY = newy*32;

    }

    public void checkPickup(Tank tank){
        //Tank fährt auf Portal
        if (this.spawnPosX == tank.getXPix() && this.spawnPosY == tank.getYPix() && !tank.getPortalblock()) {

            tank.setXYPortal(this.newPosX/32, this.newPosY/32); //teleportiere tank auf anderes Portal
            tank.blockPortal(); //blockiere, dass tank direkt wieder zurück teleportiert wird

            return;

        }

        if (this.newPosX == tank.getXPix() && this.newPosY == tank.getYPix() && !tank.getPortalblock()){ //Tank fährt auf anderes Portal

            tank.setXYPortal(this.spawnPosX/32, this.spawnPosY/32); //teleportiere tank auf anderes Portal
            tank.blockPortal(); //blockiere, dass tank direkt wieder zurück teleportiert wird

            return;

        }

    }

    public int getX(){

        return this.spawnPosX;

    }

    public int getY(){

        return this.spawnPosY;

    }

    public int getNewX(){

        return this.newPosX;

    }

    public int getNewY(){

        return this.newPosY;

    }




}
