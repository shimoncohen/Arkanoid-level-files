import biuoop.DrawSurface;
import java.awt.Color;

/**
 * the screen displayed when the game is won.
 */
public class WinScreen implements Animation {
    private boolean stop;
    private int score;

    /**
     *
     * @param score the players score.
     * constructor.
     */
    public WinScreen(int score) {
        this.stop = false;
        this.score = score;
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        Crown crown = new Crown();
        crown.drawOn(d);
        d.setColor(Color.black);
        d.drawText(Ass6Game.SCREEN_WIDTH / 4, Ass6Game.SCREEN_HEIGHT / 2, "You Win! Your score is " + this.score, 32);
    }
}
