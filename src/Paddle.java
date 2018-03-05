import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A paddle the hit the balls with.
 */
public class Paddle implements Sprite, Collidable, HitNotifier {
    private KeyboardSensor keyboard;
    private Rectangle rec;
    private Block block = new BorderBlock(this.rec, Color.lightGray);
    private int speed;
    private Color color;
    private List<HitListener> hitListeners;
    static final int SPACE_FROM_SIDES = 25;

    /**
     *
     * @param rec1 the paddles rectangle.
     * @param sensor a KeyBoard sensor.
     * @param speed1 the paddles speed.
     * @param c the paddles color.
     * constructor.
     */
    public Paddle(Rectangle rec1, KeyboardSensor sensor, int speed1, Color c) {
        this.rec = rec1;
        this.keyboard = sensor;
        this.speed = speed1;
        this.hitListeners = new ArrayList<>();
        this.color = c;
    }

    /**
     * @param dt amount of seconds past since last call.
     * moves the paddle left.
     */
    public void moveLeft(double dt) {
        if (this.rec.getUpperLeft().getX() >= 0  + SPACE_FROM_SIDES) {
            this.rec.setPosition(new Point(this.rec.getUpperLeft().getX() - this.speed * dt,
                    this.rec.getUpperLeft().getY()));
        }
    }

    /**
     * @param dt amount of seconds past since last call.
     * moves the paddle right.
     */
    public void moveRight(double dt) {
        if (this.rec.getBottomRight().getX() <= Ass6Game.SCREEN_WIDTH - SPACE_FROM_SIDES) {
            this.rec.setPosition(new Point(this.rec.getUpperLeft().getX() + this.speed * dt,
                    this.rec.getUpperLeft().getY()));
        }
    }

    @Override
    /**
     * @param dt amount of seconds past since last call.
     * moves paddle according to keys pressed.
     */
    public void timePassed(double dt) {
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight(dt);
        } else if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft(dt);
        }
    }

    @Override
    /**
     *
     * @param d a DrawSurface object.
     * draws the paddle on the screen.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(),
                (int) this.rec.getWidth(), (int) this.rec.getHeight());
    }

    @Override
    /**
     *
     * @return returns the paddles rectangle.
     * gets the paddles rectangle.
     */
    public Rectangle getCollisionRectangle() {
        return this.rec;
    }

    @Override
    /**
     *
     * @param collisionPoint a Point representing the collision location with the paddle.
     * @param currentVelocity the Velocity of the colliding object.
     * @return returns a new Velocity object according to where the paddle was hit.
     * changes the velocity of the ball that hit the block.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double width = this.rec.getWidth();
        double start = this.rec.getUpperLeft().getX();
        //divide paddle to 5 regions.
        double part = width / 5;
        double ballSpeed = currentVelocity.speed();
        Velocity v = currentVelocity;
        //check in which region of the paddle is the collision and acting accordingly.
        if (collisionPoint.getY() == this.rec.getUpperLeft().getY()
                && collisionPoint.getX() == this.rec.getUpperLeft().getX()) { //check edjes.
            v = Velocity.fromAngleAndSpeed(300, ballSpeed);
        } else if (collisionPoint.getY() == this.rec.getUpperLeft().getY()
                && collisionPoint.getX() == this.rec.getBottomRight().getX()) {
            v = Velocity.fromAngleAndSpeed(60, ballSpeed);
        } else if (insidePaddle(collisionPoint, v)) { //check sides
            this.notifyHit(hitter);
        } else if (collisionPoint.getX() > start && collisionPoint.getX() <= start + part) { //check top part.
            v = Velocity.fromAngleAndSpeed(300, ballSpeed);
        } else if (collisionPoint.getX() > start + part && collisionPoint.getX() <= start + (2 * part)) {
            v = Velocity.fromAngleAndSpeed(330, ballSpeed);
        } else if (collisionPoint.getX() > start + (2 * part) && collisionPoint.getX() <= start + (3 * part)) {
            v = new Velocity(v.getDX(), -v.getDY());
        } else if (collisionPoint.getX() > start + (3 * part) && collisionPoint.getX() <= start + (4 * part)) {
            v = Velocity.fromAngleAndSpeed(30, ballSpeed);
        } else if (collisionPoint.getX() > start + (4 * part) && collisionPoint.getX() < start + (5 * part)) {
            v = Velocity.fromAngleAndSpeed(60, ballSpeed);
        }
        return v;
    }

    @Override
    /**
     *
     * @param g a game object.
     * Add this paddle to the game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     *
     * @param game the game to remove from.
     * removes the paddle from the cretain game.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
        game.removeCollidable(this);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     *
     * @param hitter the hitting ball.
     * notifys all listeners that a hit occurred.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this.block, hitter);
        }
    }

    /**
     *
     * @return returns the center point of the top of the paddle.
     */
    public Point getCenter() {
        double x = this.rec.getUpperLeft().getX() + this.rec.getWidth();
        double y = this.rec.getUpperLeft().getY();
        Line l = new Line(this.rec.getUpperLeft(), new Point(x, y));
        return l.middle();
    }

    /**
     *
     * @param collisionPoint the point of collision.
     * @param velocity the hitting balls velocity.
     * @return returns true if the ball is inside the paddle, false otherwise.
     */
    public boolean insidePaddle(Point collisionPoint, Velocity velocity) {
        if (collisionPoint.getX() >= this.rec.getUpperLeft().getX()
                && collisionPoint.getX() <= this.rec.getBottomRight().getX()) { //check sides
            if (collisionPoint.getY() > this.rec.getUpperLeft().getY()
                    && collisionPoint.getY() <= this.rec.getBottomRight().getY() && velocity.getDY() < 0) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param hitter the hitting ball.
     */
    public void notify(Ball hitter) {
        notifyHit(hitter);
    }
}