package com.mygdx.game;//test

import com.badlogic.gdx.Gdx;


import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;

import static com.badlogic.gdx.graphics.Color.*;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class HealthBar {
    ArrayList<Tank> tanks;
    Texture lebensleiste;

    public HealthBar(ArrayList<Tank> tanks, Texture lebensleiste) {
        this.tanks = tanks;
        this.lebensleiste = lebensleiste;

    }

    public void draw(Batch batch) {

        for (int i = 0; i < tanks.size(); i++) {
            int currentHealth = tanks.get(i).checkHealth();
            batch.setColor(tanks.get(i).getFarbe());

            batch.draw(lebensleiste, 10 + i * 275, 1000, 250 - currentHealth / 4, 30); //hier position der Lebensleiste


            batch.setColor(Color.WHITE);
        }
    }


} //beep
