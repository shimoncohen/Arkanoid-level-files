import biuoop.DrawSurface;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * a chess peace block.
 */
public class ChessBlock extends Block {
    private Rectangle rec;
    private int hits;
    private List<HitListener> hitListeners;
    private Image picture;
    private String pictureString;
    private boolean towerMovement;
    private boolean horseMovement;

    /**
     *
     * @param r the blocks rectangle.
     * @param pic the blocks picture.
     * @param hitPoints the blocks hit points.
     * constructor.
     */
    public ChessBlock(Rectangle r, String pic, int hitPoints) {
        this.rec = r;
        this.hits = hitPoints;
        this.picture = extractPicture(pic);
        this.hitListeners = new LinkedList<>();
    }

    @Override
    public Rectangle getRec() {
        return this.rec;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.getRec();
    }

    @Override
    public void drawOn(DrawSurface surface) {
        surface.drawImage((int) this.rec.getUpperLeft().getX(), (int) this.rec.getUpperLeft().getY(), this.picture);
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Velocity v = currentVelocity;
        if (this.hits > 0) {
            this.hits -= 1;
        }
        if (collisionPoint == null) {
            return v;
        }
        //check if collision occured on the edge of the block.
        if (collisionPoint.getY() == this.rec.getUpperLeft().getY() && collisionPoint.getX()
                == this.rec.getUpperLeft().getX()) {
            //if a corner was hit and DX is positive and DY is negative,change DX.
            //else change DY.
            if (currentVelocity.getDY() < 0 && currentVelocity.getDX() > 0) {
                v = new Velocity(-currentVelocity.getDX(), currentVelocity.getDY());
            } else {
                v = new Velocity(currentVelocity.getDX(), -currentVelocity.getDY());
            }
        } else if (collisionPoint.getY() == this.rec.getUpperLeft().getY() && collisionPoint.getX()
                == this.rec.getBottomRight().getX()) {
            //if a corner was hit and DX is negative and DY is negative,change DX.
            //else change DY.
            if (currentVelocity.getDY() < 0 && currentVelocity.getDX() < 0) {
                v = new Velocity(-currentVelocity.getDX(), currentVelocity.getDY());
            } else {
                v = new Velocity(currentVelocity.getDX(), -currentVelocity.getDY());
            }
        } else if (collisionPoint.getY() == this.rec.getBottomRight().getY() && collisionPoint.getX()
                == this.rec.getUpperLeft().getX()) {
            //if a corner was hit and DX is positive and DY is positive,change DX.
            //else change DY.
            if (currentVelocity.getDY() > 0 && currentVelocity.getDX() > 0) {
                v = new Velocity(-currentVelocity.getDX(), currentVelocity.getDY());
            } else {
                v = new Velocity(currentVelocity.getDX(), -currentVelocity.getDY());
            }
        } else if (collisionPoint.getY() == this.rec.getBottomRight().getY() && collisionPoint.getX()
                == this.rec.getBottomRight().getX()) {
            //if a corner was hit and DX is negative and DY is positive,change DX.
            //else change DY.
            if (currentVelocity.getDY() > 0 && currentVelocity.getDX() < 0) {
                v = new Velocity(-currentVelocity.getDX(), currentVelocity.getDY());
            } else {
                v = new Velocity(currentVelocity.getDX(), -currentVelocity.getDY());
            }
        } else if (collisionPoint.getY() == this.rec.getUpperLeft().getY()) { //if collision occurs on top line.
            v = new Velocity(currentVelocity.getDX(), -currentVelocity.getDY());
        } else if (collisionPoint.getY() == this.rec.getBottomRight().getY()) { //if collision occurs on bottom line.
            v = new Velocity(currentVelocity.getDX(), -currentVelocity.getDY());
        } else if (collisionPoint.getX() == this.rec.getUpperLeft().getX()) { //if collision occurs on left line.
            v = new Velocity(-currentVelocity.getDX(), currentVelocity.getDY());
        } else if (collisionPoint.getX() == this.rec.getBottomRight().getX()) { //if collision occurs on right line.
            v = new Velocity(-currentVelocity.getDX(), currentVelocity.getDY());
        }
        this.notifyHit(hitter);
        movePiece();
        return v;
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     *
     * @param hitter the hitting ball.
     * notifys all listeners that a hit occurred.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     *
     * @return returns the blocks hit points.
     */
    public int getHitPoints() {
        return this.hits;
    }

    /**
     *
     * @param s the string containing the blocs pictures path.
     * @return returns the blocks pucture.
     */
    private Image extractPicture(String s) {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(s
                .replace("image(", "").replace(")", ""));
        Image image = null;
        try {
            this.pictureString = s.replace("image(", "").replace(")", "");
            image = ImageIO.read(inputStream);
        } catch (Exception e) {
            e.getMessage();
        }
        return image;
    }

    /**
     * the rules of a chess blocks movement when hit.
     */
    private void movePiece() {
        if (this.pictureString.contains("soldier")) {
            this.rec = new Rectangle(new Point(this.rec.getUpperLeft().getX(), this.rec.getUpperLeft().getY()
                    + this.rec.getHeight()), this.rec.getWidth(), rec.getHeight());
        } else if (this.pictureString.contains("runner")) {
            this.rec = new Rectangle(new Point(this.rec.getUpperLeft().getX() + this.rec.getWidth(),
                    this.rec.getUpperLeft().getY() + this.rec.getHeight()), this.rec.getWidth(), rec.getHeight());
        } else if (this.pictureString.contains("tower")) {
            if (this.rec.getBottomRight().getX() + 2 * this.rec.getWidth() >= Ass6Game.SCREEN_WIDTH) {
                this.towerMovement = false;
            } else if (this.rec.getUpperLeft().getX() - 2 * this.rec.getWidth() <= 0) {
                this.towerMovement = true;
            }
            if (!this.towerMovement) {
                this.rec = new Rectangle(new Point(this.rec.getUpperLeft().getX() - 2 * this.rec.getWidth(),
                        this.rec.getUpperLeft().getY()), this.rec.getWidth(), rec.getHeight());
            } else {
                this.rec = new Rectangle(new Point(this.rec.getUpperLeft().getX() + 2 * this.rec.getWidth(),
                        this.rec.getUpperLeft().getY()), this.rec.getWidth(), rec.getHeight());
            }
        } else if (this.pictureString.contains("horse")) {
            if (this.rec.getBottomRight().getX() + this.rec.getWidth() >= Ass6Game.SCREEN_WIDTH) {
                this.horseMovement = false;
            } else if (this.rec.getUpperLeft().getX() - this.rec.getWidth() <= 0) {
                this.horseMovement = true;
            }
            if (!this.horseMovement) {
                this.rec = new Rectangle(new Point(this.rec.getUpperLeft().getX() - this.rec.getWidth(),
                        this.rec.getUpperLeft().getY() + 2 * this.rec.getHeight()), this.rec.getWidth(),
                        this.rec.getHeight());
            } else {
                this.rec = new Rectangle(new Point(this.rec.getUpperLeft().getX() + this.rec.getWidth(),
                        this.rec.getUpperLeft().getY() + 2 * this.rec.getHeight()), this.rec.getWidth(),
                        this.rec.getHeight());
            }
        }
    }
}