import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * extracts info from a file.
 */
public class LevelInfoFromFile {

    /**
     *
     * @param info the info to extract from.
     * @return returns the correct inforation for every object.
     * extracts info from file.
     */
    public Object infoParse(String info) {
        if (info == null) {
            return null;
        }
        String[] line = info.split(":");
        if (line[0].equals("ball_velocities")) {
            return sortVelocitys(line[1]);
        }
        if (line[0].equals("level_name")) {
            return line[1];
        }
        if (line[0].equals("background")) {
            return line[1];
        }
        if (line[0].equals("block_definitions")) {
            return new File(line[1]);
        }
        try {
            double num = Double.parseDouble(line[1]);
            if (num <= 0) {
                return null;
            }
            return num;
        } catch (Exception e) {
            return line[0];
        }
    }

    /**
     *
     * @param velList the list of velocities.
     * @return returns a list of the balls velocities.
     */
    private List<Velocity> sortVelocitys(String velList) {
        List<Velocity> velocities = new ArrayList<>();
        String[] temp = velList.split(" ");
        for (int i = 0; i < temp.length; i++) {
            String[] vel = temp[i].split(",");
            velocities.add(Velocity.fromAngleAndSpeed(Double.parseDouble(vel[0]), Double.parseDouble(vel[1])));
        }
        return velocities;
    }
}
