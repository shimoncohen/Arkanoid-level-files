import biuoop.DrawSurface;

/**
 * the screen displayed when the game is paused.
 */
public class PauseScreen implements Animation {
    private boolean stop;

    /**
     *
     * constructor.
     */
    public PauseScreen() {
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    @Override
    public boolean shouldStop() { return this.stop; }
}