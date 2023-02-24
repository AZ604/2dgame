package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.*;
import java.util.Scanner;

public class SpreadingPeace extends Game {
    public static SpriteBatch batch;
    MainPlayingScreen mainScreen = new MainPlayingScreen(this); // initialise the playing screen
    MainPlayingScreen2 mainScreen2 = new MainPlayingScreen2(this); // init playing screen, with game mode 2
    MainMenuScreen mainMenu = new MainMenuScreen(this); // initialise the menu
    ConfigurationMenu configMenu = new ConfigurationMenu(this); // initialise the Configurations menu screen
    StatScreen statScreen = new StatScreen(this);
    AchievementScreen achievementScreen = new AchievementScreen(this);
    // Tatiana: initialisierung von 2 Players
    static Player player1 = new Player("Player1");
    Player player2 = new Player("Player2");
    HighScoreScreen highscore;

    public static int[] stats;

    {
        try {
            highscore = new HighScoreScreen (this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    MapNr nr = new MapNr();



    // AUDIO:
    private Music music; // background music
    public static float volume = 0.1f;
    public static Sound shotSound;
    public static Sound barrierBreakSound;
    public static Sound mineExplosion;


    public static HighscoreEintrag[] highscores;


    @Override
    public void create () {

        batch = new SpriteBatch();

        highscores = new HighscoreEintrag[10]; //alex: Highscore array mit 10 Einträgen wird erstellt
        try {
            filetoArray(highscores); //alex: Highscore array wird aus 2 bestehenden .txt Dateien befüllt, siehe methode unten
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        stats = readFile("Stats.txt");

        // Tatiana: erschaffen Steuerungsfunktion von Player2
        player2.Key_FORWARD=Input.Keys.UP;
        player2.Key_BACKWARD=Input.Keys.DOWN;
        player2.KEY_TURN_LEFT=Input.Keys.LEFT;
        player2.KEY_TURN_RIGHT=Input.Keys.RIGHT;
        player2.Key_SHOOT=Input.Keys.ENTER;
        player2.Key_Turret_Left=Input.Keys.M;
        player2.Key_Turret_Right=Input.Keys.N;
        player2.Key_MINE = Input.Keys.P;
        player2.Key_Shottype = Input.Keys.L;
        player2.Key_UpgradeDamage = Input.Keys.O;
        player2.Key_UpgradeHealth = Input.Keys.I;


        // AUDIO
        shotSound = Gdx.audio.newSound(Gdx.files.internal("Shot.mp3"));   // Load shot sound
        barrierBreakSound = Gdx.audio.newSound(Gdx.files.internal("barrierBreakSound.mp3"));
        mineExplosion = Gdx.audio.newSound(Gdx.files.internal("mineExplosion.wav"));

        music = Gdx.audio.newMusic(Gdx.files.internal("BackgroundMusic.mp3")); // Load music
        music.setLooping(true);
        music.setVolume(volume);
        music.play();



        this.setScreen(mainMenu); //


    }

    @Override
    public void render ()
    {
        super.render();
        music.setVolume(volume);
    }

    public static void manageScores(HighscoreEintrag[] array, Tank tank){

        addtoArray(array, new HighscoreEintrag(tank.getName(), tank.getScore()));

    }

    public static void addtoArray(HighscoreEintrag[] array, HighscoreEintrag entry){

        for (int i = 0; i < array.length; i++){

            if (entry.getPunktzahl() == array[i].getPunktzahl() && entry.getName() == array[i].getName()){

                return; //verhindert, dass bei jedem Aufruf der Highscoretabelle der aktuelle Wert mehrfach reingeschrieben wird

            }

            if (entry.getPunktzahl() > array[i].getPunktzahl()){

                if (i != 9){ //alex: Element das überschrieben wird, ist nicht das letzte der Liste, muss also weiter geschoben werden

                    HighscoreEintrag dummy = array[i];
                    array[i] = entry;
                    addtoArray(array, dummy); //alex: rufe die methode erneut auf, mit dem Element das einen nach hinten verschoben werden soll
                    return; //alex: return nach jedem Eintrag/jeder Änderung, damit ein Highscoreeintrag nicht die ganze Liste vollschreibt

                } else { //alex: letztes Element wird überschrieben, fliegt damit aus der Liste raus

                    array[i] = entry;
                    return;

                }

            }

        }

    }

    public static void arraytoFile(HighscoreEintrag[] array){ //alex: Überschreibt die Namen und Punktzahlen in den zwei .txt Dateien mit den aktuellen Werten aus dem Spiel

        try{

            FileWriter fr = new FileWriter("scores.txt");
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter out = new PrintWriter(br);
            for (int i = 0; i < array.length; i++){

                out.write(array[i].getPunktzahl() + " "); //alex: Punktzahlen aller Einträge werden als String, durch Leerzeichen getrennt, in die scores.txt geschrieben

            }

            out.close();

        }

        catch(IOException e){

            System.out.println(e);

        }

        try{

            FileWriter fr = new FileWriter("scoreNames.txt");
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter out = new PrintWriter(br);
            for (int i = 0; i < array.length; i++){

                out.write(array[i].getName()); //alex: Namen werden durch Absätze getrennt als String in scoreNames.txt geschrieben
                out.write("\n");

            }

            out.close();

        }

        catch(IOException e){

            System.out.println(e);

        }

    }

    public static void filetoArray(HighscoreEintrag[] array) throws FileNotFoundException { //alex: lädt die zuletzt in den .txt Dateien gespeicherten Namen und Punktzahlen ins zunächst leere Highscore array

        Scanner s = new Scanner(new File("scores.txt"));
        Scanner r = new Scanner(new File("scoreNames.txt"));
        for (int i = 0; i < array.length; i++){

            HighscoreEintrag entry = new HighscoreEintrag(r.nextLine(), s.nextInt());

            array[i] = entry;

        }
        s.close();
        r.close();

    }

    public static void refreshStats()
    {
        String[] temp = new String[stats.length];
        for(int i = 0; i < stats.length; i++){temp[i] = Integer.toString(stats[i]);}
        try
        {
            FileWriter fileWriter = new FileWriter("Stats.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            for (int i = 0; i < temp.length; i++)
            {
                printWriter.write(temp[i]);
                if(i < temp.length -1){printWriter.write("\n");} //damit beim schreiben nicht ein absatz nach der letzten zahl gemacht wird
            }
            printWriter.close();
        }
        catch(IOException e)
        {

        }
        for(int i = 0; i < stats.length; i++){ System.out.println(stats[i]);} //Testkram
    }

    public static void changeStatAt(int pos, int add)
    {
        stats[pos - 1] += add;
    }

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

    @Override
    public void dispose () {
        batch.dispose();
        super.dispose();
        music.dispose();
        barrierBreakSound.dispose();
        shotSound.dispose();
        mineExplosion.dispose();
    }
}
