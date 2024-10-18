import java.util.Random;

public class InputDevice {
    private Random random;

    public InputDevice() {
        this.random = new Random();
    }

    public String getType() {
        return "random";
    }

    public Integer nextInt() {
        return random.nextInt(100) + 1;
    }

    public String getLine() {
        return "The quick brown fox jumps over the lazy dog.";
    }

    public Integer[] getNumbers (int N) {
        Integer[] numArray = new Integer[N];
        for (int i = 0; i< N; i++) {
            numArray[i] = nextInt();
        }
        return numArray;
    }
}
