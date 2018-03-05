import java.io.BufferedReader;
import java.util.Map;
import java.util.TreeMap;

/**
 * gets the definition of blocks and translates them to blocks in the block factory.
 */
public class BlocksDefinitionReader {

    /**
     *
     * @param reader the reader to read the blocks from.
     * @return returns a block factory. if failed returns null.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        BlocksDefinitionReader blocksDefinitionReader = new BlocksDefinitionReader();
        String line;
        Map<String, String> defaultVals = new TreeMap<>();
        Map<String, Map<String, String>> spacers = new TreeMap<>();
        Map<String, Map<String, String>> blocks = new TreeMap<>();
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("default")) {
                    defaultVals.putAll(blocksDefinitionReader.extractDefault(line, "default"));
                } else if (line.startsWith("bdef")) {
                    blocks.putAll(blocksDefinitionReader.extract(line, "bdef"));
                } else if (line.startsWith("sdef")) {
                    spacers.putAll(blocksDefinitionReader.extract(line, "sdef"));
                }
            }
            if (!defaultVals.isEmpty()) {
                for (String key:blocks.keySet()) {
                    Map<String, String> tempArray = blocks.get(key);
                    for (String defaultKey : defaultVals.keySet()) {
                        if (!tempArray.containsKey(defaultKey)) {
                            blocks.get(key).put(defaultKey, defaultVals.get(defaultKey));
                        }
                    }
                }
            }
            Map<String, BlockCreator> creator = blocksDefinitionReader.generateBlockMap(blocks);
            Map<String, Integer> sp = blocksDefinitionReader.extractIntegerVals(spacers);
            BlocksFromSymbolsFactory blocksFromSymbolsFactory = new BlocksFromSymbolsFactory(sp, creator);
            bufferedReader.close();
            return blocksFromSymbolsFactory;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    /**
     *
     * @param line the line to read from.
     * @param replace the string to replace.
     * @return returns a map with the blocks default values.
     */
    private Map<String, String> extractDefault(String line, String replace) {
        Map<String, String> map = new TreeMap<>();
        String[] temp = line.replace(replace, "").trim().split(" ");
        for (int i = 0; i < temp.length; i++) {
            String[] def = temp[i].split(":");
            map.put(def[0], def[1]);
        }
        return map;
    }

    /**
     *
     * @param line the line to read from.
     * @param replace the string to replace.
     * @return returns a map with the blocks override values.
     */
    private Map<String, Map<String, String>> extract(String line, String replace) {
        Map<String, Map<String, String>> wholeMap = new TreeMap<>();
        Map<String, String> map = new TreeMap<>();
        String temp1 = line.replace(replace, "").trim().replace("\t", "");
        String[] temp = temp1.split(" ");
        String key = temp[0].replace("\t", "");
        temp = temp1.replace(key, "").trim().split(" ");
        for (int i = 0; i < temp.length; i++) {
            String[] def = temp[i].split(":");
            map.put(def[0], def[1]);
        }
        wholeMap.put(key.replace("symbol:", ""), map);
        return wholeMap;
    }

    /**
     *
     * @param map a map of information and its value in string form.
     * @return returns a map of information and its value.
     */
    public Map<String, Integer> extractIntegerVals(Map<String, Map<String, String>> map) {
        Map<String, Integer> temp = new TreeMap<>();
        for (String key:map.keySet()) {
            for (String innerKey:map.get(key).keySet()) {
                temp.put(key, Integer.parseInt(map.get(key).get(innerKey)));
            }
        }
        return temp;
    }

    /**
     *
     * @param map a map with a blocks key and its values in a second map as its value.
     * @return returns a map with a blocks key and its creator.
     */
    public Map<String, BlockCreator> generateBlockMap(Map<String, Map<String, String>> map) {
        Map<String, BlockCreator> temp = new TreeMap<>();
        for (String key:map.keySet()) {
            String[] fillings = new String[Integer.parseInt(map.get(key).get("hit_points"))];
            ColorsParser colorsParser = new ColorsParser();
            int j = 0;
            for (int k = 0; k < fillings.length; k++) {
                fillings[k] = map.get(key).get("fill");
            }
            for (int k = 0; k < fillings.length; k++) {
                if (map.get(key).keySet().contains("fill-" + Integer.toString(k + 1))) {
                    fillings[k] = map.get(key).get("fill-" + Integer.toString(k + 1));
                }
            }
            BlockFactory blockFactory = new BlockFactory(fillings, Integer.parseInt(map.get(key).
                    get("height")), Integer.parseInt(map.get(key).get("hit_points")),
                    Integer.parseInt(map.get(key).get("width")));
            try {
                blockFactory.setBorder(colorsParser.colorFromString(map.get(key).get("stroke")));
            } catch (Exception e) {
                e.getMessage();
            }
            temp.put(key, blockFactory);
        }
        return temp;
    }
}