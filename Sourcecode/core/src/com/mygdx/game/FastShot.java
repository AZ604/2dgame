package com.mygdx.game;

public class FastShot extends Shot {

    public FastShot(Tank panzer) {
        super(panzer);
        this.power = panzer.getFirepower() / 2;
    }

    public boolean fired(Map map) { //Morten: Methode zur Fortbewegung Schuss nach Ausführung Tank.fire()
        switch (this.rotation) {     //Rotation von Panzer/Turm übernehmen, damit Textur richtige Richtung hat

            case 315:
                if(map.getType(x + 1, y - 1, 1) == 1)
                {
                    this.getTank().damageDealt += power;
                    return false;
                }
                else if (map.getType(x + 1, y - 1) == 1 | map.getType(x + 1, y - 1, 1) == 1) {
                    return false;
                } else {
                    this.x += 2;
                    this.y -= 2;    //Blume fliegt nach rechts-unten
                    break;
                }


            case 270:
                if(map.getType(x , y - 1, 1) == 1)
                {
                    this.getTank().damageDealt += power;
                    return false;
                }
                else if (map.getType(x, y - 1) == 1 | map.getType(x, y - 1, 1) == 1) {
                    return false;
                } else {
                    this.y -= 2;    //Blume fliegt nach unten
                    break;
                }


            case 225:
                if(map.getType(x - 1, y - 1, 1) == 1)
                {
                    this.getTank().damageDealt += power;
                    return false;
                }
                else if (map.getType(x - 1, y - 1) == 1 | map.getType(x - 1, y - 1, 1) == 1) {
                    return false;
                } else {
                    this.x -= 2;
                    this.y -= 2;    //Blume fliegt nach links-unten
                    break;
                }


            case 180:
                if(map.getType(x - 1, y, 1) == 1)
                {
                    this.getTank().damageDealt += power;
                    return false;
                }
                else if (map.getType(x - 1, y) == 1 | map.getType(x - 1, y, 1) == 1) {
                    return false;
                } else {
                    this.x -= 2;    //Blume fliegt nach links
                    break;
                }



            case 135:
                if(map.getType(x - 1, y + 1, 1) == 1)
                {
                    this.getTank().damageDealt += power;
                    return false;
                }
                else if (map.getType(x - 1, y + 1) == 1 | map.getType(x - 1, y + 1, 1) == 1) {
                    return false;
                } else {
                    this.x -= 2;
                    this.y += 2;    //Blume fliegt nach links-oben
                    break;
                }


            case 90:
                if(map.getType(x, y + 1, 1) == 1)
                {
                    this.getTank().damageDealt += power;
                    return false;
                }
                else if (map.getType(x, y + 1) == 1 | map.getType(x, y + 1, 1) == 1) {
                    return false;
                } else {
                    this.y += 2;    //Blume fliegt nach oben
                    break;
                }


            case 45:
                if(map.getType(x + 1, y + 1, 1) == 1)
                {
                    this.getTank().damageDealt += power;
                    return false;
                }
                else if (map.getType(x + 1, y + 1) == 1 | map.getType(x + 1, y + 1, 1) == 1) {
                    return false;
                } else {
                    this.x += 2;
                    this.y += 2;    //Blume fliegt nach rechts-oben
                    break;
                }


            case 0:
                if(map.getType(x + 1, y, 1) == 1)
                {
                    this.getTank().damageDealt += power;
                    return false;
                }
                else if (map.getType(x + 1, y) == 1 | map.getType(x + 1, y, 1) == 1) {
                    return false;
                } else {
                    this.x += 2; //Blume fliegt nach rechts
                    break;
                }

        }
        if(map.getType(x, y, 1) == 1)
        {
            this.getTank().damageDealt += power;
            return false;
        }
        if (map.getType(x, y) == 1 | map.getType(x, y, 1) == 1) {
            return false;
        }
        return true;

    }
}
