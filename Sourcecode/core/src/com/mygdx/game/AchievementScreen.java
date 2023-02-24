package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.awt.*;

public class AchievementScreen implements Screen
{
    private SpreadingPeace game;

    private Skin skin;

    private Stage stage;

    private Texture background;

    private Label naTest;

    private Label naAc1, naAc2, naAc3, naAc4, naAc5, naAc6, naAc7, naAc8, naAc9, naAc10, naAc11, naAc12, naAc13, naAc14, naAc15, naAc16, naAc17, naAc18, naAc19, naAc20;

    private Label fAc1, fAc2, fAc3, fAc4, fAc5, fAc6, fAc7, fAc8, fAc9, fAc10, fAc11, fAc12, fAc13, fAc14, fAc15, fAc16, fAc17, fAc18, fAc19, fAc20;

    private Texture achievement1, achievement2, achievement3, achievement4, achievement5, achievement6, achievement7, achievement8, achievement9, achievement10, achievement11, achievement12, achievement13, achievement14, achievement15, achievement16, achievement17, achievement18, achievement19, achievement20;

    private Texture dimmer;

    private boolean acGet[] = new boolean[20];

    private int requirement[] = new int [20];

    public AchievementScreen(SpreadingPeace game)
    {
        this.game = game;
    }



    @Override
    public void show()
    {
        requirement[0] = 1000;
        requirement[1] = 100000;
        requirement[2] = 1000;
        requirement[3] = 100000;
        requirement[4] = 1;
        requirement[5] = 10;
        requirement[6] = 100;
        requirement[7] = 1000;
        requirement[8] = 1;
        requirement[9] = 100;
        requirement[10] = 10;
        requirement[11] = 1;
        requirement[12] = 10000;
        requirement[13] = 10;
        requirement[14] = 1000;
        requirement[15] = 1;
        requirement[16] = 1000;
        requirement[17] = 1000;
        requirement[18] = 1000;
        requirement[19] = 250;

        checkRequirement(0, 0);
        checkRequirement(1, 0);
        checkRequirement(2, 1);
        checkRequirement(3, 1);
        checkRequirement(4, 2);
        checkRequirement(5, 2);
        checkRequirement(6, 2);
        checkRequirement(7, 2);
        checkRequirement(8, 3);
        checkRequirement(9, 3);
        checkRequirement(10, 4);
        checkRequirement(11, 5);
        checkRequirement(12, 5);
        checkRequirement(13, 6);
        checkRequirement(14, 6);
        if(SpreadingPeace.stats[7] >= requirement[15] && SpreadingPeace.stats[8] >= requirement[15] && SpreadingPeace.stats[9] >= requirement[15] && SpreadingPeace.stats[10] >= requirement[15] && SpreadingPeace.stats[11] >= requirement[15] && SpreadingPeace.stats[12] >= requirement[15])
        {
            acGet[15] = true;
        }
        checkRequirement(15, 16);
        checkRequirement(16, 18);
        checkRequirement(17, 19);
        checkRequirement(18, 20);
        checkRequirement(19, 5);


        stage = new Stage();

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        background = new Texture("MenuScreen.png");

        //----------------
        //
        //----------------

        //naTest = new Label("Betesta", skin);

        naAc1 = new Label("Deal 1000 damage", skin);
        naAc2 = new Label("Deal 100000 damage", skin);
        naAc3 = new Label("Take 1000 damage", skin);
        naAc4 = new Label("Take 100000 damage", skin);
        naAc5 = new Label("Persuade 1 tank", skin);
        naAc6 = new Label("Persuade 10 tanks", skin);
        naAc7 = new Label("Persuade 100 tanks", skin);
        naAc8 = new Label("Persuade 1000 tanks", skin);
        naAc9 = new Label("Surrender 1 time", skin);
        naAc10 = new Label("Surrender 100 times", skin);
        naAc11 = new Label("Place 10 mines", skin);
        naAc12 = new Label("Make 1 step forward", skin);
        naAc13 = new Label("Make 10000 steps forward", skin);
        naAc14 = new Label("Make 10 steps backwards", skin);
        naAc15 = new Label("Make 1000 steps backwards", skin);
        naAc16 = new Label("Use every flower", skin);
        naAc17 = new Label("Take 1000 lava damage", skin);
        naAc18 = new Label("Change your controls 1000 times", skin);
        naAc19 = new Label("Spend 1000 cash", skin);
        naAc20 = new Label("Hort at least 250 cash", skin);


        fAc1 = new Label("Enough for one", skin);
        fAc2 = new Label("Spreading peace", skin);
        fAc3 = new Label("Surrendering?", skin);
        fAc4 = new Label("Try retreating", skin);
        fAc5 = new Label("just getting started", skin);
        fAc6 = new Label("going for world peace", skin);
        fAc7 = new Label("great peacekeeper", skin);
        fAc8 = new Label("Uncle Sam", skin);
        fAc9 = new Label("Giving up already?", skin);
        fAc10 = new Label("One more try...", skin);
        fAc11 = new Label("Planting seeds", skin);
        fAc12 = new Label("One small step...", skin);
        fAc13 = new Label("One giant leap...", skin);
        fAc14 = new Label("Avoiding conflict...", skin);
        fAc15 = new Label("... is a virtue", skin);
        fAc16 = new Label("Botanist", skin);
        fAc17 = new Label("Warming up", skin);
        fAc18 = new Label("Indecisive", skin);
        fAc19 = new Label("Great spender", skin);
        fAc20 = new Label("Peacetime economy", skin);


        achievement1 = new Texture("Achievement1.png");
        achievement2 = new Texture("Achievement2.png");
        achievement3 = new Texture("Achievement3.png");
        achievement4 = new Texture("Achievement4.png");
        achievement5 = new Texture("Achievement5.png");
        achievement6 = new Texture("Achievement6.png");
        achievement7 = new Texture("Achievement7.png");
        achievement8 = new Texture("Achievement8.png");
        achievement9 = new Texture("Achievement9.png");
        achievement10 = new Texture("Achievement10.png");
        achievement11 = new Texture("Achievement11.png");
        achievement12 = new Texture("Achievement12.png");
        achievement13 = new Texture("Achievement13.png");
        achievement14 = new Texture("Achievement14.png");
        achievement15 = new Texture("Achievement15.png");
        achievement16 = new Texture("Achievement16.png");
        achievement17 = new Texture("Achievement17.png");
        achievement18 = new Texture("Achievement18.png");
        achievement19 = new Texture("Achievement19.png");
        achievement20 = new Texture("Achievement20.png");

        dimmer = new Texture("AchievementDimmer.png");

        //stage.addActor(naTest);

        stage.addActor(naAc1);
        stage.addActor(naAc2);
        stage.addActor(naAc3);
        stage.addActor(naAc4);
        stage.addActor(naAc5);
        stage.addActor(naAc6);
        stage.addActor(naAc7);
        stage.addActor(naAc8);
        stage.addActor(naAc9);
        stage.addActor(naAc10);
        stage.addActor(naAc11);
        stage.addActor(naAc12);
        stage.addActor(naAc13);
        stage.addActor(naAc14);
        stage.addActor(naAc15);
        stage.addActor(naAc16);
        stage.addActor(naAc17);
        stage.addActor(naAc18);
        stage.addActor(naAc19);
        stage.addActor(naAc20);

        stage.addActor(fAc1);
        stage.addActor(fAc2);
        stage.addActor(fAc3);
        stage.addActor(fAc4);
        stage.addActor(fAc5);
        stage.addActor(fAc6);
        stage.addActor(fAc7);
        stage.addActor(fAc8);
        stage.addActor(fAc9);
        stage.addActor(fAc10);
        stage.addActor(fAc11);
        stage.addActor(fAc12);
        stage.addActor(fAc13);
        stage.addActor(fAc14);
        stage.addActor(fAc15);
        stage.addActor(fAc16);
        stage.addActor(fAc17);
        stage.addActor(fAc18);
        stage.addActor(fAc19);
        stage.addActor(fAc20);
    }

    @Override
    public void render(float delta)
    {
        game.batch.begin();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            game.setScreen(game.mainMenu);
        }


        int height = Gdx.app.getGraphics().getHeight();
        int width = Gdx.app.getGraphics().getWidth();

        game.batch.draw(background, 0, 0, width, height);

        float distLine = -120;
        float distCol = 250;

        float line1 = height - 100;
        float col1 = 100;

        placeAchievement(naAc1, col1, line1, achievement1, acGet[0], fAc1);
        placeAchievement(naAc2, col1 + distCol, line1, achievement2, acGet[1], fAc2);
        placeAchievement(naAc3, col1 + distCol * 2, line1, achievement3, acGet[2], fAc3);
        placeAchievement(naAc4, col1 + distCol * 3, line1, achievement4, acGet[3], fAc4);
        placeAchievement(naAc5, col1 + distCol * 4, line1, achievement5, acGet[4], fAc5);
        placeAchievement(naAc6, col1, line1 + distLine, achievement6, acGet[5], fAc6);
        placeAchievement(naAc7, col1 + distCol, line1 + distLine, achievement7, acGet[6], fAc7);
        placeAchievement(naAc8, col1 + distCol * 2, line1 + distLine, achievement8, acGet[7], fAc8);
        placeAchievement(naAc9, col1 + distCol * 3, line1 + distLine, achievement9, acGet[8], fAc9);
        placeAchievement(naAc10, col1 + distCol * 4, line1 + distLine, achievement10, acGet[9], fAc10);
        placeAchievement(naAc11, col1, line1 + distLine * 2, achievement11, acGet[10], fAc11);
        placeAchievement(naAc12, col1 + distCol, line1 + distLine * 2, achievement12, acGet[11], fAc12);
        placeAchievement(naAc13, col1 + distCol * 2, line1 + distLine * 2, achievement13, acGet[12], fAc13);
        placeAchievement(naAc14, col1 + distCol * 3, line1 + distLine * 2, achievement14, acGet[13], fAc14);
        placeAchievement(naAc15, col1 + distCol * 4, line1 + distLine * 2, achievement15, acGet[14], fAc15);
        placeAchievement(naAc16, col1, line1 + distLine * 3, achievement16, acGet[15], fAc16);
        placeAchievement(naAc17, col1 + distCol, line1 + distLine * 3, achievement17, acGet[16], fAc17);
        placeAchievement(naAc18, col1 + distCol * 2, line1 + distLine * 3, achievement18, acGet[17], fAc18);
        placeAchievement(naAc19, col1 + distCol * 3, line1 + distLine * 3, achievement19, acGet[18], fAc19);
        placeAchievement(naAc20, col1 + distCol * 4, line1 + distLine * 3, achievement20, acGet[19], fAc20);



        game.batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }

    //---------------------------------------------------------------------------------------
    //
    //---------------------------------------------------------------------------------------
    private void placeAchievement(Label labelF, float x, float y, Texture png, boolean got, Label labelT)
    {
        game.batch.draw(png, x, y);
        if(!got)
        {
            labelF.setPosition(x + png.getWidth()/2 - labelF.getWidth()/2, y - 25);
            game.batch.draw(dimmer, x, y);
            labelT.setPosition(x, -1000);
        }
        else
        {
            labelT.setPosition(x + png.getWidth()/2 - labelT.getWidth()/2, y - 25);
            labelF.setPosition(x, -1000);
        }
    }

    private void checkRequirement(int pos, int usedStat)
    {
        //System.out.println("pos in req: " + pos + " Used stat: " + StatScreen.statname[usedStat] + " requirements: " + requirement[pos]);
        if(SpreadingPeace.stats[usedStat] >= requirement[pos])
        {
            acGet[pos] = true;
        }
    }
}