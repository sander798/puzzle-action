package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Entity {

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        IDLE,
    }

    private String id;
    private float x, y;
    private TextureAnimation[] animations;
    private TextureAnimation currentAnimation;

    public Direction currentDirection;
    public int speed;
    public int tileX, tileY, newTileX, newTileY;
    public float deltaMovement, movementMod;

    public Entity(String id, TextureAnimation[] animations, float x, float y, int speed) {
        this.id = id;
        this.animations = animations;
        currentAnimation = animations[0];
        this.x = x;
        this.y = y;
        currentDirection = Direction.IDLE;
        this.speed = speed;
    }

    public abstract void update(PlayScene play);

    public abstract void onCollision(PlayScene play, Entity collidingEntity);

    /**
     *
     * @param play
     */
    public void updateMovement(PlayScene play) {
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
                    }
                    break;
                case DOWN:
                    setY(getY() + deltaMovement);

                    //Check if the destination has been reached
                    if (getY() - (newTileY * play.getTileSize()) >= 0) {
                        currentDirection = Direction.IDLE;
                        setY(newTileY * play.getTileSize());
                        play.entityMap.get(tileY).get(tileX).remove(this);
                    }
                    break;
                case LEFT:
                    setX(getX() - deltaMovement);

                    //Check if the destination has been reached
                    if (getX() - (newTileX * play.getTileSize()) <= 0) {
                        currentDirection = Direction.IDLE;
                        setX(newTileX * play.getTileSize());
                        play.entityMap.get(tileY).get(tileX).remove(this);
                    }
                    break;
                case RIGHT:
                    setX(getX() + deltaMovement);

                    //Check if the destination has been reached
                    if (getX() - (newTileX * play.getTileSize()) >= 0) {
                        currentDirection = Direction.IDLE;
                        setX(newTileX * play.getTileSize());
                        play.entityMap.get(tileY).get(tileX).remove(this);
                    }
                    break;
            }
        }
    }

    /**
     * Attempts to move the entity in the given direction.
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
                play.entityMap.get(newTileY).get(newTileX).add(this);
                //setCurrentAnimation(1);
            } else if (direction == Direction.DOWN
                && canMove(play, tileX, tileY + 1)) {
                newTileX = tileX;
                newTileY = tileY + 1;
                currentDirection = Direction.DOWN;
                play.entityMap.get(newTileY).get(newTileX).add(this);
                //setCurrentAnimation(2);
            } else if (direction == Direction.LEFT
                && canMove(play, tileX - 1, tileY)) {
                newTileX = tileX - 1;
                newTileY = tileY;
                currentDirection = Direction.LEFT;
                play.entityMap.get(newTileY).get(newTileX).add(this);
                //setCurrentAnimation(3);
            } else if (direction == Direction.RIGHT
                && canMove(play, tileX + 1, tileY)) {
                newTileX = tileX + 1;
                newTileY = tileY;
                currentDirection = Direction.RIGHT;
                play.entityMap.get(newTileY).get(newTileX).add(this);
                //setCurrentAnimation(4);
            }
        }
    }

    public String getID() {
        return id;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public TextureAnimation[] getAnimations() {
        return animations;
    }

    public TextureAnimation getCurrentAnimation() {
        return currentAnimation;
    }

    public TextureRegion getCurrentAnimationFrame() {
        return currentAnimation.getCurrentFrame(true);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setCurrentAnimation(int index) {
        if (index >= 0 && index < animations.length) {
            currentAnimation = animations[index];
        }
    }

    /**
     * Updates entity movement speed modifier based on current coordinates
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

    public abstract boolean canMove(PlayScene play, int newTileX, int newTileY);
}
