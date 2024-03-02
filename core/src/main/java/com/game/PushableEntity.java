package com.game;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

/**
 * Superclass of all pushable entities
 */
public abstract class PushableEntity extends Entity {

    private Direction currentDirection;
    private int tileX, tileY, newTileX, newTileY;
    private float deltaMovement, movementMod;
    private final int speed;

    public PushableEntity(String id, TextureAnimation[] animations, float x, float y, int speed) {
        super(id, animations, x, y);
        this.speed = speed;
    }

    /**
     * Attempts to move the entity in the given direction
     * @param play
     * @param direction
     */
    public void move(PlayScene play, Direction direction) {
        if (currentDirection == Direction.IDLE) {
            //setCurrentAnimation(0);

            tileX = (int) Math.floor(getX() / play.getTileSize());
            tileY = (int) Math.floor(getY() / play.getTileSize());

            if (direction == Direction.UP
                && canMove(play, tileX, tileY - 1)) {
                newTileX = tileX;
                newTileY = tileY - 1;
                currentDirection = Direction.UP;
                //setCurrentAnimation(1);
            } else if (direction == Direction.DOWN
                && canMove(play, tileX, tileY + 1)) {
                newTileX = tileX;
                newTileY = tileY + 1;
                currentDirection = Direction.DOWN;
                //setCurrentAnimation(2);
            } else if (direction == Direction.LEFT
                && canMove(play, tileX - 1, tileY)) {
                newTileX = tileX - 1;
                newTileY = tileY;
                currentDirection = Direction.LEFT;
                //setCurrentAnimation(3);
            } else if (direction == Direction.RIGHT
                && canMove(play, tileX + 1, tileY)) {
                newTileX = tileX + 1;
                newTileY = tileY;
                currentDirection = Direction.RIGHT;
                //setCurrentAnimation(4);
            }

        }

        //If not idle, move towards the new tile
        if (currentDirection != Direction.IDLE) {
            updateMovementMod(play);

            deltaMovement = speed * movementMod * Gdx.graphics.getDeltaTime();

            switch (currentDirection) {
                case UP:
                    setY(getY() - deltaMovement);

                    //Check if the destination has been reached
                    if (getY() - (newTileY * play.getTileSize()) <= 0) {
                        currentDirection = Direction.IDLE;
                        setY(newTileY * play.getTileSize());
                        play.entityMap.get(tileY).get(tileX).remove(this);
                        play.entityMap.get(newTileY).get(newTileX).add(this);
                    }
                    break;
                case DOWN:
                    setY(getY() + deltaMovement);

                    //Check if the destination has been reached
                    if (getY() - (newTileY * play.getTileSize()) >= 0) {
                        currentDirection = Direction.IDLE;
                        setY(newTileY * play.getTileSize());
                        play.entityMap.get(tileY).get(tileX).remove(this);
                        play.entityMap.get(newTileY).get(newTileX).add(this);
                    }
                    break;
                case LEFT:
                    setX(getX() - deltaMovement);

                    //Check if the destination has been reached
                    if (getX() - (newTileX * play.getTileSize()) <= 0) {
                        currentDirection = Direction.IDLE;
                        setX(newTileX * play.getTileSize());
                        play.entityMap.get(tileY).get(tileX).remove(this);
                        play.entityMap.get(newTileY).get(newTileX).add(this);
                    }
                    break;
                case RIGHT:
                    setX(getX() + deltaMovement);

                    //Check if the destination has been reached
                    if (getX() - (newTileX * play.getTileSize()) >= 0) {
                        currentDirection = Direction.IDLE;
                        setX(newTileX * play.getTileSize());
                        play.entityMap.get(tileY).get(tileX).remove(this);
                        play.entityMap.get(newTileY).get(newTileX).add(this);
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
    public boolean canMove(PlayScene play, int newTileX, int newTileY) {

        //Check for out of bounds movement
        if (newTileX < 0
            || newTileX >= play.map.getTiles()[0].length
            || newTileY < 0
            || newTileY >= play.map.getTiles().length) {
            return false;
        }

        //Check for walls
        if (play.map.getTile(newTileX, newTileY).getID().startsWith("wl")) {
            return false;
        }

        ArrayList<Entity> tileEntities = play.getTileEntities(newTileX, newTileY);
        for (int i = 0; i < tileEntities.size(); i++) {
            //Check for entities that block movement
            if (tileEntities.get(i).getID().startsWith("fld")
                || tileEntities.get(i).getID().equals("gate")
                || tileEntities.get(i).getID().startsWith("can")){
                return false;
            }

            //Check if the entity in the tile can't be moved
            if (tileEntities.get(i).getID().startsWith("bx")){

                int xMod = tileX - newTileX;
                int yMod = tileY - newTileY;

                for (Entity e : play.getTileEntities(newTileX - xMod, newTileY - yMod)) {
                    //Whitelist of passable entities
                    if (!(e.getID().startsWith("bt")
                        || e.getID().startsWith("fr")
                        || e.getID().startsWith("ply"))){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Updates entity movement speed modifier based on given coordinates
     *
     * @param play
     */
    public void updateMovementMod(PlayScene play) {
        //Get movement based on current tile
        switch (play.map.getTile(
            (int) Math.floor((getX() + (play.getTileSize() / 2)) / play.getTileSize()),
            (int) Math.floor((getY() + (play.getTileSize() / 2)) / play.getTileSize())).getID()) {
            case "flgr":
                movementMod = 2.5f;
                break;
            default:
                movementMod = 1f;
        }
    }
}
