package com.game;

public class Paper extends Entity {

    private boolean isShowing;

    public Paper(float x, float y) {
        super("papr", Load.getImages()[3], x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 0);
        tileX = (int) Math.floor(getX() / (Game.BASE_TILE_SIZE * Game.graphicsScale));
        tileY = (int) Math.floor(getY() / (Game.BASE_TILE_SIZE * Game.graphicsScale));
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
    public void onCollision(PlayScene play, Entity collidingEntity) {

    }

    @Override
    public boolean canMove(PlayScene play, int newTileX, int newTileY) {
        return false;
    }
}
