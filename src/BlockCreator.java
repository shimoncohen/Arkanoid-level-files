/**
 *
 */
public interface BlockCreator {
    // Create a block at the specified location.

    /**
     *
     * @param xpos the x value of the blocks starting point.
     * @param ypos the y value of the blocks starting point.
     * @return returns a new block.
     */
    Block create(int xpos, int ypos);
}