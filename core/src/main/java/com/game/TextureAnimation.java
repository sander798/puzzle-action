package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Wrapper class for Animation.
 */
public class TextureAnimation {

    private final int scale;
    private final int width, height;
    private final Animation<TextureRegion> animation;
    private float stateTime;

    public TextureAnimation(TextureRegion img, int topLeftY, int frameWidth, int frameHeight, int frameCount, int scale, float frameTime) {
        this.scale = scale;
        width = frameWidth;
        height = frameHeight;

        TextureRegion[] frames = new TextureRegion[frameCount];

        for (int x = 0; x < frameCount; x++) {
            frames[x] = new TextureRegion(img, x * frameWidth, topLeftY, frameWidth, frameHeight);
        }

        animation = new Animation<>(frameTime, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        stateTime = 0L;
    }

    public int getScale() {
        return scale;
    }

    public int getScaledWidth() {
        return scale * width;
    }

    public int getScaledHeight() {
        return scale * height;
    }

    public Animation<TextureRegion> getAnimation() {
        return animation;
    }

    public TextureRegion getCurrentFrame(boolean looping) {
        stateTime += Gdx.graphics.getDeltaTime();
        return animation.getKeyFrame(stateTime, looping);
    }

    public void reset() {
        stateTime = 0L;
    }
}
