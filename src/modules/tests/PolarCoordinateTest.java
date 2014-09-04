package modules.tests;

import game.GameFramework;
import modules.GameModule;
import util.physics.Vector2f;

import java.awt.*;

/**
 * Created by Shorty on 8/20/2014.
 */
public class PolarCoordinateTest extends GameModule {
    Point coordinate, center, point;
    Vector2f polar;
    String polarCoordinates, cartesianCoordinates;
    double magnitude, radians, degrees;

    public PolarCoordinateTest(GameFramework game) {
        super(game, "PolarCoordinateTest");
    }

    @Override
    public boolean initialize() {
        coordinate = new Point();
        point = new Point();
        center = new Point(game.getWidth() / 2, game.getHeight() / 2);
        polar = new Vector2f();
        return true;
    }

    @Override
    public void input(double delta) {
        coordinate = game.getMouse().getPosition();
    }

    @Override
    public void update(double delta) {
        center.x = (game.getWidth() / 2);
        center.y = (game.getHeight() / 2);
        point.x = coordinate.x - center.x;
        point.y = center.y - coordinate.y;
        magnitude = Math.sqrt(Math.pow(point.x, 2) + Math.pow(point.y, 2));
        radians = Math.atan2(point.y, point.x);
        degrees = Math.toDegrees(radians);
        if(degrees < 0) degrees = degrees + 360;
        polar.x = (float)(magnitude * Math.cos(radians));
        polar.y = (float)(magnitude * Math.sin(radians));
        polarCoordinates = String.format("(%.0f, %.0f)", magnitude, degrees);
        cartesianCoordinates = String.format("(%.0f, %.0f)", polar.x, polar.y);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.drawLine(0, center.y, game.getWidth(), center.y);
        g.drawLine(center.x, 0, center.x, game.getHeight());

        g.setColor(Color.GREEN);
        g.drawLine(center.x, center.y, coordinate.x, coordinate.y);

        g.setColor(Color.WHITE);
        g.drawString(polarCoordinates, 20, 60);
        g.drawString(cartesianCoordinates, 20, 80);

        g.setColor(Color.BLUE);
        g.drawArc((int)(center.x - magnitude),(int)(center.y - magnitude),
                (int)(2 * magnitude), (int)(2 * magnitude), 0, (int) degrees);
    }
}
