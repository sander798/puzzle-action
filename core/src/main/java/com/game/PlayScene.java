package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class PlayScene {

    /**
     * TODO: Spawn player
     * TODO: Player movement
     * TODO: Start camera centred on player
     * TODO: Draw entities
     * TODO: Cull off-screen entities
     * TODO: Update entity logic
     */

    private final int CAMERA_SPEED = 12;
    private final int BASE_TILE_SIZE = 16 * 4;

    private boolean debugMode = false;

    private int cameraX, cameraY;
    private int tileSize;
    private int viewWidthTiles, viewHeightTiles;
    private Map map;
    private Entity[][] tileEntities;

    public PlayScene() {
        cameraX = 0; //-(Game.windowWidth / 2);
        cameraY = 0; //-(Game.windowHeight / 2);
        tileSize = BASE_TILE_SIZE * Game.graphicsScale;
        viewWidthTiles = Game.windowWidth / tileSize;
        viewHeightTiles = Game.windowHeight / tileSize;
        map = LoadMap.loadMapFromFile(Gdx.files.internal("maps/testMap.pam"));
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
                    map.getTiles()[y][x].getImage().getScaledHeight() * Game.graphicsScale
                );
            }
        }

        //Draw entities

        //Draw debug info
        if (debugMode) {
            Load.getSmallFont().draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, (float) Gdx.graphics.getHeight() - 10);
            Load.getSmallFont().draw(batch, "Camera X: " + cameraX + ", Y: " + cameraY, 0, (float) Gdx.graphics.getHeight() - 40);
        }

        batch.end();
    }

    public void update() {
        //Move camera
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                cameraY -= CAMERA_SPEED;
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                cameraY += CAMERA_SPEED;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                cameraX -= CAMERA_SPEED;
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                cameraX += CAMERA_SPEED;
            }
        }

        //Toggle debug mode
        if (Gdx.input.isKeyJustPressed(Input.Keys.F3)) {
            debugMode = !debugMode;
        }

        //Exit to menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Game.scene = Game.Scene.MENU;
        }
    }

    /**
     * Updates the array of what tile each
     */
    public void calculateEntityTiles(Entity[][] tileEntities, ArrayList<Entity> entities) {
        for (Entity e : entities) {
            //tileEntities[e.getProperties()][]
        }
    }

    public void updateGraphicsScale() {
        tileSize = BASE_TILE_SIZE * Game.graphicsScale;
    }
}
