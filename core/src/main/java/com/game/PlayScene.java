package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayScene {

    /**
     * TODO: Win and death states
     * TODO: Switch between player entities
     */

    private boolean debugMode = false;
    private boolean cameraMode = false;

    private float cameraX, cameraY;
    private int cameraSpeed;
    private int tileSize, wallHeight;
    private int viewWidthTiles, viewHeightTiles;

    public Map map;
    public Entity playerEntity;
    public ArrayList<ArrayList<ArrayList<Entity>>> entityMap;

    public boolean isShowingMessage;
    public String messageText;

    public boolean[] buttonChannels = {false, false, false, false, false, false, false, false, false, false};

    private int clearPoints;//Number of key collectibles needed to open final gates
    public int points;//Current number of collected key items
    private long startTime;//Time played since level start

    public ArrayList<Entity> playerEntityList;
    public boolean hasDied, hasWon;

    private boolean justStarted;

    public PlayScene() {
        updateGraphicsScale();
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
                        ((x * tileSize) - cameraX),
                        Game.windowHeight - ((y * tileSize) - cameraY),
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
                        ((x * tileSize) - cameraX),
                        Game.windowHeight - ((y * tileSize) - cameraY),
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

        //Draw remaining point count & time
        Load.getSmallFont().draw(batch, "Time: "
            + ((System.currentTimeMillis() - startTime) / 60000)
            + ":" + (((System.currentTimeMillis() - startTime) / 1000) % 60), (float) Game.windowWidth - 200, (float) Game.windowHeight - 10);
        Load.getSmallFont().draw(batch, "Mana needed: " + (clearPoints - points), (float) Game.windowWidth - 200, (float) Game.windowHeight - 40);

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
                        shape.rect((x * tileSize - cameraX),
                            Game.windowHeight - (y * tileSize - cameraY),
                            tileSize, tileSize);
                    }
                }
            }

            shape.end();
        }
    }

    public void update() {
        if (justStarted) {
            startTime = System.currentTimeMillis();
            justStarted = false;
        }

        isShowingMessage = false;
        Arrays.fill(buttonChannels, false);

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

        //Cycle through player entities
        if (Gdx.input.isKeyJustPressed(Game.inputList[0])) {
            changePlayer();
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
        viewWidthTiles = (int)Math.ceil((double)Game.windowWidth / tileSize);
        viewHeightTiles = (int)Math.ceil((double)Game.windowHeight / tileSize);
        cameraSpeed = Game.graphicsScale * 1200;
    }

    public void centreCameraOnPlayer() {
        cameraX = (int)(playerEntity.getX() + ((float)tileSize / 2) - ((float)Game.windowWidth / 2));
        cameraY = (int)(playerEntity.getY() + ((float)tileSize / 2) - ((float)Game.windowHeight / 2));
    }

    public void changePlayer() {
        if (playerEntityList.size() <= 1) {
            return;
        }

        //Iterate through the list of player entities
        int index = playerEntityList.indexOf(playerEntity) + 1;
        if (index >= playerEntityList.size()) {
            index = 0;
        }

        playerEntity = playerEntityList.get(index);
        centreCameraOnPlayer();
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
        playerEntityList = new ArrayList<>();
        clearPoints = 0;
        points = 0;

        for (Entity e : map.getEntities()) {
            entityMap
                .get((int) Math.floor((e.getY() + (tileSize / 2)) / tileSize))
                .get((int) Math.floor((e.getX() + (tileSize / 2)) / tileSize))
                .add(e);

            //Find player
            if (e.getID().contains("ply")) {
                playerEntityList.add(e);

                if (!foundPlayer){
                    foundPlayer = true;
                    playerEntity = e;
                    centreCameraOnPlayer();
                }
            }

            //Count all key pickups
            if (e.getID().contains("mana")) {
                clearPoints++;
            }
        }

        if (playerEntity == null){
            JOptionPane.showMessageDialog(null, "Map file contained no player entity!", "Error!", JOptionPane.ERROR_MESSAGE);
            Game.scene = Game.Scene.MENU;
        }

        justStarted = true;
        isShowingMessage = false;
        hasDied = false;
        hasWon = false;
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
