package com.mygdx.game;
//Horde
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainPlayingScreen implements Screen, InputProcessor, ControllerListener { //Wellen-Modus
    public static final int HEIGHT = 1080;
    public static final int WIDTH = 1920;

    private Map map;
    //Tatiana: Players
    private List<Player> players = new ArrayList<Player>();

    private Texture tankimg, tankimg_blau, enemyTankimg, enemyTankimg_cyan, enemyTankimg_braun, enemyTankimg_lila, enemyTankimg_orange, turretimg, targetimg, flowerimg, barrierimg, speeditemimg, healthitemimg, firepoweritemimg, healthbarimg, portalimg, enemyTankimg_yellow, mineimg, peaceflagimg, damageZoneimg, explosionimg, specialShotimg;
    private Sprite tank, turret, target, flower, barrier, speeditem, healthitem, firepoweritem, portal, zielportal, mine, peaceflag, damageZoneTexture, explosion, specialShotItem;
    private Label labComboP1, labComboP2, labShotP1, labShotP2;
    private String shotTypeP1, shotTypeP2;

    private Skin skin;
    private Stage stage;
        // TANKS
    private ArrayList<Tank> tanks = new ArrayList<Tank>();
    private ArrayList<Tank> tankshelp = new ArrayList<Tank>();
    private List<Sprite> tankSprites = new ArrayList<Sprite>();
    private List<Sprite> turretSprites = new ArrayList<Sprite>();
    // ENEMY TANKS
    private ArrayList<Tank> enemyTanks = new ArrayList<Tank>();
    private List<Sprite> enemyTankSprites = new ArrayList<Sprite>();
    private List<Sprite> enemyTurretSprites = new ArrayList<Sprite>();

    private ArrayList<Tank> allTanks;

    private Impact impact;
    // State Machines

    private StateMachine stateMachine;

    // Items

    private SpeedItem speedItem1 = new SpeedItem();
    private HealthItem healthItem1 = new HealthItem();
    private FirepowerItem firepowerItem1 = new FirepowerItem();
    private SpecialShotItem specialShotItem1 = new SpecialShotItem();

    // Explosion

    private Texture[] texturearray; //alex: array to store all the explosion animation images

    // Portal

    private Portal portal1 = new Portal();

    private DamageZone damageZone = new DamageZone();

    private ArrayList<Vector3> hitCoord; //jhgfgng


    //private Barrier [] barrierArray;
    private Sprite [] barrierSpriteArray;

    private SpreadingPeace game;
    private Controller controller1;
    private Controller controller2;
    private boolean hasControllers;
    private Pathfinder pathfinder;


    List<Node> pathToPlayer1;
    List<Node> pathToPlayer2;
    List<Node> closestPath;


    private int lock = 0; //hält das spiel davon ab, nach dem pausieren neue panzer zu generieren

    public MainPlayingScreen(SpreadingPeace game) {
        this.game = game;



    }

    //Tatiana: add player in die liste Players
    public void addPlayer(Player player){
        if(!players.contains(player)){
            players.add(player);
        }
    }

    @Override
    public void show() {
        map = new Map(game.nr.getNr());
        map.setObj(0,0); //Map 0,0 nicht richtig übergeben?

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        labComboP1 = new Label("Multiplier: " + 1, skin);
        labShotP1 = new Label(shotTypeP1, skin);
        stage.addActor(labComboP1);
        stage.addActor(labShotP1);
        if(this.getPlayers().size() > 1)
        {
            labComboP2 = new Label("Multiplier: " + 1, skin);
            labShotP2 = new Label(shotTypeP2, skin);
            stage.addActor(labComboP2);
            stage.addActor(labShotP2);
        }
        //Testzwecke
        shotTypeP1 = "Test";
        shotTypeP2 = "Test";


        Controllers.addListener(this);
        Gdx.input.setInputProcessor(this);


        if(Controllers.getControllers().size == 0)
        {
            hasControllers = false;
        }
        else if(Controllers.getControllers().size ==1)
        {
            controller1 = Controllers.getControllers().first();
        }
        else
        {
            controller1 = Controllers.getControllers().first();
            controller2 = Controllers.getControllers().get(1);
        }

        map.show();

        map.resize(WIDTH,HEIGHT);


        //*** START ITEM RESPAWN THREADS***//


        new Thread(speedItem1.typeA).start();
        new Thread(healthItem1.typeA).start();
        new Thread(firepowerItem1.typeA).start();
        new Thread(specialShotItem1.typeA).start();



        //***LOAD TANK***//
        tankimg = new Texture("Panzer_front.png"); //alex: loads tank image file
        tankimg_blau = new Texture("Panzer_blau_front.png");
        //tank = new Sprite(tankimg);//alex: creates tank Sprite
        enemyTankimg = new Texture("Panzer_rot_front.png");
        enemyTankimg_braun = new Texture("Panzer_braun_front.png");
        enemyTankimg_cyan = new Texture("Panzer_cyan_front.png");
        enemyTankimg_lila = new Texture("Panzer_lila_front.png");
        enemyTankimg_orange = new Texture("Panzer_orange_front.png");
        enemyTankimg_yellow = new Texture("Panzer_gelb_front.png");

        //***LOAD TURRET***//
        turretimg = new Texture("testturret.png"); //alex: loads turret image file

        //***LOAD ITEMS***//
        speeditemimg = new Texture("speedItemImg.png"); //alex: loads item image file
        speeditem = new Sprite(speeditemimg);//alex: creates item Sprite

        healthitemimg = new Texture("healthItemImg.png"); //alex: loads item image file
        healthitem = new Sprite(healthitemimg);//alex: creates item Sprite

        firepoweritemimg = new Texture("firepowerItemImg.png"); //alex: loads item image file
        firepoweritem = new Sprite(firepoweritemimg);//alex: creates item Sprite

        specialShotimg = new Texture("FlowerItemImg.png");
        specialShotItem = new Sprite(specialShotimg);

        //Tatiana:LOAD PORTAL//
        portalimg = new Texture("portalimg.png");
        portal = new Sprite(portalimg);
        zielportal = new Sprite(portalimg);

        //***LOAD MINE***//
        mineimg = new Texture("mineimg.png");
        mine = new Sprite(mineimg);

        //***LOAD INITIAL EXPLOSION AND EXPLOSION ARRAY***//
        texturearray = new Texture[42];

        for (int i = 0; i < texturearray.length; i++){ //alex: writes 10 explosion images to a texture array, which will later be cycled to create the animation

            texturearray[i] = new Texture("explosion" + i + ".png");

        }

        explosionimg = texturearray[0]; //alex: sets first explosion image to the first Texture in the texturearray
        explosion = new Sprite(explosionimg);


        //***LOAD FLOWERS***//
        flowerimg = new Texture("Blume4.png");
        flower = new Sprite(flowerimg);

        //*** LOAD HEALTHBAR***//
        healthbarimg = new Texture("blank.png");//loads healthbar image (white pixel)


        //***LOAD Barrier***//
        barrierimg = new Texture("Barriere.png");//alex: loads target image

        //barrier = new Sprite(barrierimg);//alex: creates target Sprite
        if(map.barriers!=null)
        {
            barrierSpriteArray = new Sprite[map.barriers.length];

            for(int i = 0;i<barrierSpriteArray.length;i++)
            {
                barrierSpriteArray[i] = new Sprite(barrierimg);

            }
        }




        lock++; //damit tanks nur einmal generiert werden, nicht bei jedem Pause click neu
        // Tatiana: für jede Tank setzen wir ein Player
        if (lock <= 1) {
            this.generateTanks(this.getPlayers().size(), this.tanks, true);
            for (int i = 0; i < this.getPlayers().size(); i++) {
                this.getPlayers().get(i).setTank(this.tanks.get(i));
                this.tanks.get(i).setName(this.getPlayers().get(i).getName());
                this.tanks.get(i).respawnTank(map);
            }
            this.addSpritesToTanks(this.tanks, tankSprites, turretSprites);
        }


        if (lock <= 1) { // INIT ENEMY TANKS
            this.generateTanks(5, enemyTanks, false);
            Random rand = new Random();

            for (int i = 0; i < enemyTanks.size(); i++) {
                enemyTanks.get(i).setSchusstyp(rand.nextInt(4));
                enemyTanks.get(i).respawnTank(map);
            }

            this.addSpritesToEnemyTanks(enemyTanks, enemyTankSprites, enemyTurretSprites);
        }

        stateMachine = new StateMachine();

        pathfinder = new Pathfinder(map.getTiledMap(), map.getBlocked());

        allTanks = new ArrayList<>();
        allTanks.addAll(tanks);
        allTanks.addAll(enemyTanks);

        impact = new Impact(100,map);

        //*** START MINE DESPAWN THREADS***//
        for (int i = 0; i < this.tanks.size(); i++) { //start for each tank cause they all can have their own mine
            new Thread(this.tanks.get(i).getMine().typeA).start();
            this.tanks.get(i).setScoreZero();
        }

        //*** START EXPLOSION ANIMATE THREAD***//
        for (int i = 0; i < this.tanks.size(); i++) { //start for each tank cause they all can have their own mine which has an own explosion
            new Thread(this.tanks.get(i).getMine().getExplosion().typeA).start();
        }

        List<Node> pathToPlayer1;
        List<Node> pathToPlayer2;
        List<Node> closestPath;

    }

    //test
    @Override
    public void render(float delta) {


        // finds path from target to tank


        stateMachine.updateTime(1 / 60f); // update internal timer of the state machine
        stateMachine.updateState(); // updates the states of the machine



        labComboP1.setPosition(50, Gdx.app.getGraphics().getHeight() - 69);
        labShotP1.setPosition(labComboP1.getX() + 120, Gdx.app.getGraphics().getHeight() - 59);
        if(this.getPlayers().size() > 1)
        {
            labComboP2.setPosition(10 + 250 * 3, Gdx.app.getGraphics().getHeight() - 69);
            labShotP2.setPosition(labComboP2.getX() + 120, Gdx.app.getGraphics().getHeight() - 59);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { // Exits to the main Menu when pressing ESCAPE

            for(int i = 0; i < this.allTanks.size(); i++)
            {
                if(this.allTanks.get(i).maxCredits >= SpreadingPeace.stats[21] && this.allTanks.get(i).friendly)
                {
                    SpreadingPeace.stats[19] = this.allTanks.get(i).maxCredits;
                }
                if(this.allTanks.get(i).friendly)
                {
                    SpreadingPeace.stats[0] += this.allTanks.get(i).damageDealt;
                }
                if(this.allTanks.get(i).friendly)
                {
                    SpreadingPeace.stats[1] += this.allTanks.get(i).generalDamageTaken;
                }
                if(this.allTanks.get(i).friendly)
                {
                    SpreadingPeace.stats[16] += this.allTanks.get(i).lavaDamageTaken;
                }
            }

                game.setScreen(game.mainMenu);

        }

        map.render(1 / 60f); //Sören: shows map
        game.batch.begin();

        labComboP1.setText("Score x" + this.getPlayers().get(0).getTank().comboMultiplier(this.getPlayers().get(0).getTank().combo));
        labShotP1.setText(shotTypeP1);
        if(this.getPlayers().size() > 1)
        {
            labShotP2.setText(shotTypeP2);
        }

        if(this.getPlayers().size() > 1)
        {
            labComboP2.setText("Score x" + this.getPlayers().get(1).getTank().comboMultiplier(this.getPlayers().get(1).getTank().combo));
            labShotP2.setText(shotTypeP2);
        }
        refreshShottype();

        impact.ImpactDamage(allTanks);
        this.checkIfHit();
        this.checkIfPlayerHit();
        // render the damage zone



        //***RENDER TANK POSITION ALL THE TIME (INCLUDING TURRET OF COURSE)***//


        for (int i = 0; i < this.tanks.size(); i++) {
            if(tanks.get(i)!=null)
            {
                tankSprites.get(i).setPosition(tanks.get(i).getXPix(), tanks.get(i).getYPix());
                tankSprites.get(i).setRotation(tanks.get(i).getRotation());
                tankSprites.get(i).draw(game.batch);
                turretSprites.get(i).setPosition(tanks.get(i).getXPix(), tanks.get(i).getYPix());
                turretSprites.get(i).setRotation(tanks.get(i).getTurret().getRotation());
                turretSprites.get(i).draw(game.batch);

                tanks.get(i).lavaboden(map);

                tanks.get(i).fire(game.batch, map);

                speedItem1.checkPickup(tanks.get(i));
                healthItem1.checkPickup(tanks.get(i));
                firepowerItem1.checkPickup(tanks.get(i));
                specialShotItem1.checkPickup(tanks.get(i));
            }


            //*** MINE STUFF FOR ALL TANKS ***//
            mine.setPosition(tanks.get(i).getMine().getX(), tanks.get(i).getMine().getY());
            explosion.setPosition(tanks.get(i).getMine().getExplosion().getX(), tanks.get(i).getMine().getExplosion().getY());
            if(tanks.get(i).getMine().getVisible()) {

                mine.draw(game.batch); //alex: draw mine to game batch when it should be visible

            }

            if(tanks.get(i).getMine().getExplosion().getVisible()){

                //*** DRAW EXPLOSION***//
                explosion.draw(game.batch); //alex: draw explosion to game batch when it should be visible

            }

            //***CYCLE EXPLOSION IMAGE***//

            explosion = new Sprite(texturearray[tanks.get(i).getMine().getExplosion().getAnimation()]); //läd stets das entsprechende Bild als Textur, abhängig von Explosion.animation


            tanks.get(i).getMine().checkPickup(tanks.get(i));

            //Tatiana:
            portal1.checkPickup(tanks.get(i));

            // damageZone check proximity
            damageZone.checkPickup(tanks.get(i));

            tanks.get(i).showHealth(game.batch, healthbarimg, 10 + i * 700, 1000);

        }
        this.end();

        for(int i = 0;i<tanks.size();i++)
        {
            if(tanks.get(i).checkHealth()==0)
            {
                tanks.get(i).setDisabled();
            }
        }




        for (int i = 0; i < enemyTankSprites.size(); i++) {

            enemyTankSprites.get(i).setPosition(enemyTanks.get(i).getXPix(), enemyTanks.get(i).getYPix());
            enemyTankSprites.get(i).setRotation(enemyTanks.get(i).getRotation());
            enemyTankSprites.get(i).draw(game.batch);
            enemyTurretSprites.get(i).setPosition(enemyTanks.get(i).getXPix(), enemyTanks.get(i).getYPix());
            enemyTurretSprites.get(i).setRotation(enemyTanks.get(i).getTurret().getRotation());
            enemyTurretSprites.get(i).draw(game.batch);
            enemyTanks.get(i).lavaboden(map);
            enemyTanks.get(i).fire(game.batch,map);
            speedItem1.checkPickup(enemyTanks.get(i));
            healthItem1.checkPickup(enemyTanks.get(i));
            specialShotItem1.checkPickup(enemyTanks.get(i));
            firepowerItem1.checkPickup(enemyTanks.get(i));
            enemyTanks.get(i).despawn(map);

            //*** CHECK PLAYER MINES FOR ENEMY HIT ***//
            tanks.get(0).getMine().checkPickup(enemyTanks.get(i));
            if (tanks.size() > 1) {
                tanks.get(1).getMine().checkPickup(enemyTanks.get(i));
            }


            // Tatiana:
            portal1.checkPickup(enemyTanks.get(i));
            //  enemyTanks.get(i).showEnemyHealth(game.batch, healthbarimg, 10 + i * 300, 1040);

            for (int j = 0; j < enemyTanks.size(); j++) { // removes connections between tiles with tanks on them
                if (i == j) ;
                else {
                    pathfinder.removeConnection(enemyTanks.get(i).getX(), enemyTanks.get(i).getY(), enemyTanks.get(j).getX(), enemyTanks.get(j).getY());
                }
            }

            if (players.size() == 2) {
                pathToPlayer1 = pathfinder.findPath(enemyTanks.get(i).getX(), enemyTanks.get(i).getY(), tanks.get(0).getX(), tanks.get(0).getY());
                pathToPlayer2 = pathfinder.findPath(enemyTanks.get(i).getX(), enemyTanks.get(i).getY(), tanks.get(1).getX(), tanks.get(1).getY());

                if (pathToPlayer1.size() > pathToPlayer2.size()) closestPath = pathToPlayer2;
                else closestPath = pathToPlayer1;
            } else
                closestPath = pathfinder.findPath(enemyTanks.get(i).getX(), enemyTanks.get(i).getY(), tanks.get(0).getX(), tanks.get(0).getY());
            if (closestPath.size() == 2 && stateMachine.getState() == State.MOVING) {
                enemyTanks.get(i).setRotationByXY(closestPath.get(1).tileX, closestPath.get(1).tileY);
                enemyTanks.get(i).fire(game.batch, map);
                enemyTanks.get(i).addRounds();
            }

            if (closestPath.size() > 2 && stateMachine.getState() == State.MOVING && map.getBlocked()[closestPath.get(1).tileX][closestPath.get(1).tileY] == false) { // prevents target from overlapping with tank
                map.setUnblocked(enemyTanks.get(i).getX(), enemyTanks.get(i).getY());
                enemyTanks.get(i).setXY(closestPath.get(1).tileX, closestPath.get(1).tileY, map);
            }

            if (stateMachine.getState() == State.SHOOTING) {
                enemyTanks.get(i).fire(game.batch, map);
                enemyTanks.get(i).addRounds();
            }

            if (enemyTanks.get(i).getHealth() == 500) {
                enemyTanks.add(new Tank(this.flower, game.batch, map, false));
                enemyTankSprites.add(new Sprite(enemyTankimg));
                enemyTurretSprites.add(new Sprite(turretimg));
                enemyTanks.get(enemyTanks.size() - 1).respawnTank(map);
                enemyTanks.get(i).setHealth(499);

            }
            pathfinder = new Pathfinder(map.getTiledMap(), map.getBlocked());


        }


        //***RENDER ITEMS ALL THE TIME***//

        speeditem.setPosition(speedItem1.getX(), speedItem1.getY());

        if(speedItem1.getVisible()) { //only draws item to the map while its visible (when no tank currently has it)
            speeditem.draw(game.batch);
        }

        healthitem.setPosition(healthItem1.getX(), healthItem1.getY());

        if(healthItem1.getVisible()) { //only draws item to the map while its visible (when no tank currently has it)
            healthitem.draw(game.batch);
        }

        firepoweritem.setPosition(firepowerItem1.getX(), firepowerItem1.getY());

        if(firepowerItem1.getVisible()) { //only draws item to the map while its visible (when no tank currently has it)
            firepoweritem.draw(game.batch);
        }

        specialShotItem.setPosition(specialShotItem1.getX(), specialShotItem1.getY());

        if(specialShotItem1.getVisible()) {
            specialShotItem.draw(game.batch);
        }

        //Tatiana: RENDER PORTAL POSITION//

        portal.setPosition(portal1.getX(), portal1.getY());
        portal.draw(game.batch);
        zielportal.setPosition(portal1.getNewX(), portal1.getNewY());
        zielportal.draw(game.batch);




        if(barrierSpriteArray!=null&&map.barriers!=null)
        {
            for(int i = 0;i<barrierSpriteArray.length;i++)
            {
                if(barrierSpriteArray[i]!=null&&map.barriers[i]!=null)
                {
                    barrierSpriteArray[i].setPosition(map.barriers[i].getXPix(),map.barriers[i].getYPix());
                    barrierSpriteArray[i].draw(game.batch);
                }

            }
        }


        game.batch.end();
        stage.act();
        stage.draw();

    }



    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        game.batch.dispose();

        turretimg.dispose();
        flowerimg.dispose();
        barrierimg.dispose();
        speeditemimg.dispose();
        healthitemimg.dispose();
        firepoweritemimg.dispose();
        specialShotimg.dispose();
        mineimg.dispose();
        peaceflagimg.dispose();
        damageZoneimg.dispose();
        portalimg.dispose();
        stage.dispose();
        skin.dispose();


    }

    /**
     * Called when a key was pressed
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode) { //listens for keyboard key hits

        //***MOVE TANK WITH KEYBOARD***//
        for(Player player : players) {
            player.checkKeyCode(keycode, map);
        }

        //***Prints tank score and health to console for test purpose***//
        if (keycode == Input.Keys.T) {
            System.out.println("Score: " + tanks.get(0).getScore());
            System.out.println("Health: " + tanks.get(0).getHealth());
        }

        return true;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) //listens for gamepad button presses
    {
        if(controller == controller1 && getPlayers().size() ==1 )
        {
            if(getPlayers().get(0).hashCode() == game.player1.hashCode())
            {
                getPlayers().get(0).checkButtonCode(controller, buttonCode, map);
            }
            if(getPlayers().get(0).hashCode() == game.player2.hashCode())
            {
                getPlayers().get(0).checkButtonCode(controller, buttonCode, map);
            }
        }
        if(controller == controller1 && getPlayers().size() == 2)
        {
            getPlayers().get(0).checkButtonCode(controller, buttonCode, map);
        }
        if(controller == controller2 && getPlayers().size() == 2)
        {
            getPlayers().get(1).checkButtonCode(controller, buttonCode, map);
        }
        return true;
    }

    //Bewegung mittels Joystick
    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value)
    {
        if(controller == controller1 && getPlayers().size() ==1 )
        {
            if(getPlayers().get(0).hashCode() == game.player1.hashCode())
            {
                getPlayers().get(0).checkAxisMoved(controller, axisCode, value);
            }
            if(getPlayers().get(0).hashCode() == game.player2.hashCode())
            {
                getPlayers().get(0).checkAxisMoved(controller, axisCode, value);
            }
        }
        if(controller == controller1 && getPlayers().size() == 2)
        {
            getPlayers().get(0).checkAxisMoved(controller, axisCode, value);
        }
        if(controller == controller2 && getPlayers().size() == 2)
        {
            getPlayers().get(1).checkAxisMoved(controller, axisCode, value);
        }
        return false;
    }


    /**
     * Called when the screen was touched or a mouse button was pressed. The button parameter will be  on iOS.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) { //listens for mouse clicks

        if (button == Input.Buttons.LEFT) {
            tanks.get(0).addRounds();
        }

        return false;
    }


    @Override
    public boolean buttonUp(Controller controller, int buttonCode)
    {
        return false;
    }

    /**
     * Called when a key was released
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Called when a finger was lifted or a mouse button was released. The button parameter will be  on iOS.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param screenX
     * @param screenY
     * @param pointer the pointer for the event.
     * @return whether the input was processed
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     *
     * @param screenX
     * @param screenY
     * @return whether the input was processed
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Called when the mouse wheel was scrolled. Will not be called on iOS.
     *
     * @param amount the scroll amount, -1 or 1 depending on the direction the wheel was scrolled.
     * @return whether the input was processed.
     */
    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    /**
     * A {@link Controller} got connected.
     *
     * @param controller
     */
    @Override
    public void connected(Controller controller) {

    }

    /**
     * A {@link Controller} got disconnected.
     *
     * @param controller
     */
    @Override
    public void disconnected(Controller controller) {

    }

    /**
     * An axis on the {@link Controller} moved. The axisCode is controller specific. The axis value is in the range [-1, 1]. The
     * <code>com.badlogic.gdx.controllers.mapping</code> package hosts axes constants for known controllers.
     *
     * @param controller
     * @param axisCode
     * @param value      the axis value, -1 to 1
     * @return whether to hand the event to other listeners.
     */
    /**
    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        return false;
    }
**/
    /**
     * A POV on the {@link Controller} moved. The povCode is controller specific. The
     * <code>com.badlogic.gdx.controllers.mapping</code> package hosts POV constants for known controllers.
     *
     * @param controller
     * @param povCode
     * @param value
     * @return whether to hand the event to other listeners.
     */
    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value)
    {
        if(controller == controller1 && getPlayers().size() ==1 )
        {
            if(getPlayers().get(0).hashCode() == game.player1.hashCode())
            {
                getPlayers().get(0).checkPovCode(controller, povCode, value);
            }
            if(getPlayers().get(0).hashCode() == game.player2.hashCode())
            {
                getPlayers().get(0).checkPovCode(controller, povCode, value);
            }
        }
        if(controller == controller1 && getPlayers().size() == 2)
        {
            getPlayers().get(0).checkPovCode(controller, povCode, value);
        }
        if(controller == controller2 && getPlayers().size() == 2)
        {
            getPlayers().get(1).checkPovCode(controller, povCode, value);
        }
        return false;
    }

    /**
     * An x-slider on the {@link Controller} moved. The sliderCode is controller specific. The
     * <code>com.badlogic.gdx.controllers.mapping</code> package hosts slider constants for known controllers.
     *
     * @param controller
     * @param sliderCode
     * @param value
     * @return whether to hand the event to other listeners.
     */
    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    /**
     * An y-slider on the {@link Controller} moved. The sliderCode is controller specific. The
     * <code>com.badlogic.gdx.controllers.mapping</code> package hosts slider constants for known controllers.
     *
     * @param controller
     * @param sliderCode
     * @param value
     * @return whether to hand the event to other listeners.
     */
    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    /**
     * An accelerometer value on the {@link Controller} changed. The accelerometerCode is controller specific. The
     * <code>com.badlogic.gdx.controllers.mapping</code> package hosts slider constants for known controllers. The value is a
     * {@link Vector3} representing the acceleration on a 3-axis accelerometer in m/s^2.
     *
     * @param controller
     * @param accelerometerCode
     * @param value
     * @return whether to hand the event to other listeners.
     */
    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }

    public void addSpritesToTanks(List<Tank> tanks, List<Sprite> tankSprites, List<Sprite> turretSprites) //fügt zu jedem Panzer passende Sprites hinzu
    {
        for(int i = 0;i<tanks.size();i++)
        {
            if (i == 0) {
                switch (this.getPlayers().get(0).currentColor) {
                    case 0:
                        tankSprites.add(new Sprite(tankimg));
                        turretSprites.add(new Sprite(turretimg));
                        break;
                    case 1:
                        tankSprites.add(new Sprite(tankimg_blau));
                        turretSprites.add(new Sprite(turretimg));
                        break;
                    case 2:
                        tankSprites.add(new Sprite(enemyTankimg_braun));
                        turretSprites.add(new Sprite(turretimg));
                        break;
                    case 3:
                        tankSprites.add(new Sprite(enemyTankimg_cyan));
                        turretSprites.add(new Sprite(turretimg));
                        break;
                    case 4:
                        tankSprites.add(new Sprite(enemyTankimg_yellow));
                        turretSprites.add(new Sprite(turretimg));
                        break;
                    case 5:
                        tankSprites.add(new Sprite(enemyTankimg_lila));
                        turretSprites.add(new Sprite(turretimg));
                        break;
                    case 6:
                        tankSprites.add(new Sprite(enemyTankimg_orange));
                        turretSprites.add(new Sprite(turretimg));
                        break;
                    case 7:
                        tankSprites.add(new Sprite(enemyTankimg));
                        turretSprites.add(new Sprite(turretimg));
                        break;

                }
            }

            if (i == 1) {
                switch (this.getPlayers().get(1).currentColor) {
                    case 0:
                        tankSprites.add(new Sprite(tankimg));
                        turretSprites.add(new Sprite(turretimg));
                        break;
                    case 1:
                        tankSprites.add(new Sprite(tankimg_blau));
                        turretSprites.add(new Sprite(turretimg));
                        break;
                    case 2:
                        tankSprites.add(new Sprite(enemyTankimg_braun));
                        turretSprites.add(new Sprite(turretimg));
                        break;
                    case 3:
                        tankSprites.add(new Sprite(enemyTankimg_cyan));
                        turretSprites.add(new Sprite(turretimg));
                        break;
                    case 4:
                        tankSprites.add(new Sprite(enemyTankimg_yellow));
                        turretSprites.add(new Sprite(turretimg));
                        break;
                    case 5:
                        tankSprites.add(new Sprite(enemyTankimg_lila));
                        turretSprites.add(new Sprite(turretimg));
                        break;
                    case 6:
                        tankSprites.add(new Sprite(enemyTankimg_orange));
                        turretSprites.add(new Sprite(turretimg));
                        break;
                    case 7:
                        tankSprites.add(new Sprite(enemyTankimg));
                        turretSprites.add(new Sprite(turretimg));
                        break;

                }
            }


        }
    }

    public void addSpritesToEnemyTanks(List<Tank> tanks, List<Sprite> tankSprites, List<Sprite> turretSprites) //fügt zu jedem Panzer passende Sprites hinzu
    {
        for (int i = 0; i < tanks.size(); i++) {
            tankSprites.add(new Sprite(enemyTankimg));
            turretSprites.add(new Sprite(turretimg));
        }
    }

    public void generateTanks(int k, List<Tank> tanks, boolean friendly) //Generiert k anzahl an tanks, Vorsicht x,y sind nicht verteilt
    {
        for(int i=0;i<k;i++)
        {
            tanks.add(new Tank(flower, game.batch, map, friendly));
        }
    }

    public void checkIfHit() //überprüft ob Schuss eine Barriere oder einen Tank getroffen hat
    {
        hitCoord = map.getHitCoord(tanks, enemyTanks);


        for(int i = 0;i<hitCoord.size();i++)
        {
            int z= map.searchBarrier(hitCoord.get(i));
            if(z!=-1)
            {
                barrierSpriteArray[z]=null;
                map.setClear(map.barriers[z].x,map.barriers[z].y,1);
                pathfinder = new Pathfinder(map.getTiledMap(), map.getBlocked());
            }
            z = map.searchTank(hitCoord.get(i), enemyTanks);
            if(z!=-1)
            {
                if (enemyTankSprites.get(z) != null)
                {

                    enemyTanks.get(z).takeDamage((int) hitCoord.get(i).z); //hier soll als firepower shot.power rein!

                    break;

                }

            }

        }
    }

    public void checkIfPlayerHit() // checks if players are hit
    {
        hitCoord = map.getHitCoord(enemyTanks, tanks);


        for (int i = 0; i < hitCoord.size(); i++) {
            int z = map.searchBarrier(hitCoord.get(i));
            if (z != -1) {
                barrierSpriteArray[z] = null;
                map.setClear(map.barriers[z].x, map.barriers[z].y, 1);
                pathfinder = new Pathfinder(map.getTiledMap(), map.getBlocked());
            }
            z = map.searchTank(hitCoord.get(i), tanks);
            if (z != -1) {
                if (tanks.get(z) != null) {

                    tanks.get(z).takeDamage((int) hitCoord.get(i).z); //hier soll als firepower shot.power rein!

                    tanks.get(z).resetCombo();

                    break;

                }

            }

        }
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void end()
    {
        int k=0;
        for(int i = 0;i<tanks.size();i++)
        {
            if(tanks.get(i).checkHealth()==0)
            {
                k++;
            }
        }
        if(k==tanks.size())
        {
            for(int i = 0;i<tanks.size();i++)
            {
                tanks.get(i).respawnTank(map); // sonst wird spiel nicht resettet
            }


            while(enemyTanks.size()>5)
            {
                enemyTanks.remove(0);
                enemyTankSprites.remove(0);
                enemyTurretSprites.remove(0);
            }

            for(int i = 0; i < this.allTanks.size(); i++)
            {
                if(this.allTanks.get(i).maxCredits >= SpreadingPeace.stats[21] && this.allTanks.get(i).friendly)
                {
                    SpreadingPeace.stats[19] = this.allTanks.get(i).maxCredits;
                }
                if(this.allTanks.get(i).friendly)
                {
                    SpreadingPeace.stats[0] += this.allTanks.get(i).damageDealt;
                }
                if(this.allTanks.get(i).friendly)
                {
                    SpreadingPeace.stats[1] += this.allTanks.get(i).generalDamageTaken;
                }
                if(this.allTanks.get(i).friendly)
                {
                    SpreadingPeace.stats[16] += this.allTanks.get(i).lavaDamageTaken;
                }
            }


            game.setScreen(new GameOverScreen(game, players.get(0).getTank().getScore()));
            return;
        }

    }

    public void respawnTanks()
    {
        for(int i=0;i<enemyTanks.size();i++)
        {
            enemyTanks.get(i).despawn(map);
        }
    }

    public void refreshShottype()
    {
        if(this.getPlayers().get(0).getTank().schusstyp == 0){shotTypeP1 = "Shot: normal";}
        if(this.getPlayers().get(0).getTank().schusstyp == 1){shotTypeP1 = "Shot: fast";}
        if(this.getPlayers().get(0).getTank().schusstyp == 2){shotTypeP1 = "Shot: spread";}
        if(this.getPlayers().get(0).getTank().schusstyp == 3){shotTypeP1 = "Shot: bounce";}
        if(this.getPlayers().get(0).getTank().schusstyp == 4){shotTypeP1 = "Shot: Curved";}
        if(this.getPlayers().get(0).getTank().schusstyp == 6){shotTypeP1 = "Shot: Special";}
        if(this.getPlayers().size() > 1)
        {
            if(this.getPlayers().get(1).getTank().schusstyp == 0){shotTypeP2 = "Shot: normal";}
            if(this.getPlayers().get(1).getTank().schusstyp == 1){shotTypeP2 = "Shot: fast";}
            if(this.getPlayers().get(1).getTank().schusstyp == 2){shotTypeP2 = "Shot: spread";}
            if(this.getPlayers().get(1).getTank().schusstyp == 3){shotTypeP2 = "Shot: bounce";}
            if(this.getPlayers().get(1).getTank().schusstyp == 4){shotTypeP1 = "Shot: Curved";}
            if(this.getPlayers().get(1).getTank().schusstyp == 4){shotTypeP1 = "Shot: Special";}
        }
    }

}
