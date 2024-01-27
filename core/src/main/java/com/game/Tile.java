package com.game;

import java.util.HashMap;

/**
 * Tiles make up the basic landscape of each map.
 * They never do anything themselves, but can have unique properties, and entities sharing their 2D space.
 */
public abstract class Tile {

    /**
     * The <code>String</code> identifier for this tile used in map data.
     * Should be four characters.
     */
    private String id;
    private Image img;
    /**
     * The list of properties (if any) for this <code>Tile</code>.
     * Each should have a String key identifying the property name.
     */
    private final HashMap<String, Integer> properties;

    public Tile(String id, Image img) {
        this.id = id;
        this.img = img;
        properties = new HashMap<String, Integer>();
    }

    public Tile(String id, Image img, HashMap<String, Integer> properties) {
        this.id = id;
        this.img = img;
        this.properties = properties;
    }

    public String getID() {
        return id;
    }

    public Image getImage() {
        return img;
    }

    public HashMap<String, Integer> getProperties() {
        return properties;
    }

    public Integer setProperty(String propertyKey, int value) {
        return properties.put(propertyKey, value);
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

    public static class TileWall extends Tile {
        public TileWall() {
            super("wltl", Load.getWalls()[0],
                PropertiesList.generatePropertiesMap(new String[]{"wall"}, new int[]{1})
            );
        }
    }

    public static class GrassWall extends Tile {
        public GrassWall() {
            super("wlgr", Load.getWalls()[1],
                PropertiesList.generatePropertiesMap(new String[]{"wall"}, new int[]{1})
            );
        }
    }
}
