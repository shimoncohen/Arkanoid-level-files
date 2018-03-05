/**
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     *
     * @param game the current gamelevel.
     * @param removedBlocks the number of blocks left in the game.
     * constructor.
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    @Override
    /**
     *
     * @param beingHit a block being hit.
     * @param hitter the ball that hit the block.
     * Blocks that are hit and reach 0 hit-points are removed from the game.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeFromGame(this.game);
            beingHit.removeHitListener(this);
            this.remainingBlocks.decrease(1);
        }
        if (hitter.isShot()) {
            hitter.removeFromGame(this.game);
        }
    }
}