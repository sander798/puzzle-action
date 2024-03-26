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
                new Image(floorBaseImg, 32, 0, 16, 16, 4),//Metal
            };

            Image wallBaseImg = new Image("graphics/walls/walls.png", 1);

            walls = new Image[] {
                new Image(wallBaseImg, 0, 0, 16, 20, 4),//Tile
                new Image(wallBaseImg, 16, 0, 16, 20, 4),//Grass
                new Image(wallBaseImg, 32, 0, 16, 20, 4),//Metal
            };

            images = new Image[] {
                new Image("graphics/blank.png", 1),//0
                new Image("graphics/shade.png", 1),
                new Image("graphics/objects/woodBox.png", 4),
                new Image("graphics/objects/metalBox.png", 4),
            };

            animations = new Image[] {
                new Image("graphics/slimes/slimeGreenIdle.png", 4),//0
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
        /*
        [Colour channels:
            0 - ALL (only used by buttons)
            1 - white
            2 - red
            3 - orange
            4 - yellow
            5 - green
            6 - blue
            7 - indigo
            8 - violet
            9 - brown > conveyors? (only used by buttons)
        ]

        Entity IDs:
        [# denotes a colour number]

        papr - paper message
        btc# - circle button
        bts# - square button
        btt# - timer button
        btd# - diamond button

        mana - mana charge
        bonu - bonus charge

        ply# - player character (slimes)
        gate - rainbow gate
        ffg# - coloured force field gate
        cana - cannon (2 second delay)
        canb - cannon (3 second delay)
        canc - cannon (4 second delay)
        cand - cannon (5 second delay)
        frbl - fireball (moving)
        frpl - fireball (still)

        bxwd - wooden box
        bxmt - metal box
        bxc# - coloured box
         */

        switch (entityID) {
            case "papr":
                return new Paper(x, y);
            case "btc0":
                return new ButtonCircle.ButtonCircleAll(x, y);
            case "btc1":
                return new ButtonCircle.ButtonCircleWhite(x, y);
            case "btc2":
                return new ButtonCircle.ButtonCircleRed(x, y);
            case "btc3":
                return new ButtonCircle.ButtonCircleOrange(x, y);
            case "btc4":
                return new ButtonCircle.ButtonCircleYellow(x, y);
            case "btc5":
                return new ButtonCircle.ButtonCircleGreen(x, y);
            case "btc6":
                return new ButtonCircle.ButtonCircleBlue(x, y);
            case "btc7":
                return new ButtonCircle.ButtonCircleIndigo(x, y);
            case "btc8":
                return new ButtonCircle.ButtonCircleViolet(x, y);
            case "btc9":
                return new ButtonCircle.ButtonCircleBrown(x, y);
            case "ply5":
                return new Player.PlayerGreen(x, y);
            case "bxwd":
                return new BoxWood(x, y);
            case "bxmt":
                return new BoxMetal(x, y);
        }

        return null;
    }

    public static Tile getTileFromID(String tileID) {
        /*
        Tile IDs:
        [fl-- - floor]
        [wl-- - wall]
        [lq-- - liquid/pit]
        [cb-- - conveyor belt]

        void - nothing (acts as wall)

        fltl - tiled floor
        flgr - grass floor
        flmt - metal floor

        wltl - tiled wall
        wlgr - grass wall
        wlmt - metal wall

        flia - ice floor (normal)
        flib - ice floor (|_ turn)
        flic - ice floor (|" turn)
        flid - ice floor ("| turn)
        flie - ice floor (_| turn)

        cbna - conveyor belt up
        cbnb - conveyor belt down
        cbnc - conveyor belt left
        cbnd - conveyor belt right

        lqwt - water
        lqlv - lava
         */
        switch (tileID) {
            case "fltl":
                return new Tile.TileFloor();
            case "flgr":
                return new Tile.GrassFloor();
            case "flmt":
                return new Tile.MetalFloor();
            case "wltl":
                return new Tile.TileWall();
            case "wlgr":
                return new Tile.GrassWall();
            case "wlmt":
                return new Tile.MetalWall();
        }

        return null;
    }
}
