package com.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Tiles make up the basic landscape of each map.
 * Tiles can have various effects on entities sharing their space,
 * but for ease of programming this is defined in the Entity class
 */
public abstract class Tile {

    /**
     * The <code>String</code> identifier for this tile used in map data.
     * Should be four characters.
     */
    private String id;
    private Image img;
    private TextureAnimation animation;

    public Tile(String id, Image img) {
        this.id = id;
        this.img = img;
    }

    public Tile(String id, TextureAnimation animation) {
        this.id = id;
        this.animation = animation;
    }

    public String getID() {
        return id;
    }

    public TextureRegion getTextureRegion() {

        if (animation != null) {
            return animation.getCurrentFrame(true);
        }

        return img.getTextureRegion();
    }

    public Image getImage() {
        return img;
    }

    //////////////////////////////////////////////////////////////////////////////////
    // Tile Definitions
    //////////////////////////////////////////////////////////////////////////////////
    public static class TileFloor extends Tile {
        public TileFloor() {
            super("fltl", Load.getFloors()[0]);
        }
    }

    public static class GrassFloor extends Tile {
        public GrassFloor() {
            super("flgr", Load.getFloors()[1]);
        }
    }

    public static class MetalFloor extends Tile {
        public MetalFloor() {
            super("flmt", Load.getFloors()[2]);
        }
    }

    public static class TileWall extends Tile {
        public TileWall() {
            super("wltl", Load.getWalls()[0]);
        }
    }

    public static class GrassWall extends Tile {
        public GrassWall() {
            super("wlgr", Load.getWalls()[1]);
        }
    }

    public static class MetalWall extends Tile {
        public MetalWall() {
            super("wlmt", Load.getWalls()[2]);
        }
    }
}
