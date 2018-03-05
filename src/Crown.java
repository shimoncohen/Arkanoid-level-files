import biuoop.DrawSurface;
import java.awt.Color;
import java.awt.Polygon;

/**
 * a crown drawing.
 */
public class Crown implements Sprite {
    static final int DISTANCE_FROM_EDGES = 100;
    static final int TRIANGLE_SIZE = (Ass6Game.SCREEN_WIDTH - 2 * DISTANCE_FROM_EDGES) / 4;
    static final int GEM_SIZE = 20;

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void timePassed(double dt) {
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(new Color(255, 215, 0));
        d.fillRectangle(DISTANCE_FROM_EDGES, DISTANCE_FROM_EDGES, Ass6Game.SCREEN_WIDTH - 2 * DISTANCE_FROM_EDGES,
                Ass6Game.SCREEN_HEIGHT - 200);
        d.setColor(new Color(240, 240, 240));
        Polygon p = new Polygon();
        p.addPoint(DISTANCE_FROM_EDGES, DISTANCE_FROM_EDGES);
        p.addPoint(DISTANCE_FROM_EDGES + 2 * TRIANGLE_SIZE, DISTANCE_FROM_EDGES);
        p.addPoint(DISTANCE_FROM_EDGES + TRIANGLE_SIZE, DISTANCE_FROM_EDGES + TRIANGLE_SIZE);
        d.fillPolygon(p);
        Polygon p1 = new Polygon();
        p1.addPoint(DISTANCE_FROM_EDGES + 2 * TRIANGLE_SIZE, DISTANCE_FROM_EDGES);
        p1.addPoint(DISTANCE_FROM_EDGES + 4 * TRIANGLE_SIZE, DISTANCE_FROM_EDGES);
        p1.addPoint(DISTANCE_FROM_EDGES + 3 * TRIANGLE_SIZE, DISTANCE_FROM_EDGES + TRIANGLE_SIZE);
        d.fillPolygon(p1);
        int x = DISTANCE_FROM_EDGES + 20;
        for (int i = 0; i < 15; i++) {
            Polygon p2 = new Polygon();
            p2.addPoint(x, DISTANCE_FROM_EDGES + (Ass6Game.SCREEN_HEIGHT - 2 * DISTANCE_FROM_EDGES)
                    / 2 + 50);
            p2.addPoint(x - GEM_SIZE / 2, DISTANCE_FROM_EDGES
                    + (Ass6Game.SCREEN_HEIGHT - 2 * DISTANCE_FROM_EDGES) / 2 + 50 + GEM_SIZE);
            p2.addPoint(x, DISTANCE_FROM_EDGES
                    + (Ass6Game.SCREEN_HEIGHT - 2 * DISTANCE_FROM_EDGES) / 2 + 50 + 2 * GEM_SIZE);
            p2.addPoint(x + GEM_SIZE / 2, DISTANCE_FROM_EDGES
                    + (Ass6Game.SCREEN_HEIGHT - 2 * DISTANCE_FROM_EDGES) / 2 + 50 + GEM_SIZE);
            d.setColor(Color.black);
            d.drawPolygon(p2);
            d.setColor(Color.red);
            d.fillPolygon(p2);
            x += 2 * GEM_SIZE;
        }
    }
}
