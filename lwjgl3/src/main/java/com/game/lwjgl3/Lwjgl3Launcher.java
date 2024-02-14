package com.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.game.Game;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {

    public final static String TITLE = "Slimy Solvers";
    public final static String VERSION = "v0.0.1 -- February 13th, 2024";
    public final static String CREDITS_1 = "By Alexander Evans";

    public static int windowWidth = 640;
    public static int windowHeight = 480;

    public static void main(String[] args) {
        System.out.println("*****" + TITLE + " -- " + VERSION + "*****");
        System.out.println(CREDITS_1+ "\n###############################################################");

        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Game(windowWidth, windowHeight), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Puzzle Action");
        configuration.useVsync(true);
        //// Limits FPS to the refresh rate of the currently active monitor.
        configuration.setForegroundFPS(60);
        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.
        //configuration.setWindowedMode(640, 480);
        configuration.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}
