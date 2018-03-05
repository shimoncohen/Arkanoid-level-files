import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * Displays a highscore table.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable highScoresTable;
    private String stopKey;
    private KeyboardSensor keyboardSensor;
    private boolean stop;

    /**
     *
     * @param scores the highscore table to display.
     * @param endKey the key to exit the animation.
     * @param ks a keyboard sensor.
     * constructor.
     */
    public HighScoresAnimation(HighScoresTable scores, String endKey, KeyboardSensor ks) {
        this.highScoresTable = scores;
        this.stopKey = endKey;
        this.keyboardSensor = ks;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(100, 100, "High Scores", 50);
        int x = 100, y = 170;
        if (this.highScoresTable.getHighScores().size() != 0) {
            for (int i = 0; i < this.highScoresTable.getHighScores().size(); i++) {
                d.drawText(x, y, this.highScoresTable.getHighScores().get(i).getName(), 20);
                d.drawText(x + 100, y, Integer.toString(this.highScoresTable.getHighScores().get(i).getScore()), 20);
                y += 20;
            }
        } else {
            d.drawText(x, y + 50, "No highScores yet", 20);
        }
        if (this.keyboardSensor.isPressed(this.stopKey)) {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return stop;
    }
}