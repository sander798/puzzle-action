package com.game;

public class Paper extends Entity {

    private boolean isShowing;

    public Paper(float x, float y) {
        super("papr", Load.getImages()[3], x, y, 0);
        isShowing = false;
    }

    @Override
    public void update(PlayScene play) {
        if (play.playerEntity.tileX == tileX && play.playerEntity.tileY == tileY && !isShowing) {
            play.isShowingMessage = true;

            if (properties == null) {
                play.messageText = "No message data!";
                return;
            }

            play.messageText = properties.get("message");

        } else if (isShowing) {
            play.isShowingMessage = false;
            isShowing = false;
        }
    }

    @Override
    public void signal(PlayScene play, Entity sourceEntity, String signal) {

    }

    @Override
    public boolean canMove(PlayScene play, int newTileX, int newTileY) {
        return false;
    }
}
