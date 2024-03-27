package com.game;

import com.badlogic.gdx.Gdx;

public class ButtonTimer extends Button {

    public boolean activated;

    private float timerTime;

    public ButtonTimer(String id, Image[] images, float x, float y, int channel) {
        super(id, images, x, y, channel);
        activated = false;
        timerTime = 0;
    }

    @Override
    public void update(PlayScene play) {
        //If the button is not already activated, check if it should be
        if (activated) {
            timerTime += Gdx.graphics.getDeltaTime();

            if (timerTime >= images.length - 1) {
                activated = false;
                img = images[0];
            } else {
                img = images[(int)timerTime + 1];
            }
        }

        if (isBeingPressed(play)) {
            activated = true;
            timerTime = 0;
        }

        if (activated) {
            play.buttonChannels[channel] = true;
        }
    }

    @Override
    public void signal(PlayScene play, Entity sourceEntity, String signal) {

    }

    public static class ButtonTimerAll extends ButtonTimer {
        public ButtonTimerAll(float x, float y) {
            super("btt0", new Image[]{
                Load.getImages()[64],
                Load.getImages()[65],
                Load.getImages()[66],
                Load.getImages()[67],
                Load.getImages()[68],
                Load.getImages()[69],
                Load.getImages()[70]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 0);
        }
    }

    public static class ButtonTimerWhite extends ButtonTimer {
        public ButtonTimerWhite(float x, float y) {
            super("btt1", new Image[]{
                Load.getImages()[71],
                Load.getImages()[72],
                Load.getImages()[73],
                Load.getImages()[74],
                Load.getImages()[75],
                Load.getImages()[76],
                Load.getImages()[77]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 1);
        }
    }

    public static class ButtonTimerRed extends ButtonTimer {
        public ButtonTimerRed(float x, float y) {
            super("btt2", new Image[]{
                Load.getImages()[78],
                Load.getImages()[79],
                Load.getImages()[80],
                Load.getImages()[81],
                Load.getImages()[82],
                Load.getImages()[83],
                Load.getImages()[84]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 2);
        }
    }

    public static class ButtonTimerOrange extends ButtonTimer {
        public ButtonTimerOrange(float x, float y) {
            super("btt3", new Image[]{
                Load.getImages()[85],
                Load.getImages()[86],
                Load.getImages()[87],
                Load.getImages()[88],
                Load.getImages()[89],
                Load.getImages()[90],
                Load.getImages()[91]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 3);
        }
    }

    public static class ButtonTimerYellow extends ButtonTimer {
        public ButtonTimerYellow(float x, float y) {
            super("btt4", new Image[]{
                Load.getImages()[92],
                Load.getImages()[93],
                Load.getImages()[94],
                Load.getImages()[95],
                Load.getImages()[96],
                Load.getImages()[97],
                Load.getImages()[98]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 4);
        }
    }

    public static class ButtonTimerGreen extends ButtonTimer {
        public ButtonTimerGreen(float x, float y) {
            super("btt5", new Image[]{
                Load.getImages()[99],
                Load.getImages()[100],
                Load.getImages()[101],
                Load.getImages()[102],
                Load.getImages()[103],
                Load.getImages()[104],
                Load.getImages()[105]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 5);
        }
    }

    public static class ButtonTimerBlue extends ButtonTimer {
        public ButtonTimerBlue(float x, float y) {
            super("btt6", new Image[]{
                Load.getImages()[106],
                Load.getImages()[107],
                Load.getImages()[108],
                Load.getImages()[109],
                Load.getImages()[110],
                Load.getImages()[111],
                Load.getImages()[112]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 6);
        }
    }

    public static class ButtonTimerIndigo extends ButtonTimer {
        public ButtonTimerIndigo(float x, float y) {
            super("btt7", new Image[]{
                Load.getImages()[113],
                Load.getImages()[114],
                Load.getImages()[115],
                Load.getImages()[116],
                Load.getImages()[117],
                Load.getImages()[118],
                Load.getImages()[119]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 7);
        }
    }

    public static class ButtonTimerViolet extends ButtonTimer {
        public ButtonTimerViolet(float x, float y) {
            super("btt8", new Image[]{
                Load.getImages()[120],
                Load.getImages()[121],
                Load.getImages()[122],
                Load.getImages()[123],
                Load.getImages()[124],
                Load.getImages()[125],
                Load.getImages()[126]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 8);
        }
    }

    public static class ButtonTimerBrown extends ButtonTimer {
        public ButtonTimerBrown(float x, float y) {
            super("btt9", new Image[]{
                Load.getImages()[127],
                Load.getImages()[128],
                Load.getImages()[129],
                Load.getImages()[130],
                Load.getImages()[131],
                Load.getImages()[132],
                Load.getImages()[133]}, x * Game.BASE_TILE_SIZE, y * Game.BASE_TILE_SIZE, 9);
        }
    }
}
