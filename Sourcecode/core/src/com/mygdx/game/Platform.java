package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

public class Platform {
    private Texture platimg = new Texture("platform.png");
    private Sprite platSprite;
    private Map map;
    int x;
    int y;
    //int richtung=0;
    long currentTime = System.currentTimeMillis();

    public Platform(Map map)
    {
        this.map=map;
        platSprite = new Sprite(platimg);
        x = (int) (Math.random() * (55) + 1);
        y = (int) (Math.random() * (25) + 1);
        platSprite.setPosition(x*32,y*32);
    }

    public void draw(Batch batch)
    {
        this.move();
        platSprite.setPosition(x*32,y*32);
        platSprite.draw(batch);

    }

    private void move()
    {
        if(System.currentTimeMillis()>currentTime+2000)
        {
            currentTime = System.currentTimeMillis();
            Random random = new Random();
            int p = random.nextInt(4);



            switch (p)
            {
                case 0:

                    down(x,y);
                    break;

                case 1:
                    up(x,y);
                    break;



                case 2:
                    right(x,y);
                    break;



                case 3:
                    left(x,y);
                    break;

            }
            setPlatform(x,y);

        }

    }

    public void left(int x,int y)
    {
        if(map.getType(x-1,y)==0||map.getType(x-1,y+1)==0||map.getType(x-1,y+1)==0||map.getType(x-1,y+1)==0||map.getType(x-1,y)==1)
        {
            return;
        }
        else
        {
            for(int i = 0;i<=3;i++)
            {
                map.setLava(x+3,y+i);
            }
            this.x--;
        }

    }

    public void up(int x,int y)
    {
        if(map.getType(x,y+4)==0||map.getType(x+1,y+4)==0||map.getType(x+2,y+4)==0||map.getType(x+3,y+4)==0||map.getType(x,y+4)==1)
        {
            return;
        }
        else
        {
            for(int i = 0;i<=3;i++)
            {
                map.setLava(x+i,y);
            }
            this.y++;
        }

    }

    public void down(int x,int y)
    {
        if(map.getType(x,y)==0||map.getType(x+1,y-1)==0||map.getType(x+2,y-1)==0||map.getType(x+3,y-1)==0||map.getType(x,y-1)==1)
        {
            return;
        }
        else
        {
            for(int i = 0;i<=3;i++)
            {
                map.setLava(x+i,y+3);
            }
            this.y--;
        }

    }

    public void right(int x,int y)
    {
        if(map.getType(x+4,y)==0||map.getType(x+4,y+1)==0||map.getType(x+4,y+1)==0||map.getType(x+4,y+1)==0||map.getType(x+4,y)==1)
        {
            return;
        }
        else
        {
            for(int i = 0;i<=3;i++)
            {
                map.setLava(x,y+i);
            }
            this.x++;
        }

    }

    public void setPlatform(int x,int y)
    {
        for(int k = 0;k<=3;k++)
        {
            for(int o = 0; o<=3;o++)
            {
                map.setBoden(x+k,y+o);
            }
        }
        if(map.getType(x,y,1)==1)
        {
            map.setLava(x,y);
        }
        else
        {
            map.setObj(x,y);
        }


        if(map.getType(x+3,y+3,1)==1)
        {
            map.setLava(x+3,y+3);
        }
        else
        {
            map.setObj(x+3,y+3);
        }


        if(map.getType(x+3,y,1)==1)
        {
            map.setLava(x+3,y);
        }
        else
        {
            map.setObj(x+3,y);
        }


        if(map.getType(x,y+3,1)==1)
        {
            map.setLava(x,y);
        }
        else
        {
            map.setObj(x,y+3);
        }

    }




}
