package com.game;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

/**
 * Superclass of player-controlled slimes
 */
public abstract class Player extends Entity {

    public Player(String id, TextureAnimation[] animations, float x, float y) {
        super(id, animations, x, y, 300);
    }

    @Override
    public void update(PlayScene play) {

        /*
        1. If not moving, Get input direction
            2. Check if new tile is valid
            3. If valid move, set new destination
        4. Move towards destination
        5. If at destination, stop moving & fix position
         */

        if (currentDirection == Direction.IDLE) {
            setCurrentAnimation(0);

            //Get input and set appropriate values
            //Don't move if the camera mode button is pressed or this is not the current player
            if (!Gdx.input.isKeyPressed(Game.inputList[6]) && play.playerEntity == this) {
                tileX = (int) Math.floor(getX() / play.getTileSize());
                tileY = (int) Math.floor(getY() / play.getTileSize());

                if (Gdx.input.isKeyPressed(Game.inputList[2])
                    && canMove(play, tileX, tileY - 1)) {
                    newTileX = tileX;
                    newTileY = tileY - 1;
                    currentDirection = Direction.UP;
                    play.entityMap.get(newTileY).get(newTileX).add(this);
                    setCurrentAnimation(1);
                } else if (Gdx.input.isKeyPressed(Game.inputList[3])
                    && canMove(play, tileX, tileY + 1)) {
                    newTileX = tileX;
                    newTileY = tileY + 1;
                    currentDirection = Direction.DOWN;
                    play.entityMap.get(newTileY).get(newTileX).add(this);
                    setCurrentAnimation(2);
                } else if (Gdx.input.isKeyPressed(Game.inputList[4])
                    && canMove(play, tileX - 1, tileY)) {
                    newTileX = tileX - 1;
                    newTileY = tileY;
                    currentDirection = Direction.LEFT;
                    play.entityMap.get(newTileY).get(newTileX).add(this);
                    setCurrentAnimation(3);
                } else if (Gdx.input.isKeyPressed(Game.inputList[5])
                    && canMove(play, tileX + 1, tileY)) {
                    newTileX = tileX + 1;
                    newTileY = tileY;
                    currentDirection = Direction.RIGHT;
                    play.entityMap.get(newTileY).get(newTileX).add(this);
                    setCurrentAnimation(4);
                }
            }
        }

        //If not idle, move towards the new tile
        if (currentDirection != Direction.IDLE) {
            updateMovementMod(play);

            deltaMovement = speed * movementMod * Gdx.graphics.getDeltaTime();

            switch (currentDirection) {
                case UP -> {
                    setY(getY() - deltaMovement);
                    play.setCameraY(play.getCameraY() - deltaMovement);

                    //Check if the destination has been reached
                    if (getY() - (newTileY * play.getTileSize()) <= 0) {
                        currentDirection = Direction.IDLE;
                        setY(newTileY * play.getTileSize());
                        if (play.playerEntity == this) {
                            play.centreCameraOnPlayer();
                        }
                        play.entityMap.get(tileY).get(tileX).remove(this);
                        //Check for special tiles
                        updateTileEffect(play, Direction.UP);
                    }
                }
                case DOWN -> {
                    setY(getY() + deltaMovement);
                    play.setCameraY(play.getCameraY() + deltaMovement);

                    //Check if the destination has been reached
                    if (getY() - (newTileY * play.getTileSize()) >= 0) {
                        currentDirection = Direction.IDLE;
                        setY(newTileY * play.getTileSize());
                        if (play.playerEntity == this) {
                            play.centreCameraOnPlayer();
                        }
                        play.entityMap.get(tileY).get(tileX).remove(this);
                        //Check for special tiles
                        updateTileEffect(play, Direction.DOWN);
                    }
                }
                case LEFT -> {
                    setX(getX() - deltaMovement);
                    play.setCameraX(play.getCameraX() - deltaMovement);

                    //Check if the destination has been reached
                    if (getX() - (newTileX * play.getTileSize()) <= 0) {
                        currentDirection = Direction.IDLE;
                        setX(newTileX * play.getTileSize());
                        if (play.playerEntity == this) {
                            play.centreCameraOnPlayer();
                        }
                        play.entityMap.get(tileY).get(tileX).remove(this);
                        //Check for special tiles
                        updateTileEffect(play, Direction.LEFT);
                    }
                }
                case RIGHT -> {
                    setX(getX() + deltaMovement);
                    play.setCameraX(play.getCameraX() + deltaMovement);

                    //Check if the destination has been reached
                    if (getX() - (newTileX * play.getTileSize()) >= 0) {
                        currentDirection = Direction.IDLE;
                        setX(newTileX * play.getTileSize());
                        if (play.playerEntity == this) {
                            play.centreCameraOnPlayer();
                        }
                        play.entityMap.get(tileY).get(tileX).remove(this);
                        //Check for special tiles
                        updateTileEffect(play, Direction.RIGHT);
                    }
                }
            }
        }
    }

    /**
     * @param newTileX
     * @param newTileY
     * @return whether the new coordinates are a valid spot to move.
     */
    public boolean canMove(PlayScene play, int newTileX, int newTileY) {

        //Check for out of bounds movement
        if (newTileX < 0
            || newTileX >= play.map.getTiles()[0].length
            || newTileY < 0
            || newTileY >= play.map.getTiles().length) {
            return false;
        }

        //Check for walls and liquids
        if (play.map.getTile(newTileX, newTileY).getID().startsWith("wl")
            || play.map.getTile(newTileX, newTileY).getID().startsWith("lq")
            || play.map.getTile(newTileX, newTileY).getID().equals("void")) {
            return false;
        }

        ArrayList<Entity> tileEntities = play.getTileEntities(newTileX, newTileY);
        for (int i = 0; i < tileEntities.size(); i++) {
            //Check for entities that block movement
            if (tileEntities.get(i).getID().startsWith("fld")
                || tileEntities.get(i).getID().startsWith("ffg")
                || tileEntities.get(i).getID().startsWith("can")
                || tileEntities.get(i).getID().startsWith("ply")) {
                return false;
            }

            //Check if the entity in the tile can't be moved
            if (tileEntities.get(i).getID().startsWith("bx")) {
                //If the entity is moving, do nothing
                if (tileEntities.get(i).currentDirection != Direction.IDLE) {
                    return false;
                }

                int xMod = tileX - newTileX;
                int yMod = tileY - newTileY;
                int extraTileX = newTileX - xMod;
                int extraTileY = newTileY - yMod;

                if (extraTileX < 0
                    || extraTileX >= play.map.getTiles()[0].length
                    || extraTileY < 0
                    || extraTileY >= play.map.getTiles().length) {
                    return false;
                }

                //If there is something in the way, do nothing
                //Check for walls
                if (play.map.getTile(extraTileX, extraTileY).getID().startsWith("wl")) {
                    return false;
                }

                //Check for entities
                ArrayList<Entity> entities = play.getTileEntities(extraTileX, extraTileY);

                for (Entity e : entities) {
                    //Whitelist of passable entities
                    if (!(e.getID().startsWith("bt")
                        || e.getID().startsWith("fr"))) {
                        return false;
                    }
                }

                //Push the entity
                if (xMod > 0) {
                    tileEntities.get(i).move(play, Direction.LEFT);
                } else if (xMod < 0) {
                    tileEntities.get(i).move(play, Direction.RIGHT);
                } else if (yMod > 0) {
                    tileEntities.get(i).move(play, Direction.UP);
                } else if (yMod < 0) {
                    tileEntities.get(i).move(play, Direction.DOWN);
                }
            }
        }

        return true;
    }

    @Override
    public void signal(PlayScene play, Entity sourceEntity, String signal) {

    }

    public static class PlayerGreen extends Player {
        public PlayerGreen(float x, float y) {
            super("ply5",
                new TextureAnimation[]{
                    new TextureAnimation(Load.getAnimations()[0].getTextureRegion(), 0,16,16,2, 4, 0.5f),
                    new TextureAnimation(Load.getAnimations()[0].getTextureRegion(), 16,16,16,3, 4, 0.1f),
                    new TextureAnimation(Load.getAnimations()[0].getTextureRegion(), 32,16,16,3, 4, 0.1f),
                    new TextureAnimation(Load.getAnimations()[0].getTextureRegion(), 48,16,16,3, 4, 0.1f),
                    new TextureAnimation(Load.getAnimations()[0].getTextureRegion(), 64,16,16,3, 4, 0.1f),
                }, x, y);
        }
    }
}
