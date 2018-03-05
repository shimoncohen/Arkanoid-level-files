import java.awt.Color;

/**
 *
 */
public class ColorsParser {

    /**
     *
     * @param s the string indicating a color.
     * @return returns the correct color.
     * parse color definition and return the specified color.
     */
    public java.awt.Color colorFromString(String s) {
        s = s.toLowerCase();
        if (s.contains("rgb")) {
            String[] temp = s.replace("rgb(", "").replace(")", "").split(",");
            return new Color(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));
        }
        switch (s) {
            case "black":
                return Color.black;
            case "blue":
                return Color.blue;
            case "cyan":
                return Color.cyan;
            case "darkgray":
                return Color.darkGray;
            case "gray":
                return Color.gray;
            case "green":
                return Color.green;
            case "lightgray":
                return Color.lightGray;
            case "magenta":
                return Color.magenta;
            case "orange":
                return Color.orange;
            case "pink":
                return Color.pink;
            case "red":
                return Color.red;
            case "white":
                return Color.white;
            case "yellow":
                return Color.yellow;
            default:
                return Color.black;
        }
    }
}