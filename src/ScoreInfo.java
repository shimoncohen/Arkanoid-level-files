import java.io.Serializable;

/**
 * the score information.
 */
public class ScoreInfo implements Serializable {
    private String playerName;
    private int playerScore;

    /**
     *
     * @param name the players name.
     * @param score the players score.
     */
    public ScoreInfo(String name, int score) {
        this.playerName = name;
        this.playerScore = score;
    }

    /**
     *
     * @return returns the players name.
     */
    public String getName() {
        return this.playerName;
    }

    /**
     *
     * @return returns the highscore.
     */
    public int getScore() {
        return this.playerScore;
    }
}