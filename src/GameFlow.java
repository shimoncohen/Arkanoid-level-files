import biuoop.GUI;
import biuoop.KeyboardSensor;
import java.io.IOException;
import java.util.List;
import biuoop.DialogManager;
import java.io.File;

/**
 * incharge of running the levels one after the other.
 */
public class GameFlow {
    private Counter score;
    private Counter lives;
    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private GUI gui;
    private HighScoresTable highScoresTable;
    private File highScores;
    static final int LIVES = 7;

    /**
     *
     * @param ar animationrunner object.
     * @param ks ketboardsensor object.
     * @param gui1 a new GUI object.
     * @param table the games highscore table.
     * @param f the file to save the scores to.
     * constructor.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui1, HighScoresTable table, File f) {
        this.score = new Counter();
        this.lives = new Counter();
        this.lives.increase(LIVES);
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.gui = gui1;
        this.highScoresTable = table;
        this.highScores = f;
    }

    /**
     *
     * @param levels an array with the levels to run.
     * runs the levels in the array one after the other.
     */
    public void runLevels(List<LevelInformation> levels) {
        //go over the array of levels.
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor, this.animationRunner,
                    this.lives, this.score);
            level.initialize();
            while (this.lives.getValue() != 0 && level.getBlockCounter().getValue() != 0) {
                level.playOneTurn();
            }
            //if the player has no more lives.
            if (this.lives.getValue() == 0) {
                saveScore();
                this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, "space",
                        new LoseScreen(this.score.getValue())));
                this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, "space",
                        new HighScoresAnimation(this.highScoresTable, "space", this.keyboardSensor)));
                break;
            }
        }
        //if the player has more lives.
        if (this.lives.getValue() != 0) {
            saveScore();
            this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, "space",
                    new WinScreen(this.score.getValue())));
            this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, "space",
                    new HighScoresAnimation(this.highScoresTable, "space", this.keyboardSensor)));
        }
        try {
            this.highScoresTable.save(this.highScores);
        } catch (IOException e) {
            e.getMessage();
        }
        return;
    }

    /**
     * save the new highscore.
     */
    private void saveScore() {
        if (this.highScoresTable.getRank(this.score.getValue()) != 0) {
            DialogManager dialog = gui.getDialogManager();
            String name = dialog.showQuestionDialog("Name", "What is your name?", "");
            this.highScoresTable.add(new ScoreInfo(name, this.score.getValue()));
        }
    }
}