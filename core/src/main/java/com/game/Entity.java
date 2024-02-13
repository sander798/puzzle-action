package com.game;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;

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
ply# - player character
bxwd - wooden box
bxmt - metal box
bxc# - coloured box
btc# - circle button
bts# - square button
btt# - timer button
btd# - diamond button

 */

public abstract class Entity {

    private String id;
    private int x, y;
    private TextureAnimation[] animations;
    private TextureAnimation currentAnimation;
    private HashMap<String, Integer> properties;

    public Entity(String id, TextureAnimation[] animations, int x, int y) {
        this.id = id;
        this.animations = animations;
        currentAnimation = animations[0];
        this.x = x;
        this.y = y;
        properties = new HashMap<String, Integer>();
    }

    public abstract void onCollision(Entity collidingEntity);

    public abstract void onUpdate(float delta, Map map);

    public String getID() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TextureAnimation[] getAnimations() {
        return animations;
    }

    public TextureAnimation getCurrentAnimation() {
        return currentAnimation;
    }

    public HashMap<String, Integer> getProperties() {
        return properties;
    }

    public Integer setProperty(String propertyKey, int value) {
        return properties.put(propertyKey, value);
    }

    //////////////////////////////////////////////////////////////////////////////////
    // Entity Definitions
    //////////////////////////////////////////////////////////////////////////////////
    public static class PlayerGreen extends Entity {
        public PlayerGreen(int x, int y) {
            super("ply5",
                new TextureAnimation[]{
                    Load.getAnimations()[0],
                    Load.getAnimations()[1],
                    Load.getAnimations()[2],
                    Load.getAnimations()[3],
                    Load.getAnimations()[4],
                }, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE);
        }

        @Override
        public void onCollision(Entity collidingEntity) {

        }

        @Override
        public void onUpdate(float delta, Map map) {

        }
    }
}
