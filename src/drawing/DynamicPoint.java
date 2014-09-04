package drawing;

import util.physics.Vector2f;

import java.awt.*;

/**
 * Game Framework
 * Created by MyNameReallySux on 9/3/2014.
 * Copyright 2014©
 */

public class DynamicPoint extends Geometric {
    public DynamicPoint(Vector2f startingPoint) {
        this(startingPoint, new Vector2f(1, 1));
    }

    public DynamicPoint(Vector2f startingPoint, Vector2f velocity) {
        this.base = new Vector2f[]{startingPoint};
        this.world = new Vector2f[base.length];
        this.position = new Vector2f(startingPoint.x, startingPoint.y);
        this.velocity = velocity;
    }

    @Override
    public void update(double delta) {
        velocity.y += -9.8f * delta;
        log(velocity.y + "");
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
        world[0] = new Vector2f(base[0]);
    }

    @Override
    public void draw(Graphics g) {

    }
}
