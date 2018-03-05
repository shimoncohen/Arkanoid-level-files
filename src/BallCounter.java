/**
 * a counter that counts the remaining balls.
 */
public class BallCounter extends Counter {
    private int ballsLeft;

    // add number to current count.
    @Override
    void increase(int number) {
        this.ballsLeft += number;
    }

    // subtract number from current count.
    @Override
    void decrease(int number) {
        this.ballsLeft -= number;
    }

    // get current count.
    @Override
    int getValue() {
        return this.ballsLeft;
    }
}
