package drawing;

import game.GameFramework;
import util.Log;
import util.physics.Matrix3x3f;
import util.physics.Vector2f;

import java.awt.*;

/**
 * Game Framework
 * Created by MyNameReallySux on 9/3/2014.
 * Copyright 2014©
 */

public abstract class Geometric implements Log {
    protected Vector2f base[], world[];
    protected Vector2f position;
    protected Vector2f velocity;
    protected float rotation, rotationDelta;

    protected boolean movable, gravity;

    public Vector2f getPosition(){
        return position;
    }

    public float getPositionX() {
        return position.x;
    }

    public float getPositionY(){
        return position.y;
    }

    public Vector2f getVelocity(){
        return velocity;
    }

    public float getVelocityX(){
        return velocity.x;
    }

    public float getVelocityY(){
        return velocity.y;
    }

    public void setVelocity(float x, float y){
        this.velocity = new Vector2f(x, y);
    }

    public Vector2f[] getBasePolygon(){
        return base;
    }

    public Vector2f getBasePolygon(int i){
        return base[i];
    }

    public Vector2f[] getWorldPolygon(){
        return world;
    }

    public Vector2f getWorldPolygon(int i){
        return world[i];
    }

    public float getRotation(){
        return rotation;
    }

    public float getRotationRate(){
        return rotationDelta;
    }

    public void setRotation(float rotation){
        this.rotation = rotation;
    }

    public void setRotationRate(float rotationDelta){
        this.rotationDelta = rotationDelta;
    }

    public void setRotationData(float rotation, float rotationDelta){
        this.rotation = rotation;
        this.rotationDelta = rotationDelta;
    }

    public void setMovable(boolean movable){
        this.movable = movable;
    }
    public void setAffectedByGravity(boolean gravity){
        this.gravity = gravity;
    }

    public abstract void update(double delta);
    public abstract void draw(Graphics g);


    public void drawBox(Graphics g, float size){
        g.drawRect((int)(position.x - size / 2), (int)(position.y - size / 2), (int)size, (int)size);
    }

    public void drawCircle(Graphics g, float size){
        g.drawOval((int) position.x, (int) position.y, (int) size, (int) size);
    }


    public void applyTransformations(){
        Matrix3x3f matrix = Matrix3x3f.identity();
        matrix = matrix.mul(Matrix3x3f.scale(0.5f));
        matrix = matrix.mul(Matrix3x3f.rotate(rotation));
        matrix = matrix.mul(Matrix3x3f.translate(position.x, position.y));

        for(int i = 0; i < base.length; i++){
            world[i] = base[i].mul(matrix);
            world[i] = world[i].mul(GameFramework.Viewport());
        }
    }
}
