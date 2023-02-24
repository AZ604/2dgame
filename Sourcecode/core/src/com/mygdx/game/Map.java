package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Map implements Screen {
    private TiledMap map; //Nutzung von vordefinierter Klasse TiledMap
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    public int mapNr;
    private int mapbreite = 60; // mapbreite in tiles
    private int maphoehe = 30;  // maphoehe in tiles
    private int [][] array2 = new int[mapbreite][maphoehe];  //array für kollisionen
    private boolean[][] blocked = new boolean[mapbreite][maphoehe];
    private int [][][] array = new int[mapbreite][maphoehe][2];  //array für kollisionen
    public Barrier [] barriers;
    public static final int BODEN = 0;
    public static final int WAND = 1;
    public static final int WASSER = 2;
    public static final int LAVA = 3;

    //Wann ist ein Ort nicht befahrbar== array[x,y,0]=1 oder array[x,y,1]=1 das zweite sind die interagierbaren obj das erste die Wände

    public  Map(int mapNr){ // erstellt mapränder also Wand
        this.mapNr = mapNr;

        if(this.mapNr==1)
        {
            map1();
            barriers = map1Barrier();

        }
        else if(this.mapNr==2)
        {
            barriers=map2Barrier();

        }
        else if(this.mapNr==3)
        {
            map3();

        }
        else
        {

        }
        initBlocked();
        for(int i = 0;i<mapbreite;i++)  //Setzt Randwände
        {
            array[i][maphoehe - 1][0] = WAND;
            array[i][0][0] = WAND;
        }
        for(int i = 0;i<maphoehe-1;i++)
        {
            array[0][i][0] = WAND;
            array[mapbreite - 1][i][0] = WAND;
        }



    }

    @Override
    public void show() { //Lädt textur ...
        if(this.mapNr==1)
        {
            map = new TmxMapLoader().load("Map1.tmx");
        }

        else if(this.mapNr==2)
        {
            map = new TmxMapLoader().load("Map2.tmx");
        }
        else if(this.mapNr==3)
        {
            map = new TmxMapLoader().load("Map3.tmx");
        }
        else
        {
            map = new TmxMapLoader().load("Map1.tmx");
        }

        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();


    }

    @Override
    public void render(float delta) { //Zeigt map an
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);

        renderer.render();
    }

    @Override
    public void resize(int width, int height) {  //sorgt dafür dass map immer zentriert ist
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.position.set(width/2,height/2,0);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

        map.dispose();
        renderer.dispose();
    }

    public void setObj(int x,int y)
    {
        this.array[x][y][0]=1;
    } //setzt ein Objekt in das Kollisionsfeld

    public void setObj(int x,int y,int z)
    {
        this.array[x][y][z]=1;
    } //setzt zerstörbares Obj in Kollisionsfeld

    public void setClear(int x, int y)
    {
        this.array[x][y][1]=0;
        this.blocked[x][y] = false;
    } //löscht alles aus dem Kollisionsfeld an der Stelle

    public void setClear(int x,int y,int z)
    {
        this.array[x][y][z]=0;
        this.blocked[x][y] = false;
    } //löscht zerstörbares Obj aus dem Kollisionsfeld

    public int getType(int x,int y)
    {
        if (x >= 0 && y >= 0 && x < array.length && y < array[x].length) {
            return this.array[x][y][0];
        }
        return -1;

    } //gibt den Typ zurück siehe ganz oben

    public int getType(int x,int y, int z)
    {
        if (x >= 0 && y >= 0 && z >= 0 && x < array.length && y < array[x].length && z < array[x][y].length) {
            return this.array[x][y][z];
        }
        return -1;
    }

    public void map1()
    {
        //Wände von Map 1
        for(int i=0;i<13;i++)
        {
            if(i!=6&&i!=7)
            {
                array[i][19][0] = WAND;
            }

        }

        for(int i=29;i>18;i--)
        {
            if(i!=25&&i!=24&&i!=23)
            {
                array[12][i][0] = WAND;
            }
        }


        for(int i = 38;i<53;i++)
        {
            array[i][16][0] = WAND;

        }


        //Wasser
        for(int i = 29;i>22;i--)
        {
            array[31][i][0] = WASSER;
            array[32][i][0] = WASSER;
            array[33][i][0] = WASSER;
            array[34][i][0] = WASSER;
            array[35][i][0] = WASSER;
        }

        for(int i = 20;i>18;i--)
        {
            array[31][i][0] = WASSER;
            array[32][i][0] = WASSER;
            array[33][i][0] = WASSER;
            array[34][i][0] = WASSER;
            array[35][i][0] = WASSER;
        }

        for(int i = 30;i<35;i++)
        {
            array[i][18][0] = WASSER;
        }

        for(int i = 29;i<35;i++)
        {
            array[i][17][0] = WASSER;
        }

        for(int i = 28;i<34;i++)
        {
            array[i][16][0] = WASSER;
        }

        for(int i = 25;i<33;i++)
        {
            array[i][15][0] = WASSER;
        }

        for(int i = 23;i<32;i++)
        {
            array[i][14][0] = WASSER;
        }

        for(int i = 22;i<32;i++)
        {
            array[i][13][0] = WASSER;
        }

        for(int i = 21;i<33;i++)
        {
            array[i][12][0] = WASSER;
            array[i][11][0] = WASSER;
            array[i][10][0] = WASSER;
            array[i][9][0] = WASSER;
        }

        for(int i = 22;i<33;i++)
        {
            array[i][8][0] = WASSER;
        }

        for(int i = 23;i<32;i++)
        {
            array[i][7][0] = WASSER;
        }

        for(int i = 23;i<31;i++)
        {
            array[i][6][0] = WASSER;
        }

        for(int i = 23;i<30;i++)
        {
            array[i][5][0] = WASSER;
        }


        //Lava
        for(int i = 5;i<9;i++)
        {
            array[i][12][0] = LAVA;
            array[i][11][0] = LAVA;
        }

        for(int i = 3;i<10;i++)
        {
            array[i][10][0] = LAVA;
            array[i][9][0] = LAVA;
        }
        array[11][9][0] = LAVA;

        for(int i = 3;i<11;i++)
        {
            array[i][8][0] = LAVA;
            array[i][7][0] = LAVA;
            array[i][6][0] = LAVA;
        }

        for(int i = 4;i<11;i++)
        {
            array[i][5][0] = LAVA;
        }

        for(int i = 5;i<9;i++)
        {
            array[i][4][0] = LAVA;
        }


    }

    public void map3()
    {
        for(int x=1;x<array.length-1;x++)
        {
            for(int y=1;y<array[x].length-1;y++)
            {
                array[x][y][0]= LAVA;
            }
        }

        for(int i=26;i<=30;i++)
        {
            array[i][3][0]=BODEN;
            array[i][4][0]=BODEN;
            array[i][5][0]=BODEN;
            array[i][6][0]=BODEN;

        }
        array[26][6][0]=LAVA;

        for(int i =41;i<=44;i++)
        {
            array[i][11][0]=BODEN;
            array[i][12][0]=BODEN;
            array[i][13][0]=BODEN;
            array[i][14][0]=BODEN;
        }

        for(int i = 19;i<=23;i++)
        {
            array[i][15][0]=BODEN;
            array[i][16][0]=BODEN;
            array[i][17][0]=BODEN;
        }
        array[21][18][0]=BODEN;
        array[22][18][0]=BODEN;
    }

    public Barrier[] map1Barrier()  //Zerstörbare Barriere in Map 1
    {
       Barrier [] barriermap = new Barrier[4];
       for(int i = 0;i<4;i++)
       {
           barriermap[i]= new Barrier(25,i+1,this);
           this.blocked[25][i + 1] = true;
       }

       return barriermap;

    }

    public Barrier[] map2Barrier()
    {

        Barrier [] barriermap = new Barrier[1000];
        for(int x = 1;x<mapbreite/4;x++)
        {
            for(int y = 1;y<maphoehe-1;y++)
            {
                barriermap[y+(maphoehe-1)*x]=new Barrier(x*4,y,this);
                this.blocked[x*4][y] = true;

            }
        }

        return  barriermap;
    }

    public boolean[][] getBlocked() {
        return blocked;
    }

    public void setBlocked(int x, int y) {
        this.blocked[x][y] = true;
    }

    public void setUnblocked(int x, int y) {
        this.blocked[x][y] = false;
    }

    public ArrayList<Vector3> getHitCoord(ArrayList<Tank> tanks, ArrayList<Tank> enemyTanks)
    {
        ArrayList<Vector3> out = new ArrayList<>();
        for(int i = 0;i<tanks.size();i++)
        {
            for(int k=0;k<tanks.get(i).shots.size();k++)
            {
                Vector2 o = tanks.get(i).shots.get(k).getNext();
                if(this.getType((int)o.x,(int)o.y,1)==1)
                {
                    out.add(new Vector3(o.x,o.y,tanks.get(i).shots.get(k).getPower()));
                    if (searchTank(new Vector3(o.x, o.y, (float) 1.0), enemyTanks) != -1) //Sorgt dafür dass es nur bei Panzertreffern Punkte gibt
                    {



                            tanks.get(i).addScore(1); //Fügt Score hinzu
                            tanks.get(i).addCombo();

                            tanks.get(i).damageDealt += tanks.get(i).firepower;

                    }

                }
            }
        }
        return out;
    }


    public ArrayList<Vector3> getHitCoord(ArrayList<Tank> tanks) {
        ArrayList<Vector3> out = new ArrayList<>();
        for (int i = 0; i < tanks.size(); i++) {
            for (int k = 0; k < tanks.get(i).shots.size(); k++) {
                Vector2 o = tanks.get(i).shots.get(k).getNext();
                if (this.getType((int) o.x, (int) o.y, 1) == 1) {
                    out.add(new Vector3(o.x, o.y, tanks.get(i).shots.get(k).getPower()));
                    if (searchTank(new Vector3(o.x, o.y, (float) 1.0), tanks) != -1) //Sorgt dafür dass es nur bei Panzertreffern Punkte gibt
                    {

                            tanks.get(i).addScore(1); //Fügt Score hinzu
                            //tanks.get(i).addCombo();      //Steffen: wozu ist das hier? (evtl später entkommentieren)


                    }

                }
            }
        }
        return out;
    }

    public Integer searchBarrier(Vector3 vector) //Sucht ob getroffenes Obj eine Barriere ist
    {
        if(vector==null)
        {
            return -1;
        }
        if(barriers!=null)
        {
            for(int i = 0;i<this.barriers.length;i++)
            {
                if(barriers[i]!= null)
                {
                    if(this.barriers[i].x==vector.x&&this.barriers[i].y==vector.y)
                    {
                        SpreadingPeace.barrierBreakSound.play(SpreadingPeace.volume);
                        return i;

                    }
                }

            }
        }

        return -1;
    }

    public Integer searchTank(Vector3 vector,ArrayList<Tank> tanks) //Sucht ob getroffenes Obj ein Panzer ist
    {
        if(vector==null)
        {
            return -1;
        }
        for(int i = 0;i<tanks.size();i++)
        {
            if(tanks.get(i)!=null)
            {
                if(tanks.get(i).getX()==vector.x&&tanks.get(i).getY()==vector.y)
                {

                    return i; //index i in der Liste wird zurückgegeben
                }
            }

        }
        return -1;
    }







    private void initBlocked() {
        for (int i = 0; i < mapbreite; i++) {
            for (int j = 0; j < maphoehe; j++) {
                if (array[i][j][0] == 2 || array[i][j][1] == 1 || array[i][j][0]==1) blocked[i][j] = true;
                else blocked[i][j] = false;
            }
        }

        for (int i = 0; i < mapbreite; i++) {
            for (int j = 0; j < maphoehe; j++) {

                if (i == 0) {
                    blocked[0][j] = true;

                }

                if (j == 0) {

                    blocked[i][0] = true;

                }

                if (i == 59) {
                    blocked[59][j] = true;
                }
                if (j == 29) {
                    blocked[i][j] = true;
                }

            }


        }

    }

    public TiledMap getTiledMap() {
        return map;
    }

    /**
     * Tatiana: Gibt die Breite der Map zurück
     *
     * @return Breite der Map
     */
    public int getWidth() {
        return mapbreite;
    }

    /**
     * Tatiana: Gibt die Höhe der Map zurück
     *
     * @return Höhe der Map
     */
    public int getHeight() {
        return maphoehe;
    }

    public void setLava(int x,int y)
    {
        array[x][y][0]=LAVA;
    }

    public void setBoden(int x,int y)
    {
        array[x][y][0]=BODEN;
    }
}
