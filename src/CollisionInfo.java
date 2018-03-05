/**
 * Stores all the information about a collision.
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     *
     * @param cP A Point object representing the collision point.
     * @param cO A Collidable object that represents the object colliding with.
     * constructor.
     */
    public CollisionInfo(Point cP, Collidable cO) {
        this.collisionPoint = cP;
        this.collisionObject = cO;
    }

    /**
     *
     * @return returns the point at which the collision occurs.
     * gets the collision point.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     *
     * @return returns the collidable object involved in the collision.
     * gets the collision object.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
