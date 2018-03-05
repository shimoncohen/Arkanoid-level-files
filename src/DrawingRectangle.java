import biuoop.DrawSurface;
import java.awt.Color;

/**
 * a rectangle sprite.
 */
public class DrawingRectangle implements Sprite {
    private int height;
    private int width;
    private Point upperLeft;
    private Color color;

    /**
     *
     * @param upperLeft starting point of rectangle.
     * @param width width of rectangle.
     * @param height height of rectangle.
     * @param c the rectangles color.
     * constructor.
     */
    public DrawingRectangle(Point upperLeft, int width, int height, Color c) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.color = c;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.upperLeft.getX(), (int) this.upperLeft.getY(), this.width, this.height);
    }

    @Override
    public void timePassed(double dt) {
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     *
     * @return returns the upper left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     *
     * @return returns the width of the rectangle.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     *
     * @return returns the height of the rectangle.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     *
     * @param x a new x value.
     * sets the upper left points x value.
     */
    public void setX(double x) {
        this.upperLeft = new Point(x, this.upperLeft.getY());
    }

    /**
     *
     * @param y a new y value.
     * sets the upper left points y value.
     */
    public void setY(double y) {
        this.upperLeft = new Point(this.upperLeft.getX(), y);
    }
}
