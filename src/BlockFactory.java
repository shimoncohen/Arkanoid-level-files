import java.awt.Color;

/**
 * saves diffrent kind of blocks and thier definitions and creates them.
 */
public class BlockFactory implements BlockCreator {
    private Color border;
    private Object[] fill;
    private int hitPoints;
    private int height;
    private int width;

    /**
     *
     * @param fill the blocks fillings (images or colors).
     * @param height the blocks height.
     * @param hitPoints the blocks hitpoints.
     * @param width the blocks width.
     * constructor.
     */
    public BlockFactory(Object[] fill, int height, int hitPoints, int width) {
        this.height = height;
        this.width = width;
        this.border = null;
        this.fill = fill;
        this.hitPoints = hitPoints;
    }

    /**
     *
     * @param c the borders color.
     */
    public void setBorder(Color c) {
        this.border = c;
    }

    @Override
    public Block create(int xpos, int ypos) {
        Rectangle r = new Rectangle(new Point(xpos, ypos), this.width, this.height);
        if (this.fill[0].toString().contains("chess")) {
            ChessBlock block = new ChessBlock(r, this.fill[0].toString(), this.hitPoints);
            return block;
        } else {
            DestroyableBlock block = new DestroyableBlock(r, this.fill, this.hitPoints);
            block.setBorder(this.border);
            return block;
        }
    }
}
