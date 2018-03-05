import biuoop.DrawSurface;

/**
 * a basic block.
 */
public abstract class Block implements Sprite, Collidable, HitNotifier {

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     *
     * @param game the game to remove the block from.
     * removes current block from given game.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
        game.removeCollidable(this);
    }

    /**
     *
     * @return returns the blocks rectangle.
     * gets the blocks rectangle.
     */
    public abstract Rectangle getRec();

    @Override
    public abstract void drawOn(DrawSurface surface);

    @Override
    public abstract Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    @Override
    public abstract void removeHitListener(HitListener hl);

    @Override
    public abstract void addHitListener(HitListener hl);

    /**
     *
     * @return returns the blocks hit points.
     */
    public abstract int getHitPoints();

    @Override
    public void timePassed(double dt) {
    }
}
