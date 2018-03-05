import biuoop.DrawSurface;
import java.awt.Color;

/**
 * in charge of displaying the levels name.
 */
public class LevelNameIndicator implements Sprite {
    private String name;

    /**
     *
     * @param levelName the levels name.
     * constructor.
     */
    public LevelNameIndicator(String levelName) {
        this.name = levelName;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.black);
        d.drawText(500, 20, "Level Name: " + this.name, 20);
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void timePassed(double dt) {
    }
}
