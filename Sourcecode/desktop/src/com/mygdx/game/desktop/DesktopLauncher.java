package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MainPlayingScreen;
import com.mygdx.game.SpreadingPeace;

public class DesktopLauncher { //run this class to start "game"
	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "SprEading-Peace | Group 12"; //alex: Spielname im Fenster oben
        config.resizable = false;
        config.width = MainPlayingScreen.WIDTH;
        config.height = MainPlayingScreen.HEIGHT;
		//config.fullscreen = true; //alex: Spiel soll automatisch in Vollbild starten (konnte auf MacOS Vollbild nicht mehr schließen, daher erstmal auskommentiert)
		new LwjglApplication(new SpreadingPeace(), config);

	}

}
