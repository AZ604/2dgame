package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;

import java.awt.*;

public class GameOverScreen implements Screen {

    SpreadingPeace game;
    int score;

    Texture gameOverBanner;
    BitmapFont scoreFont;
    String highscoredata = "";

    public GameOverScreen(SpreadingPeace game, int score) {
        this.game = game;
        this.score = score;


        gameOverBanner = new Texture("gameOver.png");
        scoreFont = new BitmapFont();
    }


    @Override
    public void show() {
        if (this.score > SpreadingPeace.highscores[SpreadingPeace.highscores.length - 1].getPunktzahl()) {
            SpreadingPeace.highscores[SpreadingPeace.highscores.length - 1] = new HighscoreEintrag(SpreadingPeace.player1.getName(), SpreadingPeace.player1.getTank().getScore());
            sort(SpreadingPeace.highscores, 0, SpreadingPeace.highscores.length - 1);
            reverse(SpreadingPeace.highscores);
        }

        for (int i = 0; i < SpreadingPeace.highscores.length; i++) {
            highscoredata += SpreadingPeace.highscores[i].getName() + ": " + SpreadingPeace.highscores[i].getPunktzahl() + "\n";
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(gameOverBanner, Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() - 80 - 15, 250, 80);

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Your Score: " + score, Color.WHITE, 0, Align.left, false);
        GlyphLayout highscores = new GlyphLayout(scoreFont, highscoredata, Color.WHITE, 0, Align.left, false);
        scoreFont.draw(game.batch, scoreLayout, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2, Gdx.graphics.getHeight() - 80 - 15 * 2);
        scoreFont.draw(game.batch, highscores, Gdx.graphics.getWidth() / 2 - scoreLayout.width / 2, Gdx.graphics.getHeight() - 80 - 15 * 4);

        GlyphLayout tryAgainLayout = new GlyphLayout(scoreFont, "Try Again");
        GlyphLayout mainMenuLayout = new GlyphLayout(scoreFont, "Main Menu");

        float tryAgainX = Gdx.graphics.getWidth() / 2 - tryAgainLayout.width / 2;
        float tryAgainY = Gdx.graphics.getHeight() - tryAgainLayout.height / 2 - 500;

        float MainMenuX = Gdx.graphics.getWidth() / 2 - mainMenuLayout.width / 2;
        float MainMenuY = Gdx.graphics.getHeight() - mainMenuLayout.height / 2 - 650;

        float touchX = Gdx.input.getX(), touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

        //IF try again and main menu is being pressed

        if (Gdx.input.isTouched()) {
            if (touchX > tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY > tryAgainY - tryAgainLayout.height && touchY < tryAgainY) {
                this.dispose();
                game.batch.end();
                game.setScreen(game.mainScreen);
                return;
            }
            if (touchX > MainMenuX && touchX < MainMenuX + mainMenuLayout.width && touchY > MainMenuY - mainMenuLayout.height && touchY < MainMenuY) {
                this.dispose();
                game.batch.end();
                game.setScreen(game.mainMenu);
                return;

            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            this.dispose();
            game.batch.end();
            game.setScreen(game.mainScreen);
            return;

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { // Closes the application when pressing ESC
            this.dispose();
            game.batch.end();
            game.setScreen(game.mainMenu);
            return;
        }

        scoreFont.draw(game.batch, tryAgainLayout, tryAgainX, tryAgainY);
        scoreFont.draw(game.batch, mainMenuLayout, MainMenuX, MainMenuY);
        game.batch.end();


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

    }

    int partition(HighscoreEintrag[] arr, int low, int high) {
        HighscoreEintrag pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j].getPunktzahl() <= pivot.getPunktzahl()) { // if element is smaller or equal to the pivot shift it to the left
                i++;


                HighscoreEintrag temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }


        }

        HighscoreEintrag temp = arr[i + 1];  // swap the last element with the first highest element
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    void sort(HighscoreEintrag[] arr, int low, int high) {
        if (low < high) { // check if the arr in the stack call happens to be of length 1
            int middle = partition(arr, low, high); // gets the middle

            sort(arr, low, middle - 1);
            sort(arr, middle + 1, high);


        }

    }

    void reverse(HighscoreEintrag[] arr) {
        for (int i = 0; i < arr.length / 2; i++) {
            HighscoreEintrag temp = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - i - 1] = temp;
        }

    }


}
