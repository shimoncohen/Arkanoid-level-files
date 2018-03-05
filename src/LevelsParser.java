import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * translates written files to level objects.
 */
public class LevelsParser {
    private Reader reader;
    private LinkedList<Block> blocks;

    /**
     *
     * @param r the reader to read the levels information from.
     * constructor.
     */
    public LevelsParser(Reader r) {
        this.reader = r;
        this.blocks = new LinkedList<>();
    }

    /**
     *
     * @return returns a list of levels informations.
     */
    public Object parse() {
        BufferedReader bufferedReader = new BufferedReader(this.reader);
        String temp;
        int i = 0;
        ArrayList<String[]> levs = new ArrayList<>();
        LinkedList<LinkedList<String>> blockLists = new LinkedList<>();
        try {
            String[] lines = new String[11];
            temp = bufferedReader.readLine();
            while (temp != null) {
                while (temp != null && !temp.equals("END_LEVEL")) {
                    if (temp == null) {
                        return null;
                    }
                    if (temp.equals("START_LEVEL") || temp.equals("") || temp.startsWith("#")) {
                        temp = bufferedReader.readLine();
                        continue;
                    }
                    if (temp.equals("START_BLOCKS")) {
                        //blockList = getBlockList(bufferedReader);
                        blockLists.add(getBlockList(bufferedReader));
                        temp = bufferedReader.readLine();
                        continue;
                    }
                    if (i < lines.length) {
                        lines[i] = temp;
                        i++;
                    } else {
                        return null;
                    }
                    temp = bufferedReader.readLine();
                    while (temp != null && temp.equals("")) {
                        temp = bufferedReader.readLine();
                    }
                }
                if (lines[0] != null) {
                    levs.add(lines);
                }
                lines = new String[11];
                i = 0;
                temp = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.getMessage();
        }
        List<LevelInformation> t = turnToLevels(levs, blockLists);
        if (t == null) {
            return null;
        }
        return t;
    }

    /**
     *
     * @param lines the levels details.
     * @param a the levels blocks list.
     * @return returns a list of levels informations.
     */
    private List<LevelInformation> turnToLevels(ArrayList<String[]> lines, LinkedList<LinkedList<String>> a) {
        List<LevelInformation> temp = new LinkedList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (sortInformation(lines.get(i), a.get(i)) == null) {
                return null;
            }
            temp.add(sortInformation(lines.get(i), a.get(i)));
        }
        return temp;
    }

    /**
     *
     * @param info the levels information.
     * @param a the levels blocks.
     * @return returns a level information.
     */
    private LevelInformation sortInformation(String[] info, LinkedList<String> a) {
        Object[] temp = new Object[10];
        LevelInfoFromFile levelInfoFromFile = new LevelInfoFromFile();
        for (int i = 0; i < temp.length; i++) {
            if (levelInfoFromFile.infoParse(info[i]) == null) {
                return null;
            }
            temp[i] = levelInfoFromFile.infoParse(info[i]);
        }
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(temp[5].toString());
        Reader read = new InputStreamReader(inputStream);
        BlocksFromSymbolsFactory blocksFromSymbolsFactory = BlocksDefinitionReader.fromReader(read);
        if (blocksFromSymbolsFactory == null) {
            System.out.println("blocks file is corrupted");
            System.out.println("a block or spacer definition may be missing crucial information");
            System.exit(1);
        }
        this.blocks = getBlocks(a, blocksFromSymbolsFactory, temp);
        Level level = new Level((ArrayList<Velocity>) temp[1], temp[2].toString(),
                Double.parseDouble(temp[3].toString()), Double.parseDouble(temp[4].toString()), this.blocks);
        level.setName(temp[0].toString().trim());
        try {
            read.close();
            inputStream.close();
        } catch (IOException e) {
            e.getMessage();
        }
        return level;
    }

    /**
     *
     * @param bufferedReader the reader to read the levels blocks from.
     * @return returns a list of the levels blocks.
     */
    private LinkedList<String> getBlockList(BufferedReader bufferedReader) {
        LinkedList<String> blockList = new LinkedList<>();
        String temp;
        try {
            temp = bufferedReader.readLine();
            while (!(temp).equals("END_BLOCKS")) {
                if (!temp.equals("")) {
                    blockList.add(temp);
                }
                temp = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return blockList;
    }

    /**
     *
     * @param a the blocks list.
     * @param bfsf the blocks definitions and creators.
     * @param tempVals the x value of the blocks starting point.
     * @return returns a list of the levels blocks.
     */
    private LinkedList<Block> getBlocks(LinkedList<String> a, BlocksFromSymbolsFactory bfsf, Object[] tempVals) {
        LinkedList<Block> temp = new LinkedList<>();
        int x = (int) ((double) tempVals[6]), y = 0;
        //if ()
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals("-")) {
                y += bfsf.getSpaceWidth("-");
            } else if (a.get(i).equals("")) {
                continue;
            } else {
                if (i == 0) {
                    y += (int) ((double) tempVals[7]);
                }
                String[] temp1 = a.get(i).replace(" ", "").replace("\t", "").split("");
                for (int j = 0; j < temp1.length; j++) {
                    if (bfsf.isSeperatorSymbol(temp1[j])) {
                        x += bfsf.getSpaceWidth(temp1[j]);
                    } else {
                        if (bfsf.isBlockSymbol(temp1[j])) {
                            temp.add(bfsf.getBlock(temp1[j], x, y));
                            x += temp.getLast().getRec().getWidth();
                        } else {
                            System.out.println("Missing block definition!");
                            System.out.println("block " + temp1[j] + " not defined");
                            System.exit(1);
                        }
                    }
                }
                y += temp.getLast().getRec().getHeight();
                x = (int) ((double) tempVals[6]);
            }
        }
        return temp;
    }
}
