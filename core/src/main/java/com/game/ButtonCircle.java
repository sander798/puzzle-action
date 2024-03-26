package com.game;

public abstract class ButtonCircle extends Button {

    public ButtonCircle(String id, Image[] images, float x, float y, int channel) {
        super(id, images, x, y, channel);
    }

    @Override
    public void update(PlayScene play) {
        //If the button is being pressed, activate the channel
        if (isBeingPressed(play)) {
            play.buttonChannels[channel] = true;
            img = images[1];
        } else {
            img = images[0];
        }
    }

    @Override
    public void onCollision(PlayScene play, Entity collidingEntity) {

    }

    public static class ButtonCircleAll extends ButtonCircle {
        public ButtonCircleAll(float x, float y) {
            super("btc0", new Image[]{Load.getImages()[4], Load.getImages()[5]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 0);
        }
    }

    public static class ButtonCircleWhite extends ButtonCircle {
        public ButtonCircleWhite(float x, float y) {
            super("btc1", new Image[]{Load.getImages()[4], Load.getImages()[5]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 1);
        }
    }

    public static class ButtonCircleRed extends ButtonCircle {
        public ButtonCircleRed(float x, float y) {
            super("btc2", new Image[]{Load.getImages()[4], Load.getImages()[5]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 2);
        }
    }

    public static class ButtonCircleOrange extends ButtonCircle {
        public ButtonCircleOrange(float x, float y) {
            super("btc3", new Image[]{Load.getImages()[4], Load.getImages()[5]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 3);
        }
    }

    public static class ButtonCircleYellow extends ButtonCircle {
        public ButtonCircleYellow(float x, float y) {
            super("btc4", new Image[]{Load.getImages()[4], Load.getImages()[5]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 4);
        }
    }

    public static class ButtonCircleGreen extends ButtonCircle {
        public ButtonCircleGreen(float x, float y) {
            super("btc5", new Image[]{Load.getImages()[4], Load.getImages()[5]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 5);
        }
    }

    public static class ButtonCircleBlue extends ButtonCircle {
        public ButtonCircleBlue(float x, float y) {
            super("btc6", new Image[]{Load.getImages()[4], Load.getImages()[5]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 6);
        }
    }

    public static class ButtonCircleIndigo extends ButtonCircle {
        public ButtonCircleIndigo(float x, float y) {
            super("btc7", new Image[]{Load.getImages()[4], Load.getImages()[5]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 7);
        }
    }

    public static class ButtonCircleViolet extends ButtonCircle {
        public ButtonCircleViolet(float x, float y) {
            super("btc8", new Image[]{Load.getImages()[4], Load.getImages()[5]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 8);
        }
    }

    public static class ButtonCircleBrown extends ButtonCircle {
        public ButtonCircleBrown(float x, float y) {
            super("btc9", new Image[]{Load.getImages()[4], Load.getImages()[5]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 9);
        }
    }

}
