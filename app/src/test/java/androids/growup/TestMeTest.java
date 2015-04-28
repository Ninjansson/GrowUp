package androids.growup;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

public class TestMeTest extends TestCase {

    TestMe testMe = new TestMe();

    @SmallTest
    public void testByThree() throws Exception {
        assertEquals(true, testMe.byThree(30));
    }

    @SmallTest
    public void testByFive() throws Exception {
        assertEquals(true, testMe.byThree(30));
    }

    @SmallTest
    public void testbyThreeAndFive() throws Exception {
        assertEquals(true, testMe.byThree(30));
    }
}