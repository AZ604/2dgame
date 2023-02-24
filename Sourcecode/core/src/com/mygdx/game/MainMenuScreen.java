package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.awt.*;

public class MainMenuScreen implements Screen {

    private SpreadingPeace game; // initialise main class
    private BitmapFont font;
    private Texture background;

    private Texture buttonPlay;
    private Rectangle btnPlayRect;

    private Texture buttonExit;
    private Rectangle btnExitRect;

    private Texture buttonMap1;
    private Rectangle btnMap1;
    private Texture buttonMap2;
    private Rectangle btnMap2;
    private Texture buttonMap3;
    private Rectangle btnMap3;

    private Texture configButton; //Bild des Buttons laden
    private Rectangle btnConfigRect; //"hitbox" des Buttons erstellen

    private Texture statsButton;
    private Rectangle btnStats;

    private Texture achievementButton;
    private Rectangle btnAchievement;

    private Texture high;
    private Rectangle btnHigh;

    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private Skin skin;

    private TextField tfPlayer1Name;
    private Label lblP1Name;
    private Label lblP1Keys;
    private Label lblP1KeyValues;

    private TextField tfPlayer2Name;
    private Label lblP2Name;
    private Label lblP2Keys;
    private Label lblP2KeyValues;

    private int gameMode = 1;

    // current mode label

    private Label currentMode1;
    private Label currentMode2;

    // current map Label

    private Label currentMap1;
    private Label currentMap2;
    private Label currentMap3;

    private Button btnPlayer1 = new Button("Add Player 1", 280, 630, 140, 40);
    private Button btnPlayer2 = new Button("Add Player 2", 650, 630, 140, 40);
    private Button modeBtn1 = new Button("Horde", 280, 700, 140, 40);
    private Button modeBtn2 = new Button("Team Deathmatch", 650, 700, 140, 40);
    private Button mapSelector1 = new Button("Map 1", 280, 770, 70, 40);
    private Button mapSelector2 = new Button("Map 2", 465, 770, 70, 40);
    private Button mapSelector3 = new Button("Map 3", 650, 770, 70, 40);


    private Spieletipp tipp = new Spieletipp(font); //MORTEN


    public MainMenuScreen(SpreadingPeace game) {
        this.game = game;
    }

    //Tatiana: zeigt den aktuelle Zustand des MainMenuScreen
    public void show() {
        background = new Texture("MenuScreen.png");
        buttonPlay = new Texture("Play.png");
        btnPlayRect = new Rectangle(0, 0, buttonPlay.getWidth(), buttonPlay.getHeight());
        buttonExit = new Texture("Exit.png");
        btnExitRect = new Rectangle(0, 0, buttonExit.getWidth(), buttonExit.getHeight());
        configButton = new Texture("ConfigButton.png"); //png des Configbuttons zuweisen
        btnConfigRect = new Rectangle(0, 0, configButton.getWidth(), configButton.getHeight()); // "größe" des Buttons zuweisen
        statsButton = new Texture("StatsIcon.png");
        btnStats = new Rectangle(0, 0, statsButton.getWidth(), statsButton.getHeight());
        achievementButton = new Texture("AchievementIcon.png");
        btnAchievement = new Rectangle(0, 0, achievementButton.getWidth(), achievementButton.getHeight());
        high = new Texture("Pokal.png");
        btnHigh = new Rectangle(0, 0, high.getWidth(), high.getHeight());
        buttonMap1 = new Texture("Play.png");
        buttonMap2 = new Texture("Play.png");
        buttonMap3 = new Texture("Play.png");
        btnMap1 = new Rectangle(buttonMap1.getWidth(),buttonMap1.getHeight());
        btnMap2 = new Rectangle(buttonMap2.getWidth(),buttonMap2.getHeight());
        btnMap3 = new Rectangle(buttonMap3.getWidth(),buttonMap3.getHeight());


        if (stage == null) {
            font = new BitmapFont();
            font.setColor(Color.BLUE);
            shapeRenderer = new ShapeRenderer();
            this.stage = new Stage();
            this.skin = new Skin(Gdx.files.internal("uiskin.json"));
            //  Tatiana: Player1 Label and Textfield
            lblP1Name = new Label("Name:", skin);
            lblP1Name.setSize(60, 28);
            // initialisieren tfPlayer1Name, mit Text gleich player1.getName und ein Skin(haut(texture ,farbe und so
            tfPlayer1Name = new TextField(game.player1.getName(), skin);
            tfPlayer1Name.setSize(100, 28); //setzen der Breite und hoehe der tfPlayerName
            lblP1Keys = new Label("Player1 Keys", skin); // initialisieren lblkeys , ein text und skin
            lblP1KeyValues = new Label("Player1 Key Values", skin);// initialisieren lblkeysvalues , ein text und skin
            // Aktualisiert die Label auf keysbelegung des Player1
            updatePlayerKeysLabel(lblP1Keys, lblP1KeyValues, game.player1);
            stage.addActor(lblP1Name);
            stage.addActor(tfPlayer1Name);
            stage.addActor(lblP1Keys);
            stage.addActor(lblP1KeyValues);

            // Mode labels

            currentMode1 = new Label("Current Mode: 1", skin);
            currentMode2 = new Label("Current Mode: 2", skin);
            currentMode1.setSize(60, 28);
            currentMode2.setSize(60, 28);
            stage.addActor(currentMode1);
            stage.addActor(currentMode2);

            // Map selector labels


            currentMap1 = new Label("Current Map: 1", skin);
            currentMap2 = new Label("Current Map: 2", skin);
            currentMap3 = new Label("Current Map: 3", skin);
            currentMap1.setSize(60, 28);
            currentMap2.setSize(60, 28);
            currentMap3.setSize(60, 28);
            stage.addActor(currentMap1);
            stage.addActor(currentMap2);
            stage.addActor(currentMap3);


            //  Tatiana: Player2 Label and Textfield

            lblP2Name = new Label("Name:", skin);
            lblP2Name.setSize(60, 28);
            tfPlayer2Name = new TextField(game.player2.getName(), skin);
            tfPlayer2Name.setSize(100, 28);
            lblP2Keys = new Label("Player2 Keys", skin);
            lblP2KeyValues = new Label("Player2 Key Values", skin);
            // Aktualisiert die Label auf keysbelegung des Player2
            updatePlayerKeysLabel(lblP2Keys, lblP2KeyValues, game.player2);
            stage.addActor(lblP2Name);
            stage.addActor(tfPlayer2Name);
            stage.addActor(lblP2Keys);
            stage.addActor(lblP2KeyValues);

            Gdx.input.setInputProcessor(this.stage);
        }
    }

    // umwandeln der int keycode in ein String
    private String keycode( int keycode) {
        return Input.Keys.toString(keycode) + "\n";
    }

    /**
     * aktualisiert der label auf Keysbelegung der Player
     * str  wird die Keys des Players
     * und values die Keycode
     * @param theLabel
     * @param keyValues
     * @param player
     */
    private void updatePlayerKeysLabel(Label theLabel, Label keyValues, Player player) {
        String str = "Forward:\n"
                + "Backward:\n"
                + "Turn Left:\n"
                + "Turn Right:\n"
                + "Turret Left:\n"
                + "Turret Right:\n"
                + "Shoot:\n"
                + "Color:\n"
                + "Shot type: \n"
                + "Effectiveness: \n"
                + "Resistance: \n";
        String values = keycode(player.Key_FORWARD)
                + keycode(player.Key_BACKWARD)
                + keycode( player.KEY_TURN_LEFT)
                + keycode( player.KEY_TURN_RIGHT)
                + keycode( player.Key_Turret_Left)
                + keycode( player.Key_Turret_Right)
                + keycode(player.Key_SHOOT)
                + player.colors[player.currentColor]
                + "\n"
                + keycode(player.Key_Shottype)
                + keycode(player.Key_UpgradeDamage)
                + keycode(player.Key_UpgradeHealth);
        theLabel.setText(str.trim());
        keyValues.setText(values.trim());
    }

    /**
     * Die Classe Button
     */
    private class Button {
        Rectangle rect;
        String text;
        Color colorBg = Color.RED;
        boolean active = false;

        public Button(String text, int x, int y, int width, int height) {
            this.text = text;
            this.rect = new Rectangle(x, y, width, height);
        }

        /**
         * Male der recteckt auf die position x,y mit Hilfe von shapeRender und GlyphLayout
         * wenn Button angefasst ist, wird er coral
         * wenn den Button geklickt ist, ist er active(grün)
         * wenn nicht , ist nicht áctive(rot)
         * @param mouseX
         * @param mouseY
         */
        public void draw(int mouseX, int mouseY) {
            boolean over = rect.contains(mouseX, mouseY);
            if (over) {
                colorBg = Color.CORAL;
                if (Gdx.input.justTouched()) {
                    active = !active;
                }
            } else {
                colorBg = Color.RED;
            }
            if (active) {
                colorBg = Color.GREEN;
            }
            game.batch.end();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); //ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(colorBg);
            shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            shapeRenderer.end();
            game.batch.begin();

            font.setColor(Color.WHITE);
            GlyphLayout glyphLayout = new GlyphLayout();
            glyphLayout.setText(font, text);
            int textWidth = (int) glyphLayout.width;
            int textHeight = (int) glyphLayout.height;
            int x = (rect.width - textWidth) / 2;
            int y = (rect.height - textHeight) / 2;
            font.draw(game.batch, text, rect.x + x, rect.y + y + textHeight);

            tipp.showTip(game.batch); //MORTEN


        }
    }

    private void play() {
        if (this.gameMode == 1) {
            if (btnPlayer1.active == false && btnPlayer2.active == false) {
                game.player1.setName(tfPlayer1Name.getText());
                game.mainScreen.addPlayer(game.player1);
                btnPlayer1.active = true;
            }


            if (btnPlayer1.active) {
                game.player1.setName(tfPlayer1Name.getText());
                game.mainScreen.addPlayer(game.player1);
            }
            if (btnPlayer2.active) {
                game.player2.setName(tfPlayer2Name.getText());
                game.mainScreen.addPlayer(game.player2);
            }
            game.setScreen(game.mainScreen);
        } else {
            if (btnPlayer1.active == false && btnPlayer2.active == false) {
                game.player1.setName(tfPlayer1Name.getText());
                game.mainScreen2.addPlayer(game.player1);
                btnPlayer1.active = true;
            }
            if (btnPlayer1.active) {
                game.player1.setName(tfPlayer1Name.getText());
                game.mainScreen2.addPlayer(game.player1);
            }
            if (btnPlayer2.active) {
                game.player2.setName(tfPlayer2Name.getText());
                game.mainScreen2.addPlayer(game.player2);
            }
            game.setScreen(game.mainScreen2);

        }
    }

    // @Override
    public void render(float delta) {

        game.batch.begin();



        //Update der Labels der PlayerKeys
        updatePlayerKeysLabel(lblP1Keys, lblP1KeyValues, game.player1);
        updatePlayerKeysLabel(lblP2Keys, lblP2KeyValues, game.player2);


        int x = Gdx.input.getX();
        int y = Gdx.input.getY();
        int height = Gdx.app.getGraphics().getHeight();
        int width = Gdx.app.getGraphics().getWidth();
        int centerX = width / 2;
        y = height - y;

        /*if (Gdx.input.isKeyJustPressed(Input.Keys.H)) { //Highscore opens with pressing H
            game.setScreen(game.highscore);
        }*/

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
           play();

        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { // Closes the application when pressing ESC

            SpreadingPeace.refreshStats();

            Gdx.app.exit();
            return;

        }

        if (Gdx.input.justTouched()) {

            if (x > 720 && x < 850 && y < 480 && y > 460) game.player1.changeColor();
            if (x > 1070 && x < 1210 && y < 480 && y > 460) game.player2.changeColor();


            if (btnPlayRect.contains(x, y)) {
                play();
            } else if (btnExitRect.contains(x, y)) {
                Gdx.app.exit();
            } else if (btnConfigRect.contains(x, y)) //öffnen der Configuration wenn man mit der Maus drüber hovert und klickt
            {
                //----------------------------------------------------------------------------Stats testen
                System.out.println("Vor Change stats");
                //SpreadingPeace.changeStatAt(2, 5);
                System.out.println("nach change stats");
                for(int i = 0; i < SpreadingPeace.stats.length; i++){ System.out.println(SpreadingPeace.stats[i]);} //Testkram
                //SpreadingPeace.refreshStats();
                //----------------------------------------------------------------------------Stats testen
                game.setScreen(game.configMenu);
            } else if (btnHigh.contains(x, y)) {
                game.setScreen(game.highscore);
            }
            else if(btnStats.contains(x,y))
            {
                game.setScreen(game.statScreen);
            }
            else if(btnAchievement.contains(x, y))
            {
                game.setScreen(game.achievementScreen);
            }
        }


        btnPlayer1.rect.x = centerX - 100 - btnPlayer1.rect.width;// positioniert 100 pix links von zentrun der button player 1
        btnPlayer2.rect.x = centerX + 100; // positioniert 100 pix rechts von zentrun der button player 2

        modeBtn1.rect.x = centerX - 100 - modeBtn1.rect.width;
        modeBtn2.rect.x = centerX + 100;

        mapSelector1.rect.x = centerX - 100 - mapSelector1.rect.width;
        mapSelector2.rect.x = centerX - 40;
        mapSelector3.rect.x = centerX + 100;

        btnPlayRect.x = (width - buttonPlay.getWidth()) / 2;
        btnPlayRect.y = height / 2 - 100;
        btnExitRect.x = width / 2 - buttonExit.getWidth() / 2;
        btnExitRect.y = height / 2 - 200;
        game.batch.draw(background, 0, 0, width, height);
        btnPlayer1.draw(x, y);
        btnPlayer2.draw(x, y);

        modeBtn1.draw(x, y);
        modeBtn2.draw(x, y);

        mapSelector1.draw(x, y);
        mapSelector2.draw(x, y);
        mapSelector3.draw(x, y);



        game.batch.draw(buttonPlay, btnPlayRect.x, btnPlayRect.y);
        game.batch.draw(buttonExit, btnExitRect.x, btnExitRect.y);

        btnConfigRect.x = width - configButton.getWidth() - 50; //platzierung der "hitbox" des Buttons Config
        btnConfigRect.y = configButton.getHeight() + 50; // ^^^^
        game.batch.draw(configButton, btnConfigRect.x, btnConfigRect.y); //Bild des configButtons auf die "hitbox" setzen

        btnHigh.x = 20;
        btnHigh.y = height - 120;
        game.batch.draw(high, btnHigh.x, btnHigh.y);

        btnStats.x = width - statsButton.getWidth() - 50;
        btnStats.y = height - btnStats.height - 50;
        game.batch.draw(statsButton, btnStats.x, btnStats.y);

        btnAchievement.x = btnStats.x;
        btnAchievement.y = btnStats.y - (int) btnStats.getHeight() - 25;
        game.batch.draw(achievementButton, btnAchievement.x, btnAchievement.y);



        // font.draw(game.batch, "x:"+x+" y:"+y +" playRect:" + btnPlayRect, 10,30);
        game.batch.end();
        // macht die label sichtbar, nur wenn der Button des player1 actif sind
        lblP1Name.setVisible(btnPlayer1.active);
        tfPlayer1Name.setVisible(btnPlayer1.active);
        lblP1Keys.setVisible(btnPlayer1.active);
        lblP1KeyValues.setVisible(btnPlayer1.active);

        currentMode1.setVisible(modeBtn1.active);
        currentMode2.setVisible(modeBtn2.active);
// macht die label sichtbar, nur wenn der Button des player2 actif sind
        lblP2Name.setVisible(btnPlayer2.active);
        tfPlayer2Name.setVisible(btnPlayer2.active);
        lblP2Keys.setVisible(btnPlayer2.active);
        lblP2KeyValues.setVisible(btnPlayer2.active);
        // wenn bntplayer1 active iat, wird die position des lblP1Name,tfPlayer1Name,lblp1Keys und values gesetzt
        if (btnPlayer1.active) {
            lblP1Name.setPosition(btnPlayer1.rect.x - 160, btnPlayer1.rect.y - btnPlayer1.rect.height - 5);
            tfPlayer1Name.setPosition(btnPlayer1.rect.x - 100, btnPlayer1.rect.y - btnPlayer1.rect.height - 5);
            lblP1Keys.setPosition(btnPlayer1.rect.x, btnPlayer1.rect.y - lblP1Keys.getHeight() - 110);
            lblP1KeyValues.setPosition(btnPlayer1.rect.x + 100, btnPlayer1.rect.y - lblP1Keys.getHeight() - 110);
        }

        if (modeBtn1.active) {
            currentMode1.setPosition(modeBtn1.rect.x, modeBtn1.rect.y - modeBtn1.rect.height + 5);
            modeBtn2.active = false;
            this.gameMode = 1;
        }
        if (modeBtn2.active) {
            currentMode2.setPosition(modeBtn2.rect.x, modeBtn2.rect.y - modeBtn1.rect.height + 5);
            modeBtn1.active = false;
            this.gameMode = 2;
        }

        if (modeBtn1.active || modeBtn2.active) {
            stage.draw();
            stage.act();
        }
        // wenn bntplayer2 actif ist, wird die position des lblP1Name,tfPlayer1Name,lblp1Keys und values gesetzt
        if (btnPlayer2.active) {
            lblP2Name.setPosition(btnPlayer2.rect.x + 100 + 60, btnPlayer2.rect.y - btnPlayer2.rect.height - 5);
            tfPlayer2Name.setPosition(btnPlayer2.rect.x + 60 + 100 + 60, btnPlayer2.rect.y - btnPlayer2.rect.height - 5);
            lblP2Keys.setPosition(btnPlayer2.rect.x, btnPlayer2.rect.y - lblP2Keys.getHeight() - 110);
            lblP2KeyValues.setPosition(btnPlayer2.rect.x + 100, btnPlayer2.rect.y - lblP2Keys.getHeight() - 110);
        }
        if (btnPlayer1.active || btnPlayer2.active) {
            stage.draw();
            stage.act();
        }

        if (mapSelector1.active) {
            mapSelector2.active = false; // Schaltet andere knöpfe aus
            mapSelector3.active = false;
            game.nr.setNr(1);
            // CODE von Sören

        }
        if (mapSelector2.active) {
            mapSelector1.active = false;
            mapSelector3.active = false;

            // Code von Sören
            game.nr.setNr(2);
        }

        if (mapSelector3.active) {
            mapSelector1.active = false;
            mapSelector2.active = false;

            // code von Sören
            game.nr.setNr(3);
        }





    }

    //@Override
    public void resize(int width, int height) {
    }

    //@Override
    public void pause() {
    }

    //@Override
    public void resume() {

    }

    //@Override
    public void hide() {

    }

    // @Override
    public void dispose() {
    }

}

