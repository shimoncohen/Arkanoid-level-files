import biuoop.DrawSurface;
import java.awt.Color;

/**
 * indicates current score.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;
    static final int SCREEN_WIDTH = 800;
    static final int REC_HEIGHT = 30;

    /**
     *
     * @param s a counter for the score.
     * constructor.
     */
    public ScoreIndicator(Counter s) {
        this.score = s;
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.drawRectangle(0, 0, SCREEN_WIDTH, REC_HEIGHT);
        d.setColor(Color.black);
        d.drawText(310, 20, "Score: " + score.getValue(), 20);
    }

    @Override
    public void timePassed(double dt) {
    }
}
