/**
 * in charge of notifying listeners that a hit occurred.
 */
public interface HitNotifier {

    /**
     *
     * @param hl a hitlistener to add.
     * Add hl as a listener to hit events.
     */
    void addHitListener(HitListener hl);

    /**
     *
     * @param hl a hitlistener to remove.
     * Remove hl from the list of listeners to hit events.
     */
    void removeHitListener(HitListener hl);
}