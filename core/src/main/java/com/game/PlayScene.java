package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

    private int cameraX, cameraY;
    private int cameraSpeed;
    private int tileSize;
    private int viewWidthTiles, viewHeightTiles;
    private Map map;

    private Entity playerEntity;

    public PlayScene() {
        cameraX = 0; //-(Game.windowWidth / 2);
        cameraY = 0; //-(Game.windowHeight / 2);
        updateGraphicsScale();

        map = LoadMap.loadMapFromFile(Gdx.files.internal("maps/testMap.pam"));

        if (map == null) {
            JOptionPane.showMessageDialog(null, "Map file contained invalid data!", "Error!", JOptionPane.ERROR_MESSAGE);
            Game.scene = Game.Scene.MENU;
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
        int offsetY = -cameraY / -tileSize;
        int farY = offsetY + viewHeightTiles;
        if (offsetY < 0) {
            offsetY = 0;
        }

        int offsetX = -cameraX / -tileSize;
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
        }

        batch.end();
    }

    public void update() {
        //Move camera
        if (Gdx.input.isKeyPressed(Game.inputList[6])) {
            if (Gdx.input.isKeyPressed(Game.inputList[2])) {
                cameraY -= cameraSpeed;
            } else if (Gdx.input.isKeyPressed(Game.inputList[3])) {
                cameraY += cameraSpeed;
            }

            if (Gdx.input.isKeyPressed(Game.inputList[4])) {
                cameraX -= cameraSpeed;
            } else if (Gdx.input.isKeyPressed(Game.inputList[5])) {
                cameraX += cameraSpeed;
            }

            cameraMode = true;
        } else {

            if (cameraMode){
                cameraMode = false;
                centreCameraOnPlayer();
            }

            //Player movement
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                //cameraY -= CAMERA_SPEED;
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                //cameraY += CAMERA_SPEED;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                //cameraX -= CAMERA_SPEED;
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                //cameraX += CAMERA_SPEED;
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
        cameraSpeed = Game.graphicsScale * 3;
    }

    public void centreCameraOnPlayer() {
        cameraX = (int)(playerEntity.getX() + ((float)tileSize / 2) - ((float)Game.windowWidth / 2));
        cameraY = (int)(playerEntity.getY() + ((float)tileSize / 2) - ((float)Game.windowHeight / 2));
    }
}
