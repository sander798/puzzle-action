package com.game;

public abstract class ButtonSquare extends Button {

    public boolean activated;

    public ButtonSquare(String id, Image img, float x, float y, int channel) {
        super(id, img, x, y, channel);
        activated = false;
    }

    @Override
    public void update(PlayScene play) {
        //If the button is not already activated, check if it should be
        if (!activated && isBeingPressed(play)) {
            activated = true;
            play.buttonChannels[channel] = true;

            //Set all square buttons of this channel to activated
            for (int i = 0; i < play.map.getEntities().size(); i++) {
                if (play.map.getEntities().get(i).getID().startsWith("bts")) {
                    if (((ButtonSquare)play.map.getEntities().get(i)).channel == this.channel) {
                        ((ButtonSquare)play.map.getEntities().get(i)).activated = true;
                    }
                }
            }
        }
    }

    @Override
    public void onCollision(PlayScene play, Entity collidingEntity) {

    }

    public static class ButtonSquareAll extends ButtonSquare {
        public ButtonSquareAll(float x, float y) {
            super("bts0", Load.getImages()[3], x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 0);
        }
    }

    public static class ButtonSquareWhite extends ButtonSquare {
        public ButtonSquareWhite(float x, float y) {
            super("bts1", Load.getImages()[3], x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 1);
        }
    }

    public static class ButtonSquareRed extends ButtonSquare {
        public ButtonSquareRed(float x, float y) {
            super("bts2", Load.getImages()[3], x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 2);
        }
    }

    public static class ButtonSquareOrange extends ButtonSquare {
        public ButtonSquareOrange(float x, float y) {
            super("bts3", Load.getImages()[3], x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 3);
        }
    }

    public static class ButtonSquareYellow extends ButtonSquare {
        public ButtonSquareYellow(float x, float y) {
            super("bts4", Load.getImages()[3], x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 4);
        }
    }

    public static class ButtonSquareGreen extends ButtonSquare {
        public ButtonSquareGreen(float x, float y) {
            super("bts5", Load.getImages()[3], x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 5);
        }
    }

    public static class ButtonSquareBlue extends ButtonSquare {
        public ButtonSquareBlue(float x, float y) {
            super("bts6", Load.getImages()[3], x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 6);
        }
    }

    public static class ButtonSquareIndigo extends ButtonSquare {
        public ButtonSquareIndigo(float x, float y) {
            super("bts7", Load.getImages()[3], x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 7);
        }
    }

    public static class ButtonSquareViolet extends ButtonSquare {
        public ButtonSquareViolet(float x, float y) {
            super("bts8", Load.getImages()[3], x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 8);
        }
    }

    public static class ButtonSquareBrown extends ButtonSquare {
        public ButtonSquareBrown(float x, float y) {
            super("bts9", Load.getImages()[3], x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 9);
        }
    }
}
