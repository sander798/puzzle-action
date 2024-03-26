package com.game;

public class BoxMetal extends PushableEntity {
    public BoxMetal(float x, float y) {
        super("bxmt",
            Load.getImages()[3], x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 300);
    }

    @Override
    public void signal(PlayScene play, Entity sourceEntity, String signal) {

    }
}
