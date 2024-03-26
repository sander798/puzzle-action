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

    public static class ButtonCircleAll extends ButtonCircle {
        public ButtonCircleAll(float x, float y) {
            super("btc0", Load.getImages()[3], x, y, 0);
        }
    }

    public static class ButtonCircleWhite extends ButtonCircle {
        public ButtonCircleWhite(float x, float y) {
            super("btc1", Load.getImages()[3], x, y, 1);
        }
    }

    public static class ButtonCircleRed extends ButtonCircle {
        public ButtonCircleRed(float x, float y) {
            super("btc2", Load.getImages()[3], x, y, 2);
        }
    }

    public static class ButtonCircleOrange extends ButtonCircle {
        public ButtonCircleOrange(float x, float y) {
            super("btc3", Load.getImages()[3], x, y, 3);
        }
    }

    public static class ButtonCircleYellow extends ButtonCircle {
        public ButtonCircleYellow(float x, float y) {
            super("btc4", Load.getImages()[3], x, y, 4);
        }
    }

    public static class ButtonCircleGreen extends ButtonCircle {
        public ButtonCircleGreen(float x, float y) {
            super("btc5", Load.getImages()[3], x, y, 5);
        }
    }

    public static class ButtonCircleBlue extends ButtonCircle {
        public ButtonCircleBlue(float x, float y) {
            super("btc6", Load.getImages()[3], x, y, 6);
        }
    }

    public static class ButtonCircleIndigo extends ButtonCircle {
        public ButtonCircleIndigo(float x, float y) {
            super("btc7", Load.getImages()[3], x, y, 7);
        }
    }

    public static class ButtonCircleViolet extends ButtonCircle {
        public ButtonCircleViolet(float x, float y) {
            super("btc8", Load.getImages()[3], x, y, 8);
        }
    }

    public static class ButtonCircleBrown extends ButtonCircle {
        public ButtonCircleBrown(float x, float y) {
            super("btc9", Load.getImages()[3], x, y, 9);
        }
    }

}
