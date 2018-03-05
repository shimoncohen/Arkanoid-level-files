import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @param <T>
 * a menu with options.
 */
public class MenuAnimation<T> implements Menu {
    private String title;
    private ArrayList<String> keys;
    private ArrayList<String> messages;
    private ArrayList<Object> returnVals;
    private KeyboardSensor keyboardSensor;
    private boolean running;
    private AnimationRunner animationRunner;
    private TreeMap<String, Menu> subMenuKeys;
    private Object status;

    /**
     * @param menuTitle the menus title.
     * @param ks Keyboardsensor object.
     * @param ar animationrunner.
     * constructor.
     */
    public MenuAnimation(String menuTitle, KeyboardSensor ks, AnimationRunner ar) {
        this.title = menuTitle;
        this.keys = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.returnVals = new ArrayList<>();
        this.keyboardSensor = ks;
        this.running = false;
        this.animationRunner = ar;
        this.subMenuKeys = new TreeMap<>();
        this.status = null;
    }

    @Override
    public Object getStatus() {
        return this.status;
    }

    /**
     * resets status to null.
     */
    public void resetStatus() {
        this.status = null;
    }

    @Override
    public void addSelection(String key, String message, Object returnVal) {
        this.keys.add(key);
        this.messages.add(message);
        this.returnVals.add(returnVal);
    }

    @Override
    public boolean shouldStop() {
        if (!this.running) {
            for (int i = 0; i < this.keys.size(); i++) {
                if (this.keyboardSensor.isPressed(this.keys.get(i))) {
                    this.status = this.returnVals.get(i);
                    this.running = true;
                }
            }
            return this.running;
        } else {
            this.running = false;
            this.status = null;
            return false;
        }
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.drawText(Ass6Game.SCREEN_WIDTH / 2 - 75, 60, this.title, 30);
        int y = 200, size = 20;
        for (int i = 0; i < this.messages.size(); i++) {
            d.drawText(100, y, this.keys.get(i) + ") " + this.messages.get(i), size);
            y += size + size / 2;
        }
        if (this.keyboardSensor.isPressed("space")) {
            return;
        }
    }

    @Override
    public void addSubMenu(String key, String message, Menu subMenu) {
        this.keys.add(key);
        this.messages.add(message);
        this.returnVals.add(new RunTask(this.animationRunner,
                new KeyPressStoppableAnimation(this.keyboardSensor, "space", subMenu)));
        this.subMenuKeys.put(key, subMenu);
    }

    /**
     * resets menu options.
     */
    public void reboot() {
        this.keys = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.returnVals = new ArrayList<>();
    }
}
