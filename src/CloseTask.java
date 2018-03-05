import biuoop.GUI;
import java.io.File;
import java.io.IOException;

/**
 * a task to close the running gui.
 */
public class CloseTask implements Task {
    private GUI gui;
    private File saveFile;
    private HighScoresTable highScoresTable;

    /**
     *
     * @param gui1 a GUI object.
     * @param f the file to save the scores to.
     * @param scores the games highscores.
     * constructor.
     */
    public CloseTask(GUI gui1, File f, HighScoresTable scores) {
        this.gui = gui1;
        this.saveFile = f;
        this.highScoresTable = scores;
    }

    @Override
    public Object run() {
        try {
            this.highScoresTable.save(this.saveFile);
        } catch (IOException e) {
            e.getMessage();
        }
        this.gui.close();
        return null;
    }
}