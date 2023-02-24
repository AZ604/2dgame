package com.mygdx.game;

public class BounceShot extends Shot {


    private int tankRotation;

    public BounceShot(Tank panzer) {

        super(panzer);
        this.power = panzer.getFirepower() / 2;
        this.tankRotation = panzer.getRotation();
        this.count = 0;

    }

    public boolean fired(Map map) { //Morten: Methode zur Fortbewegung Schuss nach Ausführung Tank.fire()
        switch (this.rotation) {     //Rotation von Panzer/Turm übernehmen, damit Textur richtige Richtung hat

            case 315:
                this.x++;
                this.y--; //Blume fliegt nach rechts-unten
                break;

            case 270:
                this.y--; //Blume fliegt nach unten
                break;

            case 225:
                this.x--;
                this.y--; //Blume fliegt nach links-unten
                break;

            case 180:
                this.x--; //Blume fliegt nach links
                break;

            case 135:
                this.x--;
                this.y++; //Blume fliegt nach links-oben
                break;

            case 90:
                this.y++; //Blume fliegt nach oben
                break;

            case 45:
                this.y++;
                this.x++; //Blume fliegt nach rechts-oben
                break;

            case 0:
                this.x++; //Blume fliegt nach rechts
                break;
        }
        if (map.getType(x, y) == 1 && count < 2) {
            switch (this.rotation) {
                case 0:
                    this.setRotation(this.rotation + 180);
                    count++;
                    break;

                case 90:
                    this.setRotation(this.rotation + 180);
                    count++;
                    break;

                case 180:
                    this.setRotation(this.rotation - 180);
                    count++;
                    break;

                case 270:
                    this.setRotation(this.rotation - 180);
                    count++;
                    break;

                case 45:
                    if (this.horizontal(map) == true) {

                        getTank().shots.add(this.neuerSchuss(315));
                        return false;
                        //this.setRotation(315);
                        //count++;
                    }

                    if (this.horizontal(map) == false) {

                        getTank().shots.add(this.neuerSchuss(135));
                        return false;
                    }

                    break;

                case 135:
                    if (this.horizontal(map) == true) {

                        getTank().shots.add(this.neuerSchuss(225));
                        return false;
                        //this.setRotation(315);
                        //count++;
                    }

                    if (this.horizontal(map) == false) {

                        getTank().shots.add(this.neuerSchuss(45));
                        return false;
                    }

                    break;

                case 225:
                    if (this.horizontal(map) == true) {

                        getTank().shots.add(this.neuerSchuss(135));
                        return false;
                        //this.setRotation(315);
                        //count++;
                    }

                    if (this.horizontal(map) == false) {

                        getTank().shots.add(this.neuerSchuss(315));
                        return false;
                    }

                    break;

                case 315:
                    if (this.horizontal(map) == true) {

                        getTank().shots.add(this.neuerSchuss(45));
                        return false;
                        //this.setRotation(315);
                        //count++;
                    }

                    if (this.horizontal(map) == false) {

                        getTank().shots.add(this.neuerSchuss(225));
                        return false;
                    }

                    break;

            }


        }
        if (map.getType(x, y, 1) == 1) {
            this.getTank().damageDealt += power;
            // System.out.println("Hallo");
            return false;
        }

        if (map.getType(x, y) == 1 | map.getType(x, y, 1) == 1 && count >= 2) {
            return false;
        }

        return true;

    }

//    public int wallCheck(Map map, BounceShot bounceShot) {
//        if (map.getType(this.getX(), this.getY()) == 1 && map.getType(this.getX(), this.getY() + 1) == 1) {   //Wand links/rechts
//            return 1;
//        } else if (map.getType(this.getX(), this.getY()) == 1 && map.getType(this.getX() + 1, this.getY()) == 1) {   //Wand oben/unten
//            return 2;
//        } else return 0;
//    }

    public boolean horizontal(Map map) {
        if (map.getType(this.x, this.y) == 1 && (map.getType(this.x + 1, this.y) == 1 || map.getType(this.x - 1, this.y) == 1)) {
            return true;
        }
        return false;
    }

    public Shot neuerSchuss(int rotation) {
        Shot schuss = new BounceShot(this.getTank());
        schuss.setRotation(rotation);
        schuss.x = this.x;
        schuss.y = this.y;
        schuss.setCount(this.getCount() + 1);
        return schuss;
    }


}
