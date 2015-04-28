package androids.test;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

import androids.growup.TestMe;

/**
 * Created by kim.jansson on 2015-04-28.
 */
public class TestMeTest extends TestCase {
    TestMe testMe;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void isByThree() {
        assertEquals(true, testMe.byThree(9));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
