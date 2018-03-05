import java.util.ArrayList;

/**
 * A rectangle.
 */
public class Rectangle {
    private double height;
    private double width;
    private Point upperLeft;

    // Create a new rectangle with location and width/height.
    /**
     *
     * @param upperLeft starting point of rectangle.
     * @param width width of rectangle.
     * @param height height of rectangle.
     * constructor.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     *
     * @param line A line object.
     * @return An array of the lines intersection points with the rectangle.
     *
     * Return a (possibly empty) List of intersection points with the specified line.
     */
    public ArrayList<Point> intersectionPoints(Line line) {
        ArrayList<Point> intersections = new ArrayList();
        Point topLeft = this.getUpperLeft();
        Point bottomRight = this.getBottomRight();
        Point topRight = new Point(bottomRight.getX(), topLeft.getY());
        Point bottomLeft = new Point(topLeft.getX(), bottomRight.getY());
        Line l1 = new Line(topLeft, topRight);
        Line l2 = new Line(bottomLeft, bottomRight);
        Line l3 = new Line(topRight, bottomRight);
        Line l4 = new Line(topLeft, bottomLeft);
        if (line.isIntersecting(l1)) { // checks if the line intersects with rectangles top line.
            intersections.add(line.intersectionWith(l1));
        }
        if (line.isIntersecting(l2)) { // checks if the line intersects with rectangles bottom line.
            intersections.add(line.intersectionWith(l2));
        }
        if (line.isIntersecting(l3)) { // checks if the line intersects with rectangles right line.
            intersections.add(line.intersectionWith(l3));
        }
        if (line.isIntersecting(l4)) { // checks if the line intersects with rectangles left line.
            intersections.add(line.intersectionWith(l4));
        }
        return intersections;
    }

    /**
     *
     * @return returns the width of the rectangle.
     * gets the rectangles width.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     *
     * @return return the height of the rectangle.
     * gets the rectangles height.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     *
     * @return returns the upper-left point of the rectangle.
     * gets the upper left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     *
     * @return returns the bottom-right point of the rectangle.
     * gets the bottom right point of the rectangle.
     */
    public Point getBottomRight() {
        double x = this.upperLeft.getX() + this.width;
        double y = this.upperLeft.getY() + this.height;
        return new Point(x, y);
    }

    /**
     *
     * @param newP a Point object.
     * sets the rectangles position to a given point.
     */
    public void setPosition(Point newP) {
        this.upperLeft = newP;
    }
}