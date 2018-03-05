import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * covers an animation and stops it if the chosen key is pressed.
 */
public class KeyPressStoppableAnimation implements Animation {
    private boolean stop;
    private String keyStop;
    private Animation animationRunning;
    private KeyboardSensor ks;
    private boolean isAlreadyPressed;

    /**
     *
     * @param sensor the keyboard sensor.
     * @param key the key to stop the animation loop.
     * @param animation the animation to run.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.stop = false;
        this.isAlreadyPressed = true;
        this.keyStop = key;
        this.animationRunning = animation;
        this.ks = sensor;

    }
    // ...
    // think about the implementations of doOneFrame and shouldStop.


    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        animationRunning.doOneFrame(d, dt);
        if (this.ks.isPressed(this.keyStop)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}