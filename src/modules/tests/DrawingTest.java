package modules.tests;

import game.GameFramework;
import modules.GameModule;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Shorty on 8/8/2014.
 */
public class DrawingTest extends GameModule {
    private final Color[] COLORS = {
            Color.RED,
            Color.GREEN,
            Color.YELLOW,
            Color.BLUE};

    public ArrayList<Point> lines;
    private Color color;
    private boolean drawingLine;
    private int colorIndex;
    private Stroke stroke;

    public DrawingTest(GameFramework game) {
        super(game, "Drawing");
    }

    @Override
    public boolean initialize() {
        lines = new ArrayList<Point>();
        drawingLine = false;
        colorIndex = 1;
        stroke = new BasicStroke(1);
        return true;
    }

    @Override
    public void input(){
        if(game.keyboard.keyDown(KeyEvent.VK_D))
            lines.clear();
        if(game.keyboard.keyDown(KeyEvent.VK_1))
            stroke = new BasicStroke(1);
        if(game.keyboard.keyDown(KeyEvent.VK_2))
            stroke = new BasicStroke(3);
        if(game.keyboard.keyDown(KeyEvent.VK_3))
            stroke = new BasicStroke(5);
        if(game.keyboard.keyDown(KeyEvent.VK_4))
            stroke = new BasicStroke(10);
        if(game.mouse.buttonDown(MouseEvent.BUTTON1 - 1)){
            drawingLine = true;
            lines.add(game.mouse.getPosition());
        } else if(drawingLine) {
            lines.add(null);
            drawingLine = false;
        }
        colorSelector(game.mouse.getNotches());
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Graphics g){
        g.setColor(color);
        ((Graphics2D)g).setStroke(stroke);
        for(int i = 0; i < lines.size() - 1; i++){
            Point p1 = lines.get(i);
            Point p2 = lines.get(i+1);
            if(!(p1 == null || p2 == null)){
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
        g.drawString("Points in 'lines': " + lines.size(), 30, 250);
        g.drawString("Press 'D' to clear drawings...", 30, 290);
    }

    public void colorSelector(int notches){
        colorIndex += notches;
        color = COLORS[Math.abs(colorIndex % COLORS.length)];
    }
}
