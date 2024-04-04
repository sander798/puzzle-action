package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;

public class EditorScene {

    private Map map;

    private float cameraX, cameraY;
    private int cameraSpeed;
    private int tileSize, wallHeight;
    private int viewWidthTiles, viewHeightTiles;

    private boolean isShowingPopup;
    private boolean isShowingTiles;

    public EditorScene() {
        updateGraphicsScale();
        isShowingPopup = false;
        isShowingTiles = false;
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

        if (isShowingPopup) {
            batch.draw(
                Load.getImages()[1].getTextureRegion(),
                Game.windowWidth / 12f,
                Game.windowHeight / 12f,
                Game.windowWidth / 1.2f,
                Game.windowHeight / 1.2f
            );

            if (isShowingTiles) {

            } else {

            }
        }

        batch.end();
    }

    public void update() {
        //Move camera
        if (Game.getMouseVector().y <= 1) {
            cameraY += cameraSpeed * Gdx.graphics.getDeltaTime();
        } else if (Game.getMouseVector().y >= Game.windowHeight - 1) {
            cameraY -= cameraSpeed * Gdx.graphics.getDeltaTime();
        }

        if (Game.getMouseVector().x <= 1) {
            cameraX -= cameraSpeed * Gdx.graphics.getDeltaTime();
        } else if (Game.getMouseVector().x >= Game.windowWidth - 1) {
            cameraX += cameraSpeed * Gdx.graphics.getDeltaTime();
        }

        //Mouse click
        if (Gdx.input.isTouched(Input.Buttons.LEFT)) {

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

    public void loadMap(String path) {
        map = LoadMap.loadMapFromFile(Gdx.files.internal(path));

        if (map == null) {
            Game.scene = Game.Scene.MENU;
            throw new NullPointerException();
        }

        sortEntities();
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
            } else if (e.getID().equals("mana")) {
                pickups.add(e);
            } else if (e.getID().equals("bonu")) {
                pickups.add(e);
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

    public void setCameraX(float cameraX) {
        this.cameraX = cameraX;
    }

    public void setCameraY(float cameraY) {
        this.cameraY = cameraY;
    }

}
