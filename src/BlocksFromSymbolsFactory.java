import java.util.Map;

/**
 * gets the definition of blocks and translates them to blocks with its creator.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     *
     * @param spacer the levels spacers.
     * @param block a map with a block symbol and its block creator.
     * constructor.
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacer, Map<String, BlockCreator> block) {
        this.spacerWidths = spacer;
        this.blockCreators = block;
    }

    /**
     *
     * @param s a symbol.
     * @return returns true if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String s) {
        if (s.equals(" ")) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param s a symbol.
     * @return returns true if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String s) {
        if (this.blockCreators.containsKey(s)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param s a symbol.
     * @return returns true if 's' is a valid seperator symbol.
     */
    public boolean isSeperatorSymbol(String s) {
        if (this.spacerWidths.containsKey(s)) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param s the blocks symbol.
     * @param xpos the x value of the blocks starting point.
     * @param ypos the y value of the blocks starting point.
     * @return return a block according to the definitions associated with symbol s.
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }

    /**
     *
     * @param s a spacer symbol.
     * @return returns the width in pixels associated with the given spacer-symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}