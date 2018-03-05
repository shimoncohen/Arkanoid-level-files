import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;

/**
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) secods, before
 * it is replaced with the next one.
 */
public class CountdownAnimation implements Animation {
    private double numberOfSeconds;
    private int startCount;
    private SpriteCollection gs;
    private boolean stop;

    /**
     *
     * @param numOfSeconds number of seconds to count.
     * @param countFrom what number to count from.
     * @param gameScreen the games sprites.
     * constructor.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numberOfSeconds = numOfSeconds;
        this.startCount = countFrom;
        this.gs = gameScreen;
        this.stop = false;

    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        Sleeper sleeper = new Sleeper();
        int millisecondsPerFrame = 1000 / (int) this.numberOfSeconds;
        this.gs.drawAllOn(d);
        d.setColor(Color.red);
        long startTime = System.currentTimeMillis();
        long usedTime = System.currentTimeMillis() - startTime;
        long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
        sleeper.sleepFor(milliSecondLeftToSleep);
        d.drawText(d.getWidth() / 2, d.getHeight() / 2, Integer.toString(this.startCount), 32);
        if (this.startCount == 0) {
            this.stop = true;
        }
        this.startCount--;
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}