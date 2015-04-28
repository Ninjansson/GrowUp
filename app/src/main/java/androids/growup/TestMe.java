package androids.growup;

/**
 * Created by Kim Jansson on 2015-04-28.
 */
public class TestMe {
    public boolean byThree(int check) {
        return (check % 3 == 0);
    }

    public boolean byFive(int check) {
        return (check % 5 == 0);
    }

    public boolean byThreeAndFive(int check) {
        return (check % 3 == 0) && (check % 5 == 0);
    }
}
