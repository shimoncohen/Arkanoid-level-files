/**
 * in charge of counting.
 */
public class Counter {
    private int counting;

    /**
     *
     * @param number the number to add.
     * add number to current count.
     */
    void increase(int number) {
        this.counting += number;
    }

    /**
     *
     * @param number the number to subtract.
     * subtract number from current count.
     */
    void decrease(int number) {
        this.counting -= number;
    }

    /**
     *
     * @return returns the current count.
     */
    int getValue() {
        return this.counting;
    }
}