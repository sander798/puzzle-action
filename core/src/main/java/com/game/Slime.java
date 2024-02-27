package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Superclass of player-controlled slimes
 */
public abstract class Slime extends Entity {

    private final int PLAYER_SPEED = 300;

    private enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        IDLE,
    }

    private Direction currentDirection;
    private int tileX, tileY, newTileX, newTileY;
    private float deltaMovement, movementMod;

    public Slime(String id, TextureAnimation[] animations, float x, float y) {
        super(id, animations, x, y);
        currentDirection = Direction.IDLE;
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
            if (!Gdx.input.isKeyPressed(Game.inputList[6])) {//Don't move if the camera mode button is pressed
                tileX = (int) Math.floor(getX() / play.getTileSize());
                tileY = (int) Math.floor(getY() / play.getTileSize());

                if (Gdx.input.isKeyPressed(Game.inputList[2])
                    && canMove(play.getMap(), tileX, tileY - 1)) {
                    newTileX = tileX;
                    newTileY = tileY - 1;
                    currentDirection = Direction.UP;
                    setCurrentAnimation(1);
                } else if (Gdx.input.isKeyPressed(Game.inputList[3])
                    && canMove(play.getMap(), tileX, tileY + 1)) {
                    newTileX = tileX;
                    newTileY = tileY + 1;
                    currentDirection = Direction.DOWN;
                    setCurrentAnimation(2);
                } else if (Gdx.input.isKeyPressed(Game.inputList[4])
                    && canMove(play.getMap(), tileX - 1, tileY)) {
                    newTileX = tileX - 1;
                    newTileY = tileY;
                    currentDirection = Direction.LEFT;
                    setCurrentAnimation(3);
                } else if (Gdx.input.isKeyPressed(Game.inputList[5])
                    && canMove(play.getMap(), tileX + 1, tileY)) {
                    newTileX = tileX + 1;
                    newTileY = tileY;
                    currentDirection = Direction.RIGHT;
                    setCurrentAnimation(4);
                }
            }
        }

        //If not idle, move towards the new tile
        if (currentDirection != Direction.IDLE) {
            updateMovementMod(play);

            deltaMovement = PLAYER_SPEED * movementMod * Gdx.graphics.getDeltaTime();

            switch (currentDirection) {
                case UP:
                    setY(getY() - deltaMovement);
                    play.setCameraY(play.getCameraY() - deltaMovement);

                    //Check if the destination has been reached
                    if (getY() - (newTileY * play.getTileSize()) <= 0) {
                        currentDirection = Direction.IDLE;
                        setY(newTileY * play.getTileSize());
                        play.centreCameraOnPlayer();
                    }
                    break;
                case DOWN:
                    setY(getY() + deltaMovement);
                    play.setCameraY(play.getCameraY() + deltaMovement);

                    //Check if the destination has been reached
                    if (getY() - (newTileY * play.getTileSize()) >= 0) {
                        currentDirection = Direction.IDLE;
                        setY(newTileY * play.getTileSize());
                        play.centreCameraOnPlayer();
                    }
                    break;
                case LEFT:
                    setX(getX() - deltaMovement);
                    play.setCameraX(play.getCameraX() - deltaMovement);

                    //Check if the destination has been reached
                    if (getX() - (newTileX * play.getTileSize()) <= 0) {
                        currentDirection = Direction.IDLE;
                        setX(newTileX * play.getTileSize());
                        play.centreCameraOnPlayer();
                    }
                    break;
                case RIGHT:
                    setX(getX() + deltaMovement);
                    play.setCameraX(play.getCameraX() + deltaMovement);

                    //Check if the destination has been reached
                    if (getX() - (newTileX * play.getTileSize()) >= 0) {
                        currentDirection = Direction.IDLE;
                        setX(newTileX * play.getTileSize());
                        play.centreCameraOnPlayer();
                    }
                    break;
            }
        }
    }

    /**
     * @param newTileX
     * @param newTileY
     * @return whether the new coordinates are a valid spot to move.
     */
    public boolean canMove(Map map, int newTileX, int newTileY) {

        //Check for out of bounds movement
        if (newTileX < 0
            || newTileX >= map.getTiles()[0].length
            || newTileY < 0
            || newTileY >= map.getTiles().length) {
            return false;
        }

        //Check for walls
        if (map.getTile(newTileX, newTileY).getID().startsWith("wl")) {
            return false;
        }

        //Check for pits / liquids

        //Check for entities that block movement

        //Check if the entity in the tile can't be moved

        return true;
    }

    /**
     * Updates entity movement speed modifier based on given coordinates
     *
     * @param play
     */
    public void updateMovementMod(PlayScene play) {
        //Get movement based on current tile
        switch (play.getMap().getTile(
            (int) Math.floor((getX() + (play.getTileSize() / 2)) / play.getTileSize()),
            (int) Math.floor((getY() + (play.getTileSize() / 2)) / play.getTileSize())).getID()) {
            case "flgr":
                movementMod = 2.5f;
                break;
            default:
                movementMod = 1f;
        }
    }

    public void onCollision(PlayScene play, Entity collidingEntity) {

    }
}
