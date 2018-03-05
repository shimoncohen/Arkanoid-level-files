import java.util.List;

/**
 * reads a level from a file.
 */
public class LevelSpecificationReader {

    /**
     *
     * @param reader the reader to read from.
     * @return returns a list of level informations.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        LevelsParser split = new LevelsParser(reader);
        List<LevelInformation> temp = (List<LevelInformation>) split.parse();
        if (temp == null) {
            return null;
        }
        return temp;
    }
}