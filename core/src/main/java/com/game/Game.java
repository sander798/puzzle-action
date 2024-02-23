package com.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.swing.JOptionPane;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Game extends ApplicationAdapter {

    public static final String gameTitle = "Slimy Solvers";

    public static long initTime;//Time the game was started at

    public static boolean hasLoaded = false;//Whether the game's assets have been loaded

    public static int windowWidth;//Starting resolution for the game
    public static int windowHeight;

    public static int graphicsScale = 1;//Scale by which to multiply all visuals

    public static final int BASE_TILE_SIZE = 64;//Size of map tiles before scaling

    public static int[] inputList = { //TODO: Set based on config file
        Input.Keys.ENTER,
        Input.Keys.ESCAPE,
        Input.Keys.W,
        Input.Keys.S,
        Input.Keys.A,
        Input.Keys.D,
        Input.Keys.SHIFT_LEFT,
    };

    private static Vector2 mouseVector = new Vector2();
    private static OrthographicCamera camera;
    private static Viewport viewport;
    public static Stage stage;
    private SpriteBatch batch;
    private ShapeRenderer shape;

    public static enum Scene {
        MENU,
        PLAY,
        EDITOR,
    }

    public static Scene scene;

    //private RenderLoading renderLoading = new RenderLoading();
    private MenuScene menu;
    private PlayScene play;

    public Game(int windowWidth, int windowHeight) {
        Game.windowWidth = windowWidth;
        Game.windowHeight = windowHeight;
        scene = Scene.MENU;
    }

    @Override
    public void create() {
        initTime = System.currentTimeMillis();

        batch = new SpriteBatch();
        shape = new ShapeRenderer();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, windowWidth, windowHeight);
        viewport = new FitViewport(windowWidth, windowHeight, camera);

        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(Game.stage);
    }

    @Override
    public void render() {
        mouseVector.set(Gdx.input.getX(), Gdx.input.getY());
        stage.screenToStageCoordinates(mouseVector);
        camera.update();
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setProjectionMatrix(camera.combined);
        shape.setProjectionMatrix(camera.combined);
        shape.setAutoShapeType(true);

        if (!hasLoaded) {//Loads all resources
            Load.loadAssets(initTime);

            menu = new MenuScene();
            play = new PlayScene();

            hasLoaded = true;
        } else {

            ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);//Erases the previous frame

            if (scene == Scene.MENU) {
                menu.render(batch, shape);
            } else if (scene == Scene.PLAY) {
                play.render(batch, shape);
            }
        }
    }

    public static Vector2 getMouseVector() {
        return mouseVector;
    }

    @Override
    public void resize(int width, int height) {

        camera.setToOrtho(false, width, height);
        viewport = new FitViewport(width, height, camera);

        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(Game.stage);

        Game.windowHeight = height;
        Game.windowWidth = width;
    }

    public static void crash(String crashMessage) {
        JOptionPane.showMessageDialog(null, crashMessage, "Crash!", JOptionPane.ERROR_MESSAGE);
        Gdx.app.exit();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shape.dispose();
        Load.releaseResources();
    }

}
