package com.mygdx.game;


import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Impact {
    ArrayList <Tank> alltanks;
    int damage;
    Map map;
    Impact(int damage,Map map)
    {

        this.damage = damage;
        this.map = map;
    }

    public void ImpactDamage(ArrayList<Tank> tanks)
    {
        this.alltanks=tanks;
        for(int i = 0;i<alltanks.size();i++)
        {
            if(alltanks.get(i).getP()!=0)
            {
                if(alltanks.get(i).getForwardTile().x!=0&&alltanks.get(i).getForwardTile().y!=0)
                {
                    int x= map.searchTank(new Vector3(alltanks.get(i).getForwardTile().x,alltanks.get(i).getForwardTile().y,0),alltanks);
                    if(x!=-1)
                    {
                        alltanks.get(x).takeDamage(damage);
                        alltanks.get(i).setPZero();
                    }

                }
            }
        }
    }
}
