/**
 * tracks the score.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     *
     * @param scoreCounter a counter for the score.
     * constructor.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            this.currentScore.increase(15);
        } else {
            this.currentScore.increase(5);
        }
    }
}