import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

/**
 * in charge of running animations.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;

    /**
     *
     * @param gui1 a new gui object.
     * @param sleep a new sleeper.
     * constructor.
     */
    public AnimationRunner(GUI gui1, Sleeper sleep) {
        this.gui = gui1;
        this.framesPerSecond = 60;
        this.sleeper = sleep;
    }

    /**
     *
     * @param animation a new animation to run.
     * runs the animation loop.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
//        int count = 1;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d, (double) 1 / this.framesPerSecond);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
}