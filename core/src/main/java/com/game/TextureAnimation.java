package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Wrapper class for Animation.
 */
public class TextureAnimation {

    private final int scale;
    private int width, height;
    private Animation<TextureRegion> animation;

    public TextureAnimation(String imgPath, int frameWidth, int scale, float frameTime) {
        this.scale = scale;
        width = frameWidth;

        try {
            TextureRegion img = new TextureRegion(new Texture(imgPath));
            TextureRegion[] frames = new TextureRegion[img.getRegionWidth() / frameWidth];

            height = img.getRegionHeight();

            for (int x = 0; x * frameWidth < img.getRegionWidth(); x++) {
                frames[x] = new TextureRegion(img, x, 0, frameWidth, img.getRegionHeight());
            }

            animation = new Animation<TextureRegion>(frameTime, frames);
        } catch (Exception e) {
            e.printStackTrace();
            Game.crash("Failed to load " + imgPath);
        }
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

    public Animation getAnimation() {
        return animation;
    }
}
