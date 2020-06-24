package com.simcolife.game.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.simcolife.game.SimCoGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1300;
		config.height = 780;
		config.resizable = false;
		new LwjglApplication(new SimCoGame(), config);
	}
}
