/**
 * in charge or removing balls from the game.
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;

    /**
     *
     * @param game a game to remove the ball from.
     * @param removedBalls the amount of balls left.
     * constructor.
     */
    public BallRemover(GameLevel game, Counter removedBalls) {
        this.game = game;
        this.remainingBalls = removedBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.remainingBalls.decrease(1);
        hitter.removeFromGame(this.game);
        hitter.removeHitListener(this);
    }
}
