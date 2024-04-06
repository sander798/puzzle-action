package com.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MenuButton{

    private int width;
    private int height;
    private int x;
    private int y;
    private Image img;

    /**
     * Creates a new MenuButton using the supplied <code>Image</code>.
     * The coordinates specify the position of the bottom-left corner.
     *
     * @param img		the image to use when drawing the button
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public MenuButton(Image img, int x, int y, int width, int height){
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Creates a new MenuButton using the supplied file path to create an <code>Image</code>.
     * The coordinates specify the position of the bottom-left corner.
     *
     * @param imgPath	location of the image file to be loaded
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public MenuButton(String imgPath, int x, int y, int width, int height){
        img = new Image(imgPath, 1);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the x coordinate of the button.
     */
    public int getX(){
        return x;
    }

    /**
     * Returns the y coordinate of the button.
     */
    public int getY(){
        return y;
    }

    /**
     * Returns the width of the button.
     */
    public int getWidth(){
        return width;
    }

    /**
     * Returns the height of the button.
     */
    public int getHeight(){
        return height;
    }

    /**
     * Returns the <code>Image</code> used by the button.
     */
    public Image getImage(){
        return img;
    }

    /**
     * Returns whether the supplied {@link Vector2} is within the button's bounds.
     * @param mouseVector  the current mouse position
     */
    public boolean isInBounds(Vector2 mouseVector){
        if (mouseVector.x > x && mouseVector.x < x + width){
            if (mouseVector.y > y && mouseVector.y < y + height){
                return true;
            }
        }

        return false;
    }

    /**
     * Draws the button.
     * @param batch  the SpriteBatch to use when drawing
     * @param hoverImage the Image to draw over the button when hovered over
     */
    public void draw(SpriteBatch batch, Vector2 mouseVector, Image hoverImage){
        img.drawImage(batch, x, y, width, height);

        if (isInBounds(mouseVector)) {
            hoverImage.drawImage(batch, x, y, width, height);
        }
    }
}
