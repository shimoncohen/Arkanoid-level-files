import biuoop.DrawSurface;
import java.awt.Color;

/**
 * a sprite representing the background of the levels.
 */
public abstract class Background implements Sprite {

    @Override
    public abstract void addToGame(GameLevel g);

    @Override
    public void drawOn(DrawSurface d) {
        for (int i = 0; i < this.getDrawings().getCollection().size(); i++) {
            this.getDrawings().getCollection().get(i).drawOn(d);
        }
    }

    @Override
    public void timePassed(double dt) {
        this.getDrawings().notifyAllTimePassed(dt);
    }

    /**
     *
     * @return returns the backgrounds sprites.
     */
    public abstract SpriteCollection getDrawings();

    /**
     *
     * @return returns the backgrounds color.
     */
    public abstract Color getBackgroundColor();
}
