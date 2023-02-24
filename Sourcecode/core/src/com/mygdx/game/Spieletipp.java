package com.mygdx.game;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.concurrent.ThreadLocalRandom;

public class Spieletipp {

    private BitmapFont font;
    private int zufall, x, y;

    public Spieletipp(BitmapFont font) {
        this.font = font;
        this.zufall = ThreadLocalRandom.current().nextInt(0, 8);
        this.x = 500;
        this.y = 1000;

    }

    public void showTip(Batch batch) {
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        switch (zufall) {
            case 0:
                font.draw(batch, "Jeder Spieler verfügt über 5 verschiedene Schusstypen: Normal, Fast, Bounce, Spread und Curve.", this.x, this.y);
                return;

            case 1:
                font.draw(batch, "Nutze den Bounce-Shot um Gegner aus der Deckung heraus durch geschicktes Abprallen zu treffen.", this.x, this.y);
                return;

            case 2:
                font.draw(batch, "Pass auf! Sowohl der Bounce-Shot als auch der Curve-Shot können dich selbst treffen.", this.x, this.y);
                return;

            case 3:
                font.draw(batch, "Lege Minen, um Verfolger zu überraschen.", this.x, this.y);
                return;

            case 4:
                font.draw(batch, "Halte Ausschau nach der DamageZone. Hier verursachst du doppelten Frieden, aber wirst auch doppelt so schnell zur Aufgabe überredet.", this.x, this.y);
                return;

            case 5:
                font.draw(batch, "Achte auf das UFO! Es überredet die Panzercrew schnell zur Aufgabe.", this.x, this.y);
                return;

            case 6:
                font.draw(batch, "Du kannst jederzeit das Spiel pausieren, um dir Statistiken, Highscores und Achievements anzusehen.", this.x, this.y);
                return;

            case 7:
                font.draw(batch, "Der Spread-Shot ist gut für Gegnergruppen geeignet.", this.x, this.y);
                return;

            case 8:
                font.draw(batch, "Nutze den Fast-Shot, um einfacher auf weite Entfernung zu treffen.", this.x, this.y);
                return;

        }


    }


}

