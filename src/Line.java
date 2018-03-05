import java.util.ArrayList;

/**
 * Line object with two Point objects as values.
 */
public class Line {
    private Point start;
    private Point end;

    /**
     *
     * @param start the start point of the line.
     * @param end the end point of the line.
     * constructor.
     * makes sure the start is the point with the smaller x value.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     *
     * @param x1 x value of first Point.
     * @param y1 y value of first Point.
     * @param x2 x value of second Point.
     * @param y2 y value of second Point.
     * constructor.
     * makes sure the start is the point with the smaller x value.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     *
     * @return Returns the length of the line.
     * gets the lines length.
     */
    public double length() {
        return start.distance(end);
    }

    /**
     *
     * @return Returns the middle point of the line.
     * gets thev lines middle.
     */
    public Point middle() {
        Point middle = new Point((this.start.getX() + this.end.getX()) / 2, (this.start.getY() + this.end.getY()) / 2);
        return middle;
    }

    /**
     *
     * @return Returns the start point of the line.
     * gets the lines start.
     */
    public Point start() {
        return this.start;
    }

    /**
     *
     * @return Returns the end point of the line.
     * gets the lines end.
     */
    public Point end() {
        return this.end;
    }

    /**
     *
     * @return Returns the lines slope.
     * returns POSITIVE_INFINITY if the line is vertical, 0.0 if the line is horizontal,
     * else calculates the lines slope and returns it.
     */
    private double getSlope() {
        if (this.end.getX() == this.start.getX()) {
            return Double.POSITIVE_INFINITY;
        } else if (this.end.getY() == this.start.getY()) {
            return 0.0;
        }
        return (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
    }

    /**
     *
     * @param l1 A Line object.
     * @return Returns the intersection point of the lines.
     * gets an intersection point between two lines.
     */
    private Point getIntersection(Line l1) {
        // Calculates the value of the numbers exept x in the equation.
        double free1;
        double free2;
        double x, y;
        //if this line has no slope.
        if (this.getSlope() == Double.POSITIVE_INFINITY) {
            //if l1 has 0.0 slope.
            if (l1.getSlope() == 0.0) {
                return new Point(Math.round(this.start().getX()), Math.round(l1.start().getY()));
            }
            y = solveY(l1.getSlope(), this.start().getX(), l1.start().getX(), l1.start().getY());
            return new Point(Math.round(this.start().getX()), Math.round(y));
        } else if (l1.getSlope() == Double.POSITIVE_INFINITY) { //if l1 has no slope.
            //if this line has 0.0 slope.
            if (this.getSlope() == 0.0) {
                return new Point(Math.round(l1.start().getX()), Math.round(this.start().getY()));
            }
            y = solveY(this.getSlope(), l1.start().getX(), this.start().getX(), this.start().getY());
            return new Point(Math.round(l1.start().getX()), Math.round(y));
        } else if (this.getSlope() == 0.0) { //if this line has 0.0 slope.
            free2 = l1.start.getY() - (l1.getSlope() * l1.start.getX());
            x = (this.start.getY() - free2) / l1.getSlope();
            return new Point(Math.round(x), Math.round(this.start.getY()));
        } else if (l1.getSlope() == 0.0) { //if l1 has 0.0 slope.
            free1 = this.start.getY() - (this.getSlope() * this.start.getX());
            x = (l1.start.getY() - free1) / this.getSlope();
            return new Point(Math.round(x), Math.round(l1.start.getY()));
        } else {
            //calculate intersection point.
            free1 = this.start.getY() - (this.getSlope() * this.start.getX());
            free2 = l1.start.getY() - (l1.getSlope() * l1.start.getX());
            double allFree = free1 - free2;
            double slopes = l1.getSlope() - this.getSlope();
            x = allFree / slopes;
            y = this.solveY(this.getSlope(), x, this.start.getX(), this.start.getY());
            return new Point(Math.round(x), Math.round(y));
        }
    }

    /**
     *
     * @param m the slope.
     * @param x intersections x value.
     * @param x1 lines start point x value.
     * @param y1 lines start point y value.
     * @return Returns the value of y.
     * solves y value of a cretain point.
     */
    private double solveY(double m, double x, double x1, double y1) {
        return m * (x - x1) + y1;
    }

    /**
     *
     * @param other A Line object.
     * @return Returns true if intersection is in bounds of the lines,
     * else otherwise (when both lines have slopes).
     */
    private boolean isInBounds(Line other) {
        Point p = this.getIntersection(other);
        double maxThisY = Math.round(Math.max(this.start.getY(), this.end.getY()));
        double minThisY = Math.round(Math.min(this.start.getY(), this.end.getY()));
        double maxOtherY = Math.round(Math.max(other.start.getY(), other.end.getY()));
        double minOtherY = Math.round(Math.min(other.start.getY(), other.end.getY()));
        double maxThisX = Math.round(Math.max(this.start.getX(), this.end.getX()));
        double minThisX = Math.round(Math.min(this.start.getX(), this.end.getX()));
        double maxOtherX = Math.round(Math.max(other.start.getX(), other.end.getX()));
        double minOtherX = Math.round(Math.min(other.start.getX(), other.end.getX()));
        //check if x and y of intersection are in the bounds of the lines.
        if (p.getX() <= maxThisX && p.getX() >= minThisX) {
            if (p.getX() <= maxOtherX && p.getX() >= minOtherX) {
                if (p.getY() <= maxThisY && p.getY() >= minThisY) {
                    if (p.getY() <= maxOtherY && p.getY() >= minOtherY) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     *
     * @param start1 start point of first line.
     * @param end1 end point of first line.
     * @param start2 start point of second line.
     * @param end2 end point of second line.
     * @param m the slope of the first line.
     * @param x the x value of the second line.
     * @return Returns true if intersection is in bounds of the lines,
     * else otherwise (when one of the lines does not have a slope).
     */
    private boolean isInBoundsNoSlope(Point start1, Point end1, Point start2, Point end2, double m, double x) {
        double max1Y = Math.max(end1.getY(), start1.getY());
        double min1Y = Math.min(end1.getY(), start1.getY());
        double max1X = Math.max(end1.getX(), start1.getX());
        double min1X = Math.min(end1.getX(), start1.getX());
        double max2Y = Math.max(end2.getY(), start2.getY());
        double min2Y = Math.min(end2.getY(), start2.getY());
        //if the first line is horizontal.
        if (start1.getY() == end1.getY()) {
            double x1 = start2.getX();
            double y1 = start1.getY();
            //check if intersection point is in lines bounds.
            if (x <= max1X && x >= min1X) {
                if (start1.getY() <= max1Y && start1.getY() >= min1Y) {
                    if (y1 >= min2Y && y1 <= max2Y) {
                        return true;
                    }
                }
            }
        }
        //find y value of intersection point.
        double y = this.solveY(m , x, start1.getX(), start1.getY());
        //check if intersection point is in lines bounds.
        if (x <= max1X && x >= min1X) {
            if (y <= max1Y && y >= min1Y) {
                if (y <= max2Y && y >= min2Y) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param other A Line object.
     * @return Returns true if the lines intersect, false otherwise.
     * checks if the lines are intersecting.
     */
    public boolean isIntersecting(Line other) {
        //if the lines have the same slope.
        if (this.getSlope() == other.getSlope()) {
            return false;
        }
        //if the other line has no slope.
        if (other.getSlope() == Double.POSITIVE_INFINITY) {
            if (this.isInBoundsNoSlope(this.start, this.end,
                    other.start, other.end, this.getSlope(), other.start.getX())) {
                return true;
            }
            return false;
        }
        //if this line has no slope.
        if (this.getSlope() == Double.POSITIVE_INFINITY) {
            if (this.isInBoundsNoSlope(other.start, other.end,
                    this.start, this.end, other.getSlope(), this.start.getX())) {
                return true;
            }
            return false;
        }
        //check if the intersection of both lines is in lines bounds.
        if (this.isInBounds(other)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param other A Line object.
     * @return Returns the intersection point if the lines intersect, and null otherwise.
     * gets the two lines intersection points.
     */
    public Point intersectionWith(Line other) {
        if (this.isIntersecting(other)) {
            Point p = this.getIntersection(other);
            //if lines intersect the return intersection point.
            return p;
        }
        return null;
    }

    /**
     *
     * @param rect A rectangle object.
     * @return Returns null if line does not intersect with rectangle,
     * otherwise returns closest intersection to the start of the line.
     *
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * start of the line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        ArrayList<Point> intersections = rect.intersectionPoints(this);
        if (intersections.size() == 0) {
            return null;
        } else if (intersections.size() == 1) {
            return intersections.get(0);
        } else {
            if (this.start().distance(intersections.get(0)) > this.start().distance(intersections.get(1))) {
                return intersections.get(1);
            } else {
                return intersections.get(0);
            }
        }
    }

    /**
     *
     * @param other A Line object.
     * @return Return true is the lines are equal, false otherwise.
     * checks if other line is equal to this line.
     */
    public boolean equals(Line other) {
        if ((this.start.equals(other.start())) && (this.end.equals(other.end()))) {
            return true;
        } else if ((this.start.equals(other.end())) && (this.end.equals(other.start()))) {
            return true;
        }
        return false;
    }
}