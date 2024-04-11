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
    public static class Void extends Tile {
        public Void() {
            super("void", Load.getImages()[0]);
        }
    }

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
            case "void":
                return new Tile.Void();
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
