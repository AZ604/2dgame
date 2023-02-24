package com.mygdx.game;

import com.badlogic.gdx.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


import java.awt.*;
import java.io.*;
import java.util.*;


public class HighScoreScreen implements Screen {


    private Texture HSbackground;
    private Texture buttonExitHs;
    private Rectangle exitHs;

    private SpreadingPeace game;


    BitmapFont font;


    private ArrayList<HighscoreEintrag> eintraege;      //ArrayList mit den HighscoreEintraegen erstellen


    public HighScoreScreen(SpreadingPeace game) throws FileNotFoundException, UnsupportedEncodingException {
        eintraege = new ArrayList<HighscoreEintrag>();

        this.game=game;

    }

    public void show()
    {
        //alex: Wenn Highscore geöffnet wird, werden die aktuellen Scores der Tanks geprüft und ggf. als Highscore eingefügt
        if (game.player1.getTank() != null){

            SpreadingPeace.manageScores(SpreadingPeace.highscores, game.player1.getTank());

        }

        if (game.player2.getTank() != null){

            SpreadingPeace.manageScores(SpreadingPeace.highscores, game.player2.getTank());

        }


        SpreadingPeace.arraytoFile(SpreadingPeace.highscores);



        HSbackground = new Texture("highscoreBg.png");        // Hintergrundbild bestimmen
        buttonExitHs= new Texture("exitButtonHs.png");      // ExitButton Design bestimmen

        exitHs = new Rectangle(0,0, buttonExitHs.getWidth(), buttonExitHs.getHeight());


    }


    @Override
    public void render(float delta) {

        font = new BitmapFont();

        game.batch.begin();

        int height = Gdx.app.getGraphics().getHeight();
        int width = Gdx.app.getGraphics().getWidth();


        game.batch.draw(HSbackground, 0, 0,width, height);          //Hintergrund setzen

        for (int i = 0; i < SpreadingPeace.highscores.length; i++) { //alex: Schreibt alle Highscore Einträge untereinander auf den Bildschirm

            font.draw(game.batch, 1+i + ": " + SpreadingPeace.highscores[i].printValues(), 270, height - 70 - (40 * i));

        }


        exitHs.x = width - exitHs.width - 50;       //position exitButton
        exitHs.y = height - exitHs.height - 50 ;
        game.batch.draw(buttonExitHs,exitHs.x, exitHs.y);       //Exitbutton zeichen

        game.batch.end();


        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {        //HighscoreScreen verlassen mit ESC

            game.setScreen(game.mainMenu);      //und wieder auf den MainMenuScreen zurückkommen

        }

        int x = Gdx.input.getX();
        int y = Gdx.input.getY();

        if (Gdx.input.justTouched()) {                  //highscore mit Mausklick verlassen

                if(exitHs.contains(x,height - y))
                {
                    game.setScreen(game.mainMenu);      //und wieder auf MainMenuScreen gelangen
                }


        }

    }


    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }
}
