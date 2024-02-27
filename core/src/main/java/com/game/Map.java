package com.game;

import java.util.ArrayList;

public class Map {

    private final String name;
    private final Tile[][] tiles;
    private final ArrayList<Entity> entities;

    public Map(String name, Tile[][] tiles, ArrayList<Entity> entities) {
        this.name = name;
        this.tiles = tiles;
        this.entities = entities;
    }

    public void addEntity(Entity newEntity) {
        entities.add(newEntity);
    }

    public void removeEntity(Entity target) {
        entities.remove(target);
    }

    public String getName() {
        return name;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile getTile(int x, int y) {
        return tiles[y][x];
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
