package androids.growup;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

public class TestMeTest extends TestCase {

    TestMe testMe = new TestMe();

    public void testByThree() throws Exception {
        assertEquals(true, testMe.byThree(30));
    }

    public void testByFive() throws Exception {
        assertEquals(true, testMe.byThree(30));
        assertEquals(false, testMe.byThree(40));
    }

    public void testbyThreeAndFive() throws Exception {
        assertEquals(true, testMe.byThree(30));
    }
}