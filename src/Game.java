import biuoop.DrawSurface;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.Reader;
import java.io.IOException;
import java.util.List;

/**
 * the animation that runs the game.
 */
public class Game implements Animation {
    private GameFlow gf;
    private boolean stop;
    private String set;

    /**
     *
     * @param game the game management.
     * @param s the string of the path to read levels from.
     * constructor.
     */
    public Game(GameFlow game, String s) {
        this.gf = game;
        this.stop = false;
        this.set = s;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(this.set);
        Reader read = new InputStreamReader(inputStream);
        BufferedReader reader1 = new BufferedReader(read);
        //run the game.
        LevelSpecificationReader ls = new LevelSpecificationReader();
        List<LevelInformation> li = null;
        if (reader1 != null) {
            li = ls.fromReader(reader1);
        }
        if (li == null) {
            System.out.println("Error, cannot load levels.");
            System.out.println("Level files may be corrupted.");
            System.exit(1);
        }
        try {
            reader1.close();
            inputStream.close();
        } catch (IOException e) {
            e.getMessage();
        }
        this.gf.runLevels(li);
        this.stop = true;
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
