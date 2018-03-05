/**
 * Created by shimon on 5/30/2017.
 */
public class ShotRemover implements HitListener {
    private GameLevel game;

    /**
     *
     * @param game a game to remove the ball from.
     * constructor.
     */
    public ShotRemover(GameLevel game) {
        this.game = game;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (hitter.isShot()) {
            hitter.removeFromGame(this.game);
        }
    }
}
