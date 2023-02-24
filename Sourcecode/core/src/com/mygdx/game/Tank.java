package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


import java.util.ArrayList;

import static com.mygdx.game.MainPlayingScreen.HEIGHT;
import static com.mygdx.game.MainPlayingScreen.WIDTH;


public class Tank {

    private boolean disabled = false;
    private int x, y;
    public int rotation, health, firepower, speed;
    private String name;
    public int combo;
    private Turret turret;
    private int lavadamage = 5;
    private Items item; //tank can always carry one item (e.g. speeditem, healthitem, firepoweritem)
    private int score;
    private Sprite flower;
    private SpriteBatch batch;
    private Map map;
    public boolean friendly;

    private Color farbe;
    private int p=0;
    private Mine mine;
    private Peaceflag flag;
    public int schusstyp = 0; //0 == shot, 1 == fastShot, 2 == spreadShot, 3 == bounceShot, 4 == CurveShot, 6 == SpecialShot
    private int credits = 0;  // Money $$$$
    private int HealthUpgradeCost = 10;  // cost for upgrades
    private int DamageUpgradeCost = 10;
    //private double height = 1080.0;
    //private double width = 1920.0;//(double)WIDTH;
    private int maxHealth = 1000;
    public int maxCredits = 0;
    public int lavaDamageTaken = 0;
    public int generalDamageTaken = 0;
    public int damageDealt = 0;

    private int damageZoneMultiplier = 1;

    //private Color color;
    private boolean portalblock; // prüfen wann der Portal blockiert oder  nicht

    ArrayList<Shot> shots = new ArrayList<Shot>();


    public Tank(Sprite flower, SpriteBatch batch, Map map, boolean friendly) {

        this.flower = flower;
        this.batch = batch;
        this.map = map;
        this.friendly = friendly;

        this.health = 1000;
        this.firepower = 250;
        this.speed = 1;
        this.score = 0;
        this.combo = 0;
        this.portalblock = false;

        this.x = 1; //alex: initiale spawn position x
        this.y = 1; //alex: initiale spawn position y
        this.rotation = 0;

        this.turret = new Turret(this.rotation, this.x, this.x); //turret spawn mit identischen werten wie tank

        this.mine = new Mine();
        this.flag = new Peaceflag();

        //this.farbe = Color.BLUE;//beep


    }

    public Tank(Sprite sprite, SpriteBatch batch, Map map) {
    }

    //alex: switch case notwendig, damit Rotation nur 0, 90, 180, 270 annehmen kann!


    public void forward(Map map) { //alex: bewegt den Panzer vorwärts
        if(disabled==false)
        {
            map.setClear(this.x, this.y);
            p++;
            this.unblockPortal();//Tatiana: portal ist ungelockt
            switch (this.rotation) { //alex: Richtung abhängig von aktueller Rotation

                case 0:
                    //alex: abfrage, ob Ziel befahrbar ist
                    if (this.x + this.speed < 60) { //extra Abfrage wegen speedItem notwendig (sonst array outofbounds möglich)
                        if ((((map.getType(this.x + this.speed, this.y) == 0) && (map.getType(this.x + 1, this.y) == 0)) || map.getType(this.x + 1, this.y) == 3) && ((map.getType(this.x + this.speed, this.y, 1) == 0) && (map.getType(this.x + 1, this.y, 1) == 0)))
                            this.x += this.speed;
                    }
                    map.setObj(x, y, 1);
                    break;

                case 90:
                    //alex: abfrage, ob Ziel befahrbar ist
                    if (this.y + this.speed < 30) { //extra Abfrage wegen speedItem notwendig (sonst array outofbounds möglich)
                        if ((((map.getType(this.x, this.y + this.speed) == 0) && (map.getType(this.x, this.y + 1) == 0)) || map.getType(this.x, this.y + 1) == 3) && ((map.getType(this.x, this.y + this.speed, 1) == 0) && (map.getType(this.x, this.y + 1, 1) == 0)))
                            this.y += this.speed;
                    }
                    map.setObj(x, y, 1);
                    break;

                case 180:
                    //alex: abfrage, ob Ziel befahrbar ist
                    if (this.x - this.speed > 0) { //extra Abfrage wegen speedItem notwendig (sonst array outofbounds möglich)
                        if ((((map.getType(this.x - this.speed, this.y) == 0) && (map.getType(this.x - 1, this.y) == 0)) || map.getType(this.x - 1, this.y) == 3) && ((map.getType(this.x - this.speed, this.y, 1) == 0) && (map.getType(this.x - 1, this.y, 1) == 0)))
                            this.x -= this.speed;
                    }
                    map.setObj(x, y, 1);
                    break;

                case 270:
                    //alex: abfrage, ob Ziel befahrbar ist
                    if (this.y - this.speed > 0) { //extra Abfrage wegen speedItem notwendig (sonst array outofbounds möglich)
                        if ((((map.getType(this.x, this.y - this.speed) == 0) && (map.getType(this.x, this.y - 1) == 0)) || map.getType(this.x, this.y - 1) == 3) && ((map.getType(this.x, this.y - this.speed, 1) == 0) && (map.getType(this.x, this.y - 1, 1) == 0)))
                            this.y -= this.speed;
                    }
                    map.setObj(x, y, 1);
                    break;

            }
        }


    }

    public void backward(Map map) { //alex: bewegt den Panzer rückwärts
        if(disabled==false){
            map.setClear(this.x, this.y);
            this.unblockPortal();// Tatiana: Portal ist ungelockt
            switch (this.rotation) { //alex: Richtung abhängig von aktueller Rotation

                case 0:
                    //alex: abfrage, ob Ziel befahrbar ist
                    if ((map.getType(this.x - 1, this.y) == 0 | map.getType(this.x - 1, this.y) == 3) && map.getType(x - 1, this.y, 1) == 0)
                        this.x--;
                    map.setObj(x, y, 1);
                    break;

                case 90:
                    //alex: abfrage, ob Ziel befahrbar ist
                    if ((map.getType(this.x, this.y - 1) == 0 | map.getType(this.x, this.y - 1) == 3) && map.getType(this.x, this.y - 1, 1) == 0)
                        this.y--;
                    map.setObj(x, y, 1);
                    break;

                case 180:
                    //alex: abfrage, ob Ziel befahrbar ist
                    if ((map.getType(this.x + 1, this.y) == 0 | map.getType(this.x + 1, this.y) == 3) && map.getType(this.x + 1, this.y, 1) == 0)
                        this.x++;
                    map.setObj(x, y, 1);
                    break;

                case 270:
                    //alex: abfrage, ob Ziel befahrbar ist
                    if ((map.getType(this.x, this.y + 1) == 0 | map.getType(this.x, this.y + 1) == 3) && map.getType(this.x, this.y + 1, 1) == 0)
                        this.y++;
                    map.setObj(x, y, 1);
                    break;

            }
        }


    }


    public void rotateLeft() { //alex: rotiert den Panzer 90 Grad gegen den Uhrzeigersinn
        if(disabled == true)
        {
            return;
        }
        switch (this.rotation) { //alex: neue Rotation abhängig von aktueller Rotation

            case 0:
                this.rotation = 90; //right to up
                break;

            case 90:
                this.rotation = 180; //up to left
                break;

            case 180:
                this.rotation = 270; //left to down
                break;

            case 270:
                this.rotation = 0; //down to right
                break;

        }

    }

    public void rotateRight() { //alex: rotiert den Panzer 90 Grad im Uhrzeigersinn
        if(disabled == true)
        {
            return;
        }
        switch (this.rotation) { //alex: neue Rotation abhängig von aktueller Rotation

            case 0:
                this.rotation = 270; //right to down
                break;

            case 270:
                this.rotation = 180; //down to left
                break;

            case 180:
                this.rotation = 90; //left to up
                break;

            case 90:
                this.rotation = 0; //up to right
                break;

        }

    }

    public int getX() {

        return this.x;

    }

    public int getY() {

        return this.y;

    }

    public void setDamageZoneMultiplier(int damageZoneMultiplier) {
        this.damageZoneMultiplier = damageZoneMultiplier;
    }

    public int getRotation() {

        return this.rotation;

    }

    public void setSpeed(int speed) {

        this.speed = speed;

    }

    public void setHealth(int health) {

        this.health = health;

    }

    public void takeDamage(int firepower) {

        this.health = this.health - (firepower * this.damageZoneMultiplier);

        generalDamageTaken += firepower;
    }

    public void setFirepower(int firepower) {

        this.firepower = firepower;

    }

    public int getFirepower() {

        return this.firepower * this.damageZoneMultiplier;

    }

    public void setItem(Items item) {

        this.item = item;

    }

    public Items getItem() {

        if (this.item != null) {

            return this.item;

        } else return null;

    }

    public Mine getMine(){

        return this.mine;

    }

    public Peaceflag getFlag(){

        return this.flag;

    }


    public void addRounds() { //lädt eine Blume ins Schussarray

        if (schusstyp == 0) {
            shots.add(new Shot(this));
            StatScreen.shottypeUsed = 0;
        }

        if (schusstyp == 1) {
            shots.add(new FastShot(this));
            StatScreen.shottypeUsed = 1;
        }

        if (schusstyp == 2) {
            shots.add(new SpreadShot(this));
            StatScreen.shottypeUsed = 2;
        }

        if (schusstyp == 3) {
            shots.add(new BounceShot(this));
            StatScreen.shottypeUsed = 3;
        }

        if (schusstyp == 4) {
            shots.add(new CurveShot(this, 0));
            StatScreen.shottypeUsed = 4;
            return;
        }

        if (schusstyp == 5)
        {
            schusstyp=0;
            addRounds();
            StatScreen.shottypeUsed = 0;
            return;
        }

        if(schusstyp == 6)
        {
            shots.add(new SpecialShot(this));
            schusstyp = 0;
            StatScreen.shottypeUsed = 5;
            return;
        }

        SpreadingPeace.shotSound.play(SpreadingPeace.volume);

    }


    public void fire(SpriteBatch batch, Map map) { //alex: schießt Blume bei LEERTASTE
        if(disabled == true)
        {
            return;
        }
        int i = 0;

        while (this.shots.size() > i) { //feuert Blumen aus dem Schussarray ab

            this.flower.setPosition(this.shots.get(i).getXPix(), this.shots.get(i).getYPix());
            this.flower.setRotation(this.shots.get(i).getRotation()); //Rotation vom Schuss ist in Shot.java als Turret rotation deklariert
            this.flower.draw(this.batch);


            boolean k = this.shots.get(i).fired(map);


            if (k == false) {

                this.shots.remove(i);

            }

            i++;

        }


    }

    public void showHealth(Batch batch, Texture lebensleiste, float x, float y) {

        if (this.checkHealth() > 600) {
            batch.setColor(Color.GREEN);
        } else if (this.checkHealth() > 400) {
            batch.setColor(Color.ORANGE);
        } else {
            batch.setColor(Color.RED);
        }

        batch.draw(lebensleiste, x, y, 250 - this.checkHealth() / 4, 30);

        batch.setColor(Color.WHITE);
    }


    public void showEnemyHealth(Batch batch, Texture lebensleiste, float x, float y) {

        batch.setColor(Color.RED);

        batch.draw(lebensleiste, x, y, this.checkHealth() / 2, 30);
        batch.draw(lebensleiste, x, y, this.checkHealth() / 4, 10);

        batch.setColor(Color.WHITE);
    }

    public int getXPix() {
        double k = (double) this.x * WIDTH / 60;
        return (int) k;

    }

    public int getYPix() {

        double k = (double) this.y * (HEIGHT * 4 / 135);
        return (int) k;

    }

    public Turret getTurret() {

        return this.turret;

    }

    //Für Lava
    private void damage(int damage)
    {
        this.health = this.health - damage;
    }

    public void lavaboden(Map map) {
        if(map.mapNr==3)
        {
            if (map.getType(this.x, this.y) == 3) {
                this.damage(lavadamage/4);
                lavaDamageTaken += lavadamage/4;
            }

        }
        else
        {
            if (map.getType(this.x, this.y) == 3) {
                this.damage(lavadamage);
                lavaDamageTaken += lavadamage;
                //System.out.println("lava Damage: " + lavaDamageTaken);
            }

        }


    }

    public int getHealth() {

        return this.health;

    }

    public int checkHealth() {
        if (health < 0) {
            health = 0;
        }
        return this.health;
    }

    public void despawn(Map map) {
        if (checkHealth() == 0) {
            map.setUnblocked(this.getX(), this.getY());
            this.respawnTank(map);

        }
    }





    public void respawnTank(Map map) {
        int x = (int) (Math.random() * ((59 - 1) + 1)) + 1;
        int y = (int) (Math.random() * ((29 - 1) + 1)) + 1;

        while (map.getType(x, y) ==1||map.getType(x,y,1)==1|map.getType(x, y) ==2) {

            x = (int) (Math.random() * ((59 - 1) + 1)) + 1;
            y = (int) (Math.random() * ((29 - 1) + 1)) + 1;

        }
        map.setClear(this.getX(), this.getY(), 1);
        this.x = x;
        this.y = y;
        this.health = this.maxHealth;
        map.setObj(x, y, 1);


    }


    public void respawnTankIfZero(Map map) {
        if(this.checkHealth()==0)
        {
            this.getFlag().placeFlag(this);

            int x = (int) (Math.random() * ((59 - 1) + 1)) + 1;
            int y = (int) (Math.random() * ((29 - 1) + 1)) + 1;

            while (map.getType(x, y) ==1||map.getType(x,y,1)==1|map.getType(x, y) ==2) {

                x = (int) (Math.random() * ((59 - 1) + 1)) + 1;
                y = (int) (Math.random() * ((29 - 1) + 1)) + 1;

            }
            map.setClear(this.getX(), this.getY(), 1);
            this.x = x;
            this.y = y;
            this.health = 1000;
            map.setObj(x, y, 1);

            StatScreen.tankDestroyed = true;

            System.out.println("Respawn if Zero");
            if(friendly)
            {
                if (this.maxCredits >= SpreadingPeace.stats[21] && this.friendly)
                {
                    System.out.println("Update Cash");
                    SpreadingPeace.stats[19] = this.maxCredits;
                }
                if(this.friendly)
                {
                    System.out.println("Update damage");
                    SpreadingPeace.stats[0] += this.damageDealt;
                }
                if(this.friendly)
                {
                    System.out.println("Update damage taken");
                    SpreadingPeace.stats[1] += this.generalDamageTaken;
                }
                if(this.friendly)
                {
                    System.out.println("Update lava damage");
                    SpreadingPeace.stats[16] += this.lavaDamageTaken;
                }
            }
        }



    }

    public void setDisabled()
    {
        this.disabled=true;
    }

    public void setX(int x,Map map)
    {
        map.setClear(this.x,this.y,1);
        this.x=x;
        map.setObj(this.x,this.y,1);
    }

    public void setY(int y,Map map)
    {
        map.setClear(this.x,this.y,1);
        this.y=y;
        map.setObj(this.x,this.y,1);
    }

    public void setXY(int x,int y,Map map)
    {
        if (this.y == y && this.x > x) {
            this.rotation = 180;
            this.getTurret().setRotation(180);
        }
        if (this.y == y && this.x < x) {
            this.rotation = 0;
            this.getTurret().setRotation(0);
        }
        if (this.x == x && this.y < y) {
            this.rotation = 90;
            this.getTurret().setRotation(90);
        }
        if (this.x == x && this.y > y) {
            this.rotation = 270;
            this.getTurret().setRotation(270);
        }
        map.setClear(this.x,this.y,1);
        this.y=y;
        this.x=x;
        map.setObj(this.x,this.y,1);


    }
//Tatiana: position der Portal darf nur auf Boden laufen

    public void setXYPortal(int x, int y){

        map.setClear(this.x, this.y, 1);
        this.x = x;
        this.y = y;
        map.setObj(this.x, this.y, 1);

    }

    public void setName(String name){

        this.name = name;

    }

    public String getName(){

        return this.name;

    }

    //Combo multiplier für den score
    public void addCombo()
    {
        this.combo++;
        System.out.println("combo used, current combo: " + this.combo);
        //System.out.println("5/10: " + 5/10 + "         6/10: " + 6/10 + "          10/10: " + 10/10 + "               11/10: " + 11/10 + "               0/10: " + 0/10);
    }

    public void resetCombo()
    {
        this.combo = 0;
        System.out.println("reset Combo aktiviert");
    }

    public int comboMultiplier(int combo)
    {
        int multiplier = 1;
        multiplier += combo / 10;
        return multiplier;
    }

    public void addScore(int score)
    {
        this.credits += 1; // adds credits for buying upgrades
        if(this.credits >= this.maxCredits) {this.maxCredits = this.credits;}
        this.score += score*comboMultiplier(this.combo);
        System.out.println("Score: " + this.score + "   combo: " + combo);
    }



    public int getScore()
    {
        return this.score;
    }


    public void setRotation(int x) {
        this.rotation = x;
    }

    public void setRotationByXY(int x, int y) {
        if (this.y == y && this.x > x) {
            this.rotation = 180;
            this.getTurret().setRotation(180);
        }
        if (this.y == y && this.x < x) {
            this.rotation = 0;
            this.getTurret().setRotation(0);
        }
        if (this.x == x && this.y < y) {
            this.rotation = 90;
            this.getTurret().setRotation(90);
        }
        if (this.x == x && this.y > y) {
            this.rotation = 270;
            this.getTurret().setRotation(270);
        }
    }
//Tatiana: zurück gibt, ob der Portal blockiert ist

    public boolean getPortalblock(){

        return this.portalblock;

    }
//Tatiana: portalblock auf true setzen

    public void blockPortal(){

        this.portalblock = true;

    }
// Tatiana: portalblock auf false setzen

    public void unblockPortal(){

        this.portalblock = false;

    }

    public void setFarbe(Color farbe) {

        this.farbe = farbe; //farbe = 1 = grün
    }


    public void setScoreZero()
    {
        this.score = 0;
    }

    public Color getFarbe()
    {
        return this.farbe;
    }

    public int getP()
    {
        return this.p;
    }

    public void setPZero()
    {
        this.p = 0;
    }

    public Vector2 getForwardTile()
    {

        switch (this.rotation) { //alex: Richtung abhängig von aktueller Rotation

            case 0:

                return new Vector2(this.x + this.speed,this.y);


            case 90:

                return new Vector2(this.x,this.y+this.speed);

            case 180:

                return new Vector2(this.x - this.speed,this.y);

            case 270:

                return new Vector2(this.x,this.y-this.speed);

        }
        return new Vector2(0,0);
    }

    public void addShot(Shot e)
    {
        this.shots.add(e);
    }
    public void setSchusstypNext()
    {
        if (this.schusstyp == 4 || this.schusstyp == 6) this.schusstyp = 0;
        else this.schusstyp += 1;
    }

    public void setSchusstyp(int i) {
        if (i > 6) this.schusstyp = 6;
        else if (i < 0) this.schusstyp = 0;
        else this.schusstyp = i;
    }


    public void upgradeHealth() {
        if (this.credits - this.HealthUpgradeCost >= 0) {

            this.maxHealth += 250;

            // get percentage of current hp                         1250 = 100
            //              x   =  y
            int percentage = this.health * 100 / 1000;

            this.health = this.maxHealth * percentage / 100;  // set current hp to be percentually equal to previous hp

            SpreadingPeace.changeStatAt(19, HealthUpgradeCost); //Steffen: für die Statistiken

            // update credits

            this.credits -= this.HealthUpgradeCost;  // update credits

            this.HealthUpgradeCost *= 2;    // increase the cost for the next upgrade

        } else return;


    }

    public void upgradeFirePower() {
        if (this.credits - this.DamageUpgradeCost >= 0) {

            this.firepower += 250;

            SpreadingPeace.changeStatAt(19, HealthUpgradeCost); //Steffen: für die Statistiken

            this.credits -= this.DamageUpgradeCost;  // update credits

            this.DamageUpgradeCost *= 2;    // increase the cost for the next upgrade

        } else return;

    }

    public int getMaxHealth() {
        return this.maxHealth;

    }

    public int getCredits() {
        return this.credits;
    }

    public int getHealthUpgradeCost() {
        return this.HealthUpgradeCost;

    }

    public int getDamageUpgradeCost() {
        return this.DamageUpgradeCost;
    }

    public boolean getFriendly(){

        return this.friendly;

    }

    public int getDamageZoneMultiplier() {
        return this.damageZoneMultiplier;
    }
}