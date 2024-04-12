package com.game;

public class ButtonDiamond extends Button {

    public boolean activated;

    public ButtonDiamond(String id, Image[] images, float x, float y, int channel) {
        super(id, images, x, y, channel);
        activated = false;
    }

    @Override
    public void update(PlayScene play) {
        if (isBeingPressed(play)) {
            //Only activate if button has been released since it was last pressed
            if (justActivated) {
                toggle();
            }
        } else {
            justActivated = true;
        }

        if (activated) {
            play.buttonChannels[channel] = true;
        }
    }

    public void toggle() {
        activated = !activated;
        justActivated = false;

        if (activated) {
            img = images[1];
        } else {
            img = images[0];
        }
    }

    @Override
    public void signal(PlayScene play, Entity sourceEntity, String signal) {

    }

    public static class ButtonDiamondAll extends ButtonDiamond {
        public ButtonDiamondAll(float x, float y) {
            super("btd0", new Image[]{Load.getImages()[44], Load.getImages()[45]}, x, y, 0);
        }
    }

    public static class ButtonDiamondWhite extends ButtonDiamond {
        public ButtonDiamondWhite(float x, float y) {
            super("btd1", new Image[]{Load.getImages()[46], Load.getImages()[47]}, x, y, 1);
        }
    }

    public static class ButtonDiamondRed extends ButtonDiamond {
        public ButtonDiamondRed(float x, float y) {
            super("btd2", new Image[]{Load.getImages()[48], Load.getImages()[49]}, x, y, 2);
        }
    }

    public static class ButtonDiamondOrange extends ButtonDiamond {
        public ButtonDiamondOrange(float x, float y) {
            super("btd3", new Image[]{Load.getImages()[50], Load.getImages()[51]}, x, y, 3);
        }
    }

    public static class ButtonDiamondYellow extends ButtonDiamond {
        public ButtonDiamondYellow(float x, float y) {
            super("btd4", new Image[]{Load.getImages()[52], Load.getImages()[53]}, x, y, 4);
        }
    }

    public static class ButtonDiamondGreen extends ButtonDiamond {
        public ButtonDiamondGreen(float x, float y) {
            super("btd5", new Image[]{Load.getImages()[54], Load.getImages()[55]}, x, y, 5);
        }
    }

    public static class ButtonDiamondBlue extends ButtonDiamond {
        public ButtonDiamondBlue(float x, float y) {
            super("btd6", new Image[]{Load.getImages()[56], Load.getImages()[57]}, x, y, 6);
        }
    }

    public static class ButtonDiamondIndigo extends ButtonDiamond {
        public ButtonDiamondIndigo(float x, float y) {
            super("btd7", new Image[]{Load.getImages()[58], Load.getImages()[59]}, x, y, 7);
        }
    }

    public static class ButtonDiamondViolet extends ButtonDiamond {
        public ButtonDiamondViolet(float x, float y) {
            super("btd8", new Image[]{Load.getImages()[60], Load.getImages()[61]}, x, y, 8);
        }
    }

    public static class ButtonDiamondBrown extends ButtonDiamond {
        public ButtonDiamondBrown(float x, float y) {
            super("btd9", new Image[]{Load.getImages()[62], Load.getImages()[63]}, x, y, 9);
        }
    }
}
