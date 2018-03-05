/**
 * listeners in charge of hit events.
 */
public interface HitListener {

    /**
     *
     * @param beingHit the block being hit.
     * @param hitter the ball hitting the block.
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}