package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import javax.swing.*;
import java.util.ArrayList;

public class PlayScene {

    /**
     * TODO: Win and death states
     */

    public boolean isShowingMessage;
    public String messageText;

    private boolean debugMode = false;
    private boolean cameraMode = false;

    private float cameraX, cameraY;
    private int cameraSpeed;
    private int tileSize, wallHeight;
    private int viewWidthTiles, viewHeightTiles;

    public Map map;
    public Entity playerEntity;
    public ArrayList<ArrayList<ArrayList<Entity>>> entityMap;

    public PlayScene() {
        updateGraphicsScale();
        isShowingMessage = false;
    }

    public void render(SpriteBatch batch, ShapeRenderer shape) {
        update();

        batch.begin();

        //Calculate what is visible on screen to skip drawing everything else
        int offsetY = (int)(-cameraY / -tileSize);
        int farY = offsetY + viewHeightTiles + 1;
        if (offsetY < 0) {
            offsetY = 0;
        }

        int offsetX = (int)(-cameraX / -tileSize);
        int farX = offsetX + viewWidthTiles;
        if (offsetX < 0) {
            offsetX = 0;
        }

        //Draw floors & liquids
        for (int y = offsetY; y < map.getTiles().length && y <= farY; y++) {
            for (int x = offsetX; x < map.getTiles()[0].length && x < farX; x++) {
                if (!map.getTiles()[y][x].getID().startsWith("wl")) {
                    batch.draw(
                        map.getTiles()[y][x].getTextureRegion(),
                        ((x * tileSize) - cameraX) * Game.graphicsScale,
                        Game.windowHeight - ((y * tileSize) - cameraY) * Game.graphicsScale,
                        tileSize,
                        tileSize
                    );
                }
            }
        }

        //Draw entities
        Entity e;

        for (int i = 0; i < map.getEntities().size(); i++) {
            //Check if the entity is visible
            e = map.getEntities().get(i);

            if (e.getX() + (e.getEntityWidth() * Game.graphicsScale) > cameraX
                && e.getX() - (e.getEntityWidth() * Game.graphicsScale) < cameraX + Game.windowWidth
                && e.getY() + (e.getEntityHeight() * Game.graphicsScale) > cameraY
                && e.getY() - (e.getEntityHeight() * Game.graphicsScale) < cameraY + Game.windowHeight) {

                batch.draw(
                    e.getTextureRegion(),
                    (e.getX() - cameraX) * Game.graphicsScale,
                    Game.windowHeight - (e.getY() - cameraY) * Game.graphicsScale,
                    e.getEntityWidth() * Game.graphicsScale,
                    e.getEntityHeight() * Game.graphicsScale
                );
            }
        }

        //Draw walls
        for (int y = offsetY; y < map.getTiles().length && y <= farY; y++) {
            for (int x = offsetX; x < map.getTiles()[0].length && x < farX; x++) {
                if (map.getTiles()[y][x].getID().startsWith("wl")) {
                    batch.draw(
                        map.getTiles()[y][x].getTextureRegion(),
                        ((x * tileSize) - cameraX) * Game.graphicsScale,
                        Game.windowHeight - ((y * tileSize) - cameraY) * Game.graphicsScale,
                        tileSize,
                        wallHeight
                    );
                }
            }
        }

        //Draw messages
        if (isShowingMessage) {
            batch.draw(
                Load.getImages()[1].getTextureRegion(),
                Game.windowWidth / 6f,
                Game.windowHeight / 10f,
                Game.windowWidth / 1.5f,
                Game.windowHeight / 3f
            );
            Load.getSmallFont().draw(batch, messageText, Game.windowWidth / 5f, Game.windowHeight / 2.5f);
        }

        //Draw debug info
        if (debugMode) {
            Load.getSmallFont().draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, (float) Game.windowHeight - 10);
            Load.getSmallFont().draw(batch, "Camera X: " + cameraX + ", Y: " + cameraY, 0, (float) Game.windowHeight - 40);
            Load.getSmallFont().draw(batch, "Player X: " + playerEntity.getX() + ", Y: " + playerEntity.getY(), 0, (float) Game.windowHeight - 70);
            Load.getSmallFont().draw(batch, "        (" + (int) Math.floor((playerEntity.getX() + (tileSize / 2)) / tileSize)
                + ", " + (int) Math.floor((playerEntity.getY() + (tileSize / 2)) / tileSize) + ")", 0, (float) Game.windowHeight - 110);
        }

        batch.end();

        //Debug mode entity outlines
        if (debugMode) {
            shape.begin();

            shape.setColor(Color.PURPLE);

            for (int y = offsetY; y < map.getTiles().length && y <= farY; y++) {
                for (int x = offsetX; x < map.getTiles()[0].length && x < farX; x++) {
                    //Draw entity tile positions map
                    if (!getTileEntities(x, y).isEmpty()) {
                        shape.rect((x * tileSize - cameraX) * Game.graphicsScale,
                            Game.windowHeight - (y * tileSize - cameraY) * Game.graphicsScale,
                            tileSize, tileSize);
                    }
                }
            }

            shape.end();
        }
    }

    public void update() {
        isShowingMessage = false;

        //Update entity logic
        try {
            for (int i = 0; i < map.getEntities().size(); i++) {
                map.getEntities().get(i).update(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Game.crash("Error in entity update logic!");
        }

        //Move camera
        if (Gdx.input.isKeyPressed(Game.inputList[6])) {
            if (Gdx.input.isKeyPressed(Game.inputList[2])) {
                cameraY -= cameraSpeed * Gdx.graphics.getDeltaTime();
            } else if (Gdx.input.isKeyPressed(Game.inputList[3])) {
                cameraY += cameraSpeed * Gdx.graphics.getDeltaTime();
            }

            if (Gdx.input.isKeyPressed(Game.inputList[4])) {
                cameraX -= cameraSpeed * Gdx.graphics.getDeltaTime();
            } else if (Gdx.input.isKeyPressed(Game.inputList[5])) {
                cameraX += cameraSpeed * Gdx.graphics.getDeltaTime();
            }

            cameraMode = true;
        } else {

            if (cameraMode){
                cameraMode = false;
                centreCameraOnPlayer();
            }
        }

        //Toggle debug mode
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            debugMode = !debugMode;
        }

        //Exit to menu
        if (Gdx.input.isKeyJustPressed(Game.inputList[1])) {
            Game.scene = Game.Scene.MENU;
        }
    }

    public void updateGraphicsScale() {
        tileSize = Game.BASE_TILE_SIZE * Game.graphicsScale;
        wallHeight = (Game.BASE_TILE_SIZE + 16) * Game.graphicsScale;
        viewWidthTiles = Game.windowWidth / tileSize;
        viewHeightTiles = Game.windowHeight / tileSize;
        cameraSpeed = Game.graphicsScale * 1200;
    }

    public void centreCameraOnPlayer() {
        cameraX = (int)(playerEntity.getX() + ((float)tileSize / 2) - ((float)Game.windowWidth / 2));
        cameraY = (int)(playerEntity.getY() + ((float)tileSize / 2) - ((float)Game.windowHeight / 2));
    }

    public void loadMap(String path) {
        map = LoadMap.loadMapFromFile(Gdx.files.internal(path));

        if (map == null) {
            Game.scene = Game.Scene.MENU;
            throw new NullPointerException();
        }

        //Map entities to tile coordinates to optimize searching & collision
        entityMap = new ArrayList<>(map.getTiles().length);
        for (int i = 0; i < map.getTiles().length; i++) {
            entityMap.add(new ArrayList<>(map.getTiles()[0].length));

            for (int i2 = 0; i2 < map.getTiles()[0].length; i2++) {
                entityMap.get(i).add(new ArrayList<Entity>());
            }
        }

        boolean foundPlayer = false;

        for (Entity e : map.getEntities()) {
            entityMap
                .get((int) Math.floor((e.getY() + (tileSize / 2)) / tileSize))
                .get((int) Math.floor((e.getX() + (tileSize / 2)) / tileSize))
                .add(e);

            //Find player
            if (!foundPlayer && e.getID().contains("ply")) {
                playerEntity = e;
                centreCameraOnPlayer();
                foundPlayer = true;
            }
        }

        if (playerEntity == null){
            JOptionPane.showMessageDialog(null, "Map file contained no player entity!", "Error!", JOptionPane.ERROR_MESSAGE);
            Game.scene = Game.Scene.MENU;
        }

        sortEntities();
    }

    public void spawnEntity(Entity e) {
        map.addEntity(e);
        entityMap
            .get((int) Math.floor((e.getY() + (tileSize / 2)) / tileSize))
            .get((int) Math.floor((e.getX() + (tileSize / 2)) / tileSize))
            .add(e);
    }

    public void removeEntity(Entity e) {
        map.removeEntity(e);
        entityMap
            .get((int) Math.floor((e.getY() + (tileSize / 2)) / tileSize))
            .get((int) Math.floor((e.getX() + (tileSize / 2)) / tileSize))
            .remove(e);
    }

    public void sortEntities() {
        ArrayList<Entity> entities = map.getEntities();

        /*
        Order of appearance:
        0 - buttons, papers
        1 - pickups
        2 - enemies, players, projectiles
        3 - pushables
         */

        ArrayList<Entity> walkables = new ArrayList<Entity>();//    0
        ArrayList<Entity> pickups = new ArrayList<Entity>();//      1
        ArrayList<Entity> movers = new ArrayList<Entity>();//       2
        ArrayList<Entity> pushables = new ArrayList<Entity>();//    3

        //Find each thing's bin
        //Default bin is "movers"
        for (Entity e : entities) {
            if (e.getID().equals("papr")) {
                walkables.add(e);
            } else if (e.getID().startsWith("bt")) {
                walkables.add(e);
            } else if (e.getID().startsWith("bx")) {
                pushables.add(e);
            } else {
                movers.add(e);
            }
        }

        //Combine the bins
        walkables.addAll(pickups);
        walkables.addAll(movers);
        walkables.addAll(pushables);

        map.getEntities().clear();
        map.getEntities().addAll(walkables);
    }

    public float getCameraX() {
        return cameraX;
    }

    public float getCameraY() {
        return cameraY;
    }

    public int getTileSize() {
        return tileSize;
    }

    public ArrayList<Entity> getTileEntities(int tileX, int tileY) {
        //Check for out-of-bounds
        if (tileX < 0
            || tileX >= map.getTiles()[0].length
            || tileY < 0
            || tileY >= map.getTiles().length) {
            return null;
        }

        return entityMap.get(tileY).get(tileX);
    }

    public void setCameraX(float cameraX) {
        this.cameraX = cameraX;
    }

    public void setCameraY(float cameraY) {
        this.cameraY = cameraY;
    }
}
