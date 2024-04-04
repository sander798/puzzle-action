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

            Image circleButtons = new Image("graphics/objects/circleButton.png", 1);
            Image squareButtons = new Image("graphics/objects/squareButton.png", 1);
            Image diamondButtons = new Image("graphics/objects/diamondButton.png", 1);
            Image timerButtons = new Image("graphics/objects/timerButton.png", 1);

            images = new Image[] {
                new Image("graphics/blank.png", 1),//0
                new Image("graphics/shade.png", 1),
                new Image("graphics/objects/woodBox.png", 4),
                new Image("graphics/objects/metalBox.png", 4),
                new Image(circleButtons, 0, 0, 16, 16, 4),
                new Image(circleButtons, 16, 0, 16, 16, 4),//5
                new Image(circleButtons, 0, 16, 16, 16, 4),
                new Image(circleButtons, 16, 16, 16, 16, 4),
                new Image(circleButtons, 0, 32, 16, 16, 4),
                new Image(circleButtons, 16, 32, 16, 16, 4),
                new Image(circleButtons, 0, 48, 16, 16, 4),//10
                new Image(circleButtons, 16, 48, 16, 16, 4),
                new Image(circleButtons, 0, 64, 16, 16, 4),
                new Image(circleButtons, 16, 64, 16, 16, 4),
                new Image(circleButtons, 0, 80, 16, 16, 4),
                new Image(circleButtons, 16, 80, 16, 16, 4),//15
                new Image(circleButtons, 0, 96, 16, 16, 4),
                new Image(circleButtons, 16, 96, 16, 16, 4),
                new Image(circleButtons, 0, 112, 16, 16, 4),
                new Image(circleButtons, 16, 112, 16, 16, 4),
                new Image(circleButtons, 0, 128, 16, 16, 4),//20
                new Image(circleButtons, 16, 128, 16, 16, 4),
                new Image(circleButtons, 0, 144, 16, 16, 4),
                new Image(circleButtons, 16, 144, 16, 16, 4),
                new Image(squareButtons, 0, 0, 16, 16, 4),
                new Image(squareButtons, 16, 0, 16, 16, 4),//25
                new Image(squareButtons, 0, 16, 16, 16, 4),
                new Image(squareButtons, 16, 16, 16, 16, 4),
                new Image(squareButtons, 0, 32, 16, 16, 4),
                new Image(squareButtons, 16, 32, 16, 16, 4),
                new Image(squareButtons, 0, 48, 16, 16, 4),//30
                new Image(squareButtons, 16, 48, 16, 16, 4),
                new Image(squareButtons, 0, 64, 16, 16, 4),
                new Image(squareButtons, 16, 64, 16, 16, 4),
                new Image(squareButtons, 0, 80, 16, 16, 4),
                new Image(squareButtons, 16, 80, 16, 16, 4),//35
                new Image(squareButtons, 0, 96, 16, 16, 4),
                new Image(squareButtons, 16, 96, 16, 16, 4),
                new Image(squareButtons, 0, 112, 16, 16, 4),
                new Image(squareButtons, 16, 112, 16, 16, 4),
                new Image(squareButtons, 0, 128, 16, 16, 4),//40
                new Image(squareButtons, 16, 128, 16, 16, 4),
                new Image(squareButtons, 0, 144, 16, 16, 4),
                new Image(squareButtons, 16, 144, 16, 16, 4),
                new Image(diamondButtons, 0, 0, 16, 16, 4),
                new Image(diamondButtons, 16, 0, 16, 16, 4),//45
                new Image(diamondButtons, 0, 16, 16, 16, 4),
                new Image(diamondButtons, 16, 16, 16, 16, 4),
                new Image(diamondButtons, 0, 32, 16, 16, 4),
                new Image(diamondButtons, 16, 32, 16, 16, 4),
                new Image(diamondButtons, 0, 48, 16, 16, 4),//50
                new Image(diamondButtons, 16, 48, 16, 16, 4),
                new Image(diamondButtons, 0, 64, 16, 16, 4),
                new Image(diamondButtons, 16, 64, 16, 16, 4),
                new Image(diamondButtons, 0, 80, 16, 16, 4),
                new Image(diamondButtons, 16, 80, 16, 16, 4),//55
                new Image(diamondButtons, 0, 96, 16, 16, 4),
                new Image(diamondButtons, 16, 96, 16, 16, 4),
                new Image(diamondButtons, 0, 112, 16, 16, 4),
                new Image(diamondButtons, 16, 112, 16, 16, 4),
                new Image(diamondButtons, 0, 128, 16, 16, 4),//60
                new Image(diamondButtons, 16, 128, 16, 16, 4),
                new Image(diamondButtons, 0, 144, 16, 16, 4),
                new Image(diamondButtons, 16, 144, 16, 16, 4),
                new Image(timerButtons, 0, 0, 16, 16, 4),
                new Image(timerButtons, 16, 0, 16, 16, 4),//65
                new Image(timerButtons, 32, 0, 16, 16, 4),
                new Image(timerButtons, 48, 0, 16, 16, 4),
                new Image(timerButtons, 64, 0, 16, 16, 4),
                new Image(timerButtons, 80, 0, 16, 16, 4),
                new Image(timerButtons, 96, 0, 16, 16, 4),//70
                new Image(timerButtons, 0, 16, 16, 16, 4),
                new Image(timerButtons, 16, 16, 16, 16, 4),
                new Image(timerButtons, 32, 16, 16, 16, 4),
                new Image(timerButtons, 48, 16, 16, 16, 4),
                new Image(timerButtons, 64, 16, 16, 16, 4),//75
                new Image(timerButtons, 80, 16, 16, 16, 4),
                new Image(timerButtons, 96, 16, 16, 16, 4),
                new Image(timerButtons, 0, 32, 16, 16, 4),
                new Image(timerButtons, 16, 32, 16, 16, 4),
                new Image(timerButtons, 32, 32, 16, 16, 4),//80
                new Image(timerButtons, 48, 32, 16, 16, 4),
                new Image(timerButtons, 64, 32, 16, 16, 4),
                new Image(timerButtons, 80, 32, 16, 16, 4),
                new Image(timerButtons, 96, 32, 16, 16, 4),
                new Image(timerButtons, 0, 48, 16, 16, 4),//85
                new Image(timerButtons, 16, 48, 16, 16, 4),
                new Image(timerButtons, 32, 48, 16, 16, 4),
                new Image(timerButtons, 48, 48, 16, 16, 4),
                new Image(timerButtons, 64, 48, 16, 16, 4),
                new Image(timerButtons, 80, 48, 16, 16, 4),//90
                new Image(timerButtons, 96, 48, 16, 16, 4),
                new Image(timerButtons, 0, 64, 16, 16, 4),
                new Image(timerButtons, 16, 64, 16, 16, 4),
                new Image(timerButtons, 32, 64, 16, 16, 4),
                new Image(timerButtons, 48, 64, 16, 16, 4),//95
                new Image(timerButtons, 64, 64, 16, 16, 4),
                new Image(timerButtons, 80, 64, 16, 16, 4),
                new Image(timerButtons, 96, 64, 16, 16, 4),
                new Image(timerButtons, 0, 80, 16, 16, 4),
                new Image(timerButtons, 16, 80, 16, 16, 4),//100
                new Image(timerButtons, 32, 80, 16, 16, 4),
                new Image(timerButtons, 48, 80, 16, 16, 4),
                new Image(timerButtons, 64, 80, 16, 16, 4),
                new Image(timerButtons, 80, 80, 16, 16, 4),
                new Image(timerButtons, 96, 80, 16, 16, 4),//105
                new Image(timerButtons, 0, 96, 16, 16, 4),
                new Image(timerButtons, 16, 96, 16, 16, 4),
                new Image(timerButtons, 32, 96, 16, 16, 4),
                new Image(timerButtons, 48, 96, 16, 16, 4),
                new Image(timerButtons, 64, 96, 16, 16, 4),//110
                new Image(timerButtons, 80, 96, 16, 16, 4),
                new Image(timerButtons, 96, 96, 16, 16, 4),
                new Image(timerButtons, 0, 112, 16, 16, 4),
                new Image(timerButtons, 16, 112, 16, 16, 4),
                new Image(timerButtons, 32, 112, 16, 16, 4),//115
                new Image(timerButtons, 48, 112, 16, 16, 4),
                new Image(timerButtons, 64, 112, 16, 16, 4),
                new Image(timerButtons, 80, 112, 16, 16, 4),
                new Image(timerButtons, 96, 112, 16, 16, 4),
                new Image(timerButtons, 0, 128, 16, 16, 4),//120
                new Image(timerButtons, 16, 128, 16, 16, 4),
                new Image(timerButtons, 32, 128, 16, 16, 4),
                new Image(timerButtons, 48, 128, 16, 16, 4),
                new Image(timerButtons, 64, 128, 16, 16, 4),
                new Image(timerButtons, 80, 128, 16, 16, 4),//125
                new Image(timerButtons, 96, 128, 16, 16, 4),
                new Image(timerButtons, 0, 144, 16, 16, 4),
                new Image(timerButtons, 16, 144, 16, 16, 4),
                new Image(timerButtons, 32, 144, 16, 16, 4),
                new Image(timerButtons, 48, 144, 16, 16, 4),//130
                new Image(timerButtons, 64, 144, 16, 16, 4),
                new Image(timerButtons, 80, 144, 16, 16, 4),
                new Image(timerButtons, 96, 144, 16, 16, 4),
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
}
