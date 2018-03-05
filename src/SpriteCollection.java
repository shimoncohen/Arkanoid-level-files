import biuoop.DrawSurface;
import java.util.ArrayList;

/**
 * Stores all the objects in the game.
 */
public class SpriteCollection {
    private ArrayList<Sprite> collection;

    /**
     * constructer.
     */
    public SpriteCollection() {
        this.collection = new ArrayList<>();
    }

    /**
     *
     * @return returns the sprite collection.
     */
    public ArrayList<Sprite> getCollection() {
        ArrayList<Sprite> temp = new ArrayList<>();
        temp.addAll(this.collection);
        return temp;
    }

    /**
     *
     * @param s A Sprite array to add to SpriteCollection.
     * adds all sprites in the given array to the sprite collection.
     */
    public void addSprites(SpriteCollection s) {
        this.collection.addAll(s.getCollection());
    }

    /**
     *
     * @param s A Sprite object to add to SpriteCollection.
     * adds a sprite object to the sprite collection.
     */
    public void addSprite(Sprite s) {
        this.collection.add(s);
    }

    /**
     *
     * @param s A Sprite object to remove from the SpriteCollection.
     * removes a sprite object to the sprite collection.
     */
    public void removeSprite(Sprite s) {
        this.collection.remove(s);
    }

    /**
     *
     * @param dt amount of seconds past since last call.
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed(double dt) {
        ArrayList<Sprite> temp = new ArrayList<>();
        temp.addAll(this.collection);
        for (int i = 0; i < temp.size(); i++) {
            temp.get(i).timePassed(dt);
        }
    }

    /**
     *
     * @param d A DrawSurface object to draw sprites on.
     * call drawOn(d) on all sprites.
     */
    public void drawAllOn(DrawSurface d) {
        ArrayList<Sprite> temp = new ArrayList<>();
        temp.addAll(this.collection);
        for (int i = 0; i < temp.size(); i++) {
            temp.get(i).drawOn(d);
        }
    }
}