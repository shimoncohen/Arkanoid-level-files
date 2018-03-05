import biuoop.DrawSurface;
/**
 *
 */
public interface Animation {

    /**
     *
     * @param d creates one frame of the animation.
     * @param dt amount of seconds past since last call.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     *
     * @return returns true if the game should stop running, false otherwise.
     */
    boolean shouldStop();
}