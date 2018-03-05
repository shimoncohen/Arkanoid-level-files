/**
 * A collidable object.
 */
public interface Collidable {

    /**
     *
     * @return return the "collision shape" of the object.
     * gets the rectangle of the collided object.
     */
    Rectangle getCollisionRectangle();

    // Notify the object that we collided with it at collisionPoint with
    // a given velocity.
    // The return is the new velocity expected after the hit (based on
    // the force the object inflicted on us).
    /**
     *
     * @param collisionPoint the point of collision between the ball and the collidable object.
     * @param currentVelocity the velocity of the ball colliding with the object.
     * @param hitter the ball hitting the collidable.
     * @return changes the velocity of the ball that hit the object.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}