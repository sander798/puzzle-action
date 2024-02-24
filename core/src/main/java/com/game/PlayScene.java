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
     * TODO: Player movement
     * TODO: Player tile collisions
     * TODO: Update entity logic
     * TODO: Entity collisions
     */

    private boolean debugMode = false;
    private boolean cameraMode = false;

    private float cameraX, cameraY;
    private int cameraSpeed;
    private int tileSize;
    private int viewWidthTiles, viewHeightTiles;
    private Map map;

    private Entity playerEntity;

    public PlayScene() {
        updateGraphicsScale();

        map = LoadMap.loadMapFromFile(Gdx.files.internal("maps/testMap.ssm"));

        if (map == null) {
            Game.scene = Game.Scene.MENU;
            throw new NullPointerException();
        }

        //Find player entity
        for (Entity e : map.getEntities()) {
            if (e.getID().contains("ply")) {
                playerEntity = e;
                centreCameraOnPlayer();
                break;
            }
        }

        if (playerEntity == null){
            JOptionPane.showMessageDialog(null, "Map file contained no player entity!", "Error!", JOptionPane.ERROR_MESSAGE);
            Game.scene = Game.Scene.MENU;
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer shape) {
        update();

        batch.begin();

        //Calculate what is visible on screen to skip drawing everything else
        int offsetY = (int)(-cameraY / -tileSize);
        int farY = offsetY + viewHeightTiles;
        if (offsetY < 0) {
            offsetY = 0;
        }

        int offsetX = (int)(-cameraX / -tileSize);
        int farX = offsetX + viewWidthTiles;
        if (offsetX < 0) {
            offsetX = 0;
        }

        //Draw tiles
        for (int y = offsetY; y < map.getTiles().length && y <= farY; y++) {
            for (int x = offsetX; x < map.getTiles()[0].length && x < farX; x++) {
                batch.draw(
                    map.getTiles()[y][x].getImage().getTextureRegion(),
                    ((x * tileSize) - cameraX) * Game.graphicsScale,
                    Game.windowHeight - (((y + 1) * tileSize) - cameraY) * Game.graphicsScale,
                    tileSize,
                    tileSize
                );
            }
        }

        //Draw entities
        Entity e;

        for (int i = 0; i < map.getEntities().size(); i++) {
            //Check if the entity is visible
            e = map.getEntities().get(i);

            if (e.getX() + e.getCurrentAnimation().getScaledWidth() > cameraX
                && e.getX() - e.getCurrentAnimation().getScaledWidth() < cameraX + Game.windowWidth
                && e.getY() + e.getCurrentAnimation().getScaledHeight() > cameraY
                && e.getY() - e.getCurrentAnimation().getScaledHeight() < cameraY + Game.windowHeight) {

                batch.draw(
                    e.getCurrentAnimationFrame(),
                    (e.getX() - cameraX) * Game.graphicsScale,
                    Game.windowHeight - (e.getY() - cameraY) * Game.graphicsScale,
                    e.getCurrentAnimation().getScaledWidth(),
                    e.getCurrentAnimation().getScaledHeight()
                );
            }
        }

        //Draw debug info
        if (debugMode) {
            Load.getSmallFont().draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, (float) Gdx.graphics.getHeight() - 10);
            Load.getSmallFont().draw(batch, "Camera X: " + cameraX + ", Y: " + cameraY, 0, (float) Gdx.graphics.getHeight() - 40);
            Load.getSmallFont().draw(batch, "Player X: " + playerEntity.getX() + ", Y: " + playerEntity.getY(), 0, (float) Gdx.graphics.getHeight() - 70);
            Load.getSmallFont().draw(batch, "       (" + (int)Math.floor(playerEntity.getX() / tileSize) + ", " + (int)Math.floor(playerEntity.getY() / tileSize) + ")", 0, (float) Gdx.graphics.getHeight() - 110);

            /*
            shape.begin();

            shape.setColor(Color.BLACK);
            shape.rect((((float)Math.floor(playerEntity.getX() / tileSize)) * tileSize - cameraX) * Game.graphicsScale,
                Game.windowHeight - ((float)Math.floor(playerEntity.getY() / tileSize) * tileSize - cameraY) * Game.graphicsScale,
                tileSize, tileSize);

            shape.end();
             */
        }

        batch.end();
    }

    public void update() {
        //Update entity logic
        for (int i = 0; i < map.getEntities().size(); i++) {
            map.getEntities().get(i).update(this);
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
        viewWidthTiles = Game.windowWidth / tileSize;
        viewHeightTiles = Game.windowHeight / tileSize;
        cameraSpeed = Game.graphicsScale * 1200;
    }

    public void centreCameraOnPlayer() {
        cameraX = (int)(playerEntity.getX() + ((float)tileSize / 2) - ((float)Game.windowWidth / 2));
        cameraY = (int)(playerEntity.getY() + ((float)tileSize / 2) - ((float)Game.windowHeight / 2));
    }

    public float getCameraX() {
        return cameraX;
    }

    public float getCameraY() {
        return cameraY;
    }

    public Entity getPlayerEntity() {
        return playerEntity;
    }

    public Map getMap() {
        return map;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setCameraX(float cameraX) {
        this.cameraX = cameraX;
    }

    public void setCameraY(float cameraY) {
        this.cameraY = cameraY;
    }
}
