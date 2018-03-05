import java.util.ArrayList;

/**
 * An object consisting of the games collidable objects.
 */
public class GameEnvironment {
    private ArrayList<Collidable> collision;

    /**
     * constructor.
     */
    public GameEnvironment() {
        this.collision = new ArrayList<>();
    }

    /**
     *
     * @param c A Collidable object.
     *
     */
    public void addCollidable(Collidable c) {
        this.collision.add(c);
    }

    /**
     *
     * @param c A Collidable object.
     *
     */
    public void removeCollidable(Collidable c) {
        this.collision.remove(c);
    }

    /**
     *
     * @param trajectory The path a ball object is moving by.
     * @return The collision info of the ball with an object in its way.
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        ArrayList<Collidable> temp = new ArrayList<>();
        temp.addAll(this.collision);
        Point p1 = null;
        Collidable c = null;
        if (temp.isEmpty()) {
            return null;
        }
        for (int i = 0; i < temp.size(); i++) {
            Point inter = trajectory.closestIntersectionToStartOfLine(temp.get(i).getCollisionRectangle());
            if (inter != null) {
                if (p1 == null) {
                    p1 = inter;
                    c = temp.get(i);
                } else {
                    if (trajectory.start().distance(inter) <= trajectory.start().distance(p1)) {
                        p1 = inter;
                        c = temp.get(i);
                    }
                }
            }
        }
        if (p1 == null) {
            return null;
        }
        return new CollisionInfo(p1, c);
    }
}