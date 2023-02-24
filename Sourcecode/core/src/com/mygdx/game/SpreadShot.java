package com.mygdx.game;

public class SpreadShot extends Shot {
    int count = 0;



    public SpreadShot(Tank panzer) {

        super(panzer);



    }

    public boolean fired(Map map) { //Morten: Methode zur Fortbewegung Schuss nach Ausführung Tank.fire()
        if (f == 1) {
            f = 0;
            count++;
            if (count >= 10) {
                return false;
            }
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
            if (map.getType(x, y, 1) == 1) {
                this.getTank().damageDealt += power;
                return false;
            }
            if (map.getType(x, y) == 1 | map.getType(x, y, 1) == 1) {
                return false;
            } else {
                if (count == 3) {
                    Shot a = new Shot(this.getTank());
                    Shot b = new Shot(this.getTank());
                    a.setRotation(this.rotation);
                    b.setRotation(this.rotation);
                    switch (this.rotation) {
                        case 315:
                            a.setXY(x + 1, y + 1);
                            b.setXY(x - 1, y - 1);
                            break;

                        case 270:
                            a.setXY(x + 1, y);
                            b.setXY(x - 1, y);

                            //this.y--; //Blume fliegt nach unten
                            break;

                        case 225:
                            a.setXY(x - 1, y + 1);
                            b.setXY(x + 1, y - 1);

                            //this.x--;
                            //this.y--; //Blume fliegt nach links-unten
                            break;

                        case 180:

                            a.setXY(x, y + 1);
                            b.setXY(x, y - 1);
                            //this.x--; //Blume fliegt nach links
                            break;

                        case 135:

                            a.setXY(x + 1, y - 1);
                            b.setXY(x - 1, y + 1);
                            //this.x--;
                            //this.y++; //Blume fliegt nach links-oben
                            break;

                        case 90:

                            a.setXY(x + 1, y);
                            b.setXY(x - 1, y);
                            //this.y++; //Blume fliegt nach oben
                            break;

                        case 45:

                            a.setXY(x - 1, y + 1);
                            b.setXY(x + 1, y - 1);
                            //this.y++;
                            //this.x++; //Blume fliegt nach rechts-oben
                            break;

                        case 0:

                            a.setXY(x, y + 1);
                            b.setXY(x, y - 1);
                            //this.x++; //Blume fliegt nach rechts
                            break;
                    }
                    a.setShotgun(true);
                    b.setShotgun(true);
                    this.getTank().addShot(a);
                    this.getTank().addShot(b);
                }
                return true;
            }

        } else {
            f++;
            return true;
        }


    }
}


