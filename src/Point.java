/**
 * Point object with x and y values.
 */
public class Point {
    private double x;
    private double y;

    /**
     * @param x the x value of the point.
     * @param y the y value of the point.
     * constructor
     */
    public Point(double x , double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param other A diffrent Point objext.
     * @return distance -- return the distance of this point to the other point
     * calculates the distance between this point and other point.
     */
    public double distance(Point other) {
        if (other == null) {
            return Double.MAX_VALUE;
        }
        return Math.sqrt(((other.getX() - this.x) * (other.getX() - this.x))
                + ((other.getY() - this.y) * (other.getY() - this.y)));
    }

    /**
     * @return Return the x value of this point.
     * gets this points x.
     */
    public double getX() {
        return Math.round(this.x);
    }

    /**
     * @return Return the y value of this point.
     * gets this points y.
     */
    public double getY() {
        return Math.round(this.y);
    }

    /**
     * @param p a point.
     * @return Returns true if points are equal, false otherwise
     * equals -- return true is the points are equal, false otherwise.
     * checks if this point is equal to another point.
     */
    public boolean equals(Point p) {
        if ((this.x == p.getX()) && (this.y == p.getY())) {
            return true;
        }
        return false;
    }
}