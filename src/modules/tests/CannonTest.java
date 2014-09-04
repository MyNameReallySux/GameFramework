package modules.tests;

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
    private Vector2f[] cannon, cannonWorld;
    private float cannonRot, cannonRotRate;
    private Vector2f bullet, bulletWorld, bulletVelocity;


    public CannonTest(GameFramework game) {
        super(game, "CannonTest");
    }

    @Override
    public boolean initialize() {
        cannon = new Vector2f[]{
                new Vector2f(0f, 0.05f),
                new Vector2f(0.75f, 0.05f),
                new Vector2f(0.75f, -0.05f),
                new Vector2f(0f, -0.05f)
        };

        cannonWorld = new Vector2f[cannon.length];
        cannonRot = (float)Math.toRadians(45);
        cannonRotRate = (float)Math.toRadians(30);
        bulletVelocity = new Vector2f(0, 0);

        Matrix3x3f scale = Matrix3x3f.scale(0.5f, 0.5f);
        for(int i = 0; i < cannon.length; i++){
            cannon[i] = cannon[i].mul(scale);
        }

        return true;
    }

    @Override
    public void input(double delta) {
        if(game.getKeyboard().keyDown(KeyEvent.VK_LEFT)){
            cannonRot += cannonRotRate * delta;
        }
        if(game.getKeyboard().keyDown(KeyEvent.VK_RIGHT)){
            cannonRot -= cannonRotRate * delta;
        }

        if(game.getKeyboard().keyDownOnce(KeyEvent.VK_SPACE)) {
            Matrix3x3f matrix = Matrix3x3f.translate(5f, 0f);
            matrix = matrix.mul(Matrix3x3f.rotate(cannonRot));

            bulletVelocity = new Vector2f().mul(matrix);

            matrix = Matrix3x3f.translate(0.375f, 0f);
            matrix = matrix.mul(Matrix3x3f.rotate(cannonRot));
            matrix = matrix.mul(Matrix3x3f.translate(-1.0f, -1.0f));
            bullet = matrix.toVector(false);
        }
    }

    @Override
    public void update(double delta) {
        Matrix3x3f matrix = Matrix3x3f.identity();
        matrix = matrix.mul(Matrix3x3f.rotate(cannonRot));
        matrix = matrix.mul(Matrix3x3f.translate(-1.0f, -1.0f));

        for(int i = 0; i < cannon.length; i++){
            cannonWorld[i] = cannon[i].mul(matrix);
        }

        if(bullet != null){
            bulletVelocity.y += -9.8 * delta;
            bullet.x += bulletVelocity.x * delta;
            bullet.y += bulletVelocity.y * delta;
            bulletWorld = new Vector2f(bullet);
            if(bullet.y < -2.5f){
                bullet = null;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.drawString("<- to Raise, -> to Lower", 20, 50);
        g.drawString("Press Space to Fire Cannon", 20, 65);
        String vel = String.format("Velocity (%.2f, %.2f)", bulletVelocity.x, bulletVelocity.y);
        g.drawString(vel, 20, 80);



        for(int i = 0; i < cannon.length; i++){
            cannonWorld[i] = cannonWorld[i].mul(GameFramework.Screen().getViewport());
        }

        g.setColor(Color.WHITE);
        drawPolygon(g, cannonWorld);
        if(bullet != null){
            bulletWorld = bullet.mul(GameFramework.Screen().getViewport());
            g.drawOval((int)bulletWorld.x - 5, (int)bulletWorld.y - 5, 10, 10);
        }
    }

    public void drawPolygon(Graphics g, Vector2f[] world){
        Vector2f P;
        Vector2f S = world[world.length - 1];
        for (Vector2f vector : world) {
            P = vector;
            g.drawLine((int) S.x, (int) S.y, (int) P.x, (int) P.y);
            S = P;
        }
    }
}
