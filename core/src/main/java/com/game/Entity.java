package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
ply# - player character (slimes)
bxwd - wooden box
bxmt - metal box
bxc# - coloured box
btc# - circle button
bts# - square button
btt# - timer button
btd# - diamond button
gate - rainbow gate
fld# - coloured force field gate
cana - cannon (2 second delay)
canb - cannon (3 second delay)
canc - cannon (4 second delay)
cand - cannon (5 second delay)
frbl - fireball (moving)
frpl - fireball (still)
 */

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

    public Entity(String id, TextureAnimation[] animations, float x, float y) {
        this.id = id;
        this.animations = animations;
        currentAnimation = animations[0];
        this.x = x;
        this.y = y;
    }

    public abstract void update(PlayScene play);

    public abstract void onCollision(PlayScene play, Entity collidingEntity);

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
}
