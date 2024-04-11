package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;

public class EditorScene {

    /*
    TODO: Level resizing
    TODO: Saving
    TODO: Level name editing
    TODO: Property editing
    TODO: Play level
     */

    public record ListItem(String id, Image img) {}

    public ListItem[] tileTypes, entityTypes;

    public enum EditorState {
        SELECTING,
        PLACING_TILES,
        PLACING_ENTITIES,
        LISTING_TILES,
        LISTING_ENTITIES,
        EDITING_LEVEL_NAME,
        EDITING_PROPERTIES,
        SAVING,
    }

    private EditorState state;

    private ArrayList<ArrayList<Tile>> mapTiles;
    private ArrayList<Entity> mapEntities;

    private float cameraX, cameraY;
    private int cameraSpeed;
    private int tileSize, wallHeight;
    private int viewWidthTiles, viewHeightTiles;

    private int currentType;

    private Entity selectedEntity;

    private MenuButton selectButton, tileButton, entityButton, propertiesButton, saveButton,
        growUpButton, growDownButton, growLeftButton, growRightButton,
        shrinkUpButton, shrinkDownButton, shrinkLeftButton, shrinkRightButton;

    private boolean firstClick;

    public EditorScene() {
        updateGraphicsScale();
        registerElements();
        state = EditorState.SELECTING;
        currentType = 0;
        firstClick = false;
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
        for (int y = offsetY; y < mapTiles.size() && y <= farY; y++) {
            for (int x = offsetX; x < mapTiles.get(0).size() && x < farX; x++) {
                if (!mapTiles.get(y).get(x).getID().startsWith("wl")) {
                    batch.draw(
                        mapTiles.get(y).get(x).getTextureRegion(),
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

        for (int i = 0; i < mapEntities.size(); i++) {
            //Check if the entity is visible
            e = mapEntities.get(i);

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
        for (int y = offsetY; y < mapTiles.size() && y <= farY; y++) {
            for (int x = offsetX; x < mapTiles.get(0).size() && x < farX; x++) {
                if (mapTiles.get(y).get(x).getID().startsWith("wl")) {
                    batch.draw(
                        mapTiles.get(y).get(x).getTextureRegion(),
                        ((x * tileSize) - cameraX),
                        Game.windowHeight - ((y * tileSize) - cameraY),
                        tileSize,
                        wallHeight
                    );
                }
            }
        }

        //Draw tile or entity preview
        if (state == EditorState.PLACING_TILES) {
            batch.draw(
                tileTypes[currentType].img.getTextureRegion(),
                (((int)(Game.getMouseVector().x + (cameraX % tileSize)) / tileSize) * tileSize) - (cameraX % tileSize),
                Game.windowHeight - (((((int)(Game.windowHeight - Game.getMouseVector().y + (cameraY % tileSize)) / tileSize) + 1) * tileSize) - (cameraY % tileSize)),
                tileSize,
                tileSize
            );
        } else if (state == EditorState.PLACING_ENTITIES) {
            batch.draw(
                entityTypes[currentType].img.getTextureRegion(),
                (((int)(Game.getMouseVector().x + (cameraX % tileSize)) / tileSize) * tileSize) - (cameraX % tileSize),
                Game.windowHeight - (((((int)(Game.windowHeight - Game.getMouseVector().y + (cameraY % tileSize)) / tileSize) + 1) * tileSize) - (cameraY % tileSize)),
                tileSize,
                tileSize
            );
        }

        //Draw tile and entity selection list
        if (state == EditorState.LISTING_TILES || state == EditorState.LISTING_ENTITIES) {
            batch.draw(
                Load.getImages()[1].getTextureRegion(),
                Game.windowWidth / 12f,
                Game.windowHeight / 12f,
                Game.windowWidth / 1.2f,
                Game.windowHeight / 1.2f
            );

            if (state == EditorState.LISTING_TILES) {
                for (int i = 0; i < tileTypes.length; i++) {
                    batch.draw(
                        tileTypes[i].img.getTextureRegion(),
                        Game.windowWidth / 10f + (70 * Game.graphicsScale * (i % 22)),
                        Game.windowHeight / 1.2f - (70 * Game.graphicsScale * (i / 22)),
                        tileSize,
                        tileSize
                    );

                    //Hover shadow
                    if (Game.getMouseVector().x > Game.windowWidth / 10f + (70 * Game.graphicsScale * (i % 22))
                        && Game.getMouseVector().x < Game.windowWidth / 10f + (70 * Game.graphicsScale * (i % 22)) + tileSize){
                        if (Game.getMouseVector().y > Game.windowHeight / 1.2f - (70 * Game.graphicsScale * (i / 22))
                            && Game.getMouseVector().y < Game.windowHeight / 1.2f - (70 * Game.graphicsScale * (i / 22)) + tileSize){
                            batch.draw(
                                Load.getImages()[1].getTextureRegion(),
                                Game.windowWidth / 10f + (70 * Game.graphicsScale * (i % 22)),
                                Game.windowHeight / 1.2f - (70 * Game.graphicsScale * (i / 22)),
                                tileSize,
                                tileSize
                            );
                        }
                    }
                }
            } else if (state == EditorState.LISTING_ENTITIES) {
                for (int i = 0; i < entityTypes.length; i++) {
                    batch.draw(
                        entityTypes[i].img.getTextureRegion(),
                        Game.windowWidth / 10f + (70 * Game.graphicsScale * (i % 22)),
                        Game.windowHeight / 1.2f - (70 * Game.graphicsScale * (i / 22)),
                        tileSize,
                        tileSize
                    );

                    //Hover shadow
                    if (Game.getMouseVector().x > Game.windowWidth / 10f + (70 * Game.graphicsScale * (i % 22))
                        && Game.getMouseVector().x < Game.windowWidth / 10f + (70 * Game.graphicsScale * (i % 22)) + tileSize){
                        if (Game.getMouseVector().y > Game.windowHeight / 1.2f - (70 * Game.graphicsScale * (i / 22))
                            && Game.getMouseVector().y < Game.windowHeight / 1.2f - (70 * Game.graphicsScale * (i / 22)) + tileSize){
                            batch.draw(
                                Load.getImages()[1].getTextureRegion(),
                                Game.windowWidth / 10f + (70 * Game.graphicsScale * (i % 22)),
                                Game.windowHeight / 1.2f - (70 * Game.graphicsScale * (i / 22)),
                                tileSize,
                                tileSize
                            );
                        }
                    }
                }
            }
        }

        //Draw editor buttons
        selectButton.draw(batch, Game.getMouseVector(), Load.getImages()[1]);
        tileButton.draw(batch, Game.getMouseVector(), Load.getImages()[1]);
        entityButton.draw(batch, Game.getMouseVector(), Load.getImages()[1]);

        growUpButton.draw(batch, Game.getMouseVector(), Load.getImages()[1]);
        shrinkUpButton.draw(batch, Game.getMouseVector(), Load.getImages()[1]);
        growDownButton.draw(batch, Game.getMouseVector(), Load.getImages()[1]);
        shrinkDownButton.draw(batch, Game.getMouseVector(), Load.getImages()[1]);
        growLeftButton.draw(batch, Game.getMouseVector(), Load.getImages()[1]);
        shrinkLeftButton.draw(batch, Game.getMouseVector(), Load.getImages()[1]);
        growRightButton.draw(batch, Game.getMouseVector(), Load.getImages()[1]);
        shrinkRightButton.draw(batch, Game.getMouseVector(), Load.getImages()[1]);

        Load.getSmallFont().draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, (float) Game.windowHeight - 10);
        Load.getSmallFont().draw(batch, "Camera X: " + cameraX + ", Y: " + cameraY, 0, (float) Game.windowHeight - 40);

        batch.end();
    }

    public void update() {
        //Move camera with arrow keys
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cameraY += cameraSpeed * Gdx.graphics.getDeltaTime();
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cameraY -= cameraSpeed * Gdx.graphics.getDeltaTime();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cameraX -= cameraSpeed * Gdx.graphics.getDeltaTime();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cameraX += cameraSpeed * Gdx.graphics.getDeltaTime();
        }

        //Mouse click
        if (Gdx.input.isTouched(Input.Buttons.LEFT)) {
            if (firstClick) {
                //Prevent continuous placing unless left control is held
                if (!Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                    firstClick = false;
                }

                //Check if editor buttons have been clicked
                if (selectButton.isInBounds(Game.getMouseVector())) {//Default select mode
                    state = EditorState.SELECTING;
                } else if (tileButton.isInBounds(Game.getMouseVector())) {//List tiles
                    state = EditorState.LISTING_TILES;
                } else if (entityButton.isInBounds(Game.getMouseVector())) {//List entities
                    state = EditorState.LISTING_ENTITIES;
                } else if (growUpButton.isInBounds(Game.getMouseVector())) {//Increase map size upwards
                    mapTiles.add(0, new ArrayList<>(mapTiles.get(1).size()));
                    for (int i = 0; i < mapTiles.get(1).size(); i++) {
                        mapTiles.get(0).add(Tile.getTileFromID("fltl"));
                    }

                    //Shift all entities down one tile
                    for (Entity e : mapEntities) {
                        e.setY(e.getY() + tileSize);
                    }
                } else if (shrinkUpButton.isInBounds(Game.getMouseVector())) {//Decrease map size upwards (take off top)
                    if (mapTiles.size() > 1) {
                        mapTiles.remove(0);

                        //Shift all entities up one tile
                        for (Entity e : mapEntities) {
                            e.setY(e.getY() - tileSize);
                        }
                    }
                } else if (growDownButton.isInBounds(Game.getMouseVector())) {//Increase map size downwards
                    mapTiles.add(new ArrayList<>(mapTiles.get(0).size()));
                    for (int i = 0; i < mapTiles.get(0).size(); i++) {
                        mapTiles.get(mapTiles.size() - 1).add(Tile.getTileFromID("fltl"));
                    }
                } else if (shrinkDownButton.isInBounds(Game.getMouseVector())) {//Decrease map size downwards (take from bottom)
                    if (mapTiles.size() > 1) {
                        mapTiles.remove(mapTiles.size() - 1);
                    }
                } else if (growLeftButton.isInBounds(Game.getMouseVector())) {//Increase map size leftwards
                    for (int i = 0; i < mapTiles.size(); i++) {
                        mapTiles.get(i).add(0, Tile.getTileFromID("fltl"));
                    }

                    //Shift all entities right one tile
                    for (Entity e : mapEntities) {
                        e.setX(e.getX() + tileSize);
                    }
                } else if (shrinkLeftButton.isInBounds(Game.getMouseVector())) {//Decrease map size leftwards (take from left)
                    if (mapTiles.get(0).size() > 1) {
                        for (int i = 0; i < mapTiles.size(); i++) {
                            mapTiles.get(i).remove(0);
                        }

                        //Shift all entities left one tile
                        for (Entity e : mapEntities) {
                            e.setX(e.getX() - tileSize);
                        }
                    }
                } else if (growRightButton.isInBounds(Game.getMouseVector())) {//Increase map size rightwards
                    for (int i = 0; i < mapTiles.size(); i++) {
                        mapTiles.get(i).add(Tile.getTileFromID("fltl"));
                    }
                } else if (shrinkRightButton.isInBounds(Game.getMouseVector())) {//Decrease map size rightwards (take from right)
                    for (int i = 0; i < mapTiles.size(); i++) {
                        mapTiles.get(i).remove(mapTiles.get(i).size() - 1);
                    }
                } else if (state == EditorState.PLACING_TILES || state == EditorState.PLACING_ENTITIES) {//Place tile or entity
                    float mouseX = (((int)(Game.getMouseVector().x + (cameraX % tileSize)) / tileSize) * tileSize) - (cameraX % tileSize) + 1;
                    float mouseY = (((((int)(Game.windowHeight - Game.getMouseVector().y + (cameraY % tileSize)) / tileSize) + 1) * tileSize) - (cameraY % tileSize)) + 1;

                    int tileX = (int)(mouseX + cameraX) / tileSize;
                    int tileY = (int)(mouseY + cameraY) / tileSize;

                    if (state == EditorState.PLACING_TILES) {
                        if (tileX >= 0 && tileX < mapTiles.get(0).size() && tileY >= 0 && tileY < mapTiles.size()
                            && !mapTiles.get(tileY).get(tileX).getID().equals(tileTypes[currentType].id)) {
                            mapTiles.get(tileY).set(tileX, Tile.getTileFromID(tileTypes[currentType].id));
                        }
                    } else {
                        if (tileX >= 0 && tileX < mapTiles.get(0).size() && tileY >= 0 && tileY < mapTiles.size()) {
                            mapEntities.add(Entity.getEntityFromID(entityTypes[currentType].id, tileX, tileY));
                        }
                    }
                }

                //Check if a tile or entity has been chosen from the list
                if (state == EditorState.LISTING_TILES) {
                    for (int i = 0; i < tileTypes.length; i++) {
                        if (Game.getMouseVector().x > Game.windowWidth / 10f + (70 * Game.graphicsScale * (i % 22))
                            && Game.getMouseVector().x < Game.windowWidth / 10f + (70 * Game.graphicsScale * (i % 22)) + tileSize){
                            if (Game.getMouseVector().y > Game.windowHeight / 1.2f - (70 * Game.graphicsScale * (i / 22))
                                && Game.getMouseVector().y < Game.windowHeight / 1.2f - (70 * Game.graphicsScale * (i / 22)) + tileSize){
                                state = EditorState.PLACING_TILES;
                                currentType = i;
                            }
                        }
                    }
                } else if (state == EditorState.LISTING_ENTITIES) {
                    for (int i = 0; i < entityTypes.length; i++) {
                        if (Game.getMouseVector().x > Game.windowWidth / 10f + (70 * Game.graphicsScale * (i % 22))
                            && Game.getMouseVector().x < Game.windowWidth / 10f + (70 * Game.graphicsScale * (i % 22)) + tileSize){
                            if (Game.getMouseVector().y > Game.windowHeight / 1.2f - (70 * Game.graphicsScale * (i / 22))
                                && Game.getMouseVector().y < Game.windowHeight / 1.2f - (70 * Game.graphicsScale * (i / 22)) + tileSize){
                                state = EditorState.PLACING_ENTITIES;
                                currentType = i;
                            }
                        }
                    }
                }
            }
        } else {
            firstClick = true;
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

        selectButton = new MenuButton(Load.getImages()[1], 0, 0, tileSize, tileSize);
        tileButton = new MenuButton(Load.getFloors()[0], tileSize, 0, tileSize, tileSize);
        entityButton = new MenuButton(Load.getImages()[2], tileSize * 2, 0, tileSize, tileSize);

        growUpButton = new MenuButton(Load.getImages()[3], tileSize * ((viewWidthTiles / 2) - 1), Game.windowHeight - tileSize, tileSize, tileSize);
        shrinkUpButton = new MenuButton(Load.getImages()[4], tileSize * (viewWidthTiles / 2), Game.windowHeight - tileSize, tileSize, tileSize);
        growDownButton = new MenuButton(Load.getImages()[5], tileSize * ((viewWidthTiles / 2) - 1), 0, tileSize, tileSize);
        shrinkDownButton = new MenuButton(Load.getImages()[6], tileSize * (viewWidthTiles / 2), 0, tileSize, tileSize);
        growLeftButton = new MenuButton(Load.getImages()[7], 0, tileSize * ((viewHeightTiles / 2) + 1), tileSize, tileSize);
        shrinkLeftButton = new MenuButton(Load.getImages()[8], 0,tileSize * (viewHeightTiles / 2), tileSize, tileSize);
        growRightButton = new MenuButton(Load.getImages()[9], Game.windowWidth - tileSize, tileSize * ((viewHeightTiles / 2) + 1), tileSize, tileSize);
        shrinkRightButton = new MenuButton(Load.getImages()[10], Game.windowWidth - tileSize,tileSize * (viewHeightTiles / 2), tileSize, tileSize);
    }

    public void loadMap(String path) {
        Map map = LoadMap.loadMapFromFile(Gdx.files.internal(path));

        if (map == null) {
            Game.scene = Game.Scene.MENU;
            throw new NullPointerException();
        }

        //Create ArrayList from map data so the level can change size easily
        mapTiles = new ArrayList<>(map.getTiles().length);
        for (int i = 0; i < map.getTiles().length; i++) {
            mapTiles.add(new ArrayList<>(map.getTiles()[0].length));

            for (int i2 = 0; i2 < map.getTiles()[0].length; i2++) {
                mapTiles.get(i).add(map.getTile(i2, i));
            }
        }

        mapEntities = new ArrayList<>(map.getEntities());

        sortEntities();

        cameraX = 0;
        cameraY = -tileSize;
    }

    public void sortEntities() {
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
        for (Entity e : mapEntities) {
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

        mapEntities.clear();
        mapEntities.addAll(walkables);
    }

    //Because tiles and entities are not defined apart from instances, a list is necessary for the editor
    public void registerElements() {
        tileTypes = new ListItem[]{
            new ListItem("void", Load.getImages()[0]),
            new ListItem("fltl", Load.getFloors()[0]),
            new ListItem("flgr", Load.getFloors()[1]),
            new ListItem("flmt", Load.getFloors()[2]),
            new ListItem("wltl", Load.getWalls()[0]),
            new ListItem("wlgr", Load.getWalls()[1]),
            new ListItem("wlmt", Load.getWalls()[2]),
        };

        entityTypes = new ListItem[]{
            new ListItem("papr", Load.getImages()[1]),
            new ListItem("bxwd", Load.getImages()[2]),
            new ListItem("bxmt", Load.getImages()[3]),
            new ListItem("btc0", Load.getImages()[4]),
        };
    }

    public void saveMap() {

    }

}
