package com.mygdx.game;
//Team-Deathmatch:

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mygdx.game.MainPlayingScreen.HEIGHT;
import static com.mygdx.game.MainPlayingScreen.WIDTH;

public class MainPlayingScreen2 implements Screen, InputProcessor, ControllerListener { //Deathmatch

    private Map map; // mapNr = andere Map!
    private List<Player> players = new ArrayList<Player>();

    private Texture tankimg, tankimg_blau, enemyTankimg, enemyTankimg_cyan, enemyTankimg_braun, enemyTankimg_lila, enemyTankimg_orange, enemyTankimg_yellow, turretimg, targetimg, flowerimg, barrierimg, speeditemimg, healthitemimg, firepoweritemimg, healthbarimg, mineimg, peaceflagimg, damageZoneimg, explosionimg;
    private Sprite tank, turret, target, flower, barrier, speeditem, healthitem, firepoweritem, mine, peaceflag, damageZoneTexture, explosion;

    private String shotTypeP1, shotTypeP2, creditsP1, creditsP2, healthUpgradeCostP1, healthUpgradeCostP2, firePowerUpgradeCostP1, firePowerUpgradeCostP2;
    private Label labShotP1, labShotP2, labCreditsP1, labCreditsP2, labHPUpgradeCostP1, labHPUpgradeCostP2, labFPUpgradeCostP1, labFPUpgradeCostP2;
    private Skin skin;
    private Stage stage;

    // TANKS
    private ArrayList<Tank> tanks = new ArrayList<Tank>();
    private List<Sprite> tankSprites = new ArrayList<Sprite>();
    private List<Sprite> turretSprites = new ArrayList<Sprite>();

    // ENEMY TANKS
    private ArrayList<Tank> enemyTanks = new ArrayList<Tank>();
    private List<Sprite> enemyTankSprites = new ArrayList<Sprite>();
    private List<Sprite> enemyTurretSprites = new ArrayList<Sprite>();

    // Reference to every tank

    private ArrayList<Tank> allTanks = new ArrayList<Tank>();
    private List<Sprite> allTankSprites = new ArrayList<Sprite>();
    private List<Sprite> allTankTurretSprites = new ArrayList<Sprite>();

    private HealthBar healthBar;

    private Impact impact;
    private Platform platform;

    //UFO
    private Ufo ufo;


    // State Machines

    private StateMachine stateMachine;

    // Items

    private SpeedItem speedItem1 = new SpeedItem();
    private HealthItem healthItem1 = new HealthItem();
    private FirepowerItem firepowerItem1 = new FirepowerItem();

    // Explosion

    private Texture[] texturearray; //alex: array to store all the explosion animation images

    private ArrayList<Vector3> hitCoord;

    //private Barrier [] barrierArray;
    private Sprite[] barrierSpriteArray;

    private SpreadingPeace game;
    private Controller controller;
    private boolean hasControllers;
    private Pathfinder pathfinder;

    private Controller controller1;
    private Controller controller2;

    private DamageZone damageZone = new DamageZone();


    private int lock = 0; //hält das spiel davon ab, nach dem pausieren neue panzer zu generieren

    public MainPlayingScreen2(SpreadingPeace game) {
        this.game = game;



    }

    public void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }


    @Override
    public boolean keyDown(int keycode) {
        //***MOVE TANK WITH KEYBOARD***//
        for (Player player : players) {
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
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            tanks.get(0).addRounds();
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void show() {
        map = new Map(game.nr.getNr());
        map.setObj(0, 0); //Map 0,0 nicht richtig übergeben?

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        labShotP1 = new Label(shotTypeP1, skin);
        labCreditsP1 = new Label(creditsP1, skin);
        labHPUpgradeCostP1 = new Label(healthUpgradeCostP1, skin);
        labFPUpgradeCostP1 = new Label(firePowerUpgradeCostP1, skin);


        stage.addActor(labShotP1);
        stage.addActor(labHPUpgradeCostP1);
        stage.addActor(labCreditsP1);
        stage.addActor(labFPUpgradeCostP1);
        if(this.getPlayers().size() > 1)
        {
            labShotP2 = new Label(shotTypeP2, skin);
            labCreditsP2 = new Label(creditsP2, skin);
            labHPUpgradeCostP2 = new Label(healthUpgradeCostP2, skin);
            labFPUpgradeCostP2 = new Label(firePowerUpgradeCostP2, skin);

            stage.addActor(labShotP2);
            stage.addActor(labHPUpgradeCostP2);
            stage.addActor(labCreditsP2);
            stage.addActor(labFPUpgradeCostP2);
        }

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
        //turret = new Sprite(turretimg);//alex: creates turret Sprite

        //***LOAD ITEMS***//
        speeditemimg = new Texture("speedItemImg.png"); //alex: loads item image file
        speeditem = new Sprite(speeditemimg);//alex: creates item Sprite

        healthitemimg = new Texture("healthItemImg.png"); //alex: loads item image file
        healthitem = new Sprite(healthitemimg);//alex: creates item Sprite

        firepoweritemimg = new Texture("firepowerItemImg.png"); //alex: loads item image file
        firepoweritem = new Sprite(firepoweritemimg);//alex: creates item Sprite

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

        //***LOAD PEACEFLAG***//
        peaceflagimg = new Texture("peaceflagimg.png");
        peaceflag = new Sprite(peaceflagimg);


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
            for (int i = 0; i < barrierSpriteArray.length; i++) {
                barrierSpriteArray[i] = new Sprite(barrierimg);

            }
        }





        lock++; //damit tanks nur einmal generiert werden, nicht bei jedem Pause click neu
        if (lock <= 1) {
            this.generateTanks(players.size(), tanks, true);
            for (int i = 0; i < players.size(); i++) {
                players.get(i).setTank(tanks.get(i));
                tanks.get(i).respawnTank(this.map);
            }
            this.addSpritesToTanks(tanks, tankSprites, turretSprites);
        }


        if (lock <= 1) { // INIT ENEMY TANKS
            if (players.size() == 2) {
                this.generateTanks(3, enemyTanks, false);
            } else this.generateTanks(4, enemyTanks, false);

            Random rand = new Random();

            for (int i = 0; i < enemyTanks.size(); i++) {
                enemyTanks.get(i).setSchusstyp(rand.nextInt(4));
                enemyTanks.get(i).respawnTank(this.map);
            }

            this.addSpritesToEnemyTanks(enemyTanks, enemyTankSprites, enemyTurretSprites);
        }



        healthBar = new HealthBar(allTanks,healthbarimg);
        stateMachine = new StateMachine();

        pathfinder = new Pathfinder(map.getTiledMap(), map.getBlocked());

        impact = new Impact(100,map);

        //*** START MINE DESPAWN THREADS***//
        for (int i = 0; i < this.tanks.size(); i++) { //start for each tank cause they all can have their own mine
            new Thread(this.tanks.get(i).getMine().typeA).start();
        }

        //*** START EXPLOSION ANIMATE THREAD***//
        for (int i = 0; i < this.tanks.size(); i++) { //start for each tank cause they all can have their own mine which has an own explosion
            new Thread(this.tanks.get(i).getMine().getExplosion().typeA).start();
        }

          platform = new Platform(map);


        //Ufo
        ufo = new Ufo(map);

    }

    @Override
    public void render(float delta) {



        List<Node> closestPath;
        List<Node> currentPath;


        stateMachine.updateTime(1 / 60f); // update internal timer of the state machine
        stateMachine.updateState(); // updates the states of the machine


        labShotP1.setPosition(50, Gdx.app.getGraphics().getHeight() - 59);
        labHPUpgradeCostP1.setPosition(100, Gdx.app.getGraphics().getHeight() - 29);
        labFPUpgradeCostP1.setPosition(325, Gdx.app.getGraphics().getHeight() - 29);
        labCreditsP1.setPosition(5, Gdx.app.getGraphics().getHeight() - 29);
        if(this.getPlayers().size() > 1)
        {
            labShotP2.setPosition(10 + 250 * 3, Gdx.app.getGraphics().getHeight() - 59);
            labHPUpgradeCostP2.setPosition(10 + 250 * 3 + 95, Gdx.app.getGraphics().getHeight() - 29);
            labFPUpgradeCostP2.setPosition(10 + 250 * 3 + 320, Gdx.app.getGraphics().getHeight() - 29);
            labCreditsP2.setPosition(250 * 3, Gdx.app.getGraphics().getHeight() - 29);

        }
        labShotP1.setText(shotTypeP1);
        labHPUpgradeCostP1.setText(healthUpgradeCostP1);
        labFPUpgradeCostP1.setText(firePowerUpgradeCostP1);
        labCreditsP1.setText(creditsP1);
        if(this.getPlayers().size() > 1)
        {
            labShotP2.setText(shotTypeP2);
            labHPUpgradeCostP2.setText(healthUpgradeCostP2);
            labFPUpgradeCostP2.setText(firePowerUpgradeCostP2);
            labCreditsP2.setText(creditsP2);
        }
        refreshLabelText();


        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { // Exits to the main Menu when pressing ESCAPE
            for(int i=0;i<tanks.size();i++)
            {
                tanks.get(i).setScoreZero();
            }

            for(int i = 0; i < this.allTanks.size(); i++)
            {
                System.out.println("MP2 ESC");
                System.out.println("Panzer friendly: " + this.allTanks.get(i).friendly);
                if(this.allTanks.get(i).maxCredits > SpreadingPeace.stats[21] && this.allTanks.get(i).friendly)
                {
                    System.out.println("Update cash");
                    SpreadingPeace.stats[19] = this.allTanks.get(i).maxCredits;
                }
                if(this.allTanks.get(i).friendly)
                {
                    System.out.println("Update damage dealt");
                    SpreadingPeace.stats[0] += this.allTanks.get(i).damageDealt;
                }
                if(this.allTanks.get(i).friendly)
                {
                    System.out.println("Update damage taken");
                    SpreadingPeace.stats[1] += this.allTanks.get(i).generalDamageTaken;
                }
                if(this.allTanks.get(i).friendly)
                {
                    System.out.println("Update lava damage");
                    SpreadingPeace.stats[16] += this.allTanks.get(i).lavaDamageTaken;
                }
            }

            game.setScreen(game.mainMenu);

        }
        this.map.render(1 / 60f); //shows map
        game.batch.begin();

        //Ufo
        ufo.move(map, tanks, enemyTanks);

        ufo.draw(game.batch);

        if(game.nr.getNr()==3)
        {
            platform.draw(game.batch);
        }
        impact.ImpactDamage(allTanks);

        this.checkIfHit();
        for (int i = 0; i < this.tanks.size(); i++) {

            tanks.get(i).respawnTankIfZero(map);
            if(StatScreen.tankDestroyed == true){SpreadingPeace.changeStatAt(4, 1); StatScreen.tankDestroyed = false;}
            tankSprites.get(i).setPosition(tanks.get(i).getXPix(), tanks.get(i).getYPix());
            tankSprites.get(i).setRotation(tanks.get(i).getRotation());
            tankSprites.get(i).draw(game.batch);
            turretSprites.get(i).setPosition(tanks.get(i).getXPix(), tanks.get(i).getYPix());
            turretSprites.get(i).setRotation(tanks.get(i).getTurret().getRotation());
            turretSprites.get(i).draw(game.batch);
            tanks.get(i).lavaboden(map);
            tanks.get(i).fire(game.batch,map);
            speedItem1.checkPickup(tanks.get(i));
            healthItem1.checkPickup(tanks.get(i));
            firepowerItem1.checkPickup(tanks.get(i));


            //*** MINE STUFF FOR ALL PLAYERTANKS ***//
            mine.setPosition(tanks.get(i).getMine().getX(), tanks.get(i).getMine().getY());
            explosion.setPosition(tanks.get(i).getMine().getExplosion().getX(), tanks.get(i).getMine().getExplosion().getY());
            if(tanks.get(i).getMine().getVisible()) {

                mine.draw(game.batch); //alex: draws the mine only when visible is true

            }

            tanks.get(i).getMine().checkPickup(tanks.get(i));

            if(tanks.get(i).getMine().getExplosion().getVisible()){

                //*** DRAW EXPLOSION***//
                explosion.draw(game.batch); //alex: draw explosion to game batch when it should be visible

            }

            //***CYCLE EXPLOSION IMAGE***//

            explosion = new Sprite(texturearray[tanks.get(i).getMine().getExplosion().getAnimation()]);

            damageZone.checkPickup(tanks.get(i));

            //*** PEACEFLAG STUFF FOR ALL PLAYERTANKS ***//
            peaceflag.setPosition(tanks.get(i).getFlag().getX(), tanks.get(i).getFlag().getY());
            if(tanks.get(i).getFlag().getVisible()) {

                peaceflag.draw(game.batch); //alex: draws the peaceflag only when visible is true

            }


        }

        for (int i = 0; i < enemyTankSprites.size(); i++) {

            enemyTanks.get(i).respawnTankIfZero(map);
            if(StatScreen.tankDestroyed == true){SpreadingPeace.changeStatAt(3, 1); StatScreen.tankDestroyed = false;}
            enemyTankSprites.get(i).setPosition(enemyTanks.get(i).getXPix(), enemyTanks.get(i).getYPix());
            enemyTankSprites.get(i).setRotation(enemyTanks.get(i).getRotation());
            enemyTankSprites.get(i).draw(game.batch);
            enemyTurretSprites.get(i).setPosition(enemyTanks.get(i).getXPix(), enemyTanks.get(i).getYPix());
            enemyTurretSprites.get(i).setRotation(enemyTanks.get(i).getTurret().getRotation());
            enemyTurretSprites.get(i).draw(game.batch);
            if(map.mapNr!=3)
            {
                enemyTanks.get(i).lavaboden(map);
            }
            enemyTanks.get(i).fire(game.batch,map);
            speedItem1.checkPickup(enemyTanks.get(i));
            healthItem1.checkPickup(enemyTanks.get(i));
            firepowerItem1.checkPickup(enemyTanks.get(i));


            //*** CHECK PLAYER MINES FOR ENEMY HIT ***//
            tanks.get(0).getMine().checkPickup(enemyTanks.get(i));
            if (tanks.size() > 1) {
                tanks.get(1).getMine().checkPickup(enemyTanks.get(i));
            }

            //*** PEACEFLAG STUFF FOR ALL ENEMYTANKS ***//
            peaceflag.setPosition(enemyTanks.get(i).getFlag().getX(), enemyTanks.get(i).getFlag().getY());
            if(enemyTanks.get(i).getFlag().getVisible()) {

                peaceflag.draw(game.batch); //alex: draws the peaceflag only when visible is true

            }

            // Movement of the 5 AIs
            closestPath = pathfinder.findPath(3, 3, 58, 28);

            for (int j = 0; j < allTanks.size(); j++) {
                if (enemyTanks.get(i).getX() == allTanks.get(j).getX() && enemyTanks.get(i).getY() == allTanks.get(j).getY())
                    ;
                else {
                    currentPath = pathfinder.findPath(enemyTanks.get(i).getX(), enemyTanks.get(i).getY(), allTanks.get(j).getX(), allTanks.get(j).getY());
                    if (currentPath.size() < closestPath.size()) closestPath = currentPath;

                }

            }
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
            healthBar.draw(game.batch);

            pathfinder = new Pathfinder(map.getTiledMap(), map.getBlocked());

        }


        speeditem.setPosition(speedItem1.getX(), speedItem1.getY());

        if (speedItem1.getVisible()) { //only draws item to the map while its visible (when no tank currently has it)
            speeditem.draw(game.batch);
        }

        healthitem.setPosition(healthItem1.getX(), healthItem1.getY());

        if (healthItem1.getVisible()) { //only draws item to the map while its visible (when no tank currently has it)
            healthitem.draw(game.batch);
        }

        firepoweritem.setPosition(firepowerItem1.getX(), firepowerItem1.getY());

        if (firepowerItem1.getVisible()) { //only draws item to the map while its visible (when no tank currently has it)
            firepoweritem.draw(game.batch);
        }

        if(barrierSpriteArray!=null)
        {
            for (int i = 0; i < barrierSpriteArray.length; i++) {
                if (barrierSpriteArray[i] != null) {
                    if(map.barriers!=null&&map.barriers[i]!=null)
                    {
                        barrierSpriteArray[i].setPosition(map.barriers[i].getXPix(), map.barriers[i].getYPix());
                        barrierSpriteArray[i].draw(game.batch);
                    }

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

        tankimg.dispose();
        turretimg.dispose();
        flowerimg.dispose();
        barrierimg.dispose();
        speeditemimg.dispose();
        healthitemimg.dispose();
        firepoweritemimg.dispose();
        mineimg.dispose();
        peaceflagimg.dispose();

    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {

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

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }


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

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }

    public void addSpritesToTanks(List<Tank> tanks, List<Sprite> tankSprites, List<Sprite> turretSprites) //fügt zu jedem Panzer passende Sprites hinzu
    {
        for (int i = 0; i < tanks.size(); i++) {
            if (i == 0) {
                switch (this.getPlayers().get(0).currentColor) {
                    case 0:
                        tankSprites.add(new Sprite(tankimg));
                        turretSprites.add(new Sprite(turretimg));
                        tanks.get(i).setFarbe(Color.GREEN);
                        break;
                    case 1:
                        tankSprites.add(new Sprite(tankimg_blau));
                        turretSprites.add(new Sprite(turretimg));
                        tanks.get(i).setFarbe(Color.BLUE);
                        break;
                    case 2:
                        tankSprites.add(new Sprite(enemyTankimg_braun));
                        turretSprites.add(new Sprite(turretimg));
                        tanks.get(i).setFarbe(Color.BROWN);
                        break;
                    case 3:
                        tankSprites.add(new Sprite(enemyTankimg_cyan));
                        turretSprites.add(new Sprite(turretimg));
                        tanks.get(i).setFarbe(Color.CYAN);
                        break;
                    case 4:
                        tankSprites.add(new Sprite(enemyTankimg_yellow));
                        turretSprites.add(new Sprite(turretimg));
                        tanks.get(i).setFarbe(Color.YELLOW);
                        break;
                    case 5:
                        tankSprites.add(new Sprite(enemyTankimg_lila));
                        turretSprites.add(new Sprite(turretimg));
                        tanks.get(i).setFarbe(Color.PURPLE);
                        break;
                    case 6:
                        tankSprites.add(new Sprite(enemyTankimg_orange));
                        turretSprites.add(new Sprite(turretimg));
                        tanks.get(i).setFarbe(Color.ORANGE);
                        break;
                    case 7:
                        tankSprites.add(new Sprite(enemyTankimg));
                        turretSprites.add(new Sprite(turretimg));
                        tanks.get(i).setFarbe(Color.RED);
                        break;

                }
            }

            if (i == 1) {
                switch (this.getPlayers().get(1).currentColor) {
                    case 0:
                        tankSprites.add(new Sprite(tankimg));
                        turretSprites.add(new Sprite(turretimg));
                        tanks.get(i).setFarbe(Color.GREEN);
                        break;
                    case 1:
                        tankSprites.add(new Sprite(tankimg_blau));
                        turretSprites.add(new Sprite(turretimg));
                        tanks.get(i).setFarbe(Color.BLUE);
                        break;
                    case 2:
                        tankSprites.add(new Sprite(enemyTankimg_braun));
                        turretSprites.add(new Sprite(turretimg));
                        tanks.get(i).setFarbe(Color.BROWN);
                        break;
                    case 3:
                        tankSprites.add(new Sprite(enemyTankimg_cyan));
                        turretSprites.add(new Sprite(turretimg));
                        tanks.get(i).setFarbe(Color.CYAN);
                        break;
                    case 4:
                        tankSprites.add(new Sprite(enemyTankimg_yellow));
                        turretSprites.add(new Sprite(turretimg));
                        tanks.get(i).setFarbe(Color.YELLOW);
                        break;
                    case 5:
                        tankSprites.add(new Sprite(enemyTankimg_lila));
                        turretSprites.add(new Sprite(turretimg));
                        tanks.get(i).setFarbe(Color.PURPLE);
                        break;
                    case 6:
                        tankSprites.add(new Sprite(enemyTankimg_orange));
                        turretSprites.add(new Sprite(turretimg));
                        tanks.get(i).setFarbe(Color.ORANGE);
                        break;
                    case 7:
                        tankSprites.add(new Sprite(enemyTankimg));
                        turretSprites.add(new Sprite(turretimg));
                        tanks.get(i).setFarbe(Color.RED);
                        break;

                }
            }

            allTankSprites.add(new Sprite(tankimg));
            allTankTurretSprites.add(new Sprite(turretimg));
        }
        }


    public void generateTanks(int k, List<Tank> tanks, boolean friendly) //Generiert k anzahl an tanks, Vorsicht x,y sind nicht verteilt
    {
        for (int i = 0; i < k; i++) {
            Tank toAdd = new Tank(flower, game.batch, map, friendly);
            tanks.add(toAdd);
            this.allTanks.add(toAdd);
        }
    }

    public void checkIfHit() //überprüft ob Schuss eine Barriere oder einen Tank getroffen hat
    {
        hitCoord = map.getHitCoord(allTanks);


        for (int i = 0; i < hitCoord.size(); i++) {
            int z = map.searchBarrier(hitCoord.get(i));
            if (z != -1) {
                barrierSpriteArray[z] = null;
                map.setClear(map.barriers[z].x, map.barriers[z].y, 1);
                pathfinder = new Pathfinder(map.getTiledMap(), map.getBlocked());
            }
            z = map.searchTank(hitCoord.get(i), allTanks);
            if (z != -1) {
                if (allTankSprites.get(z) != null) {

                    allTanks.get(z).takeDamage((int) hitCoord.get(i).z); //hier soll als firepower shot.power rein!

                    allTanks.get(z).resetCombo();

                    break;

                }

            }

        }
    }

    public void addSpritesToEnemyTanks(List<Tank> tanks, List<Sprite> tankSprites, List<Sprite> turretSprites) //fügt zu jedem Panzer passende Sprites hinzu
    {
        for (int i = 0; i < tanks.size(); i++) {
            if (i == 0) {
                tankSprites.add(new Sprite(enemyTankimg));
                tanks.get(i).setFarbe(Color.RED);

            }

            else if (i == 1) {
                tankSprites.add(new Sprite(enemyTankimg_braun));
                tanks.get(i).setFarbe(Color.BROWN);

            }

            else if (i == 2) {
                tankSprites.add(new Sprite(enemyTankimg_cyan));
                tanks.get(i).setFarbe(Color.CYAN);
            }

            else if (i == 3) {
                tankSprites.add(new Sprite(enemyTankimg_lila));
                tanks.get(i).setFarbe(Color.PURPLE);
            }

            else if(i == 4)
            {
                tankSprites.add(new Sprite(enemyTankimg_orange));
                tanks.get(i).setFarbe(Color.ORANGE);
            }
            else
                {
                    tankSprites.add(new Sprite(enemyTankimg));
                    tanks.get(i).setFarbe(Color.BLACK);
                }

            turretSprites.add(new Sprite(turretimg));
            allTankSprites.add(new Sprite(enemyTankimg));
            allTankTurretSprites.add(new Sprite(turretimg));


        }
    }

    public List<Player> getPlayers() {
        return this.players;
    }


    public void refreshLabelText()
    {
        this.creditsP1 = "Credits: " + getPlayers().get(0).getTank().getCredits();
        this.healthUpgradeCostP1 = "Resistance Upgrade Cost: " + getPlayers().get(0).getTank().getHealthUpgradeCost();
        this.firePowerUpgradeCostP1 = "Effectiveness Upgrade Cost: " + getPlayers().get(0).getTank().getDamageUpgradeCost();



        if(this.getPlayers().get(0).getTank().schusstyp == 0){shotTypeP1 = "Shot: normal";}
        if(this.getPlayers().get(0).getTank().schusstyp == 1){shotTypeP1 = "Shot: fast";}
        if(this.getPlayers().get(0).getTank().schusstyp == 2){shotTypeP1 = "Shot: spread";}
        if(this.getPlayers().get(0).getTank().schusstyp == 3){shotTypeP1 = "Shot: bounce";}
        if(this.getPlayers().get(0).getTank().schusstyp == 4){shotTypeP1 = "Shot: curve";}
        if(this.getPlayers().size() > 1)
        {

            this.creditsP2 = "Credits: " + getPlayers().get(1).getTank().getCredits();
            this.healthUpgradeCostP2 = "Resistance upgrade cost: " + getPlayers().get(1).getTank().getHealthUpgradeCost();
            this.firePowerUpgradeCostP2 = "Effectiveness upgrade dost: " + getPlayers().get(1).getTank().getDamageUpgradeCost();



            if(this.getPlayers().get(1).getTank().schusstyp == 0){shotTypeP2 = "Shot: normal";}
            if(this.getPlayers().get(1).getTank().schusstyp == 1){shotTypeP2 = "Shot: fast";}
            if(this.getPlayers().get(1).getTank().schusstyp == 2){shotTypeP2 = "Shot: spread";}
            if(this.getPlayers().get(1).getTank().schusstyp == 3){shotTypeP2 = "Shot: bounce";}
            if(this.getPlayers().get(1).getTank().schusstyp == 4 ){shotTypeP2 = "Shot: curve";}
        }
    }






}
//beepboop poop #stayHydrated