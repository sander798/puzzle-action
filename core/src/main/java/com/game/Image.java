package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Image{

    private TextureRegion img;
    private int scale = 1;
    private int rotation = 0;

    /**
     * Creates an <code>Image</code> object.
     *
     * @param imgPath	a path to an image file
     * @param scale		the size multiplier to be applied when drawn
     */
    public Image(String imgPath, int scale){
        this.scale = scale;
        try{
            img = new TextureRegion(new Texture(imgPath));
            img.flip(false, false);
        }catch (Exception e){
            e.printStackTrace();
            Game.crash("Failed to load " + imgPath);
        }
    }

    /**
     * Creates an <code>Image</code> object, using only a portion of the source image file.
     *
     * @param imgPath	a path to an image file
     * @param topLeftX	the starting X coordinate on the source image file
     * @param topLeftY	the starting Y coordinate on the source image file
     * @param width		width of the selected portion
     * @param height	height of the selected portion
     * @param scale		the size multiplier to be applied when drawn
     */
    public Image(String imgPath, int topLeftX, int topLeftY, int width, int height, int scale){
        this.scale = scale;
        try{
            img = new TextureRegion(new Texture(imgPath), topLeftX, topLeftY, width, height);
            img.flip(false, false);
        }catch (Exception e){
            e.printStackTrace();
            Game.crash("Failed to load " + imgPath);
        }
    }

    /**
     * Creates an <code>Image</code> object from another <code>Image</code>, using only a portion of the source image file.
     *
     * @param image 	the source <code>Image</code>
     * @param topLeftX	the starting X coordinate on the source image file
     * @param topLeftY	the starting Y coordinate on the source image file
     * @param width		width of the selected portion
     * @param height	height of the selected portion
     * @param scale		the size multiplier to be applied when drawn
     */
    public Image(Image image, int topLeftX, int topLeftY, int width, int height, int scale){
        this.scale = scale;
        try{
            img = new TextureRegion(image.getTextureRegion(), topLeftX, topLeftY, width, height);
            img.flip(false, false);
        }catch (Exception e){
            e.printStackTrace();
            Game.crash("Failed to load " + image.getTextureRegion());
        }
    }

    /**
     * Creates an <code>Image</code> object, optionally flipping the image horizontally and/or vertically.
     *
     * @param imgPath		a path to an image file
     * @param scale			the size multiplier to be applied when drawn
     * @param horizontal	whether to flip the image horizontally
     * @param vertical		whether to flip the image vertically
     */
    public Image(String imgPath, int scale, boolean horizontal, boolean vertical){
        this.scale = scale;
        try{
            img = new TextureRegion(new Texture(imgPath));
            img.flip(horizontal, vertical);
        }catch (Exception e){
            e.printStackTrace();
            Game.crash("Failed to load " + imgPath);
        }
    }

    /**
     * Creates an <code>Image</code> object, using only a portion of the source image file and optionally flipping the image horizontally and/or vertically.
     *
     * @param imgPath		a path to an image file
     * @param topLeftX		the starting X coordinate on the source image file
     * @param topLeftY		the starting Y coordinate on the source image file
     * @param width			width of the selected portion
     * @param height		height of the selected portion
     * @param scale			the size multiplier to be applied when drawn
     * @param horizontal	whether to flip the image horizontally
     * @param vertical		whether to flip the image vertically
     */
    public Image(String imgPath, int topLeftX, int topLeftY, int width, int height, int scale, boolean horizontal, boolean vertical){
        this.scale = scale;
        try{
            img = new TextureRegion(new Texture(imgPath), topLeftX, topLeftY, width, height);
            img.flip(horizontal, vertical);
        }catch (Exception e){
            e.printStackTrace();
            Game.crash("Failed to load " + imgPath);
        }
    }

    /**
     * Creates an <code>Image</code> object, rotating the image by the given amount.
     *
     * @param imgPath	a path to an image file
     * @param scale		the size multiplier to be applied when drawn
     * @param rotation	clockwise rotation in degrees
     */
    public Image(String imgPath, int scale, int rotation) {
        this.scale = scale;
        this.rotation = rotation;
        try {
            img = new TextureRegion(new Texture(imgPath));
            img.flip(false, false);
        } catch (Exception e) {
            e.printStackTrace();
            Game.crash("Failed to load " + imgPath);
        }
    }

    /**
     * Sets the size multiplier used when drawing the <code>Image</code>.
     *
     * @param scale 	the size multiplier to be applied when drawn
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * Sets the rotation of the <code>Image</code>.
     *
     * @param rotation	clockwise rotation in degrees
     */
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    /**
     * @return  the {@link TextureRegion} used by the <code>Image</code>.
     */
    public TextureRegion getTextureRegion() {
        return img;
    }

    /**
     * @return  the size multiplier used when drawing the <code>Image</code>.
     */
    public int getScale() {
        return scale;
    }

    /**
     * @return  the width of the <code>Image</code>.
     */
    public int getWidth() {
        return img.getRegionWidth();
    }

    public int getScaledWidth() {
        return img.getRegionWidth() * scale;
    }

    /**
     * @return  the height of the <code>Image</code>.
     */
    public int getHeight() {
        return img.getRegionHeight();
    }

    public int getScaledHeight() {
        return img.getRegionHeight() * scale;
    }

    /**
     * @return  the rotation of the <code>Image</code> in degrees.
     */
    public int getRotation(){
        return rotation;
    }

    /**
     * Draws the <code>Image</code>. Note that batch.begin() must have already been called.
     *
     * @param batch	the <code>SpriteBatch</code> to be used
     * @param x
     * @param y
     */
    public void drawImage(SpriteBatch batch, int x, int y){
        batch.draw(img, x, y, 0, 0, getWidth(), getHeight(), scale, scale, rotation);
    }

    /**
     * Draws the <code>Image</code> with a custom width and height, ignoring scaling. Note that batch.begin() must have already been called.
     *
     * @param batch	the <code>SpriteBatch</code> to be used
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void drawImage(SpriteBatch batch, int x, int y, int width, int height){
        batch.draw(img, x, y, 0, 0, width, height, 1, 1, rotation);
    }

    /**
     * Draws the <code>Image</code> with a custom width, height, and a multiplier applied to these dimensions.
     * Note that batch.begin() must have already been called.
     *
     * @param batch	the <code>SpriteBatch</code> to be used
     * @param x
     * @param y
     * @param width
     * @param height
     * @param scaleX the multiplier to apply to the width
     * @param scaleY the multiplier to apply to the height
     */
    public void drawImage(SpriteBatch batch, int x, int y, int width, int height, int scaleX, int scaleY){
        batch.draw(img, x, y, 0, 0, width, height, scaleX, scaleY, rotation);
    }
}

