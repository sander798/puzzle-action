package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

/**
 * Class for loading and storing all game resources.
 */
public final class Load {

    private static Image[] floors;
    private static Image[] walls;
    private static Image[] images;
    private static Image[] animations;
    private static Sound[] sounds;
    private static Music[] music;
    private static BitmapFont[] fonts;
    private static Tile[] tiles;

    public static Image[] getFloors() {
        return floors;
    }

    public static Image[] getWalls() {
        return walls;
    }

    public static Image[] getImages() {
        return images;
    }

    public static Image[] getAnimations() {
        return animations;
    }

    public static Sound[] getSounds() {
        return sounds;
    }

    public static Music[] getMusic() {
        return music;
    }

    public static BitmapFont getMediumFont() {
        return fonts[0];
    }

    public static BitmapFont getSmallFont() {
        return fonts[1];
    }

    /**
     * Loads ALL assets needed for the game. Prints out the time taken to load. Loaded data can be accessed by the respective getters.
     *
     * @param initTime 	when the program began (used to print a total loading time for the program)
     */
    public static void loadAssets(long initTime) {

        long newTime = System.currentTimeMillis();
        loadImages();
        System.out.println((floors.length + walls.length + images.length + animations.length) + " images loaded in " + (System.currentTimeMillis() - newTime) + "ms");

        newTime = System.currentTimeMillis();
        loadFonts();
        System.out.println(fonts.length + " fonts loaded in " + (System.currentTimeMillis() - newTime) + "ms");

        //newTime = System.currentTimeMillis();
        //loadSounds();
        //System.out.println(sounds.length + " sounds loaded in " + (System.currentTimeMillis() - newTime) + "ms");

        //newTime = System.currentTimeMillis();
        //loadMusic();
        //System.out.println(sounds.length + " music files loaded in " + (System.currentTimeMillis() - newTime) + "ms");

        System.out.println("****Total load time: " + (System.currentTimeMillis() - initTime) + "ms****");
    }

    /**
     * Loads all image files needed for the game.
     */
    public static void loadImages() {

        try {
            Image floorBaseImg = new Image("graphics/tiles/floors.png", 1);

            floors = new Image[] {
                new Image(floorBaseImg, 0, 0, 16, 16, 4),//Tile
                new Image(floorBaseImg, 16, 0, 16, 16, 4),//Grass
            };

            Image wallBaseImg = new Image("graphics/walls/walls.png", 1);

            walls = new Image[] {
                new Image(wallBaseImg, 0, 0, 16, 20, 4),//Tile
                new Image(wallBaseImg, 16, 0, 16, 20, 4),//Grass
            };

            images = new Image[] {
                new Image("graphics/blank.png", 1),
                new Image("graphics/WIP.png", 4),
            };

            animations = new Image[] {
                new Image("graphics/slimes/slimeGreenIdle.png", 4),
                new Image("graphics/slimes/slimeGreenUp.png", 4),
                new Image("graphics/slimes/slimeGreenDown.png", 4),
                new Image("graphics/slimes/slimeGreenLeft.png", 4),
                new Image("graphics/slimes/slimeGreenRight.png", 4),
            };
        } catch (Exception e) {
            System.out.println("**** Failed to load image assets! ****");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Loads all sound files needed for the game.
     */
    public static void loadSounds() {

        try {
            sounds = new Sound[] {

            };
        } catch (Exception e) {
            System.out.println("**** Failed to load sound assets! ****");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Loads all music files needed for the game.
     */
    public static void loadMusic() {

        try {
            music = new Music[] {

            };
        } catch (Exception e) {
            System.out.println("**** Failed to load music assets! ****");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Loads all fonts needed for the game.
     */
    public static void loadFonts() {

        try {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("graphics/EduTASBeginner-Bold.ttf"));
            FreeTypeFontParameter parameter = new FreeTypeFontParameter();
            //parameter.flip = true;
            parameter.kerning = true;
            parameter.borderColor = Color.BLACK;
            parameter.color = Color.WHITE;

            parameter.size = 40;//TODO: make font sizes dependent on Game.graphicsScale
            parameter.borderWidth = 4;
            BitmapFont mediumFont = generator.generateFont(parameter);

            parameter.size = 24;
            parameter.borderWidth = 2;
            BitmapFont smallFont = generator.generateFont(parameter);

            generator.dispose();

            fonts = new BitmapFont[] {
                mediumFont,
                smallFont,
            };
        } catch (Exception e) {
            System.out.println("**** Failed to load font assets! ****");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Disposes of all loaded resources.
     */
    public static void releaseResources() {
        /**for (Music m : music){
            m.dispose();
        }

        for (Sound s : sounds){
            s.dispose();
        }**/

        for (BitmapFont f : fonts) {
            f.dispose();
        }
    }

    public static Entity getEntityFromID(String entityID, int x, int y) {
        if (entityID.equals("ply5")) {
            return new Entity.PlayerGreen(x, y);
        } else if (entityID.equals("flgr")) {
            //return new Tile.GrassFloor();
        }

        return null;
    }

    public static Tile getTileFromID(String tileID) {
        /*
        Tile IDs:
        [fl-- - floor]
        [wl-- - wall]
        [lq-- - liquid/pit]
        void - nothing
        fltl - tiled floor
        flgr - grass floor
        flst - stone floor
        flsd - sand floor
        flbr - brick floor
        wltl - tiled wall
        wlgr - green wall
        wlst - stone wall
        wlsd - sand wall
        wlbr - brick wall
        lqwt - water
        lqlv - lava
         */
        if (tileID.equals("fltl")) {
            return new Tile.TileFloor();
        } else if (tileID.equals("flgr")) {
            return new Tile.GrassFloor();
        } else if (tileID.equals("wltl")) {
            return new Tile.TileWall();
        } else if (tileID.equals("wlgr")) {
            return new Tile.GrassWall();
        }

        return null;
    }
}
