package com.game;

public class BoxMetal extends PushableEntity {
    public BoxMetal(float x, float y) {
        super("bxmt",
            new TextureAnimation[]{
                new TextureAnimation(Load.getAnimations()[6].getTextureRegion(), 16, 4, 100f),
            }, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 300);
    }

    @Override
    public void onCollision(PlayScene play, Entity collidingEntity) {

    }
}
