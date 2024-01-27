package com.game;

import com.badlogic.gdx.Gdx;

public class EditorScene {

    private final int CAMERA_SPEED = 4;
    private final int BASE_TILE_SIZE = 16 * 4;

    private int cameraX, cameraY;
    private int tileSize;
    private Map map;

    public EditorScene() {
        cameraX = 0;
        cameraY = 0;
        tileSize = BASE_TILE_SIZE * Game.graphicsScale;
        map = LoadMap.loadMapFromFile(Gdx.files.internal("maps/testMap.pam"));
    }
}
