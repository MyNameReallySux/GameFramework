package drawing;

import util.Log;
import util.physics.Vector2f;

import java.awt.*;

/**
 * Game Framework
 * Created by MyNameReallySux on 9/3/2014.
 * Copyright 2014©
 */

public class Polygon extends Geometric implements Log {
    public Polygon(Vector2f[] shape) {
        this(shape, new Vector2f(), 0, (float)Math.toRadians(90));
    }

    public Polygon(Vector2f[] shape, Vector2f position) {
        this(shape, position, 0, 0);
    }

    public Polygon(Vector2f[] shape, float rotation, float rotationDelta) {
        this(shape, new Vector2f(), rotation, rotationDelta);
    }

    public Polygon(Vector2f[] shape, Vector2f position, float rotation, float rotationDelta) {
        this.base = shape;
        this.position = new Vector2f();
        this.world = new Vector2f[base.length];
        this.position = position;
        this.velocity = new Vector2f(0, 0);
        this.rotation = (float)Math.toRadians(rotation);
        this.rotationDelta = (float)Math.toRadians(rotationDelta);
        this.movable = true;
        this.gravity = true;
    }

    @Override
    public void draw(Graphics g) {
        drawPolygon(g);
    }

    public void update(double delta){
        if(movable){
            if(gravity){
                velocity.y -= 9.8f * delta;
            }
            position.x += velocity.x * delta;
            position.y += velocity.y * delta;
            log(position.toString());
            for(int i = 0; i < base.length; i++){
                world[i] = new Vector2f(base[i]);
            }
        }
        if(rotating){
            rotation += rotationDelta * delta;
        }
    }

    public void drawPolygon(Graphics g){
        Vector2f P;
        Vector2f S = world[world.length - 1];
        for (Vector2f vector : world) {
            P = vector;
            g.drawLine((int) S.x, (int) S.y, (int) P.x, (int) P.y);
            S = P;
        }
    }
}
