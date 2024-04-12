package com.game;

public class BoxWood extends PushableEntity {
    public BoxWood(float x, float y) {
        super("bxwd",
            Load.getImages()[2], x, y, 300);
    }

    @Override
    public void signal(PlayScene play, Entity sourceEntity, String signal) {

    }
}
