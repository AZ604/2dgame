package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.controllers.*;
import com.badlogic.gdx.math.Vector3;

import java.awt.*;


public class ConfigurationMenu implements Screen, InputProcessor, ControllerListener
{

    private SpreadingPeace game;

    private Texture buttonSafeAndExit, symbolPlayer1, symbolPlayer2, buttonChange, background, keyboardSymbol, gamepadSymbol, buttonRestoreToDefault, soundSymbol, buttonLouder, buttonLower;
    private Rectangle btnSafeAndExitRect, btnRestoreToDefault;
    private Rectangle btnChangeForwardP1, btnChangeBackwardP1, btnChangeTurnLeftP1, btnChangeTurnRightP1, btnChangeShootP1, btnChangeRotateTurretLeftP1, btnChangeRotateTurretRightP1, btnChangeMineP1, btnChangeSSTP1, btnChangeUDP1, btnChangeUHP1;
    private Rectangle btnChangeForwardP2, btnChangeBackwardP2, btnChangeTurnLeftP2, btnChangeTurnRightP2, btnChangeShootP2, btnChangeRotateTurretLeftP2, btnChangeRotateTurretRightP2, btnChangeMineP2, btnChangeSSTP2, btnChangeUDP2, btnChangeUHP2;
    private Rectangle btnChangeForwardP1G, btnChangeBackwardP1G, btnChangeShootP1G, btnChangeRotateTurretLeftP1G, btnChangeRotateTurretRightP1G, btnChangeMineP1G;
    private Rectangle btnChangeForwardP2G, btnChangeBackwardP2G, btnChangeShootP2G, btnChangeRotateTurretLeftP2G, btnChangeRotateTurretRightP2G, btnChangeMineP2G;
    private Rectangle btnLouder, btnLower;

    private int newKeyCode;
    private int newButtonCode;
    private float volume = SpreadingPeace.volume;
    private int showVolume = (int) volume;

    private Skin skin;

    private Stage stage;

    private Label labP1Forward, labP1Backward, labP1TurnLeft, labP1TurnRight, labP1Shoot, labP1RotateTurretLeft, labP1RotateTurretRight, labP1Mine, labP1SST, labP1UD, labP1UH;
    private Label labP2Forward, labP2Backward, labP2TurnLeft, labP2TurnRight, labP2Shoot, labP2RotateTurretLeft, labP2RotateTurretRight, labP2Mine, labP2SST, labP2UD, labP2UH;
    private Label labKeyAlreadyUsed, labExplanation;
    private Label labVolume;

    private Controller controller1, controller2;

    //Constructor
    public ConfigurationMenu(SpreadingPeace game)
    {
        this.game = game;
    }

    public void show()
    {
        stage = new Stage();

        //--------------------------------------------------------------------------------------
        //       Variablen für Texturen, "hitbox" der Buttons deklarieren, skin zuweisen
        //--------------------------------------------------------------------------------------

        //Texturen
        buttonSafeAndExit = new Texture("SafeAndExitButton.png");
        symbolPlayer1 = new Texture("Player1Symbol.png");
        symbolPlayer2 = new Texture("Player2Symbol.png");
        buttonChange = new Texture("ChangeButton.png");
        background = new Texture("ConfigMenuBackground.png");
        keyboardSymbol = new Texture("KeyboardSymbol.png");
        gamepadSymbol = new Texture("GamepadSymbol.png");
        buttonRestoreToDefault = new Texture("RestoreToDefaultSettings.png");
        buttonLouder = new Texture("LouderButton.png");
        buttonLower = new Texture("LowerButton.png");
        soundSymbol = new Texture("ConfigSoundSymbol.png");

        //"hitboxen" -> Rectangles
        btnSafeAndExitRect = new Rectangle(0,0, buttonSafeAndExit.getWidth(), buttonSafeAndExit.getHeight());
        btnRestoreToDefault = new Rectangle(0, 0, buttonRestoreToDefault.getWidth(), buttonRestoreToDefault.getHeight());

        btnLouder = new Rectangle(0,0, buttonLouder.getWidth(), buttonLouder.getHeight());
        btnLower = new Rectangle(0,0, buttonLower.getWidth(),buttonLower.getHeight());
        //P1
        //Tastatur
        btnChangeForwardP1 = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeBackwardP1 = new Rectangle(0 ,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeTurnLeftP1 = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeTurnRightP1 = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeShootP1 = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeRotateTurretLeftP1 = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeRotateTurretRightP1 = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeMineP1 = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeSSTP1 = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeUDP1 = new Rectangle(0, 0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeUHP1 = new Rectangle(0, 0, buttonChange.getWidth(), buttonChange.getHeight());
        //Gamepad
        btnChangeForwardP1G = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeBackwardP1G = new Rectangle(0 ,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeShootP1G = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeRotateTurretLeftP1G = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeRotateTurretRightP1G = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeMineP1G = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        //P2
        //Tastatur
        btnChangeForwardP2 = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeBackwardP2 = new Rectangle(0 ,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeTurnLeftP2 = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeTurnRightP2 = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeShootP2 = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeRotateTurretLeftP2 = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeRotateTurretRightP2 = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeMineP2 = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeSSTP2 = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeUDP2 = new Rectangle(0, 0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeUHP2 = new Rectangle(0, 0, buttonChange.getWidth(), buttonChange.getHeight());
        //Gamepad
        btnChangeForwardP2G = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeBackwardP2G = new Rectangle(0 ,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeShootP2G = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeRotateTurretLeftP2G = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeRotateTurretRightP2G = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());
        btnChangeMineP2G = new Rectangle(0,0, buttonChange.getWidth(), buttonChange.getHeight());


        //Skin
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        //--------------------------------------------------------------------------------------
        //         Stage      &      Labels für Buttonbeschreibung erstellen
        //--------------------------------------------------------------------------------------

        //Beschriftung
        labKeyAlreadyUsed = new Label ("The Key is already in use", skin);
        labExplanation = new Label("Hold the Key and then press change", skin);

        labVolume = new Label("Volume: " + showVolume, skin);
        //P1
        labP1Forward = new Label("Forward", skin);
        labP1Backward = new Label("Backward", skin);
        labP1TurnLeft = new Label("Turn left", skin);
        labP1TurnRight = new Label("Turn right", skin);
        labP1Shoot = new Label("Fire", skin);
        labP1RotateTurretLeft = new Label("Rotate turret left", skin);
        labP1RotateTurretRight = new Label("Rotate turret right", skin);
        labP1Mine = new Label("Mine", skin);
        labP1SST = new Label("Switch Flowertype", skin);
        labP1UD = new Label("Upgrade Damage", skin);
        labP1UH = new Label("Upgrade Health", skin);
        //P2
        labP2Forward = new Label("Forward", skin);
        labP2Backward = new Label("Backward", skin);
        labP2TurnLeft = new Label("Turn left", skin);
        labP2TurnRight = new Label("Turn right", skin);
        labP2Shoot = new Label("Fire", skin);
        labP2RotateTurretLeft = new Label("Rotate turret left", skin);
        labP2RotateTurretRight = new Label("Rotate turret right", skin);
        labP2Mine = new Label("Mine", skin);
        labP2SST = new Label("Switch Flowertype", skin);
        labP2UD = new Label("Upgrade Damage", skin);
        labP2UH = new Label("Upgrade Health", skin);

        //Schriftgröße
        labKeyAlreadyUsed.setScale(2f);
        labExplanation.setScale(2f);
        //P1
        labP1Forward.setFontScale(2f);
        labP1Backward.setFontScale(2f);
        labP1TurnLeft.setFontScale(2f);
        labP1TurnRight.setFontScale(2f);
        labP1Shoot.setFontScale(2f);
        labP1RotateTurretLeft.setFontScale(2f);
        labP1RotateTurretRight.setFontScale(2f);
        labP1Mine.setFontScale(2f);
        labP1SST.setFontScale(2f);
        labP1UD.setFontScale(2f);
        labP1UH.setFontScale(2f);
        //P2
        labP2Forward.setFontScale(2f);
        labP2Backward.setFontScale(2f);
        labP2TurnLeft.setFontScale(2f);
        labP2TurnRight.setFontScale(2f);
        labP2Shoot.setFontScale(2f);
        labP2RotateTurretLeft.setFontScale(2f);
        labP2RotateTurretRight.setFontScale(2f);
        labP2Mine.setFontScale(2f);
        labP2SST.setFontScale(2f);
        labP2UD.setFontScale(2f);
        labP2UH.setFontScale(2f);

        //Label zur Stage hinzufügen
        stage.addActor(labExplanation);
        stage.addActor(labVolume);
        //P1
        stage.addActor(labP1Forward);
        stage.addActor(labP1Backward);
        stage.addActor(labP1TurnLeft);
        stage.addActor(labP1TurnRight);
        stage.addActor(labP1Shoot);
        stage.addActor(labP1RotateTurretLeft);
        stage.addActor(labP1RotateTurretRight);
        stage.addActor(labP1Mine);
        stage.addActor(labP1SST);
        stage.addActor(labP1UD);
        stage.addActor(labP1UH);
        //P2
        stage.addActor(labP2Forward);
        stage.addActor(labP2Backward);
        stage.addActor(labP2TurnLeft);
        stage.addActor(labP2TurnRight);
        stage.addActor(labP2Shoot);
        stage.addActor(labP2RotateTurretLeft);
        stage.addActor(labP2RotateTurretRight);
        stage.addActor(labP2Mine);
        stage.addActor(labP2SST);
        stage.addActor(labP2UD);
        stage.addActor(labP2UH);

        Controllers.addListener(this);
        Gdx.input.setInputProcessor(this);

        //--------------------------------------------------------------------------------------
        //                          Controller zuweisen
        //--------------------------------------------------------------------------------------
        if(Controllers.getControllers().size ==1)
        {
            controller1 = Controllers.getControllers().first();
        }
        else if(Controllers.getControllers().size >= 1)
        {
            controller1 = Controllers.getControllers().first();
            controller2 = Controllers.getControllers().get(1);
        }
    }

    @Override public void render(float delta)
    {
        game.batch.begin();

        //--------------------------------------------------------------------------------------
        //
        //--------------------------------------------------------------------------------------

        //Frequente Variablen
        int height = Gdx.app.getGraphics().getHeight();
        int width = Gdx.app.getGraphics().getWidth();

        //Screenhintergrund setzen
        game.batch.draw(background, 0, 0, width, height);

        //--------------------------------------------------------------------------------------
        //                              Buttons & Texturen platzieren
        //--------------------------------------------------------------------------------------

        //Safe+Exit Button + Restore to Default Button
        btnSafeAndExitRect.x = width - btnSafeAndExitRect.width - 50;
        btnSafeAndExitRect.y = btnSafeAndExitRect.height + 50;
        game.batch.draw(buttonSafeAndExit,btnSafeAndExitRect.x, btnSafeAndExitRect.y);

        btnRestoreToDefault.x = btnSafeAndExitRect.x - (int) btnSafeAndExitRect.getWidth() - 50;
        btnRestoreToDefault.y = btnSafeAndExitRect.y;
        game.batch.draw(buttonRestoreToDefault, btnRestoreToDefault.x, btnRestoreToDefault.y);


        btnLouder.x = width - btnLouder.width - 50;
        btnLower.x = btnLouder.x - btnLower.width - 25;

        btnLouder.y = height - 400;
        btnLower.y = btnLouder.y;

        game.batch.draw(buttonLouder, btnLouder.x, btnLouder.y);
        game.batch.draw(buttonLower, btnLower.x, btnLower.y);
        //restlicher Menüplatz
        int menuSpace = width - (width - btnSafeAndExitRect.x);
        int middle = menuSpace / 2;
        int quarter = middle / 2;

        //Player 1 & 2 Textur
        int distanceTopBorder = height - 50;

        game.batch.draw(symbolPlayer1, quarter - symbolPlayer1.getWidth() / 2, distanceTopBorder - symbolPlayer1.getHeight());
        game.batch.draw(symbolPlayer2, middle + quarter - (symbolPlayer2.getWidth() / 2), distanceTopBorder - symbolPlayer2.getHeight());

        //Labels
        labKeyAlreadyUsed.setPosition(middle, symbolPlayer1.getHeight());
        labKeyAlreadyUsed.setAlignment(4);

        labExplanation.setPosition(middle, height - symbolPlayer1. getHeight() - 70);
        labExplanation.setAlignment(4,4);

        showVolume = (int) (volume * 1000); //Volume als int anzeigen lassen (und aktualisieren)
        labVolume.setText("Volume: " + showVolume);
        labVolume.setPosition(btnLower.x,btnLouder.y + btnLouder.height + 12);
        int distanceLabelLabel = 75;
        //P1
        int labP1PosX = 200;
        labP1Forward.setPosition(labP1PosX,distanceTopBorder - symbolPlayer1.getHeight() - distanceLabelLabel);
        labP1Backward.setPosition(labP1PosX, labP1Forward.getY() - distanceLabelLabel); //hier Y-Achse andersherum for reasons?!?
        labP1TurnLeft.setPosition(labP1PosX, labP1Backward.getY() - distanceLabelLabel);
        labP1TurnRight.setPosition(labP1PosX, labP1TurnLeft.getY() - distanceLabelLabel);
        labP1Shoot.setPosition(labP1PosX, labP1TurnRight.getY() - distanceLabelLabel);
        labP1RotateTurretLeft.setPosition(labP1PosX, labP1Shoot.getY() - distanceLabelLabel);
        labP1RotateTurretRight.setPosition(labP1PosX, labP1RotateTurretLeft.getY() - distanceLabelLabel);
        labP1Mine.setPosition(labP1PosX, labP1RotateTurretRight.getY() - distanceLabelLabel);
        labP1SST.setPosition(labP1PosX, labP1Mine.getY() - distanceLabelLabel);
        labP1UD.setPosition(labP1PosX, labP1SST.getY() - distanceLabelLabel);
        labP1UH.setPosition(labP1PosX, labP1UD.getY() - distanceLabelLabel);
        //P2
        int labP2PosX = middle + 200;
        labP2Forward.setPosition(labP2PosX,distanceTopBorder - symbolPlayer2.getHeight() - distanceLabelLabel);
        labP2Backward.setPosition(labP2PosX, labP2Forward.getY() - distanceLabelLabel);
        labP2TurnLeft.setPosition(labP2PosX, labP2Backward.getY() - distanceLabelLabel);
        labP2TurnRight.setPosition(labP2PosX, labP2TurnLeft.getY() - distanceLabelLabel);
        labP2Shoot.setPosition(labP2PosX, labP2TurnRight.getY() - distanceLabelLabel);
        labP2RotateTurretLeft.setPosition(labP2PosX, labP2Shoot.getY() - distanceLabelLabel);
        labP2RotateTurretRight.setPosition(labP2PosX, labP2RotateTurretLeft.getY() - distanceLabelLabel);
        labP2Mine.setPosition(labP2PosX, labP2RotateTurretRight.getY() - distanceLabelLabel);
        labP2SST.setPosition(labP2PosX, labP2Mine.getY() - distanceLabelLabel);
        labP2UD.setPosition(labP2PosX, labP2SST.getY() - distanceLabelLabel);
        labP2UH.setPosition(labP2PosX, labP2UD.getY() - distanceLabelLabel);

        //Change P1 & P2 Buttons
        int distanceLabelButton = 300;
        int distancePlayerSymbol = 87;
        int distanceChangeButton = distanceLabelLabel;
        int distanceKeyboardGamepadChange = buttonChange.getWidth() + 50;
        int halfChangeButtonWidth = (int) btnChangeForwardP1.getWidth() / 2;
        int halfControllerSymbol = keyboardSymbol.getWidth() / 2;
        int halfGamepadSymbol = gamepadSymbol.getWidth() / 2;

        //Tastatur
        game.batch.draw(keyboardSymbol, labP1PosX + distanceLabelButton + halfChangeButtonWidth - halfControllerSymbol, distanceTopBorder - symbolPlayer1.getHeight() - 25);
        game.batch.draw(keyboardSymbol, labP2PosX + distanceLabelButton + halfChangeButtonWidth - halfControllerSymbol, distanceTopBorder - symbolPlayer1.getHeight() - 25);
            //X Pos
                //P1
                btnChangeForwardP1.x = labP1PosX + distanceLabelButton;
                btnChangeBackwardP1.x = labP1PosX + distanceLabelButton;
                btnChangeTurnLeftP1.x = labP1PosX + distanceLabelButton;
                btnChangeTurnRightP1.x = labP1PosX + distanceLabelButton;
                btnChangeShootP1.x = labP1PosX + distanceLabelButton;
                btnChangeRotateTurretLeftP1.x = labP1PosX + distanceLabelButton;
                btnChangeRotateTurretRightP1.x = labP1PosX + distanceLabelButton;
                btnChangeMineP1.x = labP1PosX + distanceLabelButton;
                btnChangeSSTP1.x = labP1PosX + distanceLabelButton;
                btnChangeUDP1.x = labP1PosX + distanceLabelButton;
                btnChangeUHP1.x = labP1PosX + distanceLabelButton;
                //P2
                btnChangeForwardP2.x = labP2PosX + distanceLabelButton;
                btnChangeBackwardP2.x = labP2PosX + distanceLabelButton;
                btnChangeTurnLeftP2.x = labP2PosX + distanceLabelButton;
                btnChangeTurnRightP2.x = labP2PosX + distanceLabelButton;
                btnChangeShootP2.x = labP2PosX + distanceLabelButton;
                btnChangeRotateTurretLeftP2.x = labP2PosX + distanceLabelButton;
                btnChangeRotateTurretRightP2.x = labP2PosX + distanceLabelButton;
                btnChangeMineP2.x = labP2PosX + distanceLabelButton;
                btnChangeSSTP2.x = labP2PosX + distanceLabelButton;
                btnChangeUDP2.x = labP2PosX + distanceLabelButton;
                btnChangeUHP2.x = labP2PosX + distanceLabelButton;
            //Y Pos
                //P1
                btnChangeForwardP1.y = distanceTopBorder - symbolPlayer1.getHeight() - distancePlayerSymbol;
                btnChangeBackwardP1.y = btnChangeForwardP1.y  - distanceChangeButton;
                btnChangeTurnLeftP1.y = btnChangeBackwardP1.y - distanceChangeButton;
                btnChangeTurnRightP1.y = btnChangeTurnLeftP1.y - distanceChangeButton;
                btnChangeShootP1.y = btnChangeTurnRightP1.y - distanceChangeButton;
                btnChangeRotateTurretLeftP1.y = btnChangeShootP1.y - distanceChangeButton;
                btnChangeRotateTurretRightP1.y = btnChangeRotateTurretLeftP1.y - distanceChangeButton;
                btnChangeMineP1.y = btnChangeRotateTurretRightP1.y - distanceChangeButton;
                btnChangeSSTP1.y = btnChangeMineP1.y - distanceChangeButton;
                btnChangeUDP1.y = btnChangeSSTP1.y  - distanceChangeButton;
                btnChangeUHP1.y = btnChangeUDP1.y - distanceChangeButton;
                //P2
                btnChangeForwardP2.y = distanceTopBorder - symbolPlayer2.getHeight() - distancePlayerSymbol;
                btnChangeBackwardP2.y = btnChangeForwardP2.y  - distanceChangeButton;
                btnChangeTurnLeftP2.y = btnChangeBackwardP2.y - distanceChangeButton;
                btnChangeTurnRightP2.y = btnChangeTurnLeftP2.y - distanceChangeButton;
                btnChangeShootP2.y = btnChangeTurnRightP2.y - distanceChangeButton;
                btnChangeRotateTurretLeftP2.y = btnChangeShootP2.y - distanceChangeButton;
                btnChangeRotateTurretRightP2.y = btnChangeRotateTurretLeftP2.y - distanceChangeButton;
                btnChangeMineP2.y = btnChangeRotateTurretRightP2.y - distanceChangeButton;
                btnChangeSSTP2.y = btnChangeMineP2.y -distanceChangeButton;
                btnChangeUDP2.y = btnChangeSSTP2.y  - distanceChangeButton;
                btnChangeUHP2.y = btnChangeUDP2.y - distanceChangeButton;
            //draw
                //P1
                game.batch.draw(buttonChange, btnChangeForwardP1.x, btnChangeForwardP1.y);
                game.batch.draw(buttonChange, btnChangeBackwardP1.x, btnChangeBackwardP1.y);
                game.batch.draw(buttonChange, btnChangeTurnLeftP1.x, btnChangeTurnLeftP1.y);
                game.batch.draw(buttonChange, btnChangeTurnRightP1.x, btnChangeTurnRightP1.y);
                game.batch.draw(buttonChange, btnChangeShootP1.x, btnChangeShootP1.y);
                game.batch.draw(buttonChange, btnChangeRotateTurretLeftP1.x, btnChangeRotateTurretLeftP1.y);
                game.batch.draw(buttonChange, btnChangeRotateTurretRightP1.x, btnChangeRotateTurretRightP1.y);
                game.batch.draw(buttonChange, btnChangeMineP1.x, btnChangeMineP1.y);
                game.batch.draw(buttonChange, btnChangeSSTP1.x, btnChangeSSTP1.y);
                game.batch.draw(buttonChange, btnChangeUDP1.x, btnChangeUDP1.y);
                game.batch.draw(buttonChange, btnChangeUHP1.x, btnChangeUHP1.y);
                //P2
                game.batch.draw(buttonChange, btnChangeForwardP2.x, btnChangeForwardP2.y);
                game.batch.draw(buttonChange, btnChangeBackwardP2.x, btnChangeBackwardP2.y);
                game.batch.draw(buttonChange, btnChangeTurnLeftP2.x, btnChangeTurnLeftP2.y);
                game.batch.draw(buttonChange, btnChangeTurnRightP2.x, btnChangeTurnRightP2.y);
                game.batch.draw(buttonChange, btnChangeShootP2.x, btnChangeShootP2.y);
                game.batch.draw(buttonChange, btnChangeRotateTurretLeftP2.x, btnChangeRotateTurretLeftP2.y);
                game.batch.draw(buttonChange, btnChangeRotateTurretRightP2.x, btnChangeRotateTurretRightP2.y);
                game.batch.draw(buttonChange, btnChangeMineP2.x, btnChangeMineP2.y);
                game.batch.draw(buttonChange, btnChangeSSTP2.x, btnChangeSSTP2.y);
                game.batch.draw(buttonChange, btnChangeUDP2.x, btnChangeUDP2.y);
                game.batch.draw(buttonChange, btnChangeUHP2.x, btnChangeUHP2.y);

        //Gamepad
        game.batch.draw(gamepadSymbol, btnChangeForwardP1.x  + distanceKeyboardGamepadChange  + halfChangeButtonWidth - halfGamepadSymbol, distanceTopBorder - symbolPlayer1.getHeight() - 25);
        game.batch.draw(gamepadSymbol, btnChangeForwardP2.x  + distanceKeyboardGamepadChange  + halfChangeButtonWidth - halfGamepadSymbol, distanceTopBorder - symbolPlayer1.getHeight() - 25);
            //X Pos
                //P1
                btnChangeForwardP1G.x = btnChangeForwardP1.x + distanceKeyboardGamepadChange;
                btnChangeBackwardP1G.x = btnChangeBackwardP1.x + distanceKeyboardGamepadChange;
                btnChangeShootP1G.x = btnChangeShootP1.x + distanceKeyboardGamepadChange;
                btnChangeRotateTurretLeftP1G.x = btnChangeRotateTurretLeftP1.x + distanceKeyboardGamepadChange;
                btnChangeRotateTurretRightP1G.x = btnChangeRotateTurretRightP1.x + distanceKeyboardGamepadChange;
                btnChangeMineP1G.x = btnChangeMineP1.x + distanceKeyboardGamepadChange;
                //P2
                btnChangeForwardP2G.x = btnChangeForwardP2.x + distanceKeyboardGamepadChange;
                btnChangeBackwardP2G.x = btnChangeBackwardP2.x + distanceKeyboardGamepadChange;
                btnChangeShootP2G.x = btnChangeShootP2.x + distanceKeyboardGamepadChange;
                btnChangeRotateTurretLeftP2G.x = btnChangeRotateTurretLeftP2.x + distanceKeyboardGamepadChange;
                btnChangeRotateTurretRightP2G.x = btnChangeRotateTurretRightP2.x + distanceKeyboardGamepadChange;
                btnChangeMineP2G.x = btnChangeMineP2.x + distanceKeyboardGamepadChange;
            //Y Pos
                //P1
                btnChangeForwardP1G.y = btnChangeForwardP1.y;
                btnChangeBackwardP1G.y = btnChangeBackwardP1.y;
                btnChangeShootP1G.y = btnChangeShootP1.y;
                btnChangeRotateTurretLeftP1G.y = btnChangeRotateTurretLeftP1.y;
                btnChangeRotateTurretRightP1G.y = btnChangeRotateTurretRightP1.y;
                btnChangeMineP1G.y = btnChangeMineP1.y;
                //P2
                btnChangeForwardP2G.y = btnChangeForwardP2.y;
                btnChangeBackwardP2G.y = btnChangeBackwardP2.y;
                btnChangeShootP2G.y = btnChangeShootP2.y;
                btnChangeRotateTurretLeftP2G.y = btnChangeRotateTurretLeftP2.y;
                btnChangeRotateTurretRightP2G.y = btnChangeRotateTurretRightP2.y;
                btnChangeMineP2G.y = btnChangeMineP2.y;
            //draw
                //P1
                game.batch.draw(buttonChange,btnChangeForwardP1G.x, btnChangeForwardP1G.y);
                game.batch.draw(buttonChange, btnChangeBackwardP1G.x, btnChangeBackwardP1G.y);
                game.batch.draw(buttonChange, btnChangeShootP1G.x, btnChangeShootP1G.y);
                game.batch.draw(buttonChange, btnChangeRotateTurretLeftP1G.x, btnChangeRotateTurretLeftP1G.y);
                game.batch.draw(buttonChange, btnChangeRotateTurretRightP1G.x, btnChangeRotateTurretRightP1G.y);
                game.batch.draw(buttonChange, btnChangeMineP1G.x, btnChangeMineP2G.y);
                //P2
                game.batch.draw(buttonChange,btnChangeForwardP2G.x, btnChangeForwardP2G.y);
                game.batch.draw(buttonChange, btnChangeBackwardP2G.x, btnChangeBackwardP2G.y);
                game.batch.draw(buttonChange, btnChangeShootP2G.x, btnChangeShootP2G.y);
                game.batch.draw(buttonChange, btnChangeRotateTurretLeftP2G.x, btnChangeRotateTurretLeftP2G.y);
                game.batch.draw(buttonChange, btnChangeRotateTurretRightP2G.x, btnChangeRotateTurretRightP2G.y);
                game.batch.draw(buttonChange, btnChangeMineP2G.x, btnChangeMineP2G.y);

        //--------------------------------------------------------------------------------------
        //                     Buttons betätigen + Menü durch ESC verlassen
        //--------------------------------------------------------------------------------------

        //per Tastatur Configs verlassen
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            game.setScreen(game.mainMenu);
        }

        //per Mausklick einen button aktivieren
        int x = Gdx.input.getX();
        int y = Gdx.input.getY();

        if(Gdx.input.justTouched())
        {
            //Save and Exit
            if(btnSafeAndExitRect.contains(x,height - y)) //Y-Achse hier aus gründen von oben??
            {
                game.setScreen(game.mainMenu);
            }
            if(btnRestoreToDefault.contains(x, height - y))
            {
                restoreDefaultSettings();
            }
            //Volume
            float volumeChangeValue = 0.01f;
            if(btnLouder.contains(x, height - y))
            {
                if(SpreadingPeace.volume <= 0.1f - volumeChangeValue)
                {
                    System.out.println("plus gedrückt");
                    SpreadingPeace.volume += volumeChangeValue;
                    volume += volumeChangeValue;
                }

            }
            if(btnLower.contains(x, height - y))
            {
                if (SpreadingPeace.volume >= 0.0f + volumeChangeValue)
                {
                    SpreadingPeace.volume -= volumeChangeValue;
                    volume -= volumeChangeValue;
                }
            }

            //Tastatur
            //Steuerung P1 ändern
            if(btnChangeForwardP1.contains(x, height - y))
            {
                //System.out.println("button");
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    //System.out.println("keydown");
                    if(!alreadyBound(newKeyCode))
                    {
                        //Gamepads.p1Forward = newKeyCode;
                        game.player1.Key_FORWARD = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                        //System.out.println("Forward in Gamepads: " + Gamepads.p1Forward);
                    }
                    else
                    {
                        keyInUse();
                        //System.out.println("keyinuse");
                    }
                }
            }
            if(btnChangeBackwardP1.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        //Gamepads.p1Backward = newKeyCode;
                        game.player1.Key_BACKWARD = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            if(btnChangeTurnLeftP1.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        //Gamepads.p1TurnLeft = newKeyCode;
                        game.player1.KEY_TURN_LEFT = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            if(btnChangeTurnRightP1.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        //Gamepads.p1TurnRight = newKeyCode;
                        game.player1.KEY_TURN_RIGHT = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            if(btnChangeShootP1.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        //Gamepads.p1Shoot = newKeyCode;
                        game.player1.Key_SHOOT = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            if(btnChangeRotateTurretLeftP1.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        //Gamepads.p1RotateTurretLeft = newKeyCode;
                        game.player1.Key_Turret_Left = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            if(btnChangeRotateTurretRightP1.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        //Gamepads.p1RotateTurretRight = newKeyCode;
                        game.player1.Key_Turret_Right = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            //Steuerung P2 ändern
            if(btnChangeForwardP2.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        //Gamepads.p2Forward = newKeyCode;
                        game.player2.Key_FORWARD = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            if(btnChangeBackwardP2.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        //Gamepads.p2Backward = newKeyCode;
                        game.player2.Key_BACKWARD = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            if(btnChangeTurnLeftP2.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        //Gamepads.p2TurnLeft = newKeyCode;
                        game.player2.KEY_TURN_LEFT = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            if(btnChangeTurnRightP2.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        //Gamepads.p2TurnRight = newKeyCode;
                        game.player2.KEY_TURN_RIGHT = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            if(btnChangeShootP2.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        //Gamepads.p2Shoot = newKeyCode;
                        game.player2.Key_SHOOT = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            if(btnChangeRotateTurretLeftP2.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        //Gamepads.p2RotateTurretLeft = newKeyCode;
                        game.player2.Key_Turret_Left = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            if(btnChangeRotateTurretRightP2.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        //Gamepads.p2RotateTurretRight = newKeyCode;
                        game.player2.Key_Turret_Right = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }

            //Gamepad
            //P1 ändern
            if(btnChangeForwardP1G.contains(x, height - y))
            {
                if(buttonDown(controller1, newButtonCode))
                {
                    if(gpadButtonAlreadyInUse1(newButtonCode))
                    {
                        game.player1.buttonForward = switchButtonsGpad1(newButtonCode, game.player1.buttonForward);
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        game.player1.buttonForward = newButtonCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                }
            }
            if(btnChangeBackwardP1G.contains(x, height - y))
            {
                if(buttonDown(controller1, newButtonCode))
                {
                    if(gpadButtonAlreadyInUse1(newButtonCode))
                    {
                        game.player1.buttonBackward = switchButtonsGpad1(newButtonCode, game.player1.buttonBackward);
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        game.player1.buttonBackward = newButtonCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                }
            }
            if(btnChangeShootP1G.contains(x, height - y))
            {
                if(buttonDown(controller1, newButtonCode))
                {
                    if(gpadButtonAlreadyInUse1(newButtonCode))
                    {
                        game.player1.buttonShoot = switchButtonsGpad1(newButtonCode, game.player1.buttonShoot);
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        game.player1.buttonShoot = newButtonCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                }
            }
            if(btnChangeRotateTurretLeftP1G.contains(x, height - y))
            {
                if(buttonDown(controller1, newButtonCode))
                {
                    if(gpadButtonAlreadyInUse1(newButtonCode))
                    {
                        game.player1.buttonRotateTurretLeft = switchButtonsGpad1(newButtonCode, game.player1.buttonRotateTurretLeft);
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        game.player1.buttonRotateTurretLeft = newButtonCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                }
            }
            if(btnChangeRotateTurretRightP1G.contains(x, height - y))
            {
                if(buttonDown(controller1, newButtonCode))
                {
                    if(gpadButtonAlreadyInUse1(newButtonCode))
                    {
                        game.player1.buttonRotateTurretRight = switchButtonsGpad1(newButtonCode, game.player1.buttonRotateTurretRight);
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        game.player1.buttonRotateTurretRight = newButtonCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                }
            }
            //P2 ändern
            if(btnChangeForwardP2G.contains(x, height - y))
            {
                if(buttonDown(controller2, newButtonCode))
                {
                    if(gpadButtonAlreadyInUse2(newButtonCode))
                    {
                        game.player2.buttonForward = switchButtonsGpad2(newButtonCode, game.player2.buttonForward);
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        game.player2.buttonForward = newButtonCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                }
            }
            if(btnChangeBackwardP2G.contains(x, height - y))
            {
                if(buttonDown(controller2, newButtonCode))
                {
                    if(gpadButtonAlreadyInUse2(newButtonCode))
                    {
                        game.player2.buttonBackward = switchButtonsGpad2(newButtonCode, game.player2.buttonBackward);
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        game.player2.buttonBackward = newButtonCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                }
            }
            if(btnChangeShootP2G.contains(x, height - y))
            {
                if(buttonDown(controller2, newButtonCode))
                {
                    if(gpadButtonAlreadyInUse2(newButtonCode))
                    {
                        game.player2.buttonShoot = switchButtonsGpad2(newButtonCode, game.player2.buttonShoot);
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        game.player2.buttonShoot = newButtonCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                }
            }
            if(btnChangeForwardP2G.contains(x, height - y))
            {
                if(buttonDown(controller2, newButtonCode))
                {
                    if(gpadButtonAlreadyInUse2(newButtonCode))
                    {
                        game.player2.buttonForward = switchButtonsGpad2(newButtonCode, game.player2.buttonForward);
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        game.player2.buttonForward = newButtonCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                }
            }
            if(btnChangeRotateTurretLeftP2G.contains(x, height - y))
            {
                if(buttonDown(controller2, newButtonCode))
                {
                    if(gpadButtonAlreadyInUse2(newButtonCode))
                    {
                        game.player2.buttonRotateTurretLeft = switchButtonsGpad2(newButtonCode, game.player2.buttonRotateTurretLeft);
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        game.player2.buttonRotateTurretLeft = newButtonCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                }
            }
            if(btnChangeRotateTurretRightP2G.contains(x, height - y))
            {
                if(buttonDown(controller2, newButtonCode))
                {
                    if(gpadButtonAlreadyInUse2(newButtonCode))
                    {
                        game.player2.buttonRotateTurretRight = switchButtonsGpad2(newButtonCode, game.player2.buttonRotateTurretRight);
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        game.player2.buttonRotateTurretRight = newButtonCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                }
            }

            //Iteration 3
            if(btnChangeMineP1.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        game.player1.Key_MINE = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            if(btnChangeMineP2.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        game.player2.Key_MINE = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            if(btnChangeSSTP1.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        game.player1.Key_Shottype = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            if(btnChangeSSTP2.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        game.player2.Key_Shottype = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            if(btnChangeMineP1G.contains(x, height - y))
            {
                if(buttonDown(controller1, newButtonCode))
                {
                    if(gpadButtonAlreadyInUse1(newButtonCode))
                    {
                        game.player1.buttonMine = switchButtonsGpad1(newButtonCode, game.player1.buttonRotateTurretRight);
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        game.player1.buttonRotateTurretRight = newButtonCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                }
            }
            if(btnChangeMineP2G.contains(x, height - y))
            {
                if(buttonDown(controller2, newButtonCode))
                {
                    if(gpadButtonAlreadyInUse2(newButtonCode))
                    {
                        game.player2.buttonMine = switchButtonsGpad2(newButtonCode, game.player2.buttonMine);
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        game.player2.buttonMine = newButtonCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                }
            }
            if(btnChangeUDP1.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        game.player1.Key_UpgradeDamage = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }

                }
            }
            if(btnChangeUHP1.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        game.player1.Key_UpgradeHealth = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }
            if(btnChangeUDP2.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        game.player2.Key_UpgradeDamage = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }

                }
            }
            if(btnChangeUHP2.contains(x, height - y))
            {
                if(keyDown(Input.Keys.ANY_KEY))
                {
                    if(!alreadyBound(newKeyCode))
                    {
                        game.player2.Key_UpgradeHealth = newKeyCode;
                        SpreadingPeace.changeStatAt(18, 1);
                    }
                    else
                    {
                        keyInUse();
                    }
                }
            }

        }
        //--------------------------------------------------------------------------------------
        //
        //--------------------------------------------------------------------------------------

        game.batch.end();
        stage.act();
        stage.draw();
    }

    @Override public void resize(int width, int height)
    {

    }

    @Override public void pause()
    {

    }

    @Override public void resume()
    {

    }

    @Override public void hide()
    {

    }

    @Override public void dispose()
    {

    }

    //--------------------------------------------------------------------------------------
    //
    //--------------------------------------------------------------------------------------

    private void restoreDefaultSettings()
    {
        game.player1.Key_FORWARD = Gamepads.p1Forward;
        game.player1.Key_BACKWARD = Gamepads.p1Backward;
        game.player1.KEY_TURN_LEFT = Gamepads.p1TurnLeft;
        game.player1.KEY_TURN_RIGHT = Gamepads.p1TurnRight;
        game.player1.Key_SHOOT = Gamepads.p1Shoot;
        game.player1.Key_Turret_Left = Gamepads.p1RotateTurretLeft;
        game.player1.Key_Turret_Right = Gamepads.p1RotateTurretRight;
        game.player1.Key_MINE = Gamepads.p1Mine;
        game.player1.Key_Shottype = Gamepads.p1ShotType;
        game.player1.Key_UpgradeHealth = Gamepads.p1UpgradeHealth;
        game.player1.Key_UpgradeDamage = Gamepads.p1UpgradeDamage;

        game.player1.buttonForward = Gamepads.p1CForward;
        game.player1.buttonBackward = Gamepads.p1CBackward;
        game.player1.buttonShoot = Gamepads.p1CShoot;
        game.player1.buttonRotateTurretLeft = Gamepads.p1CRotateTurretLeft;
        game.player1.buttonRotateTurretRight = Gamepads.p1CRotateTurretRight;

        game.player2.Key_FORWARD = Gamepads.p2Forward;
        game.player2.Key_BACKWARD = Gamepads.p2Backward;
        game.player2.KEY_TURN_LEFT = Gamepads.p2TurnLeft;
        game.player2.KEY_TURN_RIGHT = Gamepads.p2TurnRight;
        game.player2.Key_SHOOT = Gamepads.p2Shoot;
        game.player2.Key_Turret_Left = Gamepads.p2RotateTurretLeft;
        game.player2.Key_Turret_Right = Gamepads.p2RotateTurretRight;
        game.player2.Key_MINE = Gamepads.p2Mine;
        game.player2.Key_Shottype = Gamepads.p2ShotType;
        game.player2.Key_UpgradeHealth = Gamepads.p2UpgradeHealth;
        game.player2.Key_UpgradeDamage = Gamepads.p2UpgradeDamage;

        game.player2.buttonForward = Gamepads.p2CForward;
        game.player2.buttonBackward = Gamepads.p2CBackward;
        game.player2.buttonShoot = Gamepads.p2CShoot;
        game.player2.buttonRotateTurretLeft = Gamepads.p2CRotateTurretLeft;
        game.player2.buttonRotateTurretRight = Gamepads.p2CRotateTurretRight;
    }

    private boolean alreadyBound(int keycode)
    {
        //p1 Keys
        if(keycode == game.player1.Key_FORWARD) { return true; }
        if(keycode == game.player1.Key_BACKWARD) { return true; }
        if(keycode == game.player1.KEY_TURN_LEFT) { return true; }
        if(keycode == game.player1.KEY_TURN_RIGHT) { return true; }
        if(keycode == game.player1.Key_SHOOT) { return true; }
        if(keycode == game.player1.Key_Turret_Left) { return true; }
        if(keycode == game.player1.Key_Turret_Right) { return true; }
        if(keycode == game.player1.Key_MINE) { return true;}
        if(keycode == game.player1.Key_UpgradeHealth) {return true;}
        if(keycode == game.player1.Key_UpgradeDamage) {return true;}

        //p2 Keys
        if(keycode == game.player2.Key_FORWARD) { return true; }
        if(keycode == game.player2.Key_BACKWARD) { return true; }
        if(keycode == game.player2.KEY_TURN_LEFT) { return true; }
        if(keycode == game.player2.KEY_TURN_RIGHT) { return true; }
        if(keycode == game.player2.Key_SHOOT) { return true; }
        if(keycode == game.player2.Key_Turret_Left) { return true; }
        if(keycode == game.player2.Key_Turret_Right) { return true; }
        if(keycode == game.player2.Key_MINE)  { return true; }
        if(keycode == game.player2.Key_UpgradeHealth) {return true;}
        if(keycode == game.player2.Key_UpgradeDamage) {return true;}

        //fix Keys
        if(keycode == Input.Keys.ESCAPE) { return true; }
        if(keycode == Input.Keys.H) { return true; }
        if(keycode == 0) {return true;} //Key: LOG_NONE

        return false;
    }

    private boolean gpadButtonAlreadyInUse1(int buttonCode)
    {
        if(buttonCode == game.player1.buttonForward) { return true; }
        if(buttonCode == game.player1.buttonBackward) { return true; }
        if(buttonCode == game.player1.buttonShoot) { return true; }
        if(buttonCode == game.player1.buttonRotateTurretLeft) { return true; }
        if(buttonCode == game.player1.buttonRotateTurretRight) { return true; }
        if(buttonCode == game.player1.buttonMine) {return true;}

        return false;
    }

    private boolean gpadButtonAlreadyInUse2(int buttonCode)
    {
        if(buttonCode == game.player2.buttonForward) { return true; }
        if(buttonCode == game.player2.buttonBackward) { return true; }
        if(buttonCode == game.player2.buttonShoot) { return true; }
        if(buttonCode == game.player2.buttonRotateTurretLeft) { return true; }
        if(buttonCode == game.player2.buttonRotateTurretRight) { return true; }
        if(buttonCode == game.player2.buttonMine) {return true;}

        return false;
    }

    private int switchButtonsGpad1(int buttonCode, int toSwitch)
    {
        if(buttonCode == game.player1.buttonForward)
        {
            game.player1.buttonForward = toSwitch;
        }
        if(buttonCode == game.player1.buttonBackward)
        {
            game.player1.buttonBackward = toSwitch;
        }
        if(buttonCode == game.player1.buttonShoot)
        {
            game.player1.buttonShoot = toSwitch;
        }
        if(buttonCode == game.player1.buttonRotateTurretLeft)
        {
            game.player1.buttonRotateTurretLeft = toSwitch;
        }
        if(buttonCode == game.player1.buttonRotateTurretRight)
        {
            game.player1.buttonRotateTurretRight = toSwitch;
        }
        if(buttonCode == game.player1.buttonMine)
        {
            game.player1.buttonMine = toSwitch;
        }
        return buttonCode;
    }



    private int switchButtonsGpad2(int buttonCode, int toSwitch)
    {
        if(buttonCode == game.player2.buttonForward)
        {
            game.player2.buttonForward = toSwitch;
        }
        if(buttonCode == game.player2.buttonBackward)
        {
            game.player2.buttonBackward = toSwitch;
        }
        if(buttonCode == game.player2.buttonShoot)
        {
            game.player2.buttonShoot = toSwitch;
        }
        if(buttonCode == game.player2.buttonRotateTurretLeft)
        {
            game.player2.buttonRotateTurretLeft = toSwitch;
        }
        if(buttonCode == game.player2.buttonRotateTurretRight)
        {
            game.player2.buttonRotateTurretRight = toSwitch;
        }
        if(buttonCode == game.player2.buttonMine)
        {
            game.player2.buttonMine = toSwitch;
        }
        return buttonCode;
    }

    private void keyInUse()
    {
        /**
        System.out.println("wait");
        //fügt ein Label hinzu welches einem sagt, dass der Key bereits belegt ist
        System.out.println("keyinuse");

        stage.addActor(labKeyAlreadyUsed);
        System.out.println("add actor");
        stage.draw();
        System.out.println("draw");
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        System.out.println("thread");
        labKeyAlreadyUsed.remove();
        System.out.println("remove");
        stage.draw();
        System.out.println("draw 2");
         **/
    }
    //--------------------------------------------------------------------------------------
    //                     Implements Inputprocessor
    //--------------------------------------------------------------------------------------

    @Override
    public boolean keyDown(int keycode)
    {
        for(int i = 0; i <= 255; i++)
        {
            if (Gdx.input.isKeyPressed(i))
            {
                newKeyCode = i;
            }
        }
        return true;
    }


    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }
    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }

    //--------------------------------------------------------------------------------------
    //                     Implements ControllerListener
    //--------------------------------------------------------------------------------------
    @Override
    public void connected(Controller controller)
    {

    }

    @Override
    public void disconnected(Controller controller)
    {

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode)
    {
        newButtonCode = buttonCode;
        return true;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode)
    {
        return false;
    }
    @Override

    public boolean axisMoved(Controller controller, int axisCode, float value)
    {
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value)
    {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value)
    {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value)
    {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value)
    {
        return false;
    }
}
