import biuoop.DrawSurface;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.InputStream;
import java.io.IOException;

/**
 * a image sprite.
 */
public class ImageSprite implements Sprite {
    private Image image;
    private int x;
    private int y;

    /**
     *
     * @param i the file to read the image from.
     * @param x the x position to display the image at.
     * @param y the y position to display the image at.
     * constructor.
     */
    public ImageSprite(String i, int x, int y) {
        try {
            i = i.replace("image(", "").replace(")", "");
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(i);
            this.image = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.getMessage();
        }
        this.x = x;
        this.y = y;
    }

    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void timePassed(double dt) {

    }

    @Override
    public void drawOn(DrawSurface d) {
        d.drawImage(this.x, this.y, this.image);
    }
}