package com.game;

public class BoxWood extends PushableEntity {
    public BoxWood(float x, float y) {
        super("bxwd",
            new TextureAnimation[]{
                new TextureAnimation(Load.getAnimations()[5].getTextureRegion(), 16, 4, 100f),
            }, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 300);
    }

    @Override
    public void update(PlayScene play) {
        updateMovement(play);
    }

    @Override
    public void onCollision(PlayScene play, Entity collidingEntity) {

    }
}
