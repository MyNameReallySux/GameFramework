package modules;

import game.GameFramework;
import util.physics.Matrix3x3f;
import util.physics.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Created by Shorty on 8/23/2014.
 */
public class MatrixTransformationTest extends GameModule {
    private float mercuryRot, mercuryDelta;
    private float venusRot, venusDelta;
    private float earthRot, earthDelta;
    private float marsRot, marsDelta;
    private float moonRot, moonDelta;
    private boolean showStars;
    private int[] stars;

    public MatrixTransformationTest(GameFramework game) {
        super(game, "MatrixTransformationTest");
    }

    @Override
    public boolean initialize() {
        mercuryDelta = (float)Math.toRadians(0.5);
        venusDelta = (float)Math.toRadians(0.4);
        earthDelta = (float)Math.toRadians(0.2);
        marsDelta = (float)Math.toRadians(0.1);
        moonDelta = (float)Math.toRadians(1.5);
        showStars = true;
        stars = new int[1000];
        Random random = new Random();
        for(int i = 0; i < stars.length -1; i+=2){
            stars[i] = random.nextInt(game.getWidth());
            stars[i+1] = random.nextInt(game.getHeight());
        }
        return true;
    }

    @Override
    public void input() {
        if(game.getKeyboard().keyDownOnce(KeyEvent.VK_SPACE)){
            showStars = !showStars;
        }
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Graphics g) {
        Matrix3x3f sunMatrix = Matrix3x3f.identity();
        sunMatrix = sunMatrix.mul(Matrix3x3f.translate(game.getWidth() / 2, game.getHeight() / 2));

        Vector2f sun = new Vector2f().mul(sunMatrix);

        g.setColor(Color.YELLOW);
        g.fillOval((int)sun.x - 25, (int)sun.y - 25, 50, 50);

        Matrix3x3f mercuryMatrix = Matrix3x3f.translate(game.getWidth() / 12, game.getHeight() / 12);
        mercuryMatrix = mercuryMatrix.mul(Matrix3x3f.rotate(mercuryRot));
        mercuryMatrix = mercuryMatrix.mul(sunMatrix);
        mercuryRot += mercuryDelta;

        Vector2f mercury = new Vector2f().mul(mercuryMatrix);

        g.setColor(Color.GRAY);
        g.fillOval((int)mercury.x - 3, (int)mercury.y - 3, 6, 6);

        Matrix3x3f venusMatrix = Matrix3x3f.translate(game.getWidth() / 8, game.getHeight() / 8);
        venusMatrix = venusMatrix.mul(Matrix3x3f.rotate(venusRot));
        venusMatrix = venusMatrix.mul(sunMatrix);
        venusRot += venusDelta;

        Vector2f venus = new Vector2f().mul(venusMatrix);

        g.setColor(Color.GREEN);
        g.fillOval((int)venus.x - 5, (int)venus.y - 5, 10, 10);

        Matrix3x3f earthMatrix = Matrix3x3f.translate(game.getWidth() / 6, game.getHeight() / 6);
        earthMatrix = earthMatrix.mul(Matrix3x3f.rotate(earthRot));
        earthMatrix = earthMatrix.mul(sunMatrix);
        earthRot += earthDelta;

        Vector2f earth = new Vector2f().mul(earthMatrix);

        g.setColor(Color.BLUE);
        g.fillOval((int)earth.x - 5, (int)earth.y - 5, 10, 10);

        Matrix3x3f moonMatrix = Matrix3x3f.translate(10, 0);
        moonMatrix = moonMatrix.mul(Matrix3x3f.rotate(moonRot));
        moonMatrix = moonMatrix.mul(earthMatrix);
        moonRot += moonDelta;

        Vector2f moon = new Vector2f().mul(moonMatrix);

        g.setColor(Color.LIGHT_GRAY);
        g.fillOval((int)moon.x - 2, (int)moon.y - 2, 4, 4);

        Matrix3x3f marsMatrix = Matrix3x3f.translate(game.getWidth() / 4, game.getHeight() / 4);
        marsMatrix = marsMatrix.mul(Matrix3x3f.rotate(marsRot));
        marsMatrix = marsMatrix.mul(sunMatrix);
        marsRot += marsDelta;

        Vector2f mars = new Vector2f().mul(marsMatrix);

        g.setColor(Color.ORANGE);
        g.fillOval((int)mars.x - 4, (int)mars.y - 4, 8, 8);
    }
}
