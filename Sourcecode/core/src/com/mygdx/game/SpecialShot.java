package com.mygdx.game;

public class SpecialShot extends Shot {
    private int tankRotation;

    public SpecialShot(Tank tank) {
        super(tank);
        this.power = tank.getFirepower() * 4;
        this.tankRotation = tank.getRotation();
        this.count = 0;
    }

    public boolean fired(Map map)
    {
        switch (this.rotation)
        {
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
        if (map.getType(x, y) == 1) //Wand
        {
            switch (this.rotation) {
                case 315:
                    this.x--;
                    this.y++; //Blume fliegt nach rechts-unten
                    break;

                case 270:
                    this.y++; //Blume fliegt nach unten
                    break;

                case 225:
                    this.x++;
                    this.y++; //Blume fliegt nach links-unten
                    break;

                case 180:
                    this.x++; //Blume fliegt nach links
                    break;

                case 135:
                    this.x++;
                    this.y--; //Blume fliegt nach links-oben
                    break;

                case 90:
                    this.y--; //Blume fliegt nach oben
                    break;

                case 45:
                    this.y--;
                    this.x--; //Blume fliegt nach rechts-oben
                    break;

                case 0:
                    this.x--; //Blume fliegt nach rechts
                    break;
            }

            //
            Shot i = new Shot(this.getTank());
            Shot j = new Shot(this.getTank());
            Shot k = new Shot(this.getTank());
            Shot l = new Shot(this.getTank());
            Shot m = new Shot(this.getTank());
            Shot n = new Shot(this.getTank());
            Shot p = new Shot(this.getTank());
            Shot q = new Shot(this.getTank());


            int grad = 45;
            i.setRotation(0);
            j.setRotation(grad);
            k.setRotation(grad * 2);
            l.setRotation(grad * 3);
            m.setRotation(grad * 4);
            n.setRotation(grad * 5);
            p.setRotation(grad * 6);
            q.setRotation(grad * 7);

            i.setXY(x, y);
            j.setXY(x, y);
            k.setXY(x, y);
            l.setXY(x, y);
            m.setXY(x, y);
            n.setXY(x, y);
            p.setXY(x, y);
            q.setXY(x, y);


            i.setSpecialeffekt(true);
            j.setSpecialeffekt(true);
            k.setSpecialeffekt(true);
            l.setSpecialeffekt(true);
            m.setSpecialeffekt(true);
            n.setSpecialeffekt(true);
            p.setSpecialeffekt(true);
            q.setSpecialeffekt(true);
            this.getTank().addShot(i);
            this.getTank().addShot(j);
            this.getTank().addShot(k);
            this.getTank().addShot(l);
            this.getTank().addShot(m);
            this.getTank().addShot(n);
            this.getTank().addShot(p);
            this.getTank().addShot(q);
            return false;
        }

        if (map.getType(x, y, 1) == 1) //Panzer
        {
            Shot a = new Shot(this.getTank());
            Shot b = new Shot(this.getTank());
            Shot c = new Shot(this.getTank());
            Shot d = new Shot(this.getTank());
            Shot e = new Shot(this.getTank());
            Shot f = new Shot(this.getTank());
            Shot g = new Shot(this.getTank());
            Shot h = new Shot(this.getTank());


            int grad = 45;
            a.setRotation(0);
            b.setRotation(grad);
            c.setRotation(grad * 2);
            d.setRotation(grad * 3);
            e.setRotation(grad * 4);
            f.setRotation(grad * 5);
            g.setRotation(grad * 6);
            h.setRotation(grad * 7);

            a.setXY(x, y);
            b.setXY(x, y);
            c.setXY(x, y);
            d.setXY(x, y);
            e.setXY(x, y);
            f.setXY(x, y);
            g.setXY(x, y);
            h.setXY(x, y);


            a.setSpecialeffekt(true);
            b.setSpecialeffekt(true);
            c.setSpecialeffekt(true);
            d.setSpecialeffekt(true);
            e.setSpecialeffekt(true);
            f.setSpecialeffekt(true);
            g.setSpecialeffekt(true);
            h.setSpecialeffekt(true);
            this.getTank().addShot(a);
            this.getTank().addShot(b);
            this.getTank().addShot(c);
            this.getTank().addShot(d);
            this.getTank().addShot(e);
            this.getTank().addShot(f);
            this.getTank().addShot(g);
            this.getTank().addShot(h);

            this.getTank().damageDealt += power;
            return false;
        }
        return true;
    }


    }