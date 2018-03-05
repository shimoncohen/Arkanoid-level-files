/**
 * runs a given task.
 */
public class RunTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation gameAnimation;

    /**
     *
     * @param runner an animation runner.
     * @param highScoresAnimation the highscore animetion to run.
     */
    public RunTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.gameAnimation = highScoresAnimation;
    }

    @Override
    public Void run() {
        this.runner.run(this.gameAnimation);
        return null;
    }
}
