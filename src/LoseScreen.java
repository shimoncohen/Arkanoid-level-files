import biuoop.DrawSurface;

/**
 * the screen displayed when all lives are lost.
 */
public class LoseScreen implements Animation {
    private boolean stop;
    private int score;

    /**
     *
     * @param score the score at the end of the game.
     * constructor.
     */
    public LoseScreen(int score) {
        this.stop = false;
        this.score = score;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(Ass6Game.SCREEN_WIDTH / 4, Ass6Game.SCREEN_HEIGHT / 2, "Game Over. your score is " + this.score, 32);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
