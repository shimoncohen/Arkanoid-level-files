import biuoop.GUI;
import biuoop.Sleeper;
import biuoop.KeyboardSensor;
import java.io.File;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Executing class.
 */
public class Ass6Game {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    /**
     *
     * @param args the arguments to the main.
     * the main function.
     */
    public static void main(String[] args) {
        GUI gui = new GUI("Arkanoid", SCREEN_WIDTH, SCREEN_HEIGHT);
        Sleeper sleep = new Sleeper();
        AnimationRunner animationRunner = new AnimationRunner(gui, sleep);
        KeyboardSensor ks = gui.getKeyboardSensor();
        File f = new File("highscores");
        HighScoresTable highScoresTable = HighScoresTable.loadFromFile(f);
        String path;
        InputStream is = null;
        //go over parameters.
        for (int i = 0; i < args.length; i++) {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(args[i]);
            if (is != null) {
                break;
            }
        }
        if (is == null) {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream("definitions/default_sets.txt");
        }
        Reader r = null;
        try {
            r = new InputStreamReader(is);
        } catch (Exception e) {
            System.out.println("Error, cannot load file.");
            System.out.println("files may be corrupted or dosent exist.");
            System.exit(1);
        }
        BufferedReader bufferedReader = new BufferedReader(r);
        String[] info;
        MenuAnimation subMenu = new MenuAnimation<>("Level Sets", ks, animationRunner);
        Menu<Task> sMenu = subMenu;
        try {
            String temp = bufferedReader.readLine();
            while (temp != null) {
                while (temp.equals("")) {
                    temp = bufferedReader.readLine();
                }
                info = temp.split(":");
                path = bufferedReader.readLine();
                while (path.equals("")) {
                    path = bufferedReader.readLine();
                }
                sMenu.addSelection(info[0], info[1], new RunGameTask(animationRunner, highScoresTable, ks, gui,
                        f, path));
                temp = bufferedReader.readLine();
            }
            bufferedReader.close();
            is.close();
        } catch (IOException e) {
            e.getMessage();
        }
        MenuAnimation openingMenu = new MenuAnimation<String>("Main Menu", ks, animationRunner);
        Menu<Task> menu = openingMenu;
        sMenu.addSelection("b", "back to Main Menu", new Task() {
            @Override
            public Object run() {
                animationRunner.run(menu);
                Task<Void> task = menu.getStatus();
                task.run();
                openingMenu.resetStatus();
                return null;
            }
        });
        while (true) {
            menu.addSelection("s", "level sets", new Task<Void>() {
                @Override
                public Void run() {
                    while (true) {
                        animationRunner.run(sMenu);
                        Task<Void> task = sMenu.getStatus();
                        task.run();
                        subMenu.resetStatus();
                        return null;
                    }
                }
            });
            menu.addSelection("h", "Highscores", new RunTask(animationRunner,
                    new KeyPressStoppableAnimation(ks, "space", new HighScoresAnimation(highScoresTable, "s", ks))));
            menu.addSelection("q", "Quit", new CloseTask(gui, f, highScoresTable));
            animationRunner.run(menu);
            // wait for user selection
            Task<Void> task = menu.getStatus();
            task.run();
            openingMenu.resetStatus();
            openingMenu.reboot();
        }
    }
}