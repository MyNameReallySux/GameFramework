package modules.tests;

import drawing.DynamicPoint;
import drawing.Polygon;
import game.GameFramework;
import modules.GameModule;
import util.physics.Matrix3x3f;
import util.physics.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * Game Framework
 * Created by MyNameReallySux on 9/3/2014.
 * Copyright 2014©
 */

public class CannonTest extends GameModule {
    private Polygon cannon;
    private DynamicPoint bullet;

    public CannonTest(GameFramework game) {
        super(game, "CannonTest");
    }

    @Override
    public boolean initialize() {
        this.cannon = new Polygon(new Vector2f[]{
                new Vector2f(0f, 0.05f),
                new Vector2f(0.75f, 0.05f),
                new Vector2f(0.75f, -0.05f),
                new Vector2f(0f, -0.05f)
        }, new Vector2f(-1f, -1f), 45f, 90f);

        cannon.setMovable(false);
        return true;
    }

    @Override
    public void input() {
        if(game.getKeyboard().keyDown(KeyEvent.VK_LEFT)){
            cannon.setRotation((float) (cannon.getRotation() + cannon.getRotationRate() * game.getClock().getDelta()));
        }
        if(game.getKeyboard().keyDown(KeyEvent.VK_RIGHT)){
            cannon.setRotation((float)(cannon.getRotation() - cannon.getRotationRate() * game.getClock().getDelta()));
        }
        if(game.getKeyboard().keyDownOnce(KeyEvent.VK_SPACE)){
            Matrix3x3f matrix = Matrix3x3f.rotate(cannon.getRotation());
            matrix = matrix.mul(Matrix3x3f.translate(7f, 0f));

            Vector2f velocity = new Vector2f().mul(matrix);

            matrix = Matrix3x3f.translate(0.75f, 0f);
            matrix = matrix.mul(Matrix3x3f.rotate(cannon.getRotation()));
            matrix = matrix.mul(Matrix3x3f.translate(cannon.getPositionX(), cannon.getPositionY()));
            matrix = matrix.mul(GameFramework.Screen().getViewport());
            bullet = new DynamicPoint(new Vector2f().mul(matrix), velocity);
        }
    }

    @Override
    public void update(double delta) {
        if(cannon != null){
            cannon.applyTransformations();
            cannon.update(delta);
        }
        if(bullet != null){
            bullet.applyTransformations();
            bullet.update(delta);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        cannon.drawPolygon(g);
        if(bullet != null){
            bullet.drawBox(g, 5);
        }
    }
}
