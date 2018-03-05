/**
 *
 * @param <T>
 */
public interface Menu<T> extends Animation {

    /**
     *
     * @param key the selections key.
     * @param message the selections message.
     * @param returnVal the selections value.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     *
     * @return returns the menus status.
     */
    T getStatus();

    /**
     *
     * @param key the selections key.
     * @param message the selections message.
     * @param subMenu the inner menu to show.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
}