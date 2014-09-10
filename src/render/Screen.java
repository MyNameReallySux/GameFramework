package render;

import game.Game;
import util.physics.Matrix3x3f;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Game Framework
 * Created by MyNameReallySux on 8/20/2014.
 * Copyright 2014©
 */

public class Screen {
    public Game game;

    public float worldWidth, worldHeight;
    public float screenWidth, screenHeight;
    float scaleX, scaleY;
    Point2D.Float center;
    Matrix3x3f viewport;

    public Screen(Game game, float worldWidth, float worldHeight){
        this.game = game;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        resetViewport();
    }

    public void setWorldSize(float worldWidth, float worldHeight){
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public void resetViewport(){
        screenWidth = game.getWidth() - 1;
        screenHeight = game.getHeight() - 1;
        scaleX = screenWidth / worldWidth;
        scaleY = screenHeight / worldHeight;
        center = new Point2D.Float(screenWidth / 2 ,screenHeight / 2);
        viewport = Matrix3x3f.scale(scaleX, -scaleY);
        viewport = viewport.mul(Matrix3x3f.translate(center.x, center.y));
    }

    public Matrix3x3f getViewport(){
        return viewport;
    }

    public void onResize() {
        game.setResizing(true);
        Dimension size = game.getSize();
        int viewportWidth = size.width * 99 / 100;
        int viewportHeight = size.height  * 99 / 100;
        int viewportX = (size.width - viewportWidth) / 2;
        int viewportY = (size.height - viewportHeight) / 2;

        int newWidth = viewportWidth;
        int newHeight = (int)(viewportWidth * worldHeight / worldWidth);
        if (newHeight > viewportHeight){
            newWidth = (int)(viewportHeight * worldWidth / worldHeight);
            newHeight = viewportHeight;
        }

        viewportX += (viewportWidth - newWidth) / 2;
        viewportY += (viewportHeight - newHeight) / 2;

        game.setLocation(viewportX, viewportY);
        game.setSize(newWidth, newHeight);
        game.setResizing(false);

    }
}
