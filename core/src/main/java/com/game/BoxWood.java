package com.game;

public class BoxWood extends PushableEntity {
    public BoxWood(float x, float y) {
        super("bxwd",
            Load.getImages()[2], x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 300);
    }

    @Override
    public void signal(PlayScene play, Entity sourceEntity, String signal) {

    }
}
