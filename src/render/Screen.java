package render;

import game.GameFramework;
import util.physics.Matrix3x3f;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;

/**
 * Created by Shorty on 8/25/2014.
 */
public class Screen {
    public GameFramework game;

    public float worldWidth, worldHeight;
    public float screenWidth, screenHeight;
    float scaleX, scaleY;
    Point2D.Float center;
    Matrix3x3f viewport;

    public Screen(GameFramework game, float worldWidth, float worldHeight){
        this.game = game;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        resetViewport();
    }

    public void resetViewport(){
        this.screenWidth = game.getWidth() - 1;
        this.screenHeight = game.getHeight() - 1;
        this.scaleX = screenWidth / worldWidth;
        this.scaleY = screenHeight / worldHeight;
        center = new Point2D.Float(screenWidth / 2 ,screenHeight / 2);
        viewport = Matrix3x3f.scale(scaleX, -scaleY);
        viewport = viewport.mul(Matrix3x3f.translate(center.x, center.y));
    }

    public Matrix3x3f getViewport(){
        return viewport;
    }

    public void onResize(ComponentEvent e) {
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
