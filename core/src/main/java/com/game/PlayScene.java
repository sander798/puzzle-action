package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class PlayScene {

    private final int CAMERA_SPEED = 4;
    private final int BASE_TILE_SIZE = 16 * 4;

    private int cameraX, cameraY;
    private int tileSize;
    private Map map;
    private Entity[][] tileEntities;

    public PlayScene() {
        cameraX = 0;
        cameraY = 0;
        tileSize = BASE_TILE_SIZE * Game.graphicsScale;
        map = LoadMap.loadMapFromFile(Gdx.files.internal("maps/testMap.pam"));
    }

    public void render(SpriteBatch batch, ShapeRenderer shape) {
        update();

        batch.begin();

        for (int y = 0; y < map.getTiles().length; y++) {
            for (int x = 0; x < map.getTiles()[0].length; x++) {
                batch.draw(
                    map.getTiles()[y][x].getImage().getTextureRegion(),
                    ((x * tileSize) - cameraX) * Game.graphicsScale, ((y * tileSize) - cameraY) * Game.graphicsScale,
                    tileSize, map.getTiles()[y][x].getImage().getScaledHeight() * Game.graphicsScale
                );
            }
        }

        //batch.draw(Load.getFloors()[0].getTextureRegion(), cameraX, cameraY, tileSize, tileSize);

        //Load.getSmallFont().draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, (float)Gdx.graphics.getHeight() - 10);
        //Load.getSmallFont().draw(batch, "X: " + cameraX + ", Y: " + cameraY, 0, (float)Gdx.graphics.getHeight() - 40);

        batch.end();
    }

    public void update() {
        //Move camera
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                cameraY += CAMERA_SPEED * Game.graphicsScale;
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                cameraY -= CAMERA_SPEED * Game.graphicsScale;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                cameraX -= CAMERA_SPEED * Game.graphicsScale;
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                cameraX += CAMERA_SPEED * Game.graphicsScale;
            }
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
