import biuoop.DrawSurface;
import java.awt.Color;

/**
 * a backgrounds frame.
 */
public class Frame implements Sprite {
    private DrawingRectangle rec;
    private int startY;
    private Color frameColor;

    /**
     *
     * @param c the frames color.
     * constructor.
     */
    public Frame(Color c) {
        this.rec = new DrawingRectangle(new Point(0, 30), Ass6Game.SCREEN_WIDTH, Ass6Game.SCREEN_HEIGHT,
                this.frameColor);
        this.frameColor = c;
        this.startY = (int) rec.getUpperLeft().getY();
    }

    @Override
    public void timePassed(double dt) {
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.frameColor);
        d.fillRectangle((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(),
                (int) this.rec.getWidth(), (int) this.rec.getHeight());
    }

    @Override
    public void addToGame(GameLevel g) {

    }
}
