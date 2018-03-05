/**
 * Velocity specifies the change in position on the `x` and the `y`.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * constructor.
     */
    public Velocity() {
        this.dx = 0.5;
        this.dy = 1;
    }

    /**
     *
     * @param dX the change in x.
     * @param dY the change in y.
     * constructor.
     */
    public Velocity(double dX, double dY) {
        this.dx = dX;
        this.dy = dY;
    }

    /**
     * @param angle the angle of the balls movement.
     * @param speed the speed of the ball.
     * @return new Velocity.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.round(speed * Math.sin(Math.toRadians(angle)));
        double dy = Math.round(speed * (-1) * Math.cos(Math.toRadians(angle)));
        return new Velocity(dx, dy);
    }

    /**
     *
     * @return Returns the velocitys angle according to dx and dy values.
     * calculates the angle according to the velocity.
     */
    public double calculateAngle() {
        if (this.dx == 0) {
            return 0;
        }
        return this.dy / this.dx;
    }

    /**
     *
     * @param p a Point.
     * @return Returns a new point after dx and dy are applied to it.
     * Take a point with position (x,y) and return a new point
     * with position (x+dx, y+dy).
     */
    public Point applyToPoint(Point p) {
        Point p1 = new Point(p.getX() + this.dx, p.getY() + this.dy);
        return p1;
    }

    /**
     *
     * @return Returns DX value.
     * gets the velocitys dx value.
     */
    public double getDX() {
        return this.dx;
    }

    /**
     *
     * @return Returns DY value.
     * gets the velocitys dy value.
     */
    public double getDY() {
        return this.dy;
    }

    /**
     *
     * @return returns the speed according to the velocity.
     * Returns the speed according to the velocity.
     */
    public double speed() {
        return Math.sqrt((this.dx * this.dx) + (this.dy * this.dy));
    }

    /**
     *
     * @param dX The new dx value for the velocity.
     * sets the velocity dx value.
     */
    public void setDX(double dX) {
        this.dx = dX;
    }

    /**
     *
     * @param dY The new dy value for the velocity.
     * sets the velocity dy value.
     */
    public void setDY(double dY) {
        this.dy = dY;
    }
}