/**
 *
 * @param <T>
 */
public interface Task<T> {
    /**
     *
     * @return runs the task.
     */
    T run();
}