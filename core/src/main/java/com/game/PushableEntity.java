package com.game;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

/**
 * Superclass of all pushable entities
 */
public abstract class PushableEntity extends Entity {

    public PushableEntity(String id, TextureAnimation[] animations, float x, float y, int speed) {
        super(id, animations, x, y, speed);
    }

    /**
     * @param newTileX
     * @param newTileY
     * @return whether the new coordinates are a valid spot to move.
     */
    public boolean canMove(PlayScene play, int newTileX, int newTileY) {

        //Check for out of bounds movement
        if (newTileX < 0
            || newTileX >= play.map.getTiles()[0].length
            || newTileY < 0
            || newTileY >= play.map.getTiles().length) {
            return false;
        }

        //Check for walls
        if (play.map.getTile(newTileX, newTileY).getID().startsWith("wl")) {
            return false;
        }

        ArrayList<Entity> tileEntities = play.getTileEntities(newTileX, newTileY);
        for (int i = 0; i < tileEntities.size(); i++) {
            //Check for entities that block movement
            if (tileEntities.get(i).getID().startsWith("fld")
                || tileEntities.get(i).getID().equals("gate")
                || tileEntities.get(i).getID().startsWith("can")){
                return false;
            }

            //Check if the entity in the tile can't be moved
            if (tileEntities.get(i).getID().startsWith("bx")){

                int xMod = tileX - newTileX;
                int yMod = tileY - newTileY;

                for (Entity e : play.getTileEntities(newTileX - xMod, newTileY - yMod)) {
                    //Whitelist of passable entities
                    if (!(e.getID().startsWith("bt")
                        || e.getID().startsWith("fr")
                        || e.getID().startsWith("ply"))){
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
