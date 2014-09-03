package modules.tests;

import game.GameFramework;
import modules.GameModule;
import util.physics.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by Shorty on 8/11/2014.
 */
public class TransformationTest extends GameModule {
    private Vector2f[] polygon;
    private Vector2f[] world;

    private float tx, ty;
    private float vx, vy;
    private float rot, rotStep;
    private float scale, scaleStep;
    private float sx, sxStep;
    private float sy, syStep;
    private boolean doTranslate, doScale, doRotate, doXShear, doYShear;


    public TransformationTest(GameFramework game) {
        super(game, "TransformationTest");
    }

    @Override
    public boolean initialize() {
        polygon = new Vector2f[]{
                new Vector2f(20, 0),
                new Vector2f(-20, 16),
                new Vector2f(0, 0),
                new Vector2f(-20, -16)
        };
        world = new Vector2f[polygon.length];
        reset();
        return true;
    }

    @Override
    public void input() {
        if(game.keyboard.keyDownOnce(KeyEvent.VK_R))
            doRotate = !doRotate;
        if(game.keyboard.keyDownOnce(KeyEvent.VK_S))
            doScale = !doScale;
        if(game.keyboard.keyDownOnce(KeyEvent.VK_T))
            doTranslate = !doTranslate;
        if(game.keyboard.keyDownOnce(KeyEvent.VK_X))
            doXShear = !doXShear;
        if(game.keyboard.keyDownOnce(KeyEvent.VK_Y))
            doYShear = !doYShear;
        if(game.keyboard.keyDownOnce(KeyEvent.VK_SPACE))
            reset();
    }

    @Override
    public void update(double delta) {
        for(int i = 0; i < polygon.length; i++){
            world[i] = new Vector2f(polygon[i]);
        }

        if(doScale){
            scale+= scaleStep;
            if(scale < 1 || scale > 5){
                scaleStep = -scaleStep;
            }
        }

        if(doRotate){
            rot += rotStep;
            if(rot < 0 || scale > 2*Math.PI){
                rotStep = -rotStep;
            }
        }

        if(doTranslate){
            tx += vx;
            if(tx < 0 || tx > game.getWidth()){
                vx = -vx;
            }

            ty += vy;
            if(ty < 0 || ty > game.getHeight()){
                vy = -vy;
            }
        }

        if(doXShear){
            sx += sxStep;
            if(Math.abs(sx) > 2){
                sxStep = -sxStep;
            }
        }

        if(doYShear){
            sy += syStep;
            if(Math.abs(sy) > 2){
                syStep = -syStep;
            }
        }

        for (Vector2f vector : world) {
            vector.shear(sx, sy);
            vector.scale(scale, scale);
            vector.rotate(rot);
            vector.translate(tx, ty);
        }
    }

    @Override
    public void render(Graphics g) {
        g.translate(20, 100);
        g.setFont(new Font("Courier New", Font.PLAIN, 12));
        g.setColor(Color.WHITE);
        g.drawString("Translate(T): " + doTranslate, 0, 0);
        g.drawString("Rotate(R):    " + doRotate, 0, 15);
        g.drawString("Scale(S): " + doScale, 0, 30);
        g.drawString("X-Shear(X): " + doXShear, 0, 55);
        g.drawString("Y-Shear(Y): " + doYShear, 0, 70);
        g.drawString("Press [SPACE] to reset ", 0, 100);
        g.translate(-20, -100);

        Vector2f s = world[world.length - 1];
        Vector2f p = null;
        for(int i = 0; i < world.length; i++){
            p = world[i];
            g.drawLine((int)s.x, (int)s.y, (int)p.x, (int)p.y);
            s = p;
        }
    }

    public void reset(){
        Dimension dimens = game.getDisplay().getCurrentDisplayMode().getSize();
        tx = (float)(dimens.getWidth() / 2);
        ty = (float)(dimens.getHeight() / 2);
        vx = vy = 1;
        rot = 0.0f;
        rotStep = (float)Math.toRadians(1.0);
        scale = 1.0f;
        scaleStep = 0.1f;
        sx = sy = 0.0f;
        sxStep = syStep = 0.01f;
        doRotate = doScale = doTranslate = doXShear = doYShear = false;
    }
}
