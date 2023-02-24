package com.mygdx.game;

public class Turret {

    private int rotation, x, y;

    public Turret(int rotation, int x, int y){

        this.rotation = rotation;
        this.x = x;
        this.y = y;

    }

    public void rotateLeft(){ //alex: rotiert den Turm 90 Grad gegen den Uhrzeigersinn

        switch (this.rotation){ //alex: neue Rotation abhängig von aktueller Rotation

            case 315:
                this.rotation = 0; //down-right to right
                break;

            case 270:
                this.rotation = 315; //down to down-right
                break;

            case 225:
                this.rotation = 270; //down-left to down
                break;

            case 180:
                this.rotation = 225; //left to down-left
                break;

            case 135:
                this.rotation = 180; //up-left to left
                break;

            case 90:
                this.rotation = 135; //up to up-left
                break;

            case 45:
                this.rotation = 90; //up-right to up
                break;

            case 0:
                this.rotation = 45; //right to up-right
                break;

        }

    }

    public void rotateRight(){ //alex: rotiert den Turm 90 Grad im Uhrzeigersinn

        switch (this.rotation){ //alex: neue Rotation abhängig von aktueller Rotation

            case 0:
                this.rotation = 315; //right to down-right
                break;

            case 315:
                this.rotation = 270; //down-right to down
                break;

            case 270:
                this.rotation = 225; //down to down-left
                break;

            case 225:
                this.rotation = 180; //down-left to left
                break;

            case 180:
                this.rotation = 135; //left to up-left
                break;

            case 135:
                this.rotation = 90; //up-left to up
                break;

            case 90:
                this.rotation = 45; //up to up-right
                break;

            case 45:
                this.rotation = 0; //up-right to right
                break;

        }

    }


    public int getRotation(){

        return this.rotation;

    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

}
