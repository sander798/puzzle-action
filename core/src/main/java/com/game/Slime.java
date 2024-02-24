package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Superclass of player-controlled slimes
 */
public abstract class Slime extends Entity{

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
    private float deltaMovement;

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

        if (currentDirection == Direction.IDLE){
            setCurrentAnimation(0);

            //Get input and set appropriate values
            if (!Gdx.input.isKeyPressed(Game.inputList[6])) {
                tileX = (int)Math.floor(getX() / play.getTileSize());
                tileY = (int)Math.floor(getY() / play.getTileSize());

                if (Gdx.input.isKeyPressed(Game.inputList[2])) {
                    currentDirection = Direction.UP;
                    setCurrentAnimation(1);

                    if (canMove(tileX, tileY - 1)) {
                        newTileX = tileX;
                        newTileY = tileY - 1;
                    }
                } else if (Gdx.input.isKeyPressed(Game.inputList[3])) {
                    currentDirection = Direction.DOWN;
                    setCurrentAnimation(2);

                    if (canMove(tileX, tileY + 1)) {
                        newTileX = tileX;
                        newTileY = tileY + 1;
                    }
                } else if (Gdx.input.isKeyPressed(Game.inputList[4])) {
                    currentDirection = Direction.LEFT;
                    setCurrentAnimation(3);

                    if (canMove(tileX - 1, tileY)) {
                        newTileX = tileX - 1;
                        newTileY = tileY;
                    }
                } else if (Gdx.input.isKeyPressed(Game.inputList[5])) {
                    currentDirection = Direction.RIGHT;
                    setCurrentAnimation(4);

                    if (canMove(tileX + 1, tileY)) {
                        newTileX = tileX + 1;
                        newTileY = tileY;
                    }
                }
            }
        }

        //If not idle, move towards the new tile
        if (currentDirection != Direction.IDLE) {

            deltaMovement = PLAYER_SPEED * Gdx.graphics.getDeltaTime();

            switch (currentDirection) {
                case UP:
                    setY(getY() - deltaMovement);
                    play.setCameraY(play.getCameraY() - deltaMovement);
                    break;
                case DOWN:
                    setY(getY() + deltaMovement);
                    play.setCameraY(play.getCameraY() + deltaMovement);
                    break;
                case LEFT:
                    setX(getX() - deltaMovement);
                    play.setCameraX(play.getCameraX() - deltaMovement);
                    break;
                case RIGHT:
                    setX(getX() + deltaMovement);
                    play.setCameraX(play.getCameraX() + deltaMovement);
                    break;
            }
        }

        //If not idle, check if the destination has been reached
        if (currentDirection != Direction.IDLE) {

            if (currentDirection == Direction.UP && getY() - (newTileY * play.getTileSize()) <= 0) {
                currentDirection = Direction.IDLE;
                setX(newTileX * play.getTileSize());
                setY(newTileY * play.getTileSize());
            } else if (currentDirection == Direction.DOWN && getY() - (newTileY * play.getTileSize()) >= 0) {
                currentDirection = Direction.IDLE;
                setX(newTileX * play.getTileSize());
                setY(newTileY * play.getTileSize());
            } else if (currentDirection == Direction.LEFT && getX() - (newTileX * play.getTileSize()) <= 0) {
                currentDirection = Direction.IDLE;
                setX(newTileX * play.getTileSize());
                setY(newTileY * play.getTileSize());
            } else if (currentDirection == Direction.RIGHT && getX() - (newTileX * play.getTileSize()) >= 0) {
                currentDirection = Direction.IDLE;
                setX(newTileX * play.getTileSize());
                setY(newTileY * play.getTileSize());
            }
        }
    }

    /**
     * @param newTileX
     * @param newTileY
     * @return whether the new coordinates are a valid spot to move.
     */
    public boolean canMove(int newTileX, int newTileY) {
        return true;
    }

    public void onCollision(PlayScene play, Entity collidingEntity) {

    }
}
