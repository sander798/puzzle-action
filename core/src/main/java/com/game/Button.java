package com.game;

public abstract class Button extends Entity {

    public final int channel;
    public final Image[] images;
    public boolean justActivated;

    public Button(String id, Image[] images, float x, float y, int channel) {
        super(id, images[0], x, y, 0);
        this.channel = channel;
        this.images = images;

        tileX = (int) Math.floor(getX() / (Game.BASE_TILE_SIZE * Game.graphicsScale));
        tileY = (int) Math.floor(getY() / (Game.BASE_TILE_SIZE * Game.graphicsScale));

        justActivated = false;
    }

    @Override
    public boolean canMove(PlayScene play, int newTileX, int newTileY) {
        return false;
    }

    public boolean isBeingPressed(PlayScene play) {
        for (int i = 0; i < play.getTileEntities(tileX, tileY).size(); i++) {
            if (play.getTileEntities(tileX, tileY).get(i).getID().startsWith("ply")) {
                return true;
            } else if (play.getTileEntities(tileX, tileY).get(i).getID().startsWith("bx")) {
                return true;
            }
        }

        return false;
    }
}
