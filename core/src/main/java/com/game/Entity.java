package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.HashMap;

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
    public Image img;
    private TextureAnimation[] animations;
    private TextureAnimation currentAnimation;

    public Direction currentDirection;
    public int speed;
    public int tileX, tileY, newTileX, newTileY;
    public float deltaMovement, movementMod;

    public HashMap<String, String> properties;

    public Entity(String id, Image img, float x, float y, int speed) {
        this.id = id;
        this.img = img;
        this.x = x * Game.BASE_TILE_SIZE;
        this.y = y * Game.BASE_TILE_SIZE;
        currentDirection = Direction.IDLE;
        this.speed = speed;

        tileX = (int) Math.floor(getX() / (Game.BASE_TILE_SIZE * Game.graphicsScale));
        tileY = (int) Math.floor(getY() / (Game.BASE_TILE_SIZE * Game.graphicsScale));
    }

    public Entity(String id, TextureAnimation[] animations, float x, float y, int speed) {
        this.id = id;
        this.animations = animations;
        currentAnimation = animations[0];
        this.x = x * Game.BASE_TILE_SIZE;
        this.y = y * Game.BASE_TILE_SIZE;
        currentDirection = Direction.IDLE;
        this.speed = speed;

        tileX = (int) Math.floor(getX() / (Game.BASE_TILE_SIZE * Game.graphicsScale));
        tileY = (int) Math.floor(getY() / (Game.BASE_TILE_SIZE * Game.graphicsScale));
    }

    public abstract void update(PlayScene play);

    public abstract void signal(PlayScene play, Entity sourceEntity, String signal);

    public abstract boolean canMove(PlayScene play, int newTileX, int newTileY);

    /**
     * Per-frame movement update method
     * @param play
     */
    public void updateMovement(PlayScene play) {
        //If not idle, move towards the new tile
        if (currentDirection != Direction.IDLE) {
            updateMovementMod(play);

            deltaMovement = speed * movementMod * Gdx.graphics.getDeltaTime();

            switch (currentDirection) {
                case UP -> {
                    setY(getY() - deltaMovement);

                    //Check if the destination has been reached
                    if (getY() - (newTileY * play.getTileSize()) <= 0) {
                        currentDirection = Direction.IDLE;
                        setY(newTileY * play.getTileSize());
                        play.entityMap.get(tileY).get(tileX).remove(this);
                        //Check for special tiles
                        updateTileEffect(play, Direction.UP);
                    }
                }
                case DOWN -> {
                    setY(getY() + deltaMovement);

                    //Check if the destination has been reached
                    if (getY() - (newTileY * play.getTileSize()) >= 0) {
                        currentDirection = Direction.IDLE;
                        setY(newTileY * play.getTileSize());
                        play.entityMap.get(tileY).get(tileX).remove(this);
                        //Check for special tiles
                        updateTileEffect(play, Direction.DOWN);
                    }
                }
                case LEFT -> {
                    setX(getX() - deltaMovement);

                    //Check if the destination has been reached
                    if (getX() - (newTileX * play.getTileSize()) <= 0) {
                        currentDirection = Direction.IDLE;
                        setX(newTileX * play.getTileSize());
                        play.entityMap.get(tileY).get(tileX).remove(this);
                        //Check for special tiles
                        updateTileEffect(play, Direction.LEFT);
                    }
                }
                case RIGHT -> {
                    setX(getX() + deltaMovement);

                    //Check if the destination has been reached
                    if (getX() - (newTileX * play.getTileSize()) >= 0) {
                        currentDirection = Direction.IDLE;
                        setX(newTileX * play.getTileSize());
                        play.entityMap.get(tileY).get(tileX).remove(this);
                        //Check for special tiles
                        updateTileEffect(play, Direction.RIGHT);
                    }
                }
            }
        }
    }

    /**
     * Attempts to set a new entity movement direction
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

    public TextureRegion getTextureRegion() {
        return img == null ? currentAnimation.getCurrentFrame(true) : img.getTextureRegion();
    }

    public int getEntityWidth() {
        return img == null ? currentAnimation.getScaledWidth() : img.getScaledWidth();
    }

    public int getEntityHeight() {
        return img == null ? currentAnimation.getScaledHeight() : img.getScaledHeight();
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

    public void addProperty(String name, String value) {
        if (properties == null) {
            properties = PropertiesList.generatePropertiesMap(new String[]{name}, new String[]{value});
            return;
        }

        properties.put(name, value);
    }

    /**
     * Updates entity movement speed modifier based on current coordinates
     * @param play
     */
    public void updateMovementMod(PlayScene play) {
        //Get movement based on current tile
        switch (play.map.getTile(
            (int) Math.floor((getX() + (play.getTileSize() / 2)) / play.getTileSize()),
            (int) Math.floor((getY() + (play.getTileSize() / 2)) / play.getTileSize())).getID()) {
            case "flia":
                movementMod = 2.5f;
                break;
            default:
                movementMod = 1f;
        }
    }

    /**
     * Executes any custom tile effects on entities based on current coordinates, such as forced movement
     * @param play
     * @param lastDirection
     */
    public void updateTileEffect(PlayScene play, Direction lastDirection) {
        switch (play.map.getTile(
            (int) Math.floor((getX() + (play.getTileSize() / 2)) / play.getTileSize()),
            (int) Math.floor((getY() + (play.getTileSize() / 2)) / play.getTileSize())).getID()) {
            case "flia"://ice
                move(play, lastDirection);
                break;
        }
    }

    public static Entity getEntityFromID(String entityID, int x, int y) {
        /*
        [Colour channels:
            0 - ALL (only used by buttons)
            1 - white
            2 - red
            3 - orange
            4 - yellow
            5 - green
            6 - blue
            7 - indigo
            8 - violet
            9 - brown > conveyors? (only used by buttons)
        ]

        Entity IDs:
        [# denotes a colour number]

        papr - paper message
        btc# - circle button (active when something is on top of it)
        bts# - square button (active always once pressed)
        btd# - diamond button (toggles activation)
        btt# - timer button (active for a time)
        tel# - teleporter

        mana - mana charge
        bonu - bonus charge

        ply# - player character (slimes)
        cana - cannon (2 second delay)
        canb - cannon (3 second delay)
        canc - cannon (4 second delay)
        cand - cannon (5 second delay)
        frbl - fireball (moving)
        frpl - fireball (still)

        bxwd - wooden box
        bxmt - metal box
        bxc# - coloured box
        goal - rainbow goal
        gate - mana gate
        ffg# - coloured force field gate
         */

        switch (entityID) {
            case "papr":
                return new Paper(x, y);
            case "btc0":
                return new ButtonCircle.ButtonCircleAll(x, y);
            case "btc1":
                return new ButtonCircle.ButtonCircleWhite(x, y);
            case "btc2":
                return new ButtonCircle.ButtonCircleRed(x, y);
            case "btc3":
                return new ButtonCircle.ButtonCircleOrange(x, y);
            case "btc4":
                return new ButtonCircle.ButtonCircleYellow(x, y);
            case "btc5":
                return new ButtonCircle.ButtonCircleGreen(x, y);
            case "btc6":
                return new ButtonCircle.ButtonCircleBlue(x, y);
            case "btc7":
                return new ButtonCircle.ButtonCircleIndigo(x, y);
            case "btc8":
                return new ButtonCircle.ButtonCircleViolet(x, y);
            case "btc9":
                return new ButtonCircle.ButtonCircleBrown(x, y);
            case "bts0":
                return new ButtonSquare.ButtonSquareAll(x, y);
            case "bts1":
                return new ButtonSquare.ButtonSquareWhite(x, y);
            case "bts2":
                return new ButtonSquare.ButtonSquareRed(x, y);
            case "bts3":
                return new ButtonSquare.ButtonSquareOrange(x, y);
            case "bts4":
                return new ButtonSquare.ButtonSquareYellow(x, y);
            case "bts5":
                return new ButtonSquare.ButtonSquareGreen(x, y);
            case "bts6":
                return new ButtonSquare.ButtonSquareBlue(x, y);
            case "bts7":
                return new ButtonSquare.ButtonSquareIndigo(x, y);
            case "bts8":
                return new ButtonSquare.ButtonSquareViolet(x, y);
            case "bts9":
                return new ButtonSquare.ButtonSquareBrown(x, y);
            case "btd0":
                return new ButtonDiamond.ButtonDiamondAll(x, y);
            case "btd1":
                return new ButtonDiamond.ButtonDiamondWhite(x, y);
            case "btd2":
                return new ButtonDiamond.ButtonDiamondRed(x, y);
            case "btd3":
                return new ButtonDiamond.ButtonDiamondOrange(x, y);
            case "btd4":
                return new ButtonDiamond.ButtonDiamondYellow(x, y);
            case "btd5":
                return new ButtonDiamond.ButtonDiamondGreen(x, y);
            case "btd6":
                return new ButtonDiamond.ButtonDiamondBlue(x, y);
            case "btd7":
                return new ButtonDiamond.ButtonDiamondIndigo(x, y);
            case "btd8":
                return new ButtonDiamond.ButtonDiamondViolet(x, y);
            case "btd9":
                return new ButtonDiamond.ButtonDiamondBrown(x, y);
            case "btt0":
                return new ButtonTimer.ButtonTimerAll(x, y);
            case "btt1":
                return new ButtonTimer.ButtonTimerWhite(x, y);
            case "btt2":
                return new ButtonTimer.ButtonTimerRed(x, y);
            case "btt3":
                return new ButtonTimer.ButtonTimerOrange(x, y);
            case "btt4":
                return new ButtonTimer.ButtonTimerYellow(x, y);
            case "btt5":
                return new ButtonTimer.ButtonTimerGreen(x, y);
            case "btt6":
                return new ButtonTimer.ButtonTimerBlue(x, y);
            case "btt7":
                return new ButtonTimer.ButtonTimerIndigo(x, y);
            case "btt8":
                return new ButtonTimer.ButtonTimerViolet(x, y);
            case "btt9":
                return new ButtonTimer.ButtonTimerBrown(x, y);
            case "ply5":
                return new Player.PlayerGreen(x, y);
            case "bxwd":
                return new BoxWood(x, y);
            case "bxmt":
                return new BoxMetal(x, y);
        }

        return null;
    }
}
