import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * a game level.
 */
public class Level implements LevelInformation {
    private int paddleSpeed;
    private int paddleWidth;
    private ArrayList<Velocity> velocities;
    private LinkedList<Block> blocks;
    private String background;
    private String name;
    private boolean nameChanged;

    /**
     *
     * @param bVel the balls velocities.
     * @param background the levels background.
     * @param pSpeed the paddle speed.
     * @param pWidth the paddles width.
     * @param blocks the levels blocks.
     * constructor.
     */
    public Level(ArrayList<Velocity> bVel, String background, double pSpeed, double pWidth, LinkedList<Block> blocks) {
        this.paddleSpeed = (int) pSpeed;
        this.paddleWidth = (int) pWidth;
        this.velocities = bVel;
        this.blocks = blocks;
        this.background = background;
        this.name = "";
        this.nameChanged = false;
    }
    @Override
    public Sprite getBackground() {
        if (this.background.contains("color")) {
            ColorsParser colorsParser = new ColorsParser();
            return new Frame(colorsParser.colorFromString(this.background.replace("color(", "").replace(")", "")));
        } else if (this.background.contains("RGB")) {
            ColorsParser colorsParser = new ColorsParser();
            return new Frame(colorsParser.colorFromString(this.background));
        }
        return new ImageSprite(this.background, 0, 0);
    }

    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    @Override
    public int numberOfBalls() {
        return this.velocities.size();
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocks.size();
    }

    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return this.velocities;
    }

    @Override
    public String levelName() {
        return this.name;
    }

    /**
     *
     * @param s the levels name.
     * sets the levels name.
     */
    public void setName(String s) {
        if (!this.nameChanged) {
            this.name = s;
            this.nameChanged = true;
        }
    }
}
