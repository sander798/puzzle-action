package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import javax.swing.*;

public class MenuScene {

    /**
     * TODO: Add mouse control to menus
     * TODO: Add option to go to map editor
     * TODO: Add player creation and selection
     * TODO: Add level selection
     */

    private int menuCursor;
    private String[] menuItems;
    private String titleMessage;

    //These denote the active sub-menu
    private enum MenuLevel {
        TOP,
        NEW_PLAYER,
        CHANGE_PLAYER,
        CHOOSE_CAMPAIGN,
        SELECT_LEVEL,
        CHOOSE_NEW_OR_EXISTING_MAP,
        CHOOSE_CUSTOM_MAP_TO_EDIT,
        CONFIRM_QUIT,
    };

    private final String[] topMenuItems = {
        "Continue",
        "New Game",
        "Player Selection",
        "Options",
        "Level Editor",
        "Quit",
    };

    private final String[] chooseNewOrExistingMapItems = {
        "Edit Existing Map",
        "Create New Map",
        "Go Back",
    };

    private final String[] confirmQuitItems = {
        "Yes",
        "No",
    };

    private MenuLevel menuLevel;

    public MenuScene() {
        menuCursor = 0;
        menuItems = topMenuItems;
        menuLevel = MenuLevel.TOP;
        titleMessage = Game.gameTitle;
    }

    public void render(SpriteBatch batch, ShapeRenderer shape) {
        update();

        batch.begin();

        Load.getMediumFont().draw(batch, titleMessage, (float)Gdx.graphics.getWidth() / 4, (float)Gdx.graphics.getHeight() / 2 + Load.getMediumFont().getLineHeight());

        for (int i = 0; i < menuItems.length; i++) {
            if (i == menuCursor) {
                Load.getSmallFont().draw(batch, ">" + menuItems[i] + "<", (float)Gdx.graphics.getWidth() / 4, (float)Gdx.graphics.getHeight() / 2 - Load.getSmallFont().getLineHeight() * i);
            } else {
                Load.getSmallFont().draw(batch, menuItems[i], (float)Gdx.graphics.getWidth() / 4, (float)Gdx.graphics.getHeight() / 2 - Load.getSmallFont().getLineHeight() * i);
            }
        }

        //Load.getSmallFont().draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, (float)Gdx.graphics.getHeight() - 10);

        if (menuLevel == MenuLevel.SELECT_LEVEL) {

        }

        batch.end();
    }

    public void update() {

        //Move menu cursor
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Game.inputList[2])) {
            menuCursor--;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Game.inputList[3])) {
            menuCursor++;
        }

        //Check menu cursor bounds
        if (menuCursor > menuItems.length - 1) {
            menuCursor = 0;
        } else if (menuCursor < 0) {
            menuCursor = menuItems.length - 1;
        }

        //Menu item selection
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Game.inputList[0])) {
            switch (menuLevel) {
                case TOP -> {
                    if (menuCursor == 0) {//Continue
                        Game.scene = Game.Scene.PLAY;
                    } else if (menuCursor == 1) {//New Game
                        Game.loadPlayMap("maps/testMap.ssm");
                        Game.scene = Game.Scene.PLAY;
                    } else if (menuCursor == 2) {//Change Player

                    } else if (menuCursor == 3) {//Options

                    } else if (menuCursor == 4) {//Level Editor
                        //Query for new level or loading old custom one
                        menuLevel = MenuLevel.CHOOSE_NEW_OR_EXISTING_MAP;
                        menuItems = chooseNewOrExistingMapItems;
                        titleMessage = "Level Editor";
                    } else if (menuCursor == 5) {//Quit
                        menuLevel = MenuLevel.CONFIRM_QUIT;
                        menuItems = confirmQuitItems;
                        titleMessage = "Are you sure you want to quit?";
                    }
                }
                case CHOOSE_NEW_OR_EXISTING_MAP -> {
                    if (menuCursor == 0) {//Edit existing map
                        //Open file browser
                        //TODO: Replace with in-game browser?
                        Gdx.graphics.setWindowedMode(Game.windowWidth - (Game.windowWidth / 12), Game.windowHeight - (Game.windowHeight / 12));

                    } else if (menuCursor == 1) {//New map
                        //Go to editor with template map
                        Game.loadEditorMap("maps/testMap.ssm");
                        Game.scene = Game.Scene.EDITOR;
                        Gdx.graphics.setWindowedMode(Game.windowWidth - (Game.windowWidth / 12), Game.windowHeight - (Game.windowHeight / 12));
                    } else if (menuCursor == 2) {//Go back
                        menuLevel = MenuLevel.TOP;
                        menuItems = topMenuItems;
                        titleMessage = Game.gameTitle;
                    }
                }
                case CONFIRM_QUIT -> {
                    if (menuCursor == 0) {//Yes
                        Gdx.app.exit();
                    } else if (menuCursor == 1) {//No
                        menuLevel = MenuLevel.TOP;
                        menuItems = topMenuItems;
                        titleMessage = Game.gameTitle;
                    }
                }
            }

            menuCursor = 0;
        }
    }
}
