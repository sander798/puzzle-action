package com.game;

public class SlimeGreen extends Slime {
    public SlimeGreen(float x, float y) {
        super("ply5",
            new TextureAnimation[]{
                new TextureAnimation(Load.getAnimations()[0].getTextureRegion(), 16, 4, 0.5f),
                new TextureAnimation(Load.getAnimations()[1].getTextureRegion(), 16, 4, 0.1f),
                new TextureAnimation(Load.getAnimations()[2].getTextureRegion(), 16, 4, 0.1f),
                new TextureAnimation(Load.getAnimations()[3].getTextureRegion(), 16, 4, 0.1f),
                new TextureAnimation(Load.getAnimations()[4].getTextureRegion(), 16, 4, 0.1f),
            }, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE);
    }
}
