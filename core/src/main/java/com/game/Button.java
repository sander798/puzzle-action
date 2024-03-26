package com.game;

public abstract class Button extends Entity {

    public final int channel;

    public Button(String id, Image img, float x, float y, int channel) {
        super(id, img, x, y, 0);
        this.channel = channel;
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
