package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Arrays;
import java.util.Scanner;



public class StatScreen  implements Screen
{
    private SpreadingPeace game;

    //public static int[] stats;
    public static String[] statname;

    public static boolean tankDestroyed = false;

    public static int shottypeUsed = 0;

    private Label stat1, stat2, stat3, stat4, stat5, stat6, stat7, stat8, stat9, stat10, stat11, stat12, stat13, stat14, stat15, stat16, stat17, stat18, stat19, stat20, stat21;


    public StatScreen(SpreadingPeace game)
    {
        this.game = game;
    }

    private Skin skin;

    private Stage stage;

    private Texture background;


    @Override
    public void show()
    {
        stage = new Stage();

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        background = new Texture("MenuScreen.png");

        //stats = readFile("Stats.txt");

        statname = fillnames();

        System.out.println(Arrays.toString(SpreadingPeace.stats)); //Testkram
        for(int i = 0; i < statname.length; i++){ System.out.println(statname[i]);} //Testkram


        stat1 = new Label(mergeForLabel(statname[0], SpreadingPeace.stats[0]), skin);
        stat2 = new Label(mergeForLabel(statname[1], SpreadingPeace.stats[1]), skin);
        stat3 = new Label(mergeForLabel(statname[2], SpreadingPeace.stats[2]), skin);
        stat4 = new Label(mergeForLabel(statname[3], SpreadingPeace.stats[3]), skin);
        stat5 = new Label(mergeForLabel(statname[4], SpreadingPeace.stats[4]), skin);
        stat6 = new Label(mergeForLabel(statname[5], SpreadingPeace.stats[5]), skin);
        stat7 = new Label(mergeForLabel(statname[6], SpreadingPeace.stats[6]), skin);
        stat8 = new Label(mergeForLabel(statname[7], SpreadingPeace.stats[7]), skin);
        stat9 = new Label(mergeForLabel(statname[8], SpreadingPeace.stats[8]), skin);
        stat10 = new Label(mergeForLabel(statname[9], SpreadingPeace.stats[9]), skin);
        stat11 = new Label(mergeForLabel(statname[10], SpreadingPeace.stats[10]), skin);
        stat12 = new Label(mergeForLabel(statname[11], SpreadingPeace.stats[11]), skin);
        stat13 = new Label(mergeForLabel(statname[12], SpreadingPeace.stats[12]), skin);
        stat14 = new Label(mergeForLabel(statname[13], SpreadingPeace.stats[13]), skin);
        stat15 = new Label(mergeForLabel(statname[14], SpreadingPeace.stats[14]), skin);
        stat16 = new Label(mergeForLabel(statname[15], SpreadingPeace.stats[15]), skin);
        stat17 = new Label(mergeForLabel(statname[16], SpreadingPeace.stats[16]), skin);
        stat18 = new Label(mergeForLabel(statname[17], SpreadingPeace.stats[17]), skin);
        stat19 = new Label(mergeForLabel(statname[18], SpreadingPeace.stats[18]), skin);
        stat20 = new Label(mergeForLabel(statname[19], SpreadingPeace.stats[19]), skin);
        stat21 = new Label(mergeForLabel(statname[20], SpreadingPeace.stats[20]), skin);

        stage.addActor(stat1);
        stage.addActor(stat2);
        stage.addActor(stat3);
        stage.addActor(stat4);
        stage.addActor(stat5);
        stage.addActor(stat6);
        stage.addActor(stat7);
        stage.addActor(stat8);
        stage.addActor(stat9);
        stage.addActor(stat10);
        stage.addActor(stat11);
        stage.addActor(stat12);
        stage.addActor(stat13);
        stage.addActor(stat14);
        stage.addActor(stat15);
        stage.addActor(stat16);
        stage.addActor(stat17);
        stage.addActor(stat18);
        stage.addActor(stat19);
        stage.addActor(stat20);
        stage.addActor(stat21);

    }

    @Override
    public void render(float delta)
    {
        game.batch.begin();

        int height = Gdx.app.getGraphics().getHeight();
        int width = Gdx.app.getGraphics().getWidth();

        int space = (height - 100) /SpreadingPeace.stats.length;
        int fromLeft = 100;

        game.batch.draw(background, 0, 0, width, height);

        stat1.setPosition(fromLeft, height - space);
        stat2.setPosition(fromLeft, height - space * 2);
        stat3.setPosition(fromLeft, height - space * 3);
        stat4.setPosition(fromLeft, height - space * 4);
        stat5.setPosition(fromLeft, height - space * 5);
        stat6.setPosition(fromLeft, height - space * 6);
        stat7.setPosition(fromLeft, height - space * 7);
        stat8.setPosition(fromLeft, height - space * 8);
        stat9.setPosition(fromLeft, height - space * 9);
        stat10.setPosition(fromLeft, height - space * 10);
        stat11.setPosition(fromLeft, height - space * 11);
        stat12.setPosition(fromLeft, height - space * 12);
        stat13.setPosition(fromLeft, height - space * 13);
        stat14.setPosition(fromLeft, height - space * 14);
        stat15.setPosition(fromLeft, height - space * 15);
        stat16.setPosition(fromLeft, height - space * 16);
        stat17.setPosition(fromLeft, height - space * 17);
        stat18.setPosition(fromLeft, height - space * 18);
        stat19.setPosition(fromLeft, height - space * 19);
        stat20.setPosition(fromLeft, height - space * 20);
        stat21.setPosition(fromLeft, height - space * 21);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            game.setScreen(game.mainMenu);
        }

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
    //----------------------------------------------------------------------------------
    //
    //----------------------------------------------------------------------------------
    private static int[] readFile(String file)
    {
        int arrBounds = 0;
        try
        {
            Scanner s1 = new Scanner (new File (file));
            while(s1.hasNextLine())
            {
                arrBounds ++;
                s1.next();
            }
            String[] statistics = new String[arrBounds];

            Scanner s2 = new Scanner(new File(file));
            for(int i = 0; i < arrBounds; i++)
            {
                statistics[i] = s2.next();
            }

            int[] statisticsp = new int[statistics.length];

            for(int i = 0; i < statistics.length; i++)
            {
                statisticsp[i] = Integer.parseInt(statistics[i]);
            }

            return statisticsp;
        }
        catch(FileNotFoundException e)
        {
            return null;
        }
    }

    private static String[] fillnames()
    {
        String[] re = new String[SpreadingPeace.stats.length - 1];
        if(SpreadingPeace.stats.length - 1 > 0){re[0] = "Damage dealt: ";}
        if(SpreadingPeace.stats.length - 1 > 1){re[1] = "Damage taken: ";}
        if(SpreadingPeace.stats.length - 1 > 2){re[2] = "Tanks persuaded: ";}
        if(SpreadingPeace.stats.length - 1 > 3){re[3] = "Surrenders: ";}
        if(SpreadingPeace.stats.length - 1 > 4){re[4] = "Mines used: ";}
        if(SpreadingPeace.stats.length - 1 > 5){re[7] = "Special flowers used: ";}
        if(SpreadingPeace.stats.length - 1 > 6){re[5] = "Distance forward (Fields): ";}
        if(SpreadingPeace.stats.length - 1 > 7){re[6] = "Distance backwards (Fields): ";}
        if(SpreadingPeace.stats.length - 1 > 8){re[8] = "Normal flowers used: ";}
        if(SpreadingPeace.stats.length - 1 > 9){re[9] = "Spread flowers used: ";}
        if(SpreadingPeace.stats.length - 1 > 10){re[10] = "Bouncing flowers used: ";}
        if(SpreadingPeace.stats.length - 1 > 11){re[11] = "Fast flowers used: ";}
        if(SpreadingPeace.stats.length - 1 > 12){re[12] = "Curved flowers used: ";}
        if(SpreadingPeace.stats.length - 1 > 13){re[13] = "Fast items collected: ";}
        if(SpreadingPeace.stats.length - 1 > 14){re[14] = "Health items collected: ";}
        if(SpreadingPeace.stats.length - 1 > 15){re[15] = "Damage items collected: ";}
        if(SpreadingPeace.stats.length - 1 > 16){re[16] = "Lava damage taken: ";}
        if(SpreadingPeace.stats.length - 1 > 17){re[17] = "Times controls got switched: ";}
        if(SpreadingPeace.stats.length - 1 > 18){re[18] = "Cash spend: ";}
        if(SpreadingPeace.stats.length - 1 > 19){re[19] = "Maximum cash: ";}
        if(SpreadingPeace.stats.length - 1 > 20){re[20] = "Free slot: ";}
        if(SpreadingPeace.stats.length - 1 > 21){re[21] = "Free slot: ";}
        return re;
    }

    private String mergeForLabel(String name,int num)
    {
        String re;
        re = name + num;
        return re;
    }
}
