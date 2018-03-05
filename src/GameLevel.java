import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.List;

/**
 * A game being played.
 */
public class GameLevel implements Animation {
    private LevelInformation levInfo;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Counter blockCount;
    private Counter ballCount;
    private Counter score;
    private Counter lives;
    private KeyboardSensor keyboard;
    private Paddle pad;
    private Color ballsAndPaddleColor;
    private int shotCounter;
    static final int REC_HEIGHT = 30;
    static final int BORDER_HEIGHT_OR_WIDTH = 25;
    static final int PADDLE_HEIGHT = 20;
    static final int BALL_SIZE = 7;
    private AnimationRunner runner;
    private boolean running;

    /**
     *
     * @param animationRunner a new animation runner.
     * @param levelInfo a new level info.
     * @param lives a new counter for the players lives.
     * @param score a new counter for the players score.
     * @param ks a keyboardsensor.
     * constructor.
     */
    public GameLevel(LevelInformation levelInfo, KeyboardSensor ks, AnimationRunner animationRunner,
                     Counter lives, Counter score) {
        this.levInfo = levelInfo;
        this.sprites = new SpriteCollection();
        this.sprites.addSprite(this.levInfo.getBackground());
        this.environment = new GameEnvironment();
        this.blockCount = new Counter();
        this.blockCount.increase(levelInfo.numberOfBlocksToRemove());
        this.ballCount = new BallCounter();
        this.lives = lives;
        this.score = score;
        this.keyboard = ks;
        this.runner = animationRunner;
        this.shotCounter = 5;
        this.ballsAndPaddleColor = Color.blue;
    }

    /**
     *
     * @return returns the block counter.
     */
    public Counter getBlockCounter() {
        return this.blockCount;
    }

    @Override
    public boolean shouldStop() {
        if (!this.running) {
            return true;
        }
        return false;
    }

    @Override
    /**
     *
     * @param d a DrawSurface onject.
     * @param dt amount of seconds past since last call.
     * the games logic.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        if (this.shotCounter != 0) {
            this.shotCounter--;
        }
        this.sprites.drawAllOn(d);
        d.setColor(Color.red);
        d.drawText(20, Ass6Game.SCREEN_HEIGHT - 20, "Press space for a surprise", 20);
        this.sprites.notifyAllTimePassed(dt);
        //if no blocks are left.
        if (this.blockCount.getValue() == 0) {
            this.score.increase(100);
            this.running = false;
        } else if (this.ballCount.getValue() == 0) { //if no balls are left.
            this.pad.removeFromGame(this);
            this.pad = new Paddle(new Rectangle(new Point(Ass6Game.SCREEN_WIDTH / 2 - this.levInfo.paddleWidth() / 2,
                    Ass6Game.SCREEN_HEIGHT - PADDLE_HEIGHT), this.levInfo.paddleWidth(),
                    PADDLE_HEIGHT), this.keyboard, this.levInfo.paddleSpeed(), this.ballsAndPaddleColor);
            BallRemover br = new BallRemover(this, this.ballCount);
            this.pad.addHitListener(br);
            this.pad.addToGame(this);
            this.lives.decrease(1);
            this.running = false;
        }
        if (this.keyboard.isPressed("p")) { //if the key p is pressed.
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", new PauseScreen()));
        }
        if (this.shotCounter == 0) {
            if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
                Point center = new Point(this.pad.getCenter().getX(), this.pad.getCenter().getY() - 20);
                Ball shot = new Ball(center, 3, this.ballsAndPaddleColor, this.environment, true);
                shot.setVelocity(0, -7 * 60);
                this.sprites.addSprite(shot);
                this.shotCounter = 5;
            }
        }
    }

    /**
     *
     * @param c A Collidable object to add to GameEnvironment.
     * adds a collidable to the game.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     *
     * @param s1 A Sprite object to add to SpriteCollection.
     * adds a sprite to the game.
     */
    public void addSprite(Sprite s1) {
        this.sprites.addSprite(s1);
    }

    /**
     *
     * @param c a collidable object.
     * removes a collidable from the game.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     *
     * @param s a sprite object.
     * removes a sprite from the game.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * creates balls on top of the paddle.
     */
    private void createBallsOnTopOfPaddle() {
        //creates the correct amount of given balls and assigns their velocitys.
        for (int i = 0; i < this.levInfo.numberOfBalls(); i++) {
            Ball ball1 = new Ball(Ass6Game.SCREEN_WIDTH / 2, Ass6Game.SCREEN_HEIGHT - PADDLE_HEIGHT - BALL_SIZE - 1,
                    BALL_SIZE, ballsAndPaddleColor, this.levInfo.initialBallVelocities().get(i), this.environment);
            this.ballCount.increase(1);
            ball1.addToGame(this);
        }
    }

    /**
     *
     * Initialize a new game: create the Blocks and Ball (and Paddle).
     * and add them to the game.
     */
    public void initialize() {
        //add the information block on top of the game.
        Rectangle rec = new Rectangle(new Point(0, 0), Ass6Game.SCREEN_WIDTH, REC_HEIGHT);
        BorderBlock info = new BorderBlock(rec, Color.white);
        this.sprites.addSprite(info);
        //create all of the listeners.
        BlockRemover remover = new BlockRemover(this, this.blockCount);
        BallRemover br = new BallRemover(this, this.ballCount);
        ShotRemover sr = new ShotRemover(this);
        ScoreTrackingListener trackS = new ScoreTrackingListener(this.score);
        //create the paddle.
        this.pad = new Paddle(new Rectangle(new Point(Ass6Game.SCREEN_WIDTH / 2 - this.levInfo.paddleWidth() / 2,
                Ass6Game.SCREEN_HEIGHT - PADDLE_HEIGHT), this.levInfo.paddleWidth(),
                PADDLE_HEIGHT), this.keyboard, this.levInfo.paddleSpeed(), this.ballsAndPaddleColor);
        this.pad.addHitListener(br);
        this.pad.addToGame(this);
        // top border block.
        BorderBlock b = new BorderBlock(new Rectangle(new Point(0, 0 + REC_HEIGHT), Ass6Game.SCREEN_WIDTH,
                BORDER_HEIGHT_OR_WIDTH), Color.gray);
        b.addHitListener(sr);
        b.addToGame(this);
        // left border block.
        b = new BorderBlock(new Rectangle(new Point(0, BORDER_HEIGHT_OR_WIDTH + REC_HEIGHT), BORDER_HEIGHT_OR_WIDTH,
                Ass6Game.SCREEN_HEIGHT), Color.gray);
        b.addToGame(this);
        // right border block.
        b = new BorderBlock(new Rectangle(new Point(Ass6Game.SCREEN_WIDTH - BORDER_HEIGHT_OR_WIDTH,
                BORDER_HEIGHT_OR_WIDTH + REC_HEIGHT), BORDER_HEIGHT_OR_WIDTH, Ass6Game.SCREEN_HEIGHT), Color.gray);
        b.addToGame(this);
        // bottom border block
        Color removerC = this.ballsAndPaddleColor;
        b = new BorderBlock(new Rectangle(new Point(BORDER_HEIGHT_OR_WIDTH, Ass6Game.SCREEN_HEIGHT),
                Ass6Game.SCREEN_WIDTH - 2 * BORDER_HEIGHT_OR_WIDTH, BORDER_HEIGHT_OR_WIDTH), removerC);
        b.addHitListener(br);
        b.addToGame(this);
        List<Block> l = this.levInfo.blocks();
        //add listeners to blocks, and blocks to the game.
        for (Block block : l) {
            block.addHitListener(trackS);
            block.addHitListener(remover);
            block.addToGame(this);
        }
        //create all the indicators.
        ScoreIndicator sci = new ScoreIndicator(this.score);
        LivesIndicator li = new LivesIndicator(this.lives);
        LevelNameIndicator lni = new LevelNameIndicator(this.levInfo.levelName());
        li.addToGame(this);
        sci.addToGame(this);
        lni.addToGame(this);
    }

    /**
     * Run the game -- start the animation loop.
     */
    public void playOneTurn() {
        this.createBallsOnTopOfPaddle();
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        this.runner.run(this);
    }
}