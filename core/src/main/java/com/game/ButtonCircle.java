package com.game;

public abstract class ButtonCircle extends Entity {

    public final int channel;

    public ButtonCircle(String id, Image img, float x, float y, int channel) {
        super(id, img, x, y, 0);
        this.channel = channel;
    }

    @Override
    public void update(PlayScene play) {
        //If the button channel is not already activated, check if this button activates it
        if (!play.buttonChannels[channel]) {
            play.buttonChannels[channel] = play.playerEntity.tileX == tileX && play.playerEntity.tileY == tileY;
        }
    }

    @Override
    public void onCollision(PlayScene play, Entity collidingEntity) {

    }

    @Override
    public boolean canMove(PlayScene play, int newTileX, int newTileY) {
        return false;
    }
}
