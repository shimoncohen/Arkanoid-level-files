import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
class HighScoresTable implements Serializable {
    private LinkedList<ScoreInfo> scores;
    private int scoresInList;

    // Create an empty high-scores table with the specified size.
    // The size means that the table holds up to size top scores.

    /**
     *
     * @param size the highscore table size.
     */
    public HighScoresTable(int size) {
        this.scores = new LinkedList();
        this.scoresInList = size;
    }

    /**
     *
     * @param score the score to add to the table.
     * adds a high score.
     */
    public void add(ScoreInfo score) {
        int j = 0;
        if (this.scores.size() != 0) {
            for (int i = 0; i < this.scores.size(); i++) {
                j = i;
                if (score.getScore() > this.scores.get(i).getScore()) {
                    this.scores.add(i, score);
                    break;
                }
            }
        }
        if (this.scores.size() == 0 || j == this.scores.size() - 1) {
            this.scores.add(score);
        }
        if (this.scores.size() > this.scoresInList) {
            this.scores.remove(this.scores.get(this.scoresInList));
        }
    }

    /**
     *
     * @return returns the tables size.
     */
    public int size() {
        return this.scoresInList;
    }

    /**
     *
     * @return returns the list of high scores.
     * The list is sorted such that the highest scores come first.
     */
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }

    /**
     *
     * @param score the score to check.
     * @return returns the rank of the current score: where will it be on the list if added?
     */
    public int getRank(int score) {
        int j = 0;
        if (this.scores.size() == 0) {
            return 1;
        }
        for (int i = 0; i < this.scores.size(); i++) {
            j = i;
            if (score > this.scores.get(i).getScore()) {
                return i + 1;
            }
        }
        if (this.scores.size() < 5) {
            return j + 1;
        }
        return 0;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.scores = new LinkedList();
    }

    /**
     *
     * @param filename the name of the file to load from.
     * @throws IOException exception thrown if information cannot be loaded from file.
     * Load table data from file.
     * Current table data is cleared.
     */
    public void load(File filename) throws IOException {
        LinkedList<ScoreInfo> temp = new LinkedList<>();
        ScoreInfo si = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filename))) {
            for (int i = 0; i < this.size(); i++) {
                si = new ScoreInfo((String) objectInputStream.readObject(), (int) objectInputStream.readObject());
                temp.add(si);
            }
            this.scores = temp;
            objectInputStream.close();
        } catch (ClassNotFoundException e) {
            this.scores = temp;
            return;
        } catch (IOException e) {
            this.scores = temp;
            return;
        }
    }

    /**
     *
     * @param filename the name of the file to save to.
     * @throws IOException exception thrown if information cannot be written to file.
     * Save table data to the specified file.
     */
    public void save(File filename) throws IOException {
        try (ObjectOutputStream outputStreamWriter = new ObjectOutputStream(new FileOutputStream(filename))) {
            for (int i = 0; i < this.scores.size(); i++) {
                outputStreamWriter.writeObject(this.scores.get(i).getName());
                outputStreamWriter.writeObject(this.scores.get(i).getScore());
            }
            outputStreamWriter.close();
        }
    }

    /**
     *
     * @param filename the file to load the highscores from.
     * @return returns the highscore table if file exists.
     * If the file does not exist, or there is a problem with reading it, an empty table is returned.
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable hs = new HighScoresTable(5);
        try {
            hs.load(filename);
        } catch (IOException e) {
            try {
                hs.save(filename);
            } catch (IOException e1) {
                e.getMessage();
            }
        }
        return hs;
    }
}