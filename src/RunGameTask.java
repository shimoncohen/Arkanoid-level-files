import biuoop.GUI;
import biuoop.KeyboardSensor;
import java.io.File;

/**
 * a task that runs a game.
 */
public class RunGameTask implements Task {
    private AnimationRunner runner;
    private Animation gameAnimation;
    private KeyboardSensor keyboardSensor;
    private GUI gui;
    private HighScoresTable highScoresTable;
    private File file;
    private String set;

    /**
     *
     * @param runner an animation runner.
     * @param highScoresTable the games highscore table.
     * @param ks a keyboardsensor.
     * @param gui a gui object.
     * @param f the highscore file.
     * @param s the sets file location.
     */
    public RunGameTask(AnimationRunner runner, HighScoresTable highScoresTable, KeyboardSensor ks, GUI gui,
                       File f, String s) {
        this.runner = runner;
        this.highScoresTable = highScoresTable;
        this.keyboardSensor = ks;
        this.gui = gui;
        this.highScoresTable = highScoresTable;
        this.file = f;
        this.set = s;
    }

    @Override
    public Void run() {
        GameFlow game = new GameFlow(this.runner, this.keyboardSensor, this.gui, this.highScoresTable, this.file);
        Game currentGame = new Game(game, this.set);
        this.runner.run(currentGame);
        return null;
    }
}
