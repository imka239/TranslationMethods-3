import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class Main extends org.junit.Assert {
    public static void main(String [] args) {
        final Result result = new JUnitCore().run(Tests.class);
        if (result.wasSuccessful()) {
            System.out.println("Tests passed!");
            System.exit(0);
        } else {
            System.out.println("Tests failed!");
            System.exit(1);
        }
    }
}
